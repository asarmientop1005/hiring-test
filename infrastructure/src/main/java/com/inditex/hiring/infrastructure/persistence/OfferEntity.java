package com.inditex.hiring.infrastructure.persistence;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "Offer")
public class OfferEntity {
    @Id
    @Column(name = "OFFER_ID")
    private Long offerId;
    @Column(name = "BRAND_ID")
    private Integer brandId;
    @Column(name = "START_DATE")
    private Timestamp startDate;
    @Column(name = "END_DATE")
    private Timestamp endDate;
    @Column(name = "PRICE_LIST")
    private Integer priceListId;
    @Column(name = "SIZE")
    private String size;
    @Column(name = "MODEL")
    private String model;
    @Column(name = "QUALITY")
    private String quality;
    @Column(name = "PRIORITY")
    private Integer priority;
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "CURR")
    private String currencyIso;
}
