package ibf2022.paf.assessment.server.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibf2022.paf.assessment.server.Utils;
import ibf2022.paf.assessment.server.Exception.TaskCreationException;
import ibf2022.paf.assessment.server.Exception.UserCreationException;
import ibf2022.paf.assessment.server.models.SuccessResult;
import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;
import ibf2022.paf.assessment.server.repositories.TaskRepository;
import ibf2022.paf.assessment.server.repositories.UserRepository;
import static ibf2022.paf.assessment.server.Consts.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

// Task 7
@Service
public class TodoService {
    private static final Logger log = LoggerFactory.getLogger(TodoService.class);

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TaskRepository taskRepo;

    @Transactional(rollbackFor = { UserCreationException.class, TaskCreationException.class })
    public SuccessResult upsertTask(Map<String, String> data) {
        /*
         * User logic
         */

        // log.info(">> Check data map size before: " + data.size());
        String username = data.remove(FIELD_USER);
        Optional<User> user = userRepo.findUserByUsername(username);
        // log.info(">>> Check data size after: " + data.size());

        String uid;
        if (user.isPresent()) {
            uid = user.get().getUserId();

        } else {
            try {
                uid = userRepo.insertUser(Utils.createNewUser(username));

                if (uid.equals(""))
                    throw new UserCreationException(username + "was not created");

            } catch (DataAccessException e) {
                log.error("--- Error creating user");
                throw new UserCreationException(username + "could not be created");
            }
        }
        log.info(">>> User id is :" + uid);

        /*
         * Task logic
         */
        for (int i = 0; i < data.size() / 3; i++) {
            // log.info(">> Field " + data.get(createField(FIELD_DESC, i)));
            // log.info(">> Field " + data.get(createField(FIELD_PRIO, i)));
            // log.info(">> Field " + data.get(createField(FIELD_DUE, i)));
            Task newTask = new Task();
            newTask.setDescription(
                    data.get(createField(FIELD_DESC, i)));
            newTask.setPriority(
                    Integer.parseInt(data.get(createField(FIELD_PRIO, i))));
            newTask.setDueDate(
                    LocalDate.parse(data.get(createField(FIELD_DUE, i))));
            log.info(">>> Task to insert: " + newTask);

            try {
                if (!taskRepo.insertTask(newTask, uid))
                    throw new TaskCreationException("Could not save task no. " + i);

            } catch (DataAccessException e) {
                throw new TaskCreationException("Failed to save task no. " + i);
            }
        }
        return new SuccessResult(username, data.size() / 3);
    }

    private static String createField(String prefix, Integer idx) {
        return String.format("%s-%d", prefix, idx);
    }
}
