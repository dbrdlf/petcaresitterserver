package com.yukil.petcaresitterserver.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeAndArea {
    @Id
    @GeneratedValue
    private Long id;
    private LocalTime fromTime;
    private LocalTime toTime;
    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<PossibleArea> possibleArea;
    @OneToOne(mappedBy = "timeAndArea", fetch = FetchType.LAZY)
    private PetSitter petSitter;
}
