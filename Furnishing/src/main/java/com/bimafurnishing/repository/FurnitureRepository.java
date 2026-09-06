package com.bimafurnishing.repository;

import com.bimafurnishing.model.Furniture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FurnitureRepository extends JpaRepository<Furniture, Long> {}