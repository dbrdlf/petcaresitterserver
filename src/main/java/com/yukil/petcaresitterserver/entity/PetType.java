package com.yukil.petcaresitterserver.entity;

import lombok.*;

import javax.persistence.*;
import java.util.concurrent.ThreadPoolExecutor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetType {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private Type type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_sitter_id")
    private PetSitter petSitter;

    public enum Type{
        DOG,CAT,ETC
    }

}
