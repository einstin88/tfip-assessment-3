package assessment.fundstransfer.model;

import java.time.LocalDate;

/* Entity to record successful transactions */
public record Transaction(
        String transactionId,
        LocalDate date,
        Account from_account,
        Account to_account,
        Double amount) {

}
