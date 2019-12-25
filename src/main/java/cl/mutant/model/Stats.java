package cl.mutant.model;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class Stats {

    private Long count_mutant_dna;
    private Long count_human_dna;
    private Long ratio;
}
