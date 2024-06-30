package com.inditex.hiring.infrastructure.configuration;

import com.inditex.hiring.OfferApplicationService;
import com.inditex.hiring.domain.repository.OfferRepository;
import com.inditex.hiring.domain.service.OfferService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OfferConfiguration {

    @Bean
    public OfferService offerService(OfferRepository offerRepository) {
        return new OfferService(offerRepository);
    }

    @Bean
    public OfferApplicationService offerApplicationService(OfferService offerService) {
        return new OfferApplicationService(offerService);
    }
}
