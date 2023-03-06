package assessment.fundstransfer.data;

public class Queries {
    public static final String SQL_SEL_ACCOUNTS = """
            SELECT *
            FROM accounts;
                """;

    public static final String SQL_SEL_ACCOUNT_ID = """
            SELECT *
            FROM accounts
            WHERE account_id = ?
                """;

    public static final String SQL_SEL_ACCOUNT_BALANCE = """
            SELECT balance
            FROM accounts
            WHERE account_id = ?
                """;

    public static final String SQL_UPD_ACCOUNT_BALANCE = """
            UPDATE accounts
            SET balance = ?
            WHERE account_id = ?
                """;
}
