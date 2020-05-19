package magazineandnews.controller;

import magazineandnews.model.Posts;
import magazineandnews.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class PostsRestFulController {
    @Autowired
    private PostsService postsService;

    @RequestMapping(value = "/technology/", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<Posts>> listTechnology() {
        List<Posts> technologies = postsService.getListTechnologyPage();
        if (technologies.isEmpty()) {
            return new ResponseEntity<List<Posts>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Posts>>(technologies, HttpStatus.OK);
    }

    @RequestMapping(value = "/fashion/", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<Posts>> listFashion() {
        List<Posts> fashionList = postsService.getListFashionPage();
        if (fashionList.isEmpty()) {
            return new ResponseEntity<List<Posts>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Posts>>(fashionList, HttpStatus.OK);
    }

    @RequestMapping(value = "/posts/{id}",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public ResponseEntity<Posts> findById(@PathVariable("id") Long id){
        Posts posts=postsService.findById(id);
        if(posts==null){
            return new ResponseEntity<Posts>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Posts>(posts, HttpStatus.OK);
    }
}
