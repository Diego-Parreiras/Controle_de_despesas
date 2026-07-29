package com.despesas.controle.services;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

import com.despesas.controle.interfaces.DespesaService;
import com.despesas.controle.models.Despesa;
import com.despesas.controle.repositories.DespesaRepository;

@Service
public class DespesaServiceImp implements DespesaService {
    
    private final DespesaRepository despesaRepository;
    
    public DespesaServiceImp(DespesaRepository despesaRepository) {
         this.despesaRepository = despesaRepository;
    }
   
    // O QUE FAZ: Cadastra uma nova despesa no sistema.
    // COMO FAZ: Valida se o objeto não é nulo e se o valor é maior que zero (usando compareTo para BigDecimal), persistindo no banco via repositório dentro de um bloco try/catch.
    @Override
    public void criarDespesa(Despesa despesa) {
        if (despesa == null) { 
            throw new RuntimeException("Erro ao criar despesa");    
        }
        try {
            if (despesa.getValor() != null && despesa.getValor().compareTo(BigDecimal.ZERO) > 0) {
                despesaRepository.save(despesa);
            }
        } catch(Exception e) {
            throw new RuntimeException("Erro ao cadastrar despesa. Erro: ", e);
        }
    }

    // O QUE FAZ: Busca uma lista de despesas filtradas por uma data de registro específica.
    // COMO FAZ: Utiliza o método de consulta customizado do repositório (findByRegistro) encapsulado em um tratamento de exceção.
    @Override
    public List<Despesa> buscarDespesa(LocalDate data) {
        try {
            return despesaRepository.findByRegistro(data);
        } catch(Exception e) {
            throw new RuntimeException("Despesas nao encontrada. Erro: ", e);
        }
    }
    
    // O QUE FAZ: Retorna todos os registros de despesas cadastrados no banco de dados.
    // COMO FAZ: Invoca o método nativo findAll do JPA Repository com proteção de try/catch para erros de conexão.
    @Override
    public List<Despesa> buscarDespesa() {
        try {
            return despesaRepository.findAll();
        } catch(Exception e) {
            throw new RuntimeException("Erro ao buscar todas desesas. Erro: ", e);
        }
    }
    
    // O QUE FAZ: Atualiza os dados de uma despesa já existente no sistema.
    // COMO FAZ: Valida se o novo valor é positivo, busca a despesa original no banco, substitui apenas as propriedades que não são nulas e salva as alterações.
    @Override
    public void atualizarDespesa(Despesa novaDespesa) {
        if (novaDespesa.getValor() != null && novaDespesa.getValor().compareTo(BigDecimal.ZERO) > 0) {
            try {
                Despesa despesaOriginal = despesaRepository.findById(novaDespesa.getId()).orElse(null);
                
                if (despesaOriginal != null) {
                    despesaOriginal.setValor(novaDespesa.getValor());
                    
                    if (novaDespesa.getCategoria() != null) { despesaOriginal.setCategoria(novaDespesa.getCategoria()); }
                    if (novaDespesa.getDataVencimento() != null) { despesaOriginal.setDataVencimento(novaDespesa.getDataVencimento()); }
                    if (novaDespesa.getDescricao() != null) { despesaOriginal.setDescricao(novaDespesa.getDescricao()); }
                    if (novaDespesa.getPago() != null) { despesaOriginal.setPago(novaDespesa.getPago()); }
                    if (novaDespesa.getRegistro() != null) { despesaOriginal.setRegistro(novaDespesa.getRegistro()); }
                    
                    despesaRepository.save(despesaOriginal);
                }
            } catch(Exception e) {
                throw new RuntimeException("Erro ao atualizar despesa. Erro", e);
            }
        }
    }

    // O QUE FAZ: Remove uma despesa específica do banco de dados utilizando seu identificador único.
    // COMO FAZ: Chama o comando de deleção do Spring Data JPA, tratando possíveis erros (como ID inexistente ou falha no banco) através do bloco try/catch.
    @Override
    public void deletarDespesa(Long id) {
        try {
            despesaRepository.deleteById(id);
        } catch(Exception e ) {
            throw new RuntimeException("Erro ao deletar despesa. Erro: ", e);
        }
    }

    // O QUE FAZ: Calcula o valor total das despesas dentro de um mês e ano específicos.
    // COMO FAZ: Constrói dinamicamente a data inicial (primeiro dia do mês) e a data final (último dia do mês) e repassa para a query do repositório, tratando exceções.
    public BigDecimal calcularTotalDoMes(int mes, int ano) {
        try {
            LocalDate dataInicial = LocalDate.of(ano, mes, 1);
            LocalDate dataFinal = dataInicial.withDayOfMonth(dataInicial.lengthOfMonth());
            BigDecimal totalCalculado = despesaRepository.calcularTotalDespesasNoPeriodo(dataInicial, dataFinal);
            return totalCalculado != null ? totalCalculado : BigDecimal.ZERO;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular o total de despesas do mês. Erro: ", e);
        }
    }
}