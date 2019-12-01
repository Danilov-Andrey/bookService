package com.nc.bookservice.services;

import com.nc.bookservice.entities.Copies;
import com.nc.bookservice.repos.CopiesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CopiesService {
    @Autowired
    CopiesRepo copiesRepo;

    private boolean existsById(int id){
        return copiesRepo.existsById(id);
    }

    public Copies findById(int id) throws Exception{
        Copies copies = copiesRepo.findById(id).orElse(null);
        if (copies == null){
            throw new Exception("Cannot find copies with id: " + id);
        }
        else return copies;
    }

    public List<Copies> findAll(int pageNumber, int rowPerPage){
        List<Copies> copies = new ArrayList<>();
        copiesRepo.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(copies::add);
        return copies;
    }

    public void updateCopies(int id, Copies copies) throws Exception {
        Copies updatedCopies = findById(id);
        updatedCopies.setCount(copies.getCount());
        updatedCopies.setRate(copies.getRate());
        copiesRepo.save(updatedCopies);
    }
}
