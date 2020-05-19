package magazineandnews.controller;

import magazineandnews.model.Message;
import magazineandnews.model.Posts;
import magazineandnews.service.CategoryService;
import magazineandnews.service.MessageService;
import magazineandnews.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class TechnologyViewsController {
    private final String ASSIGN = "technology";
    @Autowired
    private PostsService postsService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/technology")
    public ModelAndView listTechnologyPage() {
        Iterable<Posts> postsTopViews = postsService.top3Views();
        Iterable<Posts> postsTopNew = postsService.top4PostsNews();
        List<Posts> listViewDESC = postsService.listTechnologyPageViewDESC();
        ModelAndView modelAndView = new ModelAndView("views/technology/index");
        modelAndView.addObject("listViewDESC", listViewDESC);
        modelAndView.addObject("postsTopViews", postsTopViews);
        modelAndView.addObject("postsTopNew", postsTopNew);
        return modelAndView;
    }

    @GetMapping("/technology/{id}")
    public ModelAndView infoTechWithId(@PathVariable("id") Long id) {
        Iterable<Posts> postsTopViews = postsService.top3Views();
        Iterable<Posts> postsTopNew = postsService.top4PostsNews();
        Posts technology = postsService.findById(id);
        technology.increase();
        postsService.save(technology);
        int count = messageService.countMessOfPosts(id);
        List<Message> message = messageService.findByTechnology_id(id);
        ModelAndView modelAndView = new ModelAndView("views/technology/single");
        modelAndView.addObject("technology", technology);
        modelAndView.addObject("count", count);
        modelAndView.addObject("message", message);
        modelAndView.addObject("mess", new Message());
        modelAndView.addObject("postsTopViews", postsTopViews);
        modelAndView.addObject("postsTopNew", postsTopNew);
        return modelAndView;
    }

}

