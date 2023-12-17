package collective.com.theeCollective.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorDto {
    private int authorId;
    private String Names;
    private String penName;
    private String email;
    private String password;
}
