package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.Materials;
import com.snackman.datnud11.repo.MaterialsRepository;
import com.snackman.datnud11.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<Materials> findAll() {
        return this.repo.findAll();
    }

    @Override
    public Materials findMaterialByName(String materialName) {
        if (repo.findMaterialsByMaterialName(materialName).isEmpty()){
            throw new RuntimeException("Material is not exists..");
        }
        return repo.findMaterialsByMaterialName(materialName).get();
    }

    @Override
    public Materials findById(List<Materials> materialsList, Long id) throws Exception {
        for(Materials materials: materialsList){
            if(materials.getId() == id){
                return materials;
            }
        }
        throw new Exception("not found material id " + id);
    }
}
