package com.inditex.hiring.domain.repository;

import com.inditex.hiring.domain.model.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {
    List<Offer> findAll();
    Optional<Offer> findById(Long id);
    Offer save(Offer offer);
    void deleteById(Long id);
    void deleteAll();
}
