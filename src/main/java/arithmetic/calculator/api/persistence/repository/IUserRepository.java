package arithmetic.calculator.api.persistence.repository;

import arithmetic.calculator.api.persistence.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IUserRepository extends CrudRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
}
