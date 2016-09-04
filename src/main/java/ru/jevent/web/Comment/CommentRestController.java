package ru.jevent.web.Comment;

import ru.jevent.LoggedUser;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Comment;
import ru.jevent.service.CommentService;
import ru.jevent.web.Event.EventRestController;

public class CommentRestController {
    private static final LoggerWrapper LOG = LoggerWrapper.get(EventRestController.class);

    private CommentService service;

    public Comment create(Comment comment) {
        long userId = LoggedUser.id();
        LOG.info("create {} by user {}", comment, userId);
        return service.save(comment, userId);
    }

    public Comment update(Comment comment) {
        long userId = LoggedUser.id();
        LOG.info("update {} by user {}", comment, userId);
        return service.update(comment, userId);
    }

    public void get(long id) {
        long userId = LoggedUser.id();
        LOG.info("get comment {} by user {}", id, userId);
        service.get(id, userId);
    }

    public void delete(long id) {
        long userId = LoggedUser.id();
        LOG.info("delete {} by user {}", id, userId);
        service.delete(id, userId);
    }


}
