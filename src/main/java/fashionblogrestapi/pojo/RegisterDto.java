package fashionblogrestapi.pojo;

import fashionblogrestapi.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Role role;
}
