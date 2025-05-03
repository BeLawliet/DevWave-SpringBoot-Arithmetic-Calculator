package arithmetic.calculator.api.persistence.repository;

import arithmetic.calculator.api.persistence.model.Operation;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface IOperationRepository extends ListCrudRepository<Operation, UUID> {}
