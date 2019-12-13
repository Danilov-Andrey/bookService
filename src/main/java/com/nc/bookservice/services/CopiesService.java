package com.nc.bookservice.services;

import com.nc.bookservice.dto.DataPagination;
import com.nc.bookservice.entities.Copies;
import com.nc.bookservice.exceptions.copies.CopiesNotFoundException;
import com.nc.bookservice.repos.CopiesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public DataPagination<Copies> findAllCopies(int pageNumber, int rowPerPage,  String sortBy, Sort.Direction direction){
        List<Copies> copies = new ArrayList<>();
        copiesRepo.findAll(PageRequest.of(pageNumber - 1, rowPerPage,  Sort.by(direction, sortBy))).forEach(copies::add);
        if (copies.size() == 0){
            throw new CopiesNotFoundException("Cannot find any copies");
        }
        int totalPage = (int) Math.ceil((float)copiesRepo.count()/rowPerPage);
        DataPagination<Copies> dataPagination = new DataPagination(totalPage, pageNumber, copies);
        return dataPagination;
    }

    public Copies updateCopies(int id, Copies copies) {
        Copies updatedCopies = findCopiesById(id);
        updatedCopies.setCount(copies.getCount());
        updatedCopies.setRate(copies.getRate());
        return copiesRepo.save(updatedCopies);
    }
}
