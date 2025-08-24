package com.taskify.taskifyspringback.validations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistsInDatabaseValidator implements ConstraintValidator<ExistsInDatabase, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> entityClass;

    @Override
    public void initialize(ExistsInDatabase constraintAnnotation) {
        this.entityClass = constraintAnnotation.entity();
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // let @NotNull handle null cases
        }
        return entityManager.find(entityClass, value) != null;
    }

}
