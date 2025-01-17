package albert.mylocalrestaurant.authorizationserver.repository;

import albert.mylocalrestaurant.authorizationserver.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String user);
}
