package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.Images;
import com.snackman.datnud11.repo.ImagesRepository;
import com.snackman.datnud11.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImp implements ImageService {

    @Autowired
    private ImagesRepository repo;

    @Override
    public Images save(Images images) {
        return this.repo.save(images);
    }

    @Override
    public void delete(Images images) {
        this.repo.delete(images);
    }

    @Override
    public void deleteById(Long imageId) {
        this.repo.deleteById(imageId);
    }

    @Override
    public List<Images> getAll() {
        return this.repo.findAll();
    }

    @Override
    public Images findById(Long id) throws Exception{
        Optional<Images> optional = this.repo.findById(id);
        if(optional.isEmpty()){
            throw new Exception("Not found image id " + id);
        }
        return optional.get();
    }

    @Override
    public List<Images> getImagesByProductId(long productId) {
        return this.repo.findByProductId(productId);
    }
}
