package org.trendwa.eshop.catalogservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.*;
import java.lang.annotation.*;

public interface CatalogBrandValidations {

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
}