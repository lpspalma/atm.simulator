package com.ebanx.atm.simulator.service;

import com.ebanx.atm.simulator.dto.EventDTO;
import com.ebanx.atm.simulator.entity.Account;
import com.ebanx.atm.simulator.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    public Account getAccountBalanceById(EventDTO dto) {
        Account account = accountService.getAccountBalanceById(dto.getDestination());
        if (account != null)
            account.setBalance(account.getBalance() + Integer.parseInt(dto.getAmount()));
        else
            account = accountService.createNewAccount(Long.parseLong(dto.getDestination()), Integer.parseInt(dto.getAmount()));

        return accountRepository.save(account);

    }

    public void witheDrawFromAccount(EventDTO dto) {
    }

    public void transferBetweenAccounts(EventDTO dto) {
    }
}
