package magazineandnews.service;


import magazineandnews.model.Posts;

import java.util.List;

public interface PostsService extends IService<Posts> {
    Iterable<Posts> top3Views();
    Iterable<Posts> top4PostsNews();
    //Technology
    List<Posts> listTechnologyPageViewDESC();
    List<Posts> getListTechnologyPage();
    //Fashion
    List<Posts> listFashionPageViewDESC();
    List<Posts> getListFashionPage();
}
