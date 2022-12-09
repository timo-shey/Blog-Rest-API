package fashionblogrestapi.service;

import fashionblogrestapi.enums.Role;
import fashionblogrestapi.pojo.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    RegisterResponse createUser(RegisterDto userDto);
    LoginResponse loginUser(LoginDto userDto);
    UserDto findByUsername(String username);
    List<UserDto> findByRole(Role role);
    UserDto findById(Long id);
    List<UserDto> findBySearch(String q, String c, String b);
    List<UserDto> findAllUsers();
    UserDto updateUser(UserDto updateUser, Long userId);
    String deleteUser(Long userId);
}
