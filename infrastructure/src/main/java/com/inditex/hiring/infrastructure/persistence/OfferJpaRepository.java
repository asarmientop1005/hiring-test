package com.inditex.hiring.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferJpaRepository extends JpaRepository<OfferEntity, Long> {
}
