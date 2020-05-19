package magazineandnews.controller;

import magazineandnews.model.Message;
import magazineandnews.repository.MessageRepository;
import magazineandnews.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class MessageRestFulController {
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private MessageRepository messageRepository;

    @RequestMapping(value = "/message/{id}",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<Message>> listMessage(@PathVariable("id")Long id){
        List<Message> messages=messageRepository.findByPosts_idOrderByIdDesc(id);
        if(messages.isEmpty()){
            return new ResponseEntity<List<Message>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
    }

    @RequestMapping(value = "/message/add",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        message.setPosts(postsRepository.findById(message.getId_post()).get());
        message.setTime(LocalDate.now());
        messageRepository.save(message);
        return new ResponseEntity<Message>(message, HttpStatus.CREATED);
    }


}
