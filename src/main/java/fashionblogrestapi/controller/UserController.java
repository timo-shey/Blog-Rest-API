package fashionblogrestapi.controller;

import fashionblogrestapi.enums.Role;
import fashionblogrestapi.pojo.RegisterDto;
import fashionblogrestapi.pojo.RegisterResponse;
import fashionblogrestapi.pojo.UserDto;
import fashionblogrestapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser (@Valid @RequestBody RegisterDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> findAllUsers (){
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/get_username")
    public ResponseEntity<UserDto> findUserByUsername(@RequestParam("username") String username){
        return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/get_role")
    public ResponseEntity<List<UserDto>> findUserByRole(@RequestParam("role") Role role){
        return new ResponseEntity<>(userService.findByRole(role), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> findUserBySearch(@RequestParam("q") String q,
                                                          @RequestParam("c") String c,
                                                          @RequestParam("b") String b){
        return new ResponseEntity<>(userService.findBySearch(q, c, b), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId,
                                              @RequestBody UserDto userDto){
        UserDto userResponse = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long userId){
        String deletedUser = userService.deleteUser(userId);

        return new ResponseEntity<>(deletedUser, HttpStatus.NO_CONTENT);
    }

}

