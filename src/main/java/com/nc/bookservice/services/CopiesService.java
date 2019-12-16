package com.nc.bookservice.services;

import com.nc.bookservice.entities.Copies;
import com.nc.bookservice.exceptions.copies.CopiesNotFoundException;
import com.nc.bookservice.repos.CopiesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CopiesService {
    private CopiesRepo copiesRepo;

    @Autowired
    public CopiesService(CopiesRepo copiesRepo){
        this.copiesRepo = copiesRepo;
    }

    public Copies findCopiesById(int id) {
        Copies copies = copiesRepo.findById(id).orElse(null);
        if (copies == null){
            throw new CopiesNotFoundException("Cannot find copies with id: " + id);
        }
        return copies;
    }

    public Page<Copies> findCopies(int pageNumber, int rowPerPage, String sortBy, Sort.Direction direction){
        Page<Copies> copies = copiesRepo.findAll(PageRequest.of(pageNumber - 1, rowPerPage,  Sort.by(direction, sortBy)));
        if (copies.getTotalElements() == 0){
            throw new CopiesNotFoundException("Cannot find any copies");
        }
        return copies;
    }

    public Copies updateCopies(int id, Copies copies) {
        Copies updatedCopies = findCopiesById(id);
        updatedCopies.setCount(copies.getCount());
        updatedCopies.setRate(copies.getRate());
        return copiesRepo.save(updatedCopies);
    }
}
