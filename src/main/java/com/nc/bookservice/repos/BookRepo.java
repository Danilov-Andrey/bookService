package com.nc.bookservice.repos;

import com.nc.bookservice.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepo extends PagingAndSortingRepository<Book, Integer>,
        JpaSpecificationExecutor<Book> {
    Page<Book> findByAuthor_Id(int author_id, Pageable pageable);

    Page<Book> findByPublisher_Id(int publisher_id, Pageable pageable);

    Page<Book> findByName( String name, Pageable pageable);

}
