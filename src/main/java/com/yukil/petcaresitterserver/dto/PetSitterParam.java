package com.yukil.petcaresitterserver.dto;

import com.yukil.petcaresitterserver.entity.BankAccount;
import com.yukil.petcaresitterserver.entity.PetType;
import com.yukil.petcaresitterserver.entity.TimeAndArea;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetSitterParam {
    private String email;
    private String name;
    private String password;
    private LocalDate birthday;
    private Set<PetType> petTypes;
    private BankAccountParam bankAccountParam;
    private TimeAndAreaParam timeAndAreaParam;
}
