package br.com.matheusvictor.gremio.entity;


import br.com.matheusvictor.gremio.enums.SimNaoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TBL_CANDIDATO")
public class Candidato {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID_FILME")
    private Long id;

    @Column(name = "TX_NOME")
    private String nome;

    @Column(name = "TP_CATEGORIA")
    private String categoria;

    @Column(name = "NR_QUANTIDADEVOTOS")
    private Integer quantidadeVotos;

    @Column(name = "CHK_ELEITO")
    private SimNaoEnum eleito;
}
