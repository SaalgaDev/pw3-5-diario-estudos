package br.com.etec.SalgadoSergio.diario_estudo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_TAREFA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TAREFA")
    private Long id;

    @Column(name = "TX_TITULO")
    private String titulo;

    @Column(name = "TX_MATERIA")
    private String materia;

    @Column(name = "DT_ENTREGA")
    private LocalDate dataEntrega;

    @Column(name = "TP_STATUS")
    private String status;

    @Column(name = "TP_PRIORIDADE")
    private String prioridade;

    @Column(name = "DT_EXCLUSAO")
    private LocalDate dtExclusao;

}
