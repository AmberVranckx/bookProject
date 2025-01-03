package fact.it.readinglistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private Date birthday;
}
