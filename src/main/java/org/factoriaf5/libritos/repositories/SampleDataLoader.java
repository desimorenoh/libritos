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
                new Book("Un mago de Terramar", "https://i0.wp.com/modogeeks.com/wp-content/uploads/2017/01/Un-mago-de-Terramar-Ursula-K.-Le-Guin-FREELIBROS.ORG_-e1485729618357.jpg?fit=750%2C627&ssl=1", "Ursula K. Leguin", "Fantasy"),
                new Book("Los desposeídos", "https://m.media-amazon.com/images/P/B08HW3CMJ5.01._SCLZZZZZZZ_SX500_.jpg", "Ursula K. Leguin", "Fantasy"),
                new Book("Lean Software Development", "https://images-na.ssl-images-amazon.com/images/I/51gF6uCFtvL.jpg", "Mary Poppendieck", "Software"),
                new Book("Women, Race and Class", "https://images-na.ssl-images-amazon.com/images/I/41XJ4CAHP1L._SX296_BO1,204,203,200_.jpg", "Angela Y. Davis", "Essay"),
                new Book("Object Design", "https://images-na.ssl-images-amazon.com/images/I/51vieGCZ6HL._SR600%2C315_PIWhiteStrip%2CBottomLeft%2C0%2C35_SCLZZZZZZZ_FMpng_BG255%2C255%2C255.jpg", "Rebecca Wirfs-Brock", "Software")
        ));
    }
}
