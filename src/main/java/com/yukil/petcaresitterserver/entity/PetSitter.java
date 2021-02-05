package com.yukil.petcaresitterserver.entity;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class PetSitter {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String name;
    private String password;
    private LocalDate birthday;
    @OneToMany(mappedBy = "petSitter")
    private Set<PetType> petTypes;
    @OneToMany(mappedBy = "petSitter", fetch = FetchType.LAZY)
    private List<BankAccount> bankAccount = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_and_area_id")
    private TimeAndArea timeAndArea;


}
