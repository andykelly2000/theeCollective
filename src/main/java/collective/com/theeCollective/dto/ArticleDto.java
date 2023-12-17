package collective.com.theeCollective.dto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ArticleDto {
    private int articleId;
    @NotEmpty(message = "The title should not be empty")
    private String title;
    @NotEmpty(message = "the summary should not be empty")
    private String summary;
    @NotEmpty(message = "The content can't be empty")
    private String content;
    private String authorName;
    private String category;
    private LocalDate uploadedon;
    private String coverUrl;
    private Blob cover;
    private int views;
}
