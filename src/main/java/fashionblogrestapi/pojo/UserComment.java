package fashionblogrestapi.pojo;

import fashionblogrestapi.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserComment {
    private Long id;
    private String username;
    private Role role;

}