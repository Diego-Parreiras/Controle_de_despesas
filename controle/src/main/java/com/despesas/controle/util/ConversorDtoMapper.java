package com.despesas.controle.util;

import com.despesas.controle.dtos.DespesaResponseDto;
import com.despesas.controle.dtos.UsuarioResponseDto;
import com.despesas.controle.models.Despesa;
import com.despesas.controle.models.Usuario;
import java.util.List;

public class ConversorDtoMapper {

    // O QUE FAZ: Converte uma entidade Despesa em um DespesaResponseDto.
    // COMO FAZ: Extrai os dados da entidade e instancia um novo record de DTO, ignorando o relacionamento reverso com o Usuário.
    public static DespesaResponseDto converterDespesaParaDto(Despesa entidadeDespesa) {
        if (entidadeDespesa == null) return null;
        
        return new DespesaResponseDto(
            entidadeDespesa.getId(),
            entidadeDespesa.getDescricao(),
            entidadeDespesa.getValor(),
            entidadeDespesa.getCategoria(),
            entidadeDespesa.getDataVencimento(),
            entidadeDespesa.getRegistro(),
            entidadeDespesa.getPago()
        );
    }

    // O QUE FAZ: Converte uma entidade Usuario e sua lista de despesas em um UsuarioResponseDto.
    // COMO FAZ: Mapeia os dados básicos do usuário (omitindo a senha) e utiliza Streams para converter a lista interna de entidades Despesa em uma lista de DTOs.
    public static UsuarioResponseDto converterUsuarioParaDto(Usuario entidadeUsuario) {
        if (entidadeUsuario == null) return null;

        List<DespesaResponseDto> listaDespesasDto = null;
        
        if (entidadeUsuario.getDespesas() != null) {
            listaDespesasDto = entidadeUsuario.getDespesas().stream()
                .map(ConversorDtoMapper::converterDespesaParaDto)
                .toList();
        }

        return new UsuarioResponseDto(
            entidadeUsuario.getId(),
            entidadeUsuario.getNome(),
            entidadeUsuario.getEmail(),
            entidadeUsuario.getSalarioMensal(),
            listaDespesasDto
        );
    }
}