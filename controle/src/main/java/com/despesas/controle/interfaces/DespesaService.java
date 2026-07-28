package com.despesas.controle.interfaces;

import com.despesas.controle.models.Despesa;

public interface DespesaService {
    void criarDespesa(Despesa despesa);
    Despesa buscarDespesa(Long id);
    void atualizarDespesa(Despesa despesa);
    void deletarDespesa(Long id);

}
