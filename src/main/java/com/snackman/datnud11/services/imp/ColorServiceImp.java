package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.Colors;
import com.snackman.datnud11.repo.ColorsRepository;
import com.snackman.datnud11.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorServiceImp implements ColorService {

    @Autowired
    private ColorsRepository repo;


    @Override
    public Colors save(Colors colors) {
        return this.repo.save(colors);
    }

    @Override
    public void delete(Colors colors) {
        this.repo.delete(colors);
    }

    @Override
    public void delete(Long id) {
        this.repo.deleteById(id);
    }

    @Override
    public Colors findById(Long id) throws Exception {
        Optional<Colors> optional = this.repo.findById(id);
        if(optional.isEmpty()){
            throw new Exception("Not found color id " + id);
        }
        return optional.get();
    }

    @Override
    public Colors findById(List<Colors> colorsList, Long id) throws Exception {
        for(Colors colors: colorsList){
            if(colors.getId() == id){
                return colors;
            }
        }
        throw new Exception("not found color " + id);
    }

    @Override
    public List<Colors> findAll() {
        return this.repo.findAll();
    }
}
