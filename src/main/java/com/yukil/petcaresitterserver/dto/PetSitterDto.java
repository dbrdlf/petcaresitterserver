package com.yukil.petcaresitterserver.dto;

import com.yukil.petcaresitterserver.entity.PetType;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetSitterDto extends RepresentationModel<PetSitterDto> {
    private Long id;
    private String email;
    private String name;
    private String password;
    private LocalDate birthday;
    private Integer age;
    private Set<PetType> petTypes;
    private List<BankAccountDto> bankAccount = new ArrayList<>();
    private TimeAndAreaDto timeAndArea;
}
