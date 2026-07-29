package com.despesas.controle.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.despesas.controle.enuns.FormaDePagamento;

// O QUE FAZ: Transporta os dados de saída de uma Despesa para o frontend de forma segura.
// COMO FAZ: Utiliza um record do Java para criar um objeto imutável contendo apenas os campos essenciais, omitindo a entidade Usuario para quebrar o loop infinito bidirecional.
public record DespesaResponseDto(
    Long id,
    String descricao,
    BigDecimal valor,
    String categoria,
    LocalDate dataVencimento,
    LocalDate registro,
    FormaDePagamento pago
) {}