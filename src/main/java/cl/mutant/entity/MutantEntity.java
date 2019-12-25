package cl.mutant.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Builder
@Entity
@Table(name = "mutant")
public class MutantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "mutant_id_seq")
    @SequenceGenerator(name = "mutant_id_seq", sequenceName = "mutant_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "dna", unique = true, columnDefinition="TEXT")
    private String dna;

    @NotNull
    @Column(name = "isMutant")
    private Boolean isMutant;


}