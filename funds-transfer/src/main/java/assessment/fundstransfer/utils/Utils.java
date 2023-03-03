package assessment.fundstransfer.utils;

import java.time.LocalDate;
import java.util.List;

import assessment.fundstransfer.model.Account;
import assessment.fundstransfer.model.Transaction;
import jakarta.json.Json;

public class Utils {
        /*
         * Generate the parameters to update account balances
         */
        public static final List<Object[]> createUpdateDetails(
                        Account from,
                        Account to,
                        Double amount) {

                return List.of(
                                new Object[] {
                                                from.balance() - amount,
                                                from.accountId()
                                },
                                new Object[] {
                                                to.balance() + amount,
                                                to.accountId()
                                }

                );
        }

        /*
         * Generate the trasaction document
         */
        public static final Transaction createTransactionResult(
                        String transactionId,
                        Account from,
                        Account to,
                        Double amount) {
                return new Transaction(
                                transactionId,
                                LocalDate.now(),
                                from,
                                to,
                                amount);
        }

        /*
         * Convert transaction document to JSON for storage in redis
         */
        public static final String createJsonTransaction(Transaction data) {
                return Json.createObjectBuilder()
                                .add("transactionId", data.transactionId())
                                .add("date", data.date().toString())
                                .add("from_account", data.from_account().accountId())
                                .add("to_account", data.to_account().accountId())
                                .add("amount", data.amount())
                                .build().toString();
        }
}
