package assessment.fundstransfer.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsRepository {
    @Autowired
    private JdbcTemplate template;

    
}
