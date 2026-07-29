package com.despesas.controle.dtos;

import java.math.BigDecimal;
import java.util.List;

// O QUE FAZ: Transporta os dados de saída de um Usuário para o frontend protegendo informações sensíveis.
// COMO FAZ: Oculta o campo de senha e substitui a lista de entidades Despesa por uma lista de DespesaResponseDto, garantindo a segurança e prevenindo recursão no JSON.
public record UsuarioResponseDto(
    Long id,
    String nome,
    String email,
    BigDecimal salarioMensal,
    List<DespesaResponseDto> despesas
) {}