package com.despesas.controle.services;

import org.springframework.stereotype.Service;

import com.despesas.controle.interfaces.DespesaService;
import com.despesas.controle.models.Despesa;
import com.despesas.controle.repositories.DespesaRepository;

@Service
public class DespesaServiceImp implements DespesaService {
    
    private final DespesaRepository despesaRepository;
    public DespesaServiceImp (DespesaRepository despesaRepository){
         this.despesaRepository = despesaRepository;
    }
   
    @Override
    public void criarDespesa(Despesa despesa) {
        if(despesa == null){ throw new RuntimeException("Erro ao criar despesa");    }
        try{
            if(despesa.getValor()!=null && despesa.getValor()>0){
                despesaRepository.save(despesa);
            }
        }catch(Exception e){
            throw new RuntimeException("Erro ao cadastrar despesa. Erro: ",e);
        }
}

    @Override
    public Despesa buscarDespesa(Long id) {

        throw new RuntimeException("Erro ao buscar despesa");
    }

    @Override
    public void atualizarDespesa(Despesa despesa) {
        throw new RuntimeException("Erro ao atualizar despesa");
    }

    @Override
    public void deletarDespesa(Long id) {
    throw new RuntimeException("Erro ao deletar despesa");
    }

}
