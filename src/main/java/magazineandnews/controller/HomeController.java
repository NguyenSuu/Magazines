package magazineandnews.controller;

import magazineandnews.model.Posts;
import magazineandnews.service.MessageService;
import magazineandnews.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class HomeController {
    @Autowired
    private PostsService postsService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/")
    public ModelAndView homePage() {
        Iterable<Posts> postsTopViews = postsService.top3Views();
        Iterable<Posts> postsTopNew = postsService.top4PostsNews();
        List<Posts> listTechnologyPageViewDESC=postsService.listTechnologyPageViewDESC();
        List<Posts>listFashionPageViewDESC =postsService.listFashionPageViewDESC();
        ModelAndView modelAndView = new ModelAndView("views/index");
        modelAndView.addObject("postsTopViews", postsTopViews);
        modelAndView.addObject("postsTopNew", postsTopNew);
        modelAndView.addObject("listTechnologyPageViewDESC",listTechnologyPageViewDESC);
        modelAndView.addObject("listFashionPageViewDESC",listFashionPageViewDESC);
        return modelAndView;
    }
}
