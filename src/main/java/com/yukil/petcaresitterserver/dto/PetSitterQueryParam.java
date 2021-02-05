package com.yukil.petcaresitterserver.dto;

import com.yukil.petcaresitterserver.entity.PetType;
import com.yukil.petcaresitterserver.entity.PossibleArea;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Set;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetSitterQueryParam {

    private String email;
    private String name;
    private Integer ageGoe;
    private Integer ageLoe;
    private Set<PetType> petTypes;
    @NotNull
    private String fromTime;
    @NotNull
    private String toTime;
    private PossibleArea possibleAreas;
}
