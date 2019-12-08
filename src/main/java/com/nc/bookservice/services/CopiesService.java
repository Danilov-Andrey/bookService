package com.nc.bookservice.services;

import com.nc.bookservice.dto.DataPagination;
import com.nc.bookservice.entities.Copies;
import com.nc.bookservice.repos.CopiesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    public Copies findById(int id) throws Exception{
        Copies copies = copiesRepo.findById(id).orElse(null);
        if (copies == null){
            throw new Exception("Cannot find copies with id: " + id);
        }
        return copies;
    }

    public DataPagination<Copies> findAll(int pageNumber, int rowPerPage){
        List<Copies> copies = new ArrayList<>();
        int totalPage = (int) Math.ceil((float)copiesRepo.count()/rowPerPage);
        copiesRepo.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(copies::add);
        DataPagination dataPagination = new DataPagination(totalPage, pageNumber, copies);
        return dataPagination;
    }

    public void updateCopies(int id, Copies copies) throws Exception {
        Copies updatedCopies = findById(id);
        updatedCopies.setCount(copies.getCount());
        updatedCopies.setRate(copies.getRate());
        copiesRepo.save(updatedCopies);
    }
}
