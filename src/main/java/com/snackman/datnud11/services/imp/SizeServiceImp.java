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
    public Size findById(Long id) throws Exception{
        Optional<Size> optional = this.repo.findById(id);
        if(optional.isEmpty()){
            throw new Exception("Not found size id " + id);
        }
        return optional.get();
    }

    @Override
    public List<Size> findAll() {
        return this.repo.findAll();
    }
}
