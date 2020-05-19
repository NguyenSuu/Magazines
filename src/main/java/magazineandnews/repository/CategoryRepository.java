package magazineandnews.repository;

import magazineandnews.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,Long> {
    @Query("SELECT c FROM Category c WHERE c.id=1")
    Category TechnologyCategory();

    @Query("SELECT c FROM Category c WHERE c.id=2")
    Category FashionCategory();
}
