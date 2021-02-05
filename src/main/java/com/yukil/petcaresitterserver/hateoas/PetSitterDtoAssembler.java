package com.yukil.petcaresitterserver.hateoas;

import com.yukil.petcaresitterserver.controller.PetSitterController;
import com.yukil.petcaresitterserver.dto.BankAccountDto;
import com.yukil.petcaresitterserver.dto.PetSitterDto;
import com.yukil.petcaresitterserver.dto.TimeAndAreaDto;
import com.yukil.petcaresitterserver.entity.BankAccount;
import com.yukil.petcaresitterserver.entity.PetSitter;
import com.yukil.petcaresitterserver.entity.TimeAndArea;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PetSitterDtoAssembler extends RepresentationModelAssemblerSupport<PetSitter, PetSitterDto> {

    public PetSitterDtoAssembler() {
        super(PetSitterController.class, PetSitterDto.class);
    }

    @Override
    public PetSitterDto toModel(PetSitter petSitter) {
        PetSitterDto petSitterDto = PetSitterDto.builder()
                .birthday(petSitter.getBirthday())
                .email(petSitter.getEmail())
                .name(petSitter.getName())
                .petTypes(petSitter.getPetTypes())
                .id(petSitter.getId())
                .age(getAge(petSitter.getBirthday()))
                .password(petSitter.getPassword())
                .timeAndArea(toTimeAndAreaDto(petSitter.getTimeAndArea()))
                .bankAccount(toBankAccountDto(petSitter.getBankAccount()))
                .build();
        petSitterDto.add(linkTo(methodOn(PetSitterController.class).getPetSitter(petSitter.getId())).withSelfRel());
        return petSitterDto;
    }

    private List<BankAccountDto> toBankAccountDto(List<BankAccount> bankAccounts) {
        if (bankAccounts == null) {
            return new ArrayList<>();
        }
        return bankAccounts.stream().map(b -> BankAccountDto.builder()
                .accountNumber(b.getAccountNumber())
                .bank(b.getBank())
                .id(b.getId())
                .owner(b.getOwner())
                .build()
        ).collect(Collectors.toList());
    }

    private TimeAndAreaDto toTimeAndAreaDto(TimeAndArea timeAndArea) {
        if (timeAndArea == null) {
            return new TimeAndAreaDto();
        }
        return TimeAndAreaDto.builder()
                .fromTime(timeAndArea.getFromTime())
                .possibleArea(timeAndArea.getPossibleArea())
                .id(timeAndArea.getId())
                .toTime(timeAndArea.getToTime())

                .build()
                ;
    }


    private Integer getAge(LocalDate birthday) {
        if (birthday == null) {
            return 0;
        }
        Period period = birthday.until(LocalDate.now());
        return period.getYears();
    }
}
