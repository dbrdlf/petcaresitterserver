package com.yukil.petcaresitterserver.dto;

import com.yukil.petcaresitterserver.entity.Bank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountParam {
    private String accountNumber;
    private Bank bank;
    private String owner;
}
