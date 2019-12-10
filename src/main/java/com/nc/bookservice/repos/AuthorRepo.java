package com.nc.bookservice.repos;

import com.nc.bookservice.entities.Author;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AuthorRepo extends PagingAndSortingRepository<Author, Integer>,
        JpaSpecificationExecutor<Author> {
}
