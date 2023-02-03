package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.ShoesCollar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoesCollarRepository extends JpaRepository<ShoesCollar, Long> {
}