package com.inditex.hiring.infrastructure.rest;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OfferDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 448171649369562796L;
    @NotNull(message = "Mandatory value offerId")
    private Long offerId;
    @NotNull(message = "Mandatory value brandId")
    private Integer brandId;
    @NotEmpty(message = "No empty value startDate")
    private String startDate;
    @NotEmpty(message = "No empty value endDate")
    private String endDate;
    private Long priceListId;
    @Size(min = 9, max = 9, message = "productPartnumber must be exactly 9 characters")
    private String productPartnumber;
    private Integer priority;
    private BigDecimal price;
    private String currencyIso;
}