package albert.mylocalrestaurant.authorizationserver.service;

import albert.mylocalrestaurant.authorizationserver.model.User;
import albert.mylocalrestaurant.authorizationserver.repository.RoleRepository;
import albert.mylocalrestaurant.authorizationserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public void register(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("User already exists");
        }
        var user = new User(username, passwordEncoder.encode(password));
        user.getRoles().add(roleRepository.findByName("USER"));
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
