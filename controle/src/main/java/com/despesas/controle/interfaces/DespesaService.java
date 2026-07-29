package com.despesas.controle.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.despesas.controle.models.Despesa;

public interface DespesaService {
    void criarDespesa(Despesa despesa);
    List<Despesa> buscarDespesa(LocalDate data);
    List<Despesa> buscarDespesa();
    void atualizarDespesa(Despesa despesa);
    void deletarDespesa(Long id);

}
