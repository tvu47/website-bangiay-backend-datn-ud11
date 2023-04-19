package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Materials;

import java.util.List;

public interface MaterialService {

    Materials findById(Long id) throws Exception;

    List<Materials> findAll();

    Materials save(Materials materials);

    Materials findById(List<Materials> materialsList, Long id) throws Exception;

    Materials findByName(List<Materials> materials, String name) throws Exception;

}
