package assessment.fundstransfer.model;

/*
 * Entity to retrieve account details from MySql: acme_bank.account
 */
public record Account(
    String accountId,
    String firstName,
    Double balance
) {
    
}
