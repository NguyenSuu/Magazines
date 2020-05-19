package magazineandnews.controller;


import magazineandnews.model.Message;
import magazineandnews.model.Posts;
import magazineandnews.service.MessageService;
import magazineandnews.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
public class FashionViewsController {
    @Autowired
    private PostsService postsService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/fashion")
    public ModelAndView fashionPage() {
        Iterable<Posts> postsTopViews = postsService.top3Views();
        Iterable<Posts> postsTopNew = postsService.top4PostsNews();
        List<Posts> listViewDESC = postsService.listFashionPageViewDESC();
        ModelAndView modelAndView = new ModelAndView("views/fashion/index");
        modelAndView.addObject("postsTopViews", postsTopViews);
        modelAndView.addObject("postsTopNew", postsTopNew);
        modelAndView.addObject("listViewDESC", listViewDESC);
        return modelAndView;
    }

    @GetMapping("/fashion/{id}")
    public ModelAndView infoTechWithId(@PathVariable("id") Long id) {
        Iterable<Posts> postsTopViews = postsService.top3Views();
        Iterable<Posts> postsTopNew = postsService.top4PostsNews();
        Posts posts = postsService.findById(id);
        posts.increase();
        postsService.save(posts);
        List<Message> message = messageService.findByFashion_id(id);
        int count=messageService.countMessOfPosts(id);
        ModelAndView modelAndView = new ModelAndView("views/fashion/single");
        modelAndView.addObject("fashion", posts);
        modelAndView.addObject("count",count);
        modelAndView.addObject("message", message);
        modelAndView.addObject("mess", new Message());
        modelAndView.addObject("postsTopViews", postsTopViews);
        modelAndView.addObject("postsTopNew", postsTopNew);
        return modelAndView;
    }

    @PostMapping("/fashion")
    public ModelAndView saveFeedBack(@Valid @ModelAttribute("mess") Message mess, BindingResult bindingResult) {
        new Message().validate(mess, bindingResult);
        Long id = mess.getPosts().getId();
        Posts fashion = postsService.findById(id);
        Iterable<Posts> postsTopViews = postsService.top3Views();
        Iterable<Posts> postsTopNew = postsService.top4PostsNews();
        ModelAndView modelAndView = new ModelAndView("views/fashion/single");
        modelAndView.addObject("fashion", fashion);
        modelAndView.addObject("mess", new Message());
        modelAndView.addObject("postsTopViews", postsTopViews);
        modelAndView.addObject("postsTopNew", postsTopNew);
        if (bindingResult.hasFieldErrors()) {
            List<Message> message = messageService.findByFashion_id(id);
            modelAndView.addObject("message", message);
            return modelAndView;
        } else {
            mess.setTime(LocalDate.now());
            messageService.save(mess);
            int count=messageService.countMessOfPosts(id);
            modelAndView.addObject("count",count);
            List<Message> message1 = messageService.findByFashion_id(id);
            modelAndView.addObject("message", message1);
            return modelAndView;
        }
    }
}
