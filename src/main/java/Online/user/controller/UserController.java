package Online.user.controller;


import Online.user.entity.Users;
import Online.user.repository.UserRepo;
import Online.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/all-users")
    public Optional<List<Users>> getAllUsers(){
      return userService.getAllUsers();
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestParam String userName,
                                            @RequestParam String email,
                                            @RequestParam String password,
                                            @RequestParam String role) {
        return userService.createUser(userName, email, password, role);
    }

    @GetMapping("/get-user")
    public ResponseEntity<Users> getUserByName(@RequestParam String userName){
        Users users = userService.getUsersByName(userName);
        return ResponseEntity.ok(users);
    }
}
