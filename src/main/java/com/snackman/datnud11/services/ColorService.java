package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Colors;

import java.awt.*;
import java.util.List;

public interface ColorService {

    Colors save(Colors colors);

    void delete(Colors colors);

    void delete(Long id);

    Colors findById(Long id);

    Colors findById(List<Colors> colorsList, Long id) throws Exception;

    List<Colors> findAll();

    Colors findByColorName(String colorName) throws Exception;

}
