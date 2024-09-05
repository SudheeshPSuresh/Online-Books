package Online.user.service;

import Online.exception.UserNotFoundException;
import Online.user.entity.Roles;
import Online.user.entity.Users;
import Online.user.repository.RoleRepository;
import Online.user.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepository roleRepository;

    public Optional<List<Users>> getAllUsers() {
       return Optional.of(userRepo.findAll());
    }

    @Transactional
    public ResponseEntity<String> createUser(String userName, String email, String password, String roleName) {
        Users user = new Users();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);

        Roles role = roleRepository.findByName(roleName.toUpperCase());
        if (role != null) {
            user.setRoles(new HashSet<>());
            user.getRoles().add(role);
            userRepo.save(user);
            return new ResponseEntity<>(userName, HttpStatus.CREATED);

        }else {
            return ResponseEntity.ok ( "'" +roleName.toUpperCase() + "' Role not found");
        }
    }

    public Users getUsersByName(String userName) {
        return userRepo.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }
}
