package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.dto.SizeDTO;
import com.snackman.datnud11.entity.Size;
import com.snackman.datnud11.repo.ProductsRepository;
import com.snackman.datnud11.repo.SizeRepository;
import com.snackman.datnud11.services.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SizeServiceImp implements SizeService {

    @Autowired
    private SizeRepository repo;

    @Override
    public Size save(Size size) {
        return this.repo.save(size);
    }

    @Override
    public void delete(Size size) {
        this.repo.delete(size);
    }

    @Override
    public void delete(Long id) {
        this.repo.deleteById(id);
    }

    @Override
    public Size findById(Long id) {
      System.out.println("size id: "+ id);
        Optional<Size> optional = this.repo.findById(id);
        if(optional.isEmpty()){
            throw new RuntimeException("Not found size id " + id);
        }
        return optional.get();
    }

    @Override
    public Size findById(List<Size> sizeList, Long id) throws Exception {
        for(Size size: sizeList){
            if(size.getId() == id){
                return size;
            }
        }
        throw new Exception("not found size " + id);
    }

    @Override
    public Size findByName(List<Size> sizeList, String name) throws Exception {
        for(Size size : sizeList){
            if(size.getSizeName().equals(name)){
                return size;
            }
        }
        throw new Exception();
    }

    @Override
    public List<Size> findAll() {
        return this.repo.findAll();
    }

    @Override
    public Size findBySizeName(String name) throws Exception {
        List<Size> sizeList = this.repo.findBySizeName(name);
        if(sizeList.isEmpty()){
            throw new Exception("not found size " + name);
        }
        return sizeList.get(0);
    }
}
