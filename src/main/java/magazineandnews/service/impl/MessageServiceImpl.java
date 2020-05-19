package magazineandnews.service.impl;

import magazineandnews.model.Message;
import magazineandnews.repository.MessageRepository;
import magazineandnews.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<Message> findByTechnology_id(Long id) {
        return messageRepository.findByPosts_idOrderByIdDesc(id);
    }

    @Override
    public List<Message> findAll() {
        return (List<Message>) messageRepository.findAll();
    }

    @Override
    public Message findById(Long id) {
        return messageRepository.findById(id).get();
    }

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }

    @Override
    public void remove(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public List<Message> findByFashion_id(Long id) {
        return messageRepository.findByPosts_idOrderByIdDesc(id);
    }

    @Override
    public int countMessOfPosts(Long id) {
        return messageRepository.countAllByPosts_Id(id);
    }

    @Override
    public List<Message> listContact() {
        return messageRepository.listContact();
    }

}
