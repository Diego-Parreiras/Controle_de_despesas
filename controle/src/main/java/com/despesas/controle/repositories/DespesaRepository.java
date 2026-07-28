package com.despesas.controle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.despesas.controle.models.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long>{

}
