package ru.itgirl.libraryproject.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToMany
    @JoinTable(
            name = "author_book",
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))

    private Set<Author> authors;

}
