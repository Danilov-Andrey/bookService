package app.repos;

import app.entities.Author;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AuthorRepo extends PagingAndSortingRepository<Author, Integer>,
        JpaSpecificationExecutor<Author> {

}
