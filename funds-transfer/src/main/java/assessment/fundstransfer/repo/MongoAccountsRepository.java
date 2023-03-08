package assessment.fundstransfer.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import assessment.fundstransfer.model.Account;

@Repository
public class MongoAccountsRepository {
    @Autowired
    private MongoTemplate template;

    public List<Account> findAccountList() {
        return template.findAll(Account.class);
    }

    public Account findAccountById(String id) {
        Query query = Query.query(
                new Criteria("account_id").is(id));

        return template.findOne(query, Account.class);
    }

    public Double findAccountBalance(String id){
        return findAccountById(id).balance();
    }

    public Boolean doesAccountExist(String id) {
        Query query = Query.query(
                new Criteria("accound_id").is(id));

        return template.exists(query, Account.class);
    }

    // Updates one account only...
    public UpdateResult updateAccountBalance(String id, Double newBalance) {
        Query query = Query.query(
                new Criteria("account_id").is(id));
        Update update = new Update().set("balance", newBalance);

        return template.updateFirst(query, update, Account.class);
    }
}
