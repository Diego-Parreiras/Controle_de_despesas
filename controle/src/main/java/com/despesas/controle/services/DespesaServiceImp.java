package com.despesas.controle.services;

import org.springframework.stereotype.Service;

import com.despesas.controle.interfaces.DespesaService;
import com.despesas.controle.models.Despesa;

@Service
public class DespesaServiceImp implements DespesaService {

    @Override
    public Despesa criarDespesa(Despesa despesa) {
        throw new RuntimeException("Erro ao criar despesa");
    }

    @Override
    public Despesa buscarDespesa(Long id) {
        throw new RuntimeException("Erro ao buscar despesa");
    }

    @Override
    public Despesa atualizarDespesa(Despesa despesa) {
        throw new RuntimeException("Erro ao atualizar despesa");
    }

    @Override
    public void deletarDespesa(Long id) {
    throw new RuntimeException("Erro ao deletar despesa");
    }

}
