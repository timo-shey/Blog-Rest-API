package fashionblogrestapi.service.impl;

import fashionblogrestapi.entity.User;
import fashionblogrestapi.enums.Role;
import fashionblogrestapi.exception.AlreadyExistException;
import fashionblogrestapi.exception.AppException;
import fashionblogrestapi.exception.NotFoundException;
import fashionblogrestapi.pojo.*;
import fashionblogrestapi.repository.UserRepository;
import fashionblogrestapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
    }

    @Override
    public RegisterResponse createUser(RegisterDto userDto) {
        User user = new User();

        BeanUtils.copyProperties(userDto, user);

        Boolean userExist = userRepository.existsByUsername(userDto.getUsername());

        if (userExist) throw new AlreadyExistException("Username Already Exists.");

        userRepository.save(user);

        if (user.getId() > 1L) user.setRoles(Role.VISITOR);
        else user.setRoles(Role.ADMIN);

        User savedUser = userRepository.save(user);

        RegisterResponse savedUserDto = new RegisterResponse();
        savedUserDto.setId(savedUser.getId());
        savedUserDto.setFirstName(savedUser.getFirstName());
        savedUserDto.setLastName(savedUser.getLastName());
        savedUserDto.setUsername(savedUser.getUsername());
        savedUserDto.setRole(savedUser.getRoles());
        return savedUserDto;
    }

    @Override
    public LoginResponse loginUser(LoginDto userDto) {
        if (userDto.getUsername() == null || userDto.getPassword() == null)
            throw new AppException(HttpStatus.BAD_REQUEST, "Complete all fields.");

        User user = userRepository.findByUsername(userDto.getUsername()).orElseThrow(
                ()-> {throw new NotFoundException("User not found.");}
        );

        if (!user.getPassword().equals(userDto.getPassword()))
            throw new AppException(HttpStatus.BAD_REQUEST, "Incorrect Password.");

        LoginResponse loginDto = new LoginResponse();
        loginDto.setUsername(user.getUsername());
        loginDto.setFirstName(user.getFirstName());
        loginDto.setLastName(user.getLastName());
        loginDto.setRole(user.getRoles());

        return loginDto;
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> {
            throw new NotFoundException("User not found.");});

        return convertEntityToDto(user);
    }

    @Override
    public List<UserDto> findByRole(Role role) {
        List<User> userList = userRepository.findByRoles(role).orElseThrow(
                ()-> {throw new NotFoundException("There are no Users under the role: " + role + ".");});

        return convertListEntityToDto(userList);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()-> {throw new NotFoundException("User not found.");});

        return convertEntityToDto(user);
    }

    @Override
    public List<UserDto> findBySearch(String q, String c, String b) {
        List<User> userList = userRepository.searchAllByUsernameOrFirstNameOrLastNameContainsIgnoreCase(q, c, b).orElseThrow(
                ()-> {
                    throw new NotFoundException("User(s) not found.");
                });

        return convertListEntityToDto(userList);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> userList = userRepository.findAll();

        List<UserDto> foundUserList = convertListEntityToDto(userList);

        if (userList.isEmpty()) throw new NotFoundException("User(s) not found.");
        return foundUserList;
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto updateUser, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> {throw new NotFoundException("User not found.");}
        );
        user.setUsername(updateUser.getUsername());
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setPassword(updateUser.getPassword());

        User newUser = userRepository.save(user);

        return convertEntityToDto(user);
    }

    @Override
    public String deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> {throw new NotFoundException("User not found.");});

        if (user.getRoles().equals(Role.ADMIN)) throw new AppException(HttpStatus.BAD_REQUEST,
                user.getUsername() + "'s profile cannot be deleted.");
        else userRepository.delete(user);

        return user.getUsername() + "'s profile has been deleted successfully.";
    }

    private UserDto convertEntityToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getRoles()
        );
    }

    private static List<UserDto> convertListEntityToDto(List<User> userList) {
        return userList.stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getUsername(),
                        user.getRoles()))
                .collect(Collectors.toList());
    }
}
