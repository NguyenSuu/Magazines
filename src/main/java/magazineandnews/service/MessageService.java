package magazineandnews.service;

import magazineandnews.model.Message;

import java.util.List;

public interface MessageService extends IService<Message>{
    List<Message> findByTechnology_id(Long id);
    void save(Message message);
    List<Message> findByFashion_id(Long id);
    int countMessOfPosts(Long id);
    List<Message> listContact();

}
