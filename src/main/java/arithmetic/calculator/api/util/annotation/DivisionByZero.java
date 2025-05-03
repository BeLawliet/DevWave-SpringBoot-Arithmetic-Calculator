package arithmetic.calculator.api.util.annotation;

import arithmetic.calculator.api.util.validation.DivisionZeroValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DivisionZeroValidation.class)
public @interface DivisionByZero {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
