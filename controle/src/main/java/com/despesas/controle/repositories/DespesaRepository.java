package com.despesas.controle.repositories;

import java.util.List;
import java.util.Optional;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.despesas.controle.models.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long>{
    List<Despesa> findByRegistro(LocalDate data);



}
