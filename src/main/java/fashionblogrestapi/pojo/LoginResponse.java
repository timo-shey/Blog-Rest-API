package fashionblogrestapi.pojo;

import fashionblogrestapi.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String firstName;
    private String lastName;
    private String username;
    private Role role;
}
