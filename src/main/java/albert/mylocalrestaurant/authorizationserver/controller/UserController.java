package albert.mylocalrestaurant.authorizationserver.controller;

import albert.mylocalrestaurant.authorizationserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/api/users")
    public ResponseEntity<Void> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        userService.register(userRegisterRequest.username(), userRegisterRequest.password());
        return ResponseEntity.ok().build();
    }
}
