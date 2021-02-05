package com.yukil.petcaresitterserver.controller;

import com.yukil.petcaresitterserver.dto.PetSitterDto;
import com.yukil.petcaresitterserver.dto.PetSitterParam;
import com.yukil.petcaresitterserver.dto.PetSitterQueryParam;
import com.yukil.petcaresitterserver.dto.PetSitterQueryParamValidator;
import com.yukil.petcaresitterserver.entity.PetSitter;
import com.yukil.petcaresitterserver.hateoas.PetSitterDtoAssembler;
import com.yukil.petcaresitterserver.service.PetSitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/pet-sitter")
@RequiredArgsConstructor
public class PetSitterController {

    private final PetSitterService petSitterService;
    private final PetSitterDtoAssembler petSitterDtoAssembler;
    private final PagedResourcesAssembler pagedResourcesAssembler;
    private final PetSitterQueryParamValidator petSitterQueryParamValidator;

    @PostMapping
    public ResponseEntity createPetSitter(@RequestBody PetSitterParam petSitterParam, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        PetSitter petSitter = petSitterService.createPetSitter(petSitterParam);
        PetSitterDto petSitterDto = petSitterDtoAssembler.toModel(petSitter);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(PetSitterController.class).slash(petSitterDto.getId());
        petSitterDto.add(selfLinkBuilder.withRel("update-pet-sitter"));
        petSitterDto.add(selfLinkBuilder.withRel("delete-pet-sitter"));
        petSitterDto.add(selfLinkBuilder.withRel("get-pet-sitter"));
        petSitterDto.add(linkTo(PetSitterController.class).withRel("query-pet-sitters"));
        petSitterDto.add(Link.of("docs/index.html#resources-create-pet-sitter", "profile"));
        return ResponseEntity.ok(petSitterDto);
    }


    @GetMapping("/{id}")
    public ResponseEntity getPetSitter(@PathVariable("id") Long id) {
        PetSitter petSitter = petSitterService.getPetSitter(id);
        if (petSitter == null) {
            return ResponseEntity.noContent().build();
        }
        PetSitterDto petSitterDto = petSitterDtoAssembler.toModel(petSitter);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(PetSitterController.class).slash(petSitterDto.getId());
        petSitterDto.add(selfLinkBuilder.withRel("update-pet-sitter"));
        petSitterDto.add(selfLinkBuilder.withRel("delete-pet-sitter"));
        petSitterDto.add(linkTo(PetSitterController.class).withRel("query-pet-sitters"));
        petSitterDto.add(Link.of("docs/index.html#resources-get-pet-sitter", "profile"));
        return ResponseEntity.ok(petSitterDto);
    }

    @GetMapping
    public ResponseEntity queryPetSitter(Pageable pageable,@Valid PetSitterQueryParam param, Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        petSitterQueryParamValidator.validate(param, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        Page<PetSitter> petSitterPage = petSitterService.queryPetSitter(pageable, param);
        if (petSitterPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        PagedModel pagedModel = pagedResourcesAssembler.toModel(petSitterPage, petSitterDtoAssembler);
        return ResponseEntity.ok(pagedModel);
    }

    private ResponseEntity badRequest(Errors errors) {
        EntityModel errorModel = EntityModel.of(errors);

        return ResponseEntity.badRequest().body(errorModel);
    }
}
