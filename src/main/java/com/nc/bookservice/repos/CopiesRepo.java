package com.nc.bookservice.repos;

import com.nc.bookservice.entities.Copies;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CopiesRepo extends PagingAndSortingRepository<Copies, Integer>,
        JpaSpecificationExecutor<Copies> {
}
