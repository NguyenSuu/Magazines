package magazineandnews.controller;

import magazineandnews.model.Message;
import magazineandnews.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;


@Controller
public class ContactController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/contact")
    public ModelAndView contactPage() {
        ModelAndView modelAndView = new ModelAndView("views/contact");
        modelAndView.addObject("mess", new Message());
        return modelAndView;
    }

    @PostMapping("/contact")
    public ModelAndView saveMessageContact(@Valid @ModelAttribute("mess") Message mess, BindingResult bindingResult) {
        new Message().validate(mess, bindingResult);
        mess.setContact(true);
        mess.setTime(LocalDate.now());
        ModelAndView modelAndView = new ModelAndView("views/contact");
        modelAndView.addObject("mess", new Message());
        if (bindingResult.hasFieldErrors()) {
            return modelAndView;
        } else {
            messageService.save(mess);
            return modelAndView;
        }
    }

}