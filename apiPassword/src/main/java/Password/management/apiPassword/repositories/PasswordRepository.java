package Password.management.apiPassword.repositories;

import Password.management.apiPassword.document.Password;
import Password.management.apiPassword.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordRepository extends MongoRepository<Password, UUID> {
}
