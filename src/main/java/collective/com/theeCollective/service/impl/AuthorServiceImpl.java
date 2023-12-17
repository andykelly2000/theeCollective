package collective.com.theeCollective.service.impl;

import collective.com.theeCollective.dto.AuthorDto;
import collective.com.theeCollective.model.Author;
import collective.com.theeCollective.repository.AuthorRepository;
import collective.com.theeCollective.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDto> findAllAuthor() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map((author) -> mapToAuthorDto(author)).collect(Collectors.toList());
    }

    @Override
    public List<AuthorDto> searchAuthors(String query) {
        List<Author> authors = authorRepository.searchAuthors(query);
        return authors.stream().map(author -> mapToAuthorDto(author)).collect(Collectors.toList());
    }

    @Override
    public AuthorDto findById(long authorId) {
        Author author = authorRepository.findById(authorId).get();
        return mapToAuthorDto(author);
    }

    @Override
    public void updateAuthor(AuthorDto authorDto) {
        Author author = mapToAuthor(authorDto);
        authorRepository.save(author);
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void delete(Long authorId) {
        authorRepository.deleteById(authorId);
    }

    private Author mapToAuthor(AuthorDto author) {
        Author authorDto = Author.builder()
                .authorId(author.getAuthorId())
                .email(author.getEmail())
                .password(author.getPassword())
                .Names(author.getNames())
                .penName(author.getPenName())
                .build();
        return authorDto;
    }

    private AuthorDto mapToAuthorDto(Author author) {
        AuthorDto authorDto = AuthorDto.builder()
                .authorId(author.getAuthorId())
                .Names(author.getNames())
                .email(author.getEmail())
                .password(author.getPassword())
                .penName(author.getPenName())
                .build();
        return authorDto;
    }
}