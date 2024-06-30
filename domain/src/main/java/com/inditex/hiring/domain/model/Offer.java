package com.inditex.hiring.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Offer {
    private Long offerId;
    private Integer brandId;
    private String startDate;
    private String endDate;
    private Long priceListId;
    private String productPartnumber;
    private Integer priority;
    private BigDecimal price;
    private String currencyIso;


}
