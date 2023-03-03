package assessment.fundstransfer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import assessment.fundstransfer.model.Account;
import assessment.fundstransfer.repo.AccountsRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FundsTransferService {
    @Autowired
    private AccountsRepository repoSql;

    public List<Account> getAccountList(){
        log.info(">>> Retrieving list of accounts...");
        return repoSql.findAccountList();
    }
}
