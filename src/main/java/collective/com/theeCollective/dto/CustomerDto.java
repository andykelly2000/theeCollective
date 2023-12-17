package collective.com.theeCollective.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CustomerDto {
    private int customerId;
    private String names;
    private String username;
    private String email;
    private String password;
    private int age;
}