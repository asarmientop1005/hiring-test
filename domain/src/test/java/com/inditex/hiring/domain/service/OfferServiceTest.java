package com.inditex.hiring.domain.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.hiring.domain.exception.NoSuchResourceFoundException;
import com.inditex.hiring.domain.exception.NoSuchResourceFoundException;
import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.domain.repository.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private OfferService offerService;

    private List<Offer> offers;

    @BeforeEach
    void setUp() throws IOException {
        // Read data from JSON file
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getResourceAsStream("/offers.json")) {
            offers = objectMapper.readValue(inputStream, new TypeReference<List<Offer>>() {});
        }
    }

    @Test
    void getAllOffers_ShouldReturnAllOffers() {
        // Arrange
        when(offerRepository.findAll()).thenReturn(offers);

        // Act
        List<Offer> result = offerService.getAllOffers();

        // Assert
        assertEquals(offers, result);
        verify(offerRepository, times(1)).findAll();
    }

    @Test
    void getOfferById_ShouldReturnOffer_WhenOfferExists() {
        // Arrange
        Offer offer = offers.get(0);
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

        // Act
        Offer result = offerService.getOfferById(1L);

        // Assert
        assertEquals(offer, result);
        verify(offerRepository, times(1)).findById(1L);
    }

    @Test
    void getOfferById_ShouldThrowNoSuchResourceFoundException_WhenOfferDoesNotExist() {
        // Arrange
        when(offerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchResourceFoundException exception = assertThrows(NoSuchResourceFoundException.class, () -> {
            offerService.getOfferById(1L);
        });
        assertEquals("Offer not found with id: 1", exception.getMessage());
        verify(offerRepository, times(1)).findById(1L);
    }

    @Test
    void createOffer_ShouldReturnSavedOffer() {
        // Arrange
        Offer offer = offers.get(0);
        when(offerRepository.save(offer)).thenReturn(offer);

        // Act
        Offer result = offerService.createOffer(offer);

        // Assert
        assertEquals(offer, result);
        verify(offerRepository, times(1)).save(offer);
    }

    @Test
    void updateOffer_ShouldReturnUpdatedOffer_WhenOfferExists() {
        // Arrange
        Offer offer = offers.get(0);
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(offerRepository.save(offer)).thenReturn(offer);

        // Act
        Offer result = offerService.updateOffer(1L, offer);

        // Assert
        assertEquals(offer, result);
        verify(offerRepository, times(1)).findById(1L);
        verify(offerRepository, times(1)).save(offer);
    }

    @Test
    void updateOffer_ShouldThrowNoSuchResourceFoundException_WhenOfferDoesNotExist() {
        // Arrange
        when(offerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchResourceFoundException exception = assertThrows(NoSuchResourceFoundException.class, () -> {
            offerService.updateOffer(1L, offers.get(0));
        });
        assertEquals("Offer not found with id: 1", exception.getMessage());
        verify(offerRepository, times(1)).findById(1L);
        verify(offerRepository, times(0)).save(offers.get(0));
    }

    @Test
    void deleteOffer_ShouldCallDeleteById_WhenOfferExists() {
        // Arrange
        Offer offer = offers.get(0);
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

        // Act
        offerService.deleteOffer(1L);

        // Assert
        verify(offerRepository, times(1)).findById(1L);
        verify(offerRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteOffer_ShouldThrowNoSuchResourceFoundException_WhenOfferDoesNotExist() {
        // Arrange
        when(offerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchResourceFoundException exception = assertThrows(NoSuchResourceFoundException.class, () -> {
            offerService.deleteOffer(1L);
        });
        assertEquals("Offer not found with id: 1", exception.getMessage());
        verify(offerRepository, times(1)).findById(1L);
        verify(offerRepository, times(0)).deleteById(1L);
    }

    @Test
    void deleteAllOffers_ShouldCallDeleteAll() {
        // Act
        offerService.deleteAllOffers();

        // Assert
        verify(offerRepository, times(1)).deleteAll();
    }
}
