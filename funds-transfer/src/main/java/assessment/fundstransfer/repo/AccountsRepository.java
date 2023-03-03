package assessment.fundstransfer.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static assessment.fundstransfer.data.Queries.*;
import assessment.fundstransfer.model.Account;

@Repository
public class AccountsRepository {
    @Autowired
    private JdbcTemplate template;

    public List<Account> findAccountList() {
        return template.query(SQL_SEL_ACCOUNTS,
                DataClassRowMapper.newInstance(Account.class));
    }

    public Account findAccountById(String id) {
        return template.queryForObject(SQL_SEL_ACCOUNT_ID,
                DataClassRowMapper.newInstance(Account.class),
                id);
    }

    public int[] updateAccountBalance(List<Object[]> updateDetails) {

        return template.batchUpdate(SQL_UPD_ACCOUNT_BALANCE, updateDetails);
    }
}
