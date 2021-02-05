package com.yukil.petcaresitterserver.entity;

import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
import com.yukil.petcaresitterserver.repository.BankAccountRepository;
import com.yukil.petcaresitterserver.repository.PetSitterRepository;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PetSitterTest {

    @Autowired
    PetSitterRepository petSitterRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;


    @Test
    @DisplayName("펫시터 생성 테스트")
    public void createPetSitter() throws Exception{
        //given
        BankAccount bankAccount = BankAccount.builder()
                .bank(Bank.KB)
                .owner("kim")
                .accountNumber("1111-2222-3333-4444")
                .build();
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        PetSitter petSitter = PetSitter.builder()
                .name("kim")
                .birthday(LocalDate.of(1970, 1, 20))
                .email("kim@gmail.com")
                .password("pass")
                .bankAccount(Arrays.asList(bankAccount))
                .build();

        PetSitter savedPetsitter = petSitterRepository.save(petSitter);
        //then
        assertThat(savedPetsitter.getEmail()).isEqualTo(petSitter.getEmail());
//        assertThat(savedPetsitter.getBankAccount().getBank()).isEqualTo(bankAccount.getBank());

    }

    
}