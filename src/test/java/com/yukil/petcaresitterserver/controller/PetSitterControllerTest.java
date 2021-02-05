package com.yukil.petcaresitterserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yukil.petcaresitterserver.dto.BankAccountParam;
import com.yukil.petcaresitterserver.dto.PetSitterParam;
import com.yukil.petcaresitterserver.dto.TimeAndAreaParam;
import com.yukil.petcaresitterserver.entity.*;
import com.yukil.petcaresitterserver.repository.BankAccountRepository;
import com.yukil.petcaresitterserver.repository.PetSitterRepository;
import com.yukil.petcaresitterserver.repository.PetTypeRepository;
import com.yukil.petcaresitterserver.repository.TimeAndAreaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class PetSitterControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    PetSitterRepository petSitterRepository;
    @Autowired
    TimeAndAreaRepository timeAndAreaRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    PetTypeRepository petTypeRepository;


    @Test
    @DisplayName("펫시터 생성 컨트롤러 테스트")
    public void createPetSitterControllerTest() throws Exception{
        //given
        PetSitterParam param = createPetSitterParam();
        //when&then
        mockMvc.perform(post("/api/pet-sitter")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(param))
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("create-pet-sitter",
                        links(linkWithRel("self").description("link to self"),
                                linkWithRel("update-pet-sitter").description("link to update pet-sitter"),
                                linkWithRel("delete-pet-sitter").description("link to delete pet-sitter"),
                                linkWithRel("get-pet-sitter").description("link to get pet-sitter"),
                                linkWithRel("query-pet-sitters").description("link to query pet-sitters"),
                                linkWithRel("profile").description("link to profile")
                                )

                ))
                ;

    }

    @Test
    @DisplayName("펫시터 1명 조회")
    public void getPetSitter() throws Exception{
        //given
        PetSitter petSitter = createPetSitter();
        //when
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/pet-sitter/{id}", petSitter.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("없는 펫시터 조회")
    public void getPetSitterUnknown() throws Exception{
        //given
        PetSitter petSitter = createPetSitter();
        //when
        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/pet-sitter/{id}", 100L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
        )
                .andExpect(status().isNoContent())
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("펫시터 리스트 조회")
    @Transactional
    public void queryPetSitter() throws Exception{
        //given
        IntStream.range(1,5).forEach(i -> createPetsitter(i));
        //when
        em.flush();
        em.clear();
        //then
        mockMvc.perform(get("/api/pet-sitter")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .param("page", "0")
                .param("size", "10")
                .param("sort", "name,DESC")
//                .param("fromTime", "1234")
                .param("fromTime", LocalTime.of(10,10).toString())
                .param("toTime", LocalTime.of(11,10).toString())
        ).andExpect(status().isOk())
        .andDo(print())
        ;
    }

    @Autowired
    EntityManager em;


    private void createPetsitter(int i) {
        TimeAndArea area = new TimeAndArea();
        if (i == 1) {

            area = TimeAndArea.builder().toTime(LocalTime.of(23, 10))
                    .fromTime(LocalTime.of(8, 00))
                    .possibleArea(Set.of(PossibleArea.SEOUL))
                    .build();
            timeAndAreaRepository.save(area);

        }else{
            area = TimeAndArea.builder().toTime(LocalTime.of(13, 10))
                    .fromTime(LocalTime.of(10, 00))
                    .possibleArea(Set.of(PossibleArea.SEOUL))
                    .build();
            timeAndAreaRepository.save(area);


        }
        BankAccount bankAccount = BankAccount.builder()
                .accountNumber("1234-1234-1234")
                .owner("kim")
                .bank(Bank.KB)
                .build();
        bankAccountRepository.save(bankAccount);

        PetType petType = PetType.builder()
                .type(PetType.Type.CAT)
                .build();
        petTypeRepository.save(petType);

        PetSitter petSitter = PetSitter.builder()
                .birthday(LocalDate.of(1990,5,20))
                .email("kim@gmail.com")
                .name("kim_" + i)
                .password("pass")
                .timeAndArea(area)
                .bankAccount(Arrays.asList(bankAccount))
                .petTypes(Set.of(petType))
                .build();
        petSitterRepository.save(petSitter);
    }

    private PetSitter createPetSitter() {
        PetSitter petSitter = PetSitter.builder()
                .birthday(LocalDate.of(1990,5,20))
                .email("kim@gmail.com")
                .name("kim")
                .password("pass")
                .build();
        return petSitterRepository.save(petSitter);
    }

    private PetSitterParam createPetSitterParam() {
        PetSitterParam petSitterParam = PetSitterParam.builder()
                .birthday(LocalDate.of(1990,5,20))
                .email("kim@gmail.com")
                .name("kim")
                .password("pass")
                .bankAccountParam(BankAccountParam.builder().accountNumber("1234-1234-1234-1234").bank(Bank.KB).owner("kim").build())
                .timeAndAreaParam(TimeAndAreaParam.builder().fromTime(LocalTime.of(10,0)).toTime(LocalTime.of(20,0)).possibleArea(Set.of(PossibleArea.SEOUL,PossibleArea.INCHEON)).build())
                .build();
        return petSitterParam;
    }




}
