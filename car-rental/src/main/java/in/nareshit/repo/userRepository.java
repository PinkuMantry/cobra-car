package in.nareshit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.Entity.user;

public interface userRepository extends JpaRepository<user, Integer> {
	
	@Query("SELECT u FROM user u WHERE u.email=:email")
	Optional<user> findByEmail(String email);
}
