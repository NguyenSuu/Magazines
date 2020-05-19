package magazineandnews.service.impl;


import magazineandnews.model.Posts;
import magazineandnews.repository.PostsRepository;
import magazineandnews.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PostsServiceImpl implements PostsService {
    @Autowired
    private PostsRepository postsRepository;

    @Override
    public List<Posts> findAll() {
        return (List<Posts>) postsRepository.findAll();
    }

    @Override
    public Posts findById(Long id) {
        return postsRepository.findById(id).get();
    }

    @Override
    public void save(Posts object) {
        postsRepository.save(object);
    }

    @Override
    public void remove(Long id) {
        postsRepository.deleteById(id);
    }

    @Override
    public Iterable<Posts> top3Views() {
        return postsRepository.findTop3ByOrderByCountDesc();
    }

    @Override
    public Iterable<Posts> top4PostsNews() {
        return postsRepository.findTop4ByOrderByIdDesc();
    }

    //Technology
    @Override
    public List<Posts> getListTechnologyPage() {
        return postsRepository.getListTechnologyPage();
    }


    @Override
    public List<Posts> listTechnologyPageViewDESC() {
        return postsRepository.listTechnologyPageViewDESC();
    }

    //Fashion
    @Override
    public List<Posts> listFashionPageViewDESC() {
        return postsRepository.listFashionPageViewDESC();
    }

    @Override
    public List<Posts> getListFashionPage() {
        return postsRepository.getListFashionPage();
    }
}
