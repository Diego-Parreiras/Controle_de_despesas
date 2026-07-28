package com.despesas.controle.interfaces;

import com.despesas.controle.models.Despesa;

public interface DespesaService {
    Despesa criarDespesa(Despesa despesa);
    Despesa buscarDespesa(Long id);
    Despesa atualizarDespesa(Despesa despesa);
    void deletarDespesa(Long id);

}
