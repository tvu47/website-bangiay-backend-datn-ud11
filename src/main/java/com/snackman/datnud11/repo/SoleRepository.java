package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.Sole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SoleRepository extends JpaRepository<Sole, Long> {
}