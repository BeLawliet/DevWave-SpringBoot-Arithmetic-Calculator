package arithmetic.calculator.api.persistence.repository;

import arithmetic.calculator.api.persistence.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface IOperationRepository extends JpaRepository<Operation, UUID>, JpaSpecificationExecutor<Operation> {}
