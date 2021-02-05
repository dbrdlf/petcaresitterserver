package com.yukil.petcaresitterserver.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class BankAccount {
    @Id
    @GeneratedValue
    private Long id;
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private Bank bank;
    private String owner;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_sitter_id")
    private PetSitter petSitter;

}
