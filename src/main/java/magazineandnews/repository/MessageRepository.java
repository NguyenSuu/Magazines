package magazineandnews.repository;

import magazineandnews.model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MessageRepository extends CrudRepository<Message,Long> {
    @Query("SELECT m " +
            "FROM Message m " +
            "WHERE m.posts.id=:id " +
            "ORDER BY m.id DESC ")
    List<Message> findByPosts_idOrderByIdDesc(@Param("id")Long id);

    @Query("SELECT m " +
            "FROM Message m " +
            "WHERE m.contact=true " +
            "ORDER BY m.id DESC ")
    List<Message> listContact();

    int countAllByPosts_Id(Long id);
}
