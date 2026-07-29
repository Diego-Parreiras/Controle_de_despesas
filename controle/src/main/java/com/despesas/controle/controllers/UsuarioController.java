package com.despesas.controle.controllers;

import com.despesas.controle.dtos.UsuarioResponseDto;
import com.despesas.controle.interfaces.UsuarioService;
import com.despesas.controle.models.Usuario;
import com.despesas.controle.util.ConversorDtoMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // O QUE FAZ: Recebe uma requisição para cadastrar um novo usuário.
    // COMO FAZ: Captura o JSON do corpo da requisição (@RequestBody), envia para a camada de serviço e retorna o status HTTP adequado com tratamento de erros (try/catch).
    @PostMapping
    public ResponseEntity<String> criarUsuario(@RequestBody Usuario usuario) {
        try {
            usuarioService.criarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor.");
        }
    }

    // O QUE FAZ: Busca um usuário pelo ID e retorna os dados limpos (sem senha).
    // COMO FAZ: Recebe o ID pela URL (@PathVariable), busca no banco via serviço, converte a entidade para UsuarioResponseDto usando o Mapper e retorna ao cliente.
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Long id) {
        try {
            Usuario usuarioEncontrado = usuarioService.buscarUsuario(id);
            if (usuarioEncontrado != null) {
                UsuarioResponseDto usuarioDto = ConversorDtoMapper.converterUsuarioParaDto(usuarioEncontrado);
                return ResponseEntity.status(HttpStatus.OK).body(usuarioDto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao buscar usuário.");
        }
    }

    // O QUE FAZ: Atualiza os dados de um usuário existente.
    // COMO FAZ: Recebe o ID na URL e os novos dados no corpo da requisição, repassa para o serviço atualizar e devolve a confirmação.
    @PutMapping("/{idOrigem}")
    public ResponseEntity<String> atualizarUsuario(@PathVariable Long idOrigem, @RequestBody Usuario usuarioAtualizado) {
        try {
            usuarioService.atualizarUsuario(idOrigem, usuarioAtualizado);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário atualizado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao atualizar usuário.");
        }
    }

    // O QUE FAZ: Deleta um usuário do sistema com base no ID fornecido.
    // COMO FAZ: Aciona o serviço de deleção com o parâmetro da URL e gerencia o retorno de sucesso ou erro (try/catch).
    @DeleteMapping("/{id}")
    public ResponseEntity<String> apagarUsuario(@PathVariable Long id) {
        try {
            usuarioService.apagarUsuario(id);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao apagar usuário.");
        }
    }
}