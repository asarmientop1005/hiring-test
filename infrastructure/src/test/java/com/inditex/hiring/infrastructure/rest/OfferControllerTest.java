package com.inditex.hiring.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.hiring.OfferApplicationService;
import com.inditex.hiring.domain.model.Offer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OfferControllerTest {

    @Mock
    private OfferApplicationService offerApplicationService;

    @InjectMocks
    private OfferController offerController;

    private MockMvc mockMvc;
    private OfferDTO offerDTO;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(offerController).build();

        // Load test data from JSON file
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getResourceAsStream("/offer-rest-data.json")) {
            offerDTO = objectMapper.readValue(inputStream, OfferDTO.class);
        }
    }

    @Test
    public void testCreateOffer() throws Exception {
        // Arrange
        Offer offer = OfferMapper.INSTANCE.toDomain(offerDTO);
        when(offerApplicationService.createOffer(any(Offer.class))).thenReturn(offer);

        // Act & Assert
        mockMvc.perform(post("/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(offerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.offerId").value(1));

        verify(offerApplicationService, times(1)).createOffer(any());
    }

    @Test
    public void testDeleteOfferById() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/offer/1"))
                .andExpect(status().isNoContent());
        verify(offerApplicationService, times(1)).deleteOffer(1L);

    }

    @Test
    public void testGetOfferById() throws Exception {
        // Arrange
        Offer offer = OfferMapper.INSTANCE.toDomain(offerDTO);
        when(offerApplicationService.getOfferById(1L)).thenReturn(offer);

        // Act & Assert
        mockMvc.perform(get("/offer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.offerId").value(1));

        verify(offerApplicationService, times(1)).getOfferById(1L);
    }

    @Test
    public void testDeleteAllOffers() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/offer"))
                .andExpect(status().isNoContent());

        verify(offerApplicationService, times(1)).deleteAllOffers();
    }

    @Test
    public void testGetAllOffers() throws Exception {
        // Arrange
        Offer offer = OfferMapper.INSTANCE.toDomain(offerDTO);
        List<Offer> offers = Collections.singletonList(offer);
        when(offerApplicationService.getAllOffers()).thenReturn(offers);

        // Act & Assert
        mockMvc.perform(get("/offer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].offerId").value(1));

        verify(offerApplicationService, times(1)).getAllOffers();
    }

    @Test
    public void testUpdateOffer() throws Exception {
        // Arrange
        Offer offer = OfferMapper.INSTANCE.toDomain(offerDTO);
        when(offerApplicationService.updateOffer(any(Long.class), any(Offer.class))).thenReturn(offer);

        // Act & Assert
        mockMvc.perform(put("/offer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(offerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.offerId").value(1));

        verify(offerApplicationService, times(1)).updateOffer(anyLong(), any());
    }
}
