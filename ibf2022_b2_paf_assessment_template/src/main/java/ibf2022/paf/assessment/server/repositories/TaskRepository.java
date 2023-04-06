package ibf2022.paf.assessment.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.Queries;
import ibf2022.paf.assessment.server.models.Task;

// Task 6
@Repository
public class TaskRepository {
    @Autowired
    private JdbcTemplate template;

    public Boolean insertTask(Task task, String user_id) {
        return template.update(
                Queries.SQL_INSERT_TASK,
                user_id,
                task.getDescription(), task.getPriority(), task.getDueDate()) == 1;
    }
}
