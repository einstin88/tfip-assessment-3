package assessment.fundstransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import assessment.fundstransfer.repo.AccountsRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FundsTransferService {
    @Autowired
    private AccountsRepository repoSql;

    
}
