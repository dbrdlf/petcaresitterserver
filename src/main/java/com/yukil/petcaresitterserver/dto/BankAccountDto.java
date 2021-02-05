package com.yukil.petcaresitterserver.dto;

import com.yukil.petcaresitterserver.entity.Bank;
import com.yukil.petcaresitterserver.entity.BankAccount;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BankAccountDto extends RepresentationModel<BankAccountDto> {
    private Long id;
    private String accountNumber;
    private Bank bank;
    private String owner;
}
