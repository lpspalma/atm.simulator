package com.ebanx.atm.simulator.service;

import com.ebanx.atm.simulator.dto.EventDTO;
import com.ebanx.atm.simulator.dto.EventType;
import com.ebanx.atm.simulator.entity.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class EventHandler {
    @Autowired
    private EventService eventService;

    private final ObjectMapper mapper = new ObjectMapper();

    public Map<String, Account> eventHandler(JsonNode request) {
        try {
            EventDTO dto = mapper.treeToValue(request, EventDTO.class);
            EventType eventType = EventType.fromString(dto.getType());

            return handleEvent(eventType, dto);
        } catch (IllegalArgumentException | JsonProcessingException e) {
            log.error("Error processing event: {}", e.getMessage());
            return null;
        }
    }

    private Map<String, Account> handleEvent(EventType eventType, EventDTO dto) {
        return
                switch (eventType) {
                    case DEPOSIT_EVENT -> eventService.getAccountBalanceById(dto.getDestination(), dto.getAmount());
                    case WITHDRAW_EVENT -> eventService.withdrawFromAccount(dto.getDestination(), dto.getAmount());
                    case TRANSFER_EVENT -> eventService.transferBetweenAccounts(dto);
                };
    }

}
