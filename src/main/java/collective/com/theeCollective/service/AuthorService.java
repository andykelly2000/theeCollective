package collective.com.theeCollective.service;

import collective.com.theeCollective.dto.AuthorDto;
import collective.com.theeCollective.model.Author;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AuthorService {
    List<AuthorDto> findAllAuthor();

    List <AuthorDto> searchAuthors(String query);

    AuthorDto findById(long authorId);

    void updateAuthor(AuthorDto author);

    Author saveAuthor(Author author);

    void delete(Long authorId);

}
