package collective.com.theeCollective.repository;

import collective.com.theeCollective.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("select c from Author c where c.Names like concat('%', :query, '%') ")
    List<Author> searchAuthors(String query);
}
