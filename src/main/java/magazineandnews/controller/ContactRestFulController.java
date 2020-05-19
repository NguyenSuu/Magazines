package magazineandnews.controller;


import magazineandnews.model.Message;
import magazineandnews.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactRestFulController {
    @Autowired
    private MessageRepository messageRepository;
    @RequestMapping(value = "/contact/",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<Message>> listContact(){
        List<Message> listConatct=messageRepository.listContact();
        if(listConatct.isEmpty()){
            return new ResponseEntity<List<Message>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Message>>(listConatct, HttpStatus.OK);
    }
    @RequestMapping(value = "/contact/{id}",method = RequestMethod.PUT,produces = "application/json;charset=UTF-8")
    public ResponseEntity<Message> updateMessage(@PathVariable("id")Long id, @RequestBody Message contact){
        Message currentContact= messageRepository.findById(id).get();
        if (currentContact == null) {
            return new ResponseEntity<Message>(HttpStatus.NOT_FOUND);
        }
        currentContact.setChecked(contact.isChecked());
        messageRepository.save(currentContact);
        return new ResponseEntity<Message>(currentContact, HttpStatus.OK);
    }
    @Autowired
    private JavaMailSender mailSender;
    @RequestMapping(value = "/sendmail",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public ResponseEntity<Message> sendMail(@RequestBody Message message){
        SimpleMailMessage crunchifyMsg = new SimpleMailMessage();
        crunchifyMsg.setFrom("ea4eb534fe-35ad2f@inbox.mailtrap.io");
        crunchifyMsg.setTo(message.getEmail());
        crunchifyMsg.setSubject(message.getSubject());
        crunchifyMsg.setText(message.getInfo());
        mailSender.send(crunchifyMsg);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }
}
