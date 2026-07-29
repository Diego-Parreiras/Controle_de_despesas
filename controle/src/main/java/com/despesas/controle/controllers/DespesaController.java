package com.despesas.controle.controllers;

import com.despesas.controle.dtos.DespesaResponseDto;
import com.despesas.controle.models.Despesa;
import com.despesas.controle.services.DespesaServiceImp;
import com.despesas.controle.util.ConversorDtoMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/despesas")
public class DespesaController {

    private final DespesaServiceImp despesaService;

    public DespesaController(DespesaServiceImp despesaService) {
        this.despesaService = despesaService;
    }

    // O QUE FAZ: Recebe uma requisição para cadastrar uma nova despesa.
    // COMO FAZ: Captura o objeto Despesa do corpo da requisição e repassa para a camada de serviço, devolvendo o status HTTP adequado.
    @PostMapping
    public ResponseEntity<String> criarDespesa(@RequestBody Despesa despesaRecebida) {
        try {
            despesaService.criarDespesa(despesaRecebida);
            return ResponseEntity.status(HttpStatus.CREATED).body("Despesa criada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor.");
        }
    }

    // O QUE FAZ: Retorna todas as despesas cadastradas no sistema formatadas e seguras.
    // COMO FAZ: Busca as entidades no serviço e utiliza a API de Streams para converter a lista de Entidades em uma lista de DespesaResponseDto usando o nosso Mapper.
    @GetMapping
    public ResponseEntity<?> buscarTodasDespesas() {
        try {
            List<Despesa> listaEntidades = despesaService.buscarDespesa();
            List<DespesaResponseDto> listaDtoSegura = listaEntidades.stream()
                    .map(ConversorDtoMapper::converterDespesaParaDto)
                    .toList();
            return ResponseEntity.status(HttpStatus.OK).body(listaDtoSegura);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao buscar despesas.");
        }
    }

    // O QUE FAZ: Calcula e devolve o valor total gasto em um mês e ano específicos.
    // COMO FAZ: Recebe os parâmetros pela URL (?mes=10&ano=2026), aciona a lógica no serviço e retorna o valor numérico.
    @GetMapping("/total")
    public ResponseEntity<?> obterTotalDoMes(@RequestParam int mes, @RequestParam int ano) {
        try {
            BigDecimal totalCalculado = despesaService.calcularTotalDoMes(mes, ano);
            return ResponseEntity.status(HttpStatus.OK).body(totalCalculado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao calcular total do mês.");
        }
    }

    // O QUE FAZ: Atualiza as informações de uma despesa.
    // COMO FAZ: Recebe o ID na URL e o objeto modificado no corpo, garantindo a amarração do ID antes de repassar para o serviço.
    @PutMapping("/{idDespesa}")
    public ResponseEntity<String> atualizarDespesa(@PathVariable Long idDespesa, @RequestBody Despesa despesaAtualizada) {
        try {
            despesaAtualizada.setId(idDespesa);
            despesaService.atualizarDespesa(despesaAtualizada);
            return ResponseEntity.status(HttpStatus.OK).body("Despesa atualizada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao atualizar despesa.");
        }
    }

    // O QUE FAZ: Apaga um registro de despesa do banco de dados.
    // COMO FAZ: Recebe o ID na URL e chama o serviço de deleção com bloco try/catch.
    @DeleteMapping("/{idDespesa}")
    public ResponseEntity<String> deletarDespesa(@PathVariable Long idDespesa) {
        try {
            despesaService.deletarDespesa(idDespesa);
            return ResponseEntity.status(HttpStatus.OK).body("Despesa deletada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao deletar despesa.");
        }
    }
}