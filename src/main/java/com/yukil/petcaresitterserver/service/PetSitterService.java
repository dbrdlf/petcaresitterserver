package com.yukil.petcaresitterserver.service;

import com.yukil.petcaresitterserver.dto.PetSitterParam;
import com.yukil.petcaresitterserver.dto.PetSitterQueryParam;
import com.yukil.petcaresitterserver.entity.PetSitter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PetSitterService {
    PetSitter createPetSitter(PetSitterParam petSitterParam);

    PetSitter getPetSitter(Long id);

    Page<PetSitter> queryPetSitter(Pageable pageable, PetSitterQueryParam param);

}
