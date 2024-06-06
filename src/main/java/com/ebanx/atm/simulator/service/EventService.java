package com.ebanx.atm.simulator.service;

import com.ebanx.atm.simulator.dto.EventDTO;
import com.ebanx.atm.simulator.entity.Account;
import com.ebanx.atm.simulator.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    /**
     * Adds a specified amount to the balance of an account.
     * If the account does not exist, a new account is created with the given balance.
     *
     * @param id     The ID of the account to update.
     * @param amount The amount to add to the account balance.
     * @return A map containing the updated account under the key "destination".
     * @throws IllegalArgumentException if the account ID is null, empty, or invalid.
     */
    public String getAccountBalanceById(String id, int amount) {
        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("Account ID cannot be null or empty");

        Account account = accountService.getAccountById(id);

        if (account != null)
            account.setBalance(account.getBalance() + amount);
        else {
            long accountId;
            try {
                accountId = Long.parseLong(id);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid account ID format", e);
            }
            account = accountService.createNewAccount(accountId, amount);
        }

        accountRepository.save(account);

        return "{\"destination\": "+"{\"id\":\"" + account.getId() + "\", \"balance\":" + account.getBalance() + "}}";

    }

    /**
     * Withdraws a specified amount from an account if the account exists and has sufficient balance.
     *
     * @param id     The ID of the account to withdraw from.
     * @param amount The amount to withdraw.
     * @return A map containing the updated account under the key "origin", or null if the account does not exist or has insufficient balance.
     */
    public String withdrawFromAccount(String id, int amount) {
        Account account = accountService.getAccountById(id);
        if (account == null)
            return null;
        if (account.getBalance() < amount)
            return null;
        performWithdrawal(account, amount);
        accountRepository.save(account);

        return "{\"origin\": "+"{\"id\":\"" + account.getId() + "\", \"balance\":" + account.getBalance() + "}}";
    }

    /**
     * Transfers a specified amount from one account to another.
     * If the destination account does not exist, a new account is created with the transferred amount.
     *
     * @param dto The EventDTO containing the details of the transfer.
     * @return A map containing the origin and destination accounts under the keys "origin" and "destination" respectively, or null if the origin account does not exist.
     */
    public String transferBetweenAccounts(EventDTO dto) {
        Account origin = accountService.getAccountById(dto.getOrigin());
        if (origin == null)
            return null;

        Account destination = accountService.getAccountById(dto.getDestination());
        if (destination == null)
            destination = accountService.createNewAccount(Long.parseLong(dto.getDestination()), 0);

        transferBalance(origin, destination, dto.getAmount());

        accountRepository.saveAll(List.of(destination, origin));

        return "{\"origin\": "+"{\"id\":\"" + origin.getId() + "\", \"balance\":" + origin.getBalance() + "}, \"destination\": {\"id\":\"" + destination.getId() + "\", \"balance\":" + destination.getBalance() + "}}";
    }

    private void performWithdrawal(Account account, int amount) {
        account.setBalance(account.getBalance() - amount);
    }

    private void transferBalance(Account origin, Account destination, int amount) {
        origin.setBalance(origin.getBalance() - amount);
        destination.setBalance(destination.getBalance() + amount);
    }

}
