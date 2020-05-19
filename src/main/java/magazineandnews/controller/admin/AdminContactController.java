package magazineandnews.controller.admin;

import magazineandnews.model.Message;
import magazineandnews.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminContactController extends AdminBaseController{
    private final String ASSIGN="Contact";
    @Autowired
    private MessageService messageService;
    @RequestMapping(value = "/contact",method = RequestMethod.GET)
    public ModelAndView listContact(){
        List<Message> contactList=messageService.listContact();
        ModelAndView modelAndView=new ModelAndView("admin/contact");
        modelAndView.addObject("assign",ASSIGN);
        modelAndView.addObject("contactList",contactList);
        return modelAndView;
    }

}
