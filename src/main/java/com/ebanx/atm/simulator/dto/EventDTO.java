package com.ebanx.atm.simulator.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDTO {
    private String type;
    private String destination;
    private String amount;
    private String origin;
}
