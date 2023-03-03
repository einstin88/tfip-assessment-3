package assessment.fundstransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import assessment.fundstransfer.model.Transaction;
import assessment.fundstransfer.utils.Consts;
import assessment.fundstransfer.utils.Utils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LogAuditService {
    @Autowired
    @Qualifier(Consts.DB_REDIS)
    RedisTemplate<String, String> template;

    /*
     * Simple service to log successful transactions to redis
     */
    public void insertLog(Transaction transaction) {
        log.info(">>> Logging transaction to Redis...");
        template.opsForValue()
                .set(
                        transaction.transactionId(),
                        Utils.createJsonTransaction(transaction));
    }
}
