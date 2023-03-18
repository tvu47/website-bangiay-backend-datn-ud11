package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.Materials;
import com.snackman.datnud11.repo.MaterialsRepository;
import com.snackman.datnud11.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MaterialServiceImp implements MaterialService {

    @Autowired
    private MaterialsRepository repo;

    @Override
    public Materials findById(Long id) throws Exception {
        Optional<Materials> optional = this.repo.findById(id);
        if(optional.isEmpty()){
            throw new Exception("not found");
        }
        return optional.get();
    }
}
