package collective.com.theeCollective.service;

import collective.com.theeCollective.dto.ArticleDto;
import collective.com.theeCollective.model.Article;

import java.util.List;

public interface ArticleService {
    List<ArticleDto> findAllArticles();
    ArticleDto findById(long articleId);

    void updateArticle(ArticleDto article);

    Article saveArticle(Article article);

    void delete(Long articleId);
    List<ArticleDto> searchArticles(String query);

}
