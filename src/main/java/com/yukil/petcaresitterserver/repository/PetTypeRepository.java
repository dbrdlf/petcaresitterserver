package com.yukil.petcaresitterserver.repository;

import com.yukil.petcaresitterserver.entity.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetTypeRepository extends JpaRepository<PetType, Long> {
}
