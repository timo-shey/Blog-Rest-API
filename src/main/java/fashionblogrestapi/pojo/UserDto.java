package fashionblogrestapi.pojo;

import fashionblogrestapi.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Long id;
    @NotNull(message = "First name cannot be missing or empty.")
    @Size(min = 2, message = "First name must not be less than 2 characters.")
    private String firstName;
    @NotNull(message = "Last name cannot be missing or empty.")
    @Size(min = 2, message = "Last name must not be less than 2 characters.")
    private String lastName;
    @NotNull(message = "Username cannot be missing or empty.")
    @Size(min = 2, message = "Username must not be less than 2 characters.")
    private String username;

    @NotNull(message = "Password is a required field.")
    @Size(min = 2, message = "Password must be equal to or greater than 8 characters and less than 16 characters.")
    private String password;
    private Role role;

    public UserDto(Long id, String firstName, String lastName, String username, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = role;
    }

}

