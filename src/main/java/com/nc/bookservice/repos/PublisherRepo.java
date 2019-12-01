package com.nc.bookservice.repos;

import com.nc.bookservice.entities.Publisher;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface PublisherRepo extends PagingAndSortingRepository<Publisher, Integer>,
        JpaSpecificationExecutor<Publisher> {

     Publisher findByName(String name);
}
