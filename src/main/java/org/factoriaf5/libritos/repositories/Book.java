package org.factoriaf5.libritos.repositories;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
@Entity
@Table(name = "books")

public class Book implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String title;
        private String coverImage;
        private String author;
        private String category;




        public Book(String title, String coverImage, String author, String category) {
            this.title = title;
            this.coverImage = coverImage;
            this.author = author;
            this.category = category;
        }

    public Book() {

    }

    public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

        public String getCoverImage() {
        return coverImage;
    }

        @Override
        public String toString() {
            return "Book{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", coverImage='" + coverImage + '\'' +
                    ", author='" + author + '\'' +
                    ", category='" + category + '\'' +
                    '}';
        }


        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Book book = (Book) o;
            return Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(coverImage, book.coverImage) && Objects.equals(author, book.author) && Objects.equals(category, book.category);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, title, coverImage, author, category);
        }
    }


