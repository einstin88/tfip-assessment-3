package ibf2022.paf.assessment.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ibf2022.paf.assessment.server.models.SuccessResult;
import ibf2022.paf.assessment.server.services.TodoService;

// Task 8
@Controller
public class TasksController {
    private static final Logger log = LoggerFactory.getLogger(TasksController.class);

    @Autowired
    private TodoService svc;

    // Task 4
    @PostMapping(path = "/task", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveTodoTasks(
            @RequestBody MultiValueMap<String, String> form,
            Model model) {

        log.info(">>> Submitted form: " + form);

        SuccessResult result = svc.upsertTask(form.toSingleValueMap());
        model.addAttribute("username", result.username());
        model.addAttribute("taskCount", result.tasksAdded());

        return "result";
    }
}
