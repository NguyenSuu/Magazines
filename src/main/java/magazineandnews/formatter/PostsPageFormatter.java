package magazineandnews.formatter;

import magazineandnews.model.Posts;
import magazineandnews.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class PostsPageFormatter implements Formatter<Posts> {

    @Autowired
    private PostsService technologyService;

    @Override
    public Posts parse(String s, Locale locale) throws ParseException {
        return technologyService.findById(Long.parseLong(s));
    }

    @Override
    public String print(Posts technologyPage, Locale locale) {
        return "[" + technologyPage.getId() + ", " +technologyPage.getTitle() + "]";
    }
}
