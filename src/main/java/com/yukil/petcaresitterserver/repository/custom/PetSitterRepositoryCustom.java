package com.yukil.petcaresitterserver.repository.custom;

import com.yukil.petcaresitterserver.dto.PetSitterQueryParam;
import com.yukil.petcaresitterserver.entity.PetSitter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PetSitterRepositoryCustom {
    Page<PetSitter> queryPetSitter(Pageable pageable, PetSitterQueryParam param);

}
