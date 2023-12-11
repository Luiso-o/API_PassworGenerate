package Password.management.apiPassword.repositories;

import Password.management.apiPassword.document.PasswordDocument;
import Password.management.apiPassword.enumerations.PasswordUse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PasswordRepository extends MongoRepository<PasswordDocument, UUID> {
    List<PasswordDocument> findByUse (PasswordUse use);
}
