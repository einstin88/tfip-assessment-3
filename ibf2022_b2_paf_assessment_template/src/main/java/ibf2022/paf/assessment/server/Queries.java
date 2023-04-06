package ibf2022.paf.assessment.server;

public class Queries {
    public static final String SQL_FIND_USERNAME = """
            SELECT *
            FROM user
            WHERE username = ?
                """;

    public static final String SQL_INSERT_USER = """
            INSERT INTO user (user_id, username, name)
            VALUES (?, ?, ?)
                """;

    public static final String SQL_INSERT_TASK = """
            INSERT INTO task (user_id, description, priority, due_date)
            VALUES (?, ?, ?, ?)
                """;
}
