package com.inditex.hiring;

import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.domain.service.OfferService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OfferApplicationService {
    private final OfferService offerService;

    public List<Offer> getAllOffers() {
        return offerService.getAllOffers();
    }

    public Offer getOfferById(Long id) {
        return offerService.getOfferById(id);
    }

    public Offer createOffer(Offer offer) {
        return offerService.createOffer(offer);
    }

    public Offer updateOffer(Long id, Offer offer) {
        return offerService.updateOffer(id, offer);
    }

    public void deleteOffer(Long id) {
        offerService.deleteOffer(id);
    }

    public void deleteAllOffers() {
        offerService.deleteAllOffers();
    }
}
