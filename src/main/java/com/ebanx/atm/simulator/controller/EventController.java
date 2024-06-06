package com.ebanx.atm.simulator.controller;

import com.ebanx.atm.simulator.service.EventHandler;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @Autowired
    private EventHandler eventHandler;

    @PostMapping("/event")
    ResponseEntity<Object> handleEvent(@RequestBody JsonNode request) {
        try {
            String response = eventHandler.eventHandler(request);
            if (response != null && !response.isEmpty())
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid event type: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleExceptions(Exception e) {
        return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
