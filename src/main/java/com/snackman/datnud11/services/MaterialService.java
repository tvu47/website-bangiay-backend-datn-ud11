package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Materials;

public interface MaterialService {

    Materials findById(Long id) throws Exception;

}
