package albert.mylocalrestaurant.authorizationserver;

import albert.mylocalrestaurant.authorizationserver.model.Role;
import albert.mylocalrestaurant.authorizationserver.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
@RequiredArgsConstructor
public class RoleInitializer implements ApplicationRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) {
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));
    }
}
