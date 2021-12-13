package org.factoriaf5.libritos.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class SampleDataLoader {
    private BookRepository bookRepository;

    @Autowired
    public SampleDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void loadSampleData() {
        bookRepository.saveAll(List.of(
                new Book("Una habitación propia", "https://images-na.ssl-images-amazon.com/images/I/81b8hISH47L.jpg", "Virginia Woolf", "Essay"),
                new Book("Un mago de Terramar", "https://images-na.ssl-images-amazon.com/images/I/61ha2ITvhsL.jpg", "Ursula K. Leguin", "Fantasy"),
                new Book("Los desposeídos", "https://m.media-amazon.com/images/P/B08HW3CMJ5.01._SCLZZZZZZZ_SX500_.jpg", "Ursula K. Leguin", "Fantasy"),
                new Book("Lean Software Development", "https://images-na.ssl-images-amazon.com/images/I/51JBRvhI1GL.jpg", "Mary Poppendieck", "Software"),
                new Book("Women, Race and Class", "https://images-na.ssl-images-amazon.com/images/I/817rtjlwP0L.jpg", "Angela Y. Davis", "Essay"),
                new Book("Object Design", "https://images-na.ssl-images-amazon.com/images/I/A17f9QsXaXL.jpg", "Rebecca Wirfs-Brock", "Software")
        ));
    }
}
