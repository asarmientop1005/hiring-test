package com.inditex.hiring.infrastructure.rest;

import com.inditex.hiring.OfferApplicationService;
import com.inditex.hiring.domain.model.Offer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/offer")
@RequiredArgsConstructor
public class OfferController {
    private final OfferApplicationService offerApplicationService;

    /**
     * Creation Method
     * @param offerDTO
     * @return Offer created
     */
    @PostMapping
    public ResponseEntity<OfferDTO> createOffer(@RequestBody @Valid OfferDTO offerDTO) {
        Offer offer = OfferMapper.INSTANCE.toDomain(offerDTO);
        return new ResponseEntity<>(
                OfferMapper.INSTANCE.toDTO(offerApplicationService.createOffer(offer)),
                HttpStatus.CREATED
        );
    }

    /**
     * Delete offer by id
     * @param id
     * @return void
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOfferById(@PathVariable Long id) {
        offerApplicationService.deleteOffer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get offer by id
     * @param id
     * @return Offer obtained from database
     */
    @GetMapping("/{id}")
    public ResponseEntity<OfferDTO> getOfferById(@PathVariable Long id) {
        return new ResponseEntity<>(
                OfferMapper.INSTANCE.toDTO(offerApplicationService.getOfferById(id)), HttpStatus.OK
        );
    }

    /**
     *  Delete all offers
     * @return Delete all offers in database
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteAllOffers() {
        offerApplicationService.deleteAllOffers();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get all offers
     * @return Get all offers in database
     */
    @GetMapping
    public ResponseEntity<List<OfferDTO>> getAllOffers() {
        return new ResponseEntity<>(
                offerApplicationService.getAllOffers()
                        .stream().map(OfferMapper.INSTANCE::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    /**
     * Update offer by id
     * @param id
     * @param offerDTO
     * @return Offer updated
     */
    @PutMapping("/{id}")
    public ResponseEntity<Offer> updateOffer(@PathVariable Long id, @RequestBody OfferDTO offerDTO) {
        Offer offer = OfferMapper.INSTANCE.toDomain(offerDTO);
        return new ResponseEntity<>(offerApplicationService.updateOffer(id, offer), HttpStatus.OK);
    }
}