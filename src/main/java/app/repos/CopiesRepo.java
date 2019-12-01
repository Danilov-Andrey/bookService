package app.repos;

import app.entities.Copies;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CopiesRepo extends PagingAndSortingRepository<Copies, Integer>,
        JpaSpecificationExecutor<Copies> {
}
