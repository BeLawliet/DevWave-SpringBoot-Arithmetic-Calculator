package arithmetic.calculator.api.util;

import arithmetic.calculator.api.persistence.model.EOperationType;
import arithmetic.calculator.api.persistence.model.Operation;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

public class OperationSpecsUtils {
    private OperationSpecsUtils() {}

    public static Specification<Operation> byUser(Long userId) {
        return (root, query, cb) -> cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Operation> byOperationType(EOperationType operationType) {
        return (root, query, cb) -> cb.equal(root.get("operation"), operationType);
    }

    public static Specification<Operation> betweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> cb.between(root.get("timestamp"), startDate, endDate);
    }
}
