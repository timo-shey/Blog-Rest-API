package fashionblogrestapi.repository;

import fashionblogrestapi.entity.User;
import fashionblogrestapi.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<List<User>> findByRoles(Role role);
    Boolean existsByUsername(String username);
    Optional<List<User>> searchAllByUsernameOrFirstNameOrLastNameContainsIgnoreCase(String q, String b, String c);
}
