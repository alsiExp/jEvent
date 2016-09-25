package ru.jevent.web.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.jevent.LoggedUser;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Comment;
import ru.jevent.service.CommentService;

@Controller
public class CommentRestController {
    private static final LoggerWrapper LOG = LoggerWrapper.get(CommentRestController.class);

    private final CommentService service;

    @Autowired
    public CommentRestController(CommentService service) {
        this.service = service;
    }

    public Comment create(Comment comment) {
        long userId = LoggedUser.id();
        LOG.info("create {} by user {}", comment, userId);
        return service.save(comment);
    }

    public Comment update(Comment comment) {
        long userId = LoggedUser.id();
        LOG.info("update {} by user {}", comment, userId);
        return service.update(comment);
    }

    public void get(long id) {
        long userId = LoggedUser.id();
        LOG.info("get comment {} by user {}", id, userId);
        service.get(id);
    }

    public void delete(long id) {
        long userId = LoggedUser.id();
        LOG.info("delete {} by user {}", id, userId);
        service.delete(id);
    }


}
