package org.example.car.repository;

import org.example.car.entity.Car;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public final class CarSpecifications {

    private CarSpecifications() {
    }

    public static Specification<Car> notDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("deleted"));
    }

    public static Specification<Car> brandEquals(String brand) {
        return (root, query, criteriaBuilder) ->
                brand == null || brand.isBlank()
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(criteriaBuilder.lower(root.get("brand")), brand.toLowerCase());
    }

    public static Specification<Car> yearEquals(Integer year) {
        return (root, query, criteriaBuilder) ->
                year == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("year"), year);
    }

    public static Specification<Car> priceGreaterThanOrEqual(BigDecimal min) {
        return (root, query, criteriaBuilder) ->
                min == null ? criteriaBuilder.conjunction() : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), min);
    }

    public static Specification<Car> priceLessThanOrEqual(BigDecimal max) {
        return (root, query, criteriaBuilder) ->
                max == null ? criteriaBuilder.conjunction() : criteriaBuilder.lessThanOrEqualTo(root.get("price"), max);
    }
}

