package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Images;

import java.util.List;

public interface ImageService {

    Images save(Images images);

    void delete(Images images);

    void deleteById(Long imageId);

    List<Images> getAll();

    Images findById(Long id) throws Exception;

    List<Images> getImagesByProductId(long productId);

}
