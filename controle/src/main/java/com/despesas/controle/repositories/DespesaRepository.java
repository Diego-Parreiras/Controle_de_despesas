package com.despesas.controle.repositories;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.despesas.controle.models.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long>{
    List<Despesa> findByRegistro(LocalDate data);

    // O QUE FAZ: Soma o valor de todas as despesas que vencem dentro de um período de datas.
    // COMO FAZ: Utiliza uma consulta JPQL explícita e parametrizada para somar a coluna valor onde a dataVencimento está entre a dataInicial e a dataFinal.
    @Query("SELECT SUM(d.valor) FROM Despesa d WHERE d.dataVencimento >= :dataInicial AND d.dataVencimento <= :dataFinal")
    BigDecimal calcularTotalDespesasNoPeriodo(@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate dataFinal);


}
