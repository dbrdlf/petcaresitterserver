package com.yukil.petcaresitterserver.dto;

import com.yukil.petcaresitterserver.entity.PossibleArea;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeAndAreaDto extends RepresentationModel<TimeAndAreaDto> {
    private Long id;
    private LocalTime fromTime;
    private LocalTime toTime;
    private Set<PossibleArea> possibleArea;


}
