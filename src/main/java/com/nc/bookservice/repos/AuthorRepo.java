package com.nc.bookservice.repos;

import com.nc.bookservice.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AuthorRepo extends PagingAndSortingRepository<Author, Integer>,
        JpaSpecificationExecutor<Author> {
    @Query(
            value = "select * from author where author.first_name = ? or author.last_name = ? ",
            nativeQuery = true)
    Page<Author> findByLastNameOrFirstName(String firstArg, String secondArg, PageRequest pageable);

    @Query(
            value = "select * from author where author.first_name = ? and author.last_name = ? or author.last_name = ? and author.first_name = ?",
            nativeQuery = true)
    Page<Author> findByLastNameAndFirstName(String firstArg, String secondArg, String thirdArg, String fourthArg, PageRequest pageable);

 }
