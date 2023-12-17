package collective.com.theeCollective.service.impl;

import collective.com.theeCollective.dto.ArticleDto;
import collective.com.theeCollective.model.Article;
import collective.com.theeCollective.repository.ArticleRepository;
import collective.com.theeCollective.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<ArticleDto> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map((article) -> mapToArticleDto(article)).collect(Collectors.toList());
    }

    @Override
    public ArticleDto findById(long articleID) {
        Article article = articleRepository.findById(articleID).get();
        return mapToArticleDto(article);
    }

    @Override
    public void updateArticle(ArticleDto articleDto) {
        Article article = mapToArticle(articleDto);
        articleRepository.save(article);
    }

    @Override
    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void delete(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    @Override
    public List<ArticleDto> searchArticles(String query) {
        List<Article> articles = articleRepository.searchArticles(query);
        return articles.stream().map(article -> mapToArticleDto(article)).collect(Collectors.toList());
    }

    private Article mapToArticle(ArticleDto article) {
        Article articleDto = Article.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .summary(article.getSummary())
                .views(article.getViews())
                .uploadedon(article.getUploadedon())
                .authorName(article.getAuthorName())
                .category(article.getCategory())
                .content(article.getContent())
                .views(article.getViews())
                .coverUrl(article.getCoverUrl())
                .cover(article.getCover())
                .build();
        return articleDto;
    }

    private ArticleDto mapToArticleDto(Article article){
        ArticleDto articleDto = ArticleDto.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .authorName(article.getAuthorName())
                .category(article.getCategory())
                .content(article.getContent())
                .coverUrl(article.getCoverUrl())
                .summary(article.getSummary())
                .uploadedon(article.getUploadedon())
                .views(article.getViews())
                .cover(article.getCover())
                .build();
        return articleDto;
    }
}
