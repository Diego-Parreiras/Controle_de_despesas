    package com.despesas.controle.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.despesas.controle.enuns.FormaDePagamento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "DESPESAS")
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DESPESA")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    private String descricao;
    private BigDecimal valor;
    private String categoria;
    private LocalDate dataVencimento;
    private LocalDate registro;
    @Enumerated(EnumType.STRING)
    private FormaDePagamento pago;

}
