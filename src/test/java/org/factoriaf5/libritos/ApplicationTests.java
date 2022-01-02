package org.factoriaf5.libritos;

import org.factoriaf5.libritos.repositories.Book;
import org.factoriaf5.libritos.repositories.BookRepository;
import org.factoriaf5.libritos.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser
    void loadsTheHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Autowired
    BookRepository bookRepository;

    @Test
    @WithMockUser
    void returnsTheExistingBooks() throws Exception {

        Book book = bookRepository.save(new Book("Harry Potter and the Philosopher's Stone", "https://www.gog.com/games?page=1&sort=popularity", "J.K. Rowling", "fantasy"));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/all"))
                .andExpect(model().attribute("books", hasItem(book)))
                .andExpect(model().attribute("categories", hasItems(
                hasProperty("name", is("Essay")),
                hasProperty("name", is("Fantasy")),
                hasProperty("name", is("Software"))
        )));
    }

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    @WithMockUser
    void returnsAFormToAddNewBooks() throws Exception {
        mockMvc.perform(get("/books/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/edit"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attribute("title", "Create new book"))
                .andExpect(model().attribute("categories", hasItems(
                        hasProperty("name", is("Essay")),
                        hasProperty("name", is("Fantasy")),
                        hasProperty("name", is("Software"))
                )));
    }

    @Test
    @WithMockUser
    void allowsToCreateANewBook() throws Exception {
        mockMvc.perform(post("/books/new")
                        .with(csrf())
                        .param("title", "Harry Potter and the Philosopher's Stone")
                        .param("author", "J.K. Rowling")
                        .param("category", "fantasy")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        List<Book> existingBooks = (List<Book>) bookRepository.findAll();
        assertThat(existingBooks, contains(allOf(
                hasProperty("title", equalTo("Harry Potter and the Philosopher's Stone")),
                hasProperty("author", equalTo("J.K. Rowling")),
                hasProperty("category", equalTo("fantasy"))
        )));
    }
    @Test
    @WithMockUser
    void returnsAFormToEditBooks() throws Exception {
        Book book = bookRepository.save(new Book("Harry Potter and the Philosopher's Stone", "https://www.gog.com/games?page=1&sort=popularity", "J.K. Rowling", "fantasy"));
        mockMvc.perform(get("/books/edit/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("books/edit"))
                .andExpect(model().attribute("book", book))
                .andExpect(model().attribute("title", "Edit book"))
                .andExpect(model().attribute("categories", hasItems(
                hasProperty("name", is("Essay")),
                hasProperty("name", is("Fantasy")),
                hasProperty("name", is("Software"))
        )));
    }

    @Test
    @WithMockUser
    void allowsToDeleteABook() throws Exception {
        Book book = bookRepository.save(new Book("Harry Potter and the Philosopher's Stone", "https://www.gog.com/games?page=1&sort=popularity", "J.K. Rowling", "fantasy"));
        mockMvc.perform(get("/books/delete/" + book.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        assertThat(bookRepository.findById(book.getId()), equalTo(Optional.empty()));
    }
    //Ejemplo de test que comprueba que los usuarios no autorizados no pueden crear nuevos libros (sin WithMockUser)
    //se debe repetir con cada uno que sea necesario yque redirija a "http://localhost/login")
    @Test
    void anonymousUsersCannotCreateABook() throws Exception {
        mockMvc.perform(post("/books/new")
                        .param("title", "Harry Potter and the Philosopher's Stone")
                        .param("author", "J.K. Rowling")
                        .param("category", "fantasy")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
    @Test
    @WithMockUser
    void allowsToSearchBooksByTitle() throws Exception {

        Book bookWithWord = bookRepository.save(new Book("Harry Potter and the Philosopher's Stone", "https://www.gog.com/games?page=1&sort=popularity", "J.K. Rowling", "fantasy"));
        Book bookWithoutWord = bookRepository.save(new Book("Lean Software Development", "https://images-na.ssl-images-amazon.com/images/I/51JBRvhI1GL.jpg", "Mary Poppendieck", "Software"));

        mockMvc.perform(get("/books/search?word=Harry"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/front"))
                .andExpect(model().attribute("title", equalTo("Books containing \"Harry\"")))
                .andExpect(model().attribute("books", hasItem(bookWithWord)))
                .andExpect(model().attribute("books", not(hasItem(bookWithoutWord))))
                .andExpect(model().attribute("categories", hasItems(
                        hasProperty("name", is("Essay")),
                        hasProperty("name", is("Fantasy")),
                        hasProperty("name", is("Software"))
                )));
    }

    @Test
    void providesTheValidCategories() {
        CategoryRepository categoryRepository = new CategoryRepository();

        assertThat(categoryRepository.findAll(), hasItems(
                hasProperty("name", is("Essay")),
                hasProperty("name", is("Fantasy")),
                hasProperty("name", is("Software"))
        ));
    }

    @Test
    @WithMockUser
    void returnsBooksFromAGivenCategory() throws Exception {

        Book fantasyBook = bookRepository.save(new Book("Harry Potter and the Philosopher's Stone", "https://www.gog.com/games?page=1&sort=popularity", "J.K. Rowling", "fantasy"));
        Book softwareBook = bookRepository.save(new Book("Lean Software Development", "https://images-na.ssl-images-amazon.com/images/I/51JBRvhI1GL.jpg", "Mary Poppendieck", "Software"));

        mockMvc.perform(get("/books?category=fantasy"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/all"))
                .andExpect(model().attribute("books", hasItem(fantasyBook)))
                .andExpect(model().attribute("books", not(hasItem(softwareBook))));
    }
}
