package ru.jevent.web.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.jevent.service.CommentService;

@Controller
public class CommentController {
    @Autowired
    private CommentService service;

    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    public String commentList(Model model) {
        model.addAttribute("commentList", service.getAll());
        return "commentList";
    }
}
