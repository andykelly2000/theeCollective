package collective.com.theeCollective.repository;

import collective.com.theeCollective.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByTitle (String url);
    @Query("select c from Article c where c.title like CONCAT('%',:query, '%') ")
    List<Article> searchArticles(String query);
}
