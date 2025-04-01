package com.example.library_management_system.utils;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtils {

    private SpecificationUtils() {}

    public static <T> Specification<T> likeFilter(String fieldName, Object value) {
        if (value == null || value.toString().isEmpty()) {
            return null;
        }
        return (root, query, cb) -> {
            if (value instanceof String) {
                return cb.like(root.get(fieldName), "%" + value + "%");
            } else {
                return cb.like(cb.toString(root.get(fieldName)), "%" + value + "%");
            }
        };
    }

    @SafeVarargs
    public static <T> Specification<T> buildSpecification(Specification<T>... specifications) {
        Specification<T> result = Specification.where(null);
        for (Specification<T> spec : specifications) {
            if (spec != null) { // Skip null specifications
                result = result.and(spec);
            }
        }
        return result;
    }
}