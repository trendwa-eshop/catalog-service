package org.trendwa.eshop.catalogservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.*;
import java.lang.annotation.*;

public interface CatalogItemValidations {

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 30, message = "Name cannot be longer than 30 characters")
    @Target({ ElementType.FIELD, ElementType.PARAMETER })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Constraint(validatedBy = {})
    @interface Name {
        String message() default "Invalid name";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    @Size(max = 600, message = "Description cannot be longer than 600 characters")
    @Target({ ElementType.FIELD, ElementType.PARAMETER })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Constraint(validatedBy = {})
    @interface Description {
        String message() default "Invalid description";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Target({ ElementType.FIELD, ElementType.PARAMETER })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Constraint(validatedBy = {})
    @interface Price {
        String message() default "Invalid price";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    @NotNull(message = "Stock cannot be null")
    @Min(value = 0, message = "Stock cannot be less than 0")
    @Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Constraint(validatedBy = {})
    @interface Stock {
        String message() default "Invalid stock";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }
}