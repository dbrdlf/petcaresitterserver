package com.yukil.petcaresitterserver.dto;

import com.yukil.petcaresitterserver.entity.PetSitter;
import com.yukil.petcaresitterserver.entity.PossibleArea;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeAndAreaParam {
    private LocalTime fromTime;
    private LocalTime toTime;
    private Set<PossibleArea> possibleArea;
}
