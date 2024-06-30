package com.inditex.hiring.infrastructure.persistence;

import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.domain.repository.OfferRepository;
import com.inditex.hiring.infrastructure.rest.OfferMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class OfferRepositoryImpl implements OfferRepository {

    private final OfferJpaRepository offerJpaRepository;
    private final OfferMapper offerMapper = Mappers.getMapper(OfferMapper.class);

    @Override
    public List<Offer> findAll() {
        return offerJpaRepository.findAll().stream()
                .map(offerMapper::toDomainFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Offer> findById(Long id) {
        return offerJpaRepository.findById(id)
                .map(offerMapper::toDomainFromEntity);
    }

    @Override
    public Offer save(Offer offer) {
        OfferEntity offerEntity = offerMapper.toEntity(offer);
        return offerMapper.toDomainFromEntity(
                offerJpaRepository.save(offerEntity)
        );
    }

    @Override
    public void deleteById(Long id) {
        offerJpaRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        offerJpaRepository.deleteAll();
    }
}
