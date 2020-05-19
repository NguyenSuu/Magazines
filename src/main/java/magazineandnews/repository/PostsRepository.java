package magazineandnews.repository;


import magazineandnews.model.Posts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PostsRepository extends CrudRepository<Posts, Long> {

    @Query("SELECT p " +
            "FROM Posts p ORDER BY p.count DESC   ")
    List<Posts> getAllPosts();

    Iterable<Posts> findTop3ByOrderByCountDesc();

    Iterable<Posts> findTop4ByOrderByIdDesc();


    //    Technology
    @Query("SELECT p " +
            "FROM Posts p " +
            "WHERE p.category.id=1" +
            "ORDER BY p.count DESC ")
    List<Posts> listTechnologyPageViewDESC();

    @Query("SELECT p " +
            "FROM Posts p " +
            "WHERE p.category.id=1" +
            "ORDER BY p.id DESC ")
    List<Posts> getListTechnologyPage();

    //Fashion
    @Query("SELECT p " +
            "FROM Posts p " +
            "WHERE p.category.id=2" +
            "ORDER BY p.count DESC ")
    List<Posts> listFashionPageViewDESC();

    @Query("SELECT p " +
            "FROM Posts p " +
            "WHERE p.category.id=2" +
            "ORDER BY p.id DESC ")
    List<Posts> getListFashionPage();

}
