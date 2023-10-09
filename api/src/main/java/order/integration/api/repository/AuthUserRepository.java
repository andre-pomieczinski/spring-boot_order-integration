package order.integration.api.repository;

import order.integration.api.model.AuthUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthUserRepository extends JpaRepository<AuthUserModel, Long> {
    UserDetails findByLogin(String login);
}
