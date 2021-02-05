package com.yukil.petcaresitterserver.service;

import com.yukil.petcaresitterserver.dto.PetSitterDto;
import com.yukil.petcaresitterserver.dto.PetSitterParam;
import com.yukil.petcaresitterserver.dto.PetSitterQueryParam;
import com.yukil.petcaresitterserver.entity.BankAccount;
import com.yukil.petcaresitterserver.entity.PetSitter;
import com.yukil.petcaresitterserver.entity.TimeAndArea;
import com.yukil.petcaresitterserver.repository.BankAccountRepository;
import com.yukil.petcaresitterserver.repository.PetSitterRepository;
import com.yukil.petcaresitterserver.repository.TimeAndAreaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PetSitterServiceImpl implements PetSitterService{
    private final PetSitterRepository petSitterRepository;
    private final BankAccountRepository bankAccountRepository;
    private final TimeAndAreaRepository timeAndAreaRepository;
    private final ModelMapper modelMapper;

    @Override
    public PetSitter createPetSitter(PetSitterParam petSitterParam) {
        BankAccount bankAccount = modelMapper.map(petSitterParam.getBankAccountParam(), BankAccount.class);
        TimeAndArea timeAndArea = modelMapper.map(petSitterParam.getTimeAndAreaParam(), TimeAndArea.class);
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        TimeAndArea savedTimeAndArea = timeAndAreaRepository.save(timeAndArea);
        PetSitter petSitter = PetSitter.builder()
                .password(petSitterParam.getPassword())
                .email(petSitterParam.getEmail())
                .petTypes(petSitterParam.getPetTypes())
                .name(petSitterParam.getName())
                .birthday(petSitterParam.getBirthday())
                .bankAccount(Arrays.asList(savedBankAccount))
                .timeAndArea(savedTimeAndArea)
                .build();
        return  petSitterRepository.save(petSitter);

    }

    @Override
    public PetSitter getPetSitter(Long id) {
        Optional<PetSitter> optionalPetSitter = petSitterRepository.findById(id);
        if (!optionalPetSitter.isPresent()) {
            return null;
        }
        return optionalPetSitter.get();
    }

    @Override
    public Page<PetSitter> queryPetSitter(Pageable pageable, PetSitterQueryParam param) {
        return petSitterRepository.queryPetSitter(pageable, param);
    }

}
