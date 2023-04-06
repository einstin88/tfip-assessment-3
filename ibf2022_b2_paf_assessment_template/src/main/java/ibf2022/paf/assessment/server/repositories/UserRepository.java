package ibf2022.paf.assessment.server.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.models.User;
import static ibf2022.paf.assessment.server.Queries.*;

// Task 3
@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate template;

    public Optional<User> findUserByUsername(String username) {
        try {
            return Optional.of(
                    template.queryForObject(
                            SQL_FIND_USERNAME,
                            BeanPropertyRowMapper.newInstance(User.class),
                            username));

        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public String insertUser(User user) {
        String uid = UUID.randomUUID().toString().substring(0, 8);

        return template.update(SQL_INSERT_USER,
                uid,
                user.getUsername(),
                user.getName()) > 0 ? uid : "";

    }
}
