package com.snackman.datnud11.services;

import com.snackman.datnud11.dto.SizeDTO;
import com.snackman.datnud11.entity.Size;

import java.util.List;

public interface SizeService {

    Size save(Size size);

    void delete(Size size);

    void delete(Long id);

    Size findById(Long id);

    Size findById(List<Size> sizeList, Long id) throws Exception;
    List<Size> findAll();

    Size findBySizeName(String name) throws Exception;
}
