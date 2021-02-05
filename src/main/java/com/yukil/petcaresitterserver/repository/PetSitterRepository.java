package com.yukil.petcaresitterserver.repository;

import com.yukil.petcaresitterserver.entity.PetSitter;
import com.yukil.petcaresitterserver.repository.custom.PetSitterRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetSitterRepository extends JpaRepository<PetSitter, Long>, PetSitterRepositoryCustom {
}
