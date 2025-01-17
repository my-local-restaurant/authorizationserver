package albert.mylocalrestaurant.authorizationserver.security;

import albert.mylocalrestaurant.authorizationserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var user = userService.findByUsername(username);
        return new DefaultUserDetails(user);
    }
}
