package com.inditex.hiring.infrastructure.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.infrastructure.rest.OfferMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferRepositoryImplTest {

    @Mock
    private OfferJpaRepository offerJpaRepository;

    @InjectMocks
    private OfferRepositoryImpl offerRepositoryImpl;

    private final OfferMapper offerMapper = Mappers.getMapper(OfferMapper.class);

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
    void testFindAll() {
        // Arrange
        when(offerJpaRepository.findAll()).thenReturn(List.of(offerMapper.toEntity(offers.get(0))));

        // Act
        List<Offer> result = offerRepositoryImpl.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(offers.get(0).getOfferId(), result.get(0).getOfferId());
        verify(offerJpaRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        Long id = 1L;
        when(offerJpaRepository.findById(id)).thenReturn(Optional.of(offerMapper.toEntity(offers.get(0))));

        // Act
        Optional<Offer> result = offerRepositoryImpl.findById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(offers.get(0).getOfferId(), result.get().getOfferId());
        verify(offerJpaRepository, times(1)).findById(id);
    }

    @Test
    void testSave() {
        // Arrange
        Offer offer = offers.get(0);
        when(offerJpaRepository.save(any())).thenReturn(offerMapper.toEntity(offer));

        // Act
        Offer result = offerRepositoryImpl.save(offer);

        // Assert
        assertNotNull(result);
        assertEquals(offer.getOfferId(), result.getOfferId());
        verify(offerJpaRepository, times(1)).save(any());
    }

    @Test
    void testDeleteById() {
        // Arrange
        Long id = 1L;
        doNothing().when(offerJpaRepository).deleteById(id);

        // Act
        offerRepositoryImpl.deleteById(id);

        // Assert
        verify(offerJpaRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteAll() {
        // Arrange
        doNothing().when(offerJpaRepository).deleteAll();

        // Act
        offerRepositoryImpl.deleteAll();

        // Assert
        verify(offerJpaRepository, times(1)).deleteAll();
    }
}
