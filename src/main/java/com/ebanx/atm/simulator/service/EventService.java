package com.ebanx.atm.simulator.service;

import com.ebanx.atm.simulator.dto.EventDTO;
import com.ebanx.atm.simulator.dto.EventType;
import com.ebanx.atm.simulator.entity.Account;
import com.ebanx.atm.simulator.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EventService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    public Map<String, Account> getAccountBalanceById(EventType type, String id, int amount) {
        Account account = accountService.getAccountById(id);
        if (account != null)
            account.setBalance(account.getBalance() + amount);
        else
            account = accountService.createNewAccount(Long.parseLong(id), amount);

        accountRepository.save(account);
        return responseBuilder(type, account);
    }

    public Map<String, Account> withdrawFromAccount(EventType type, String id, int amount) {
        Account account = accountService.getAccountById(id);
        if (account != null && account.getBalance() >= amount){
            account.setBalance(account.getBalance() - amount);
            accountRepository.save(account);
        }

        return account!=null ? responseBuilder(type, account) : null;
    }

    public void transferBetweenAccounts(EventDTO dto) {
    }

    private Map<String, Account> responseBuilder(EventType type, Account account) {
        Map<String, Account> response = new HashMap<>();
        switch (type) {
            case DEPOSIT_EVENT -> response.put("destination", account);
            case WITHDRAW_EVENT -> response.put("origin", account);
        }

        return response;
    }
}
