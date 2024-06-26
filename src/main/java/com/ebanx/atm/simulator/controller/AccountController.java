package com.ebanx.atm.simulator.controller;

import com.ebanx.atm.simulator.entity.Account;
import com.ebanx.atm.simulator.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/balance")
    ResponseEntity<Object> getAccountBalance(@RequestParam String account_id){
        Account account = accountService.getAccountById(account_id);
        if(account != null)
            return new ResponseEntity<>(account.getBalance(), HttpStatus.OK);

        return new ResponseEntity<>("0", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/reset")
    ResponseEntity<String> resetDatabase() {
        accountService.deleteAllAccounts();
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
