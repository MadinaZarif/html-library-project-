package ru.itgirl.libraryproject.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.dto.AuthorDto;
import ru.itgirl.libraryproject.dto.BookDto;
import ru.itgirl.libraryproject.model.Author;
import ru.itgirl.libraryproject.model.Book;
import ru.itgirl.libraryproject.repository.AuthorRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto getAuthorByid(Long id) {
        Author author = authorRepository.findById(id).orElseThrow();
        return convertToDto(author);
    }

    private AuthorDto convertToDto(Author author) {
        List<BookDto> bookDtoList = author.getBooks()
                .stream()
                .map(book -> BookDto.builder()
                        //  .genre(book.getGenre().getName())
                        .name(book.getName())
                        .id(book.getId())
                        .build())
                .toList();

        return AuthorDto.builder()
                //    .books(bookDtoList)
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build();
    }
    @Override
    public AuthorDto getByNameV2(String name) {
        Author author = authorRepository.findAuthorNameBySql(name).orElseThrow(() -> new NoSuchElementException("No author found with name: " + name));
        return convertEntityToDto(author);

    }

    @Override
    public AuthorDto getByNameV3(String name) {
        Specification<Author> authorSpecification = Specification.where(new Specification<Author>() {
            @Override
            public Predicate toPredicate(Root<Author> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("name"), name);
            }
        });
        Author author = authorRepository.findOne(authorSpecification).orElseThrow();
        return convertEntityToDto(author);
    }

    @Override
    public AuthorDto getByNameV1(String name) {
        Author author = authorRepository.findAuthorByName(name).orElseThrow();
        return convertEntityToDto(author);
    }

    private AuthorDto convertEntityToDto(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname()).build();

    }

}