package com.inditex.hiring.infrastructure.rest;

import com.inditex.hiring.domain.model.Offer;
import com.inditex.hiring.infrastructure.persistence.OfferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper
public interface OfferMapper {
    OfferMapper INSTANCE = Mappers.getMapper(OfferMapper.class);

    OfferDTO toDTO(Offer offer);
    Offer toDomain(@Valid OfferDTO entity);

    @Mapping(source = "startDate", target = "startDate", qualifiedByName = "timestampToString")
    @Mapping(source = "endDate", target = "endDate", qualifiedByName = "timestampToString")
    @Mapping(target = "productPartnumber", expression = "java(combineProductPartNumber(entity.getSize(), " +
            "entity.getModel(), entity.getQuality()))")
    Offer toDomainFromEntity(OfferEntity entity);

    @Mapping(source = "startDate", target = "startDate", qualifiedByName = "stringToTimestamp")
    @Mapping(source = "endDate", target = "endDate", qualifiedByName = "stringToTimestamp")
    @Mapping(source = "productPartnumber", target = "size", qualifiedByName = "extractSize")
    @Mapping(source = "productPartnumber", target = "model", qualifiedByName = "extractModel")
    @Mapping(source = "productPartnumber", target = "quality", qualifiedByName = "extractQuality")

    OfferEntity toEntity(Offer entity);

    @Named("stringToTimestamp")
    default Timestamp stringToTimestamp(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH.mm.ss'Z'");
            Date parsedDate = dateFormat.parse(date);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Named("timestampToString")
    default String timestampToString(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH.mm.ss'Z'");
        return dateFormat.format(timestamp);
    }

    @Named("extractSize")
    default String extractSize(String productPartnumber) {
        return productPartnumber.substring(0, 2);
    }

    @Named("extractModel")
    default String extractModel(String productPartnumber) {
        return productPartnumber.substring(2, 6);
    }

    @Named("extractQuality")
    default String extractQuality(String productPartnumber) {
        return productPartnumber.substring(6, 9);
    }

    default String combineProductPartNumber(String size, String model, String quality) {
        return size + model + quality;
    }
}