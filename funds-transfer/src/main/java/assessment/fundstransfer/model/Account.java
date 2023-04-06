package assessment.fundstransfer.model;

import org.springframework.data.mongodb.core.mapping.Document;

/*
 * Entity to retrieve account details from MySql: acme_bank.account
 */
@Document(collection = "accounts")
public record Account(
    String accountId,
    String firstName,
    Double balance
) {
    // public Account {
    //     balance = balance > 10d ? balance : null;
    // }
}

