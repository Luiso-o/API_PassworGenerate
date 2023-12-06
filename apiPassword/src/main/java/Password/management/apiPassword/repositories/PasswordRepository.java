package Password.management.apiPassword.repositories;

import Password.management.apiPassword.document.PasswordDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PasswordRepository extends MongoRepository<PasswordDocument, UUID> {
}
