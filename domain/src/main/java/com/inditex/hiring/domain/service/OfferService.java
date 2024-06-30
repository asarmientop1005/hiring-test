package com.inditex.hiring.domain.service;

import com.inditex.hiring.domain.exception.NoSuchResourceFoundException;
import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.domain.repository.OfferRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    public Offer getOfferById(Long id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new NoSuchResourceFoundException("Offer not found with id: " + id));
    }

    public Offer createOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    public Offer updateOffer(Long id, Offer offer) {
        if (offerRepository.findById(id).isEmpty()) {
            throw new NoSuchResourceFoundException("Offer not found with id: " + id);
        }
        offer.setOfferId(id);
        return offerRepository.save(offer);
    }

    public void deleteOffer(Long id) {
        if (offerRepository.findById(id).isEmpty()) {
            throw new NoSuchResourceFoundException("Offer not found with id: " + id);
        }
        offerRepository.deleteById(id);
    }

    public void deleteAllOffers() {
        offerRepository.deleteAll();
    }
}
