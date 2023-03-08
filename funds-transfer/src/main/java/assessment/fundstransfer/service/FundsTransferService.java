package assessment.fundstransfer.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import assessment.fundstransfer.model.Account;
import assessment.fundstransfer.model.Transaction;
import assessment.fundstransfer.model.TransferRequest;
import assessment.fundstransfer.repo.AccountsRepository;
import assessment.fundstransfer.utils.Utils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FundsTransferService {
    @Autowired
    private AccountsRepository repoSql;

    /*
     * Retrieves all available accounts on MySql
     */
    public List<Account> getAccountList() {
        log.info(">>> Retrieving list of accounts...");
        return repoSql.findAccountList();
    }

    /*
     * Carries out further validations on conditions c0,2,5
     * - c0: accounts 'from' and 'to' must exist
     * - c2: cannot transfer to same account
     * - c5: can only transfer if account has sufficient balance
     */
    public void additionalTransferValidation(
            TransferRequest form, BindingResult result) {

        if (c0(form.from(), form.to())) {
            ObjectError err = new FieldError("accountErr", "from",
                    "Invalid 'from' or 'to' account");

            result.addError(err);
            return;
        }

        if (c2(form.from(), form.to())) {
            FieldError err = new FieldError("toErr", "from",
                    "Cannot transfer to same account");

            result.addError(err);
            return;
        }

        if (c5(form.from(), form.amount())) {
            FieldError err = new FieldError("amtErr", "amount",
                    "Insufficient balance to transfer");

            result.addError(err);
            return;
        }
    }

    /*
     * Perform updates on account balances
     * - Generate unique ID
     * - Verify that updates are successful
     * - Generate transaction document
     */
    @Transactional
    public Transaction executeTransaction(TransferRequest form, BindingResult bindingResult) {

        String transactionID = UUID.randomUUID().toString().substring(0, 8);
        log.info(">>> Generated new Transaction ID: " + transactionID);

        Account from = repoSql.findAccountById(form.from());
        Account to = repoSql.findAccountById(form.to());
        Double amount = form.amount();

        // Updates account balances of sender and receiver
        int[] results = repoSql.updateAccountBalance(
                Utils.createUpdateDetails(
                        from,
                        to,
                        amount));

        // Verify if updates are successful
        for (int result : results) {
            if (result < 1) {
                FieldError err = new FieldError("transationErr", "amount",
                        "Transfer failed");

                bindingResult.addError(err);

                throw new DataAccessException("Transfer failed") {
                };
            }
        }

        // Generate transaction document
        return Utils.createTransactionResult(
                transactionID,
                from,
                to,
                amount);
    }

    /*
     * Helper functions for validating c0,2,5
     */
    private Boolean c0(String from, String to) {
        try {
            repoSql.findAccountById(from);
            repoSql.findAccountById(to);

            return false;
        } catch (DataAccessException e) {
            return true;
        }
    }

    private Boolean c2(String from, String to) {
        return from.equals(to);
    }

    private Boolean c5(String from, Double amount) {
        if (repoSql.findAccountBalance(from) < amount)
            return true;

        return false;
    }
}
