package com.despesas.controle.services;

import org.springframework.stereotype.Service;

import com.despesas.controle.interfaces.UsuarioService;
import com.despesas.controle.models.Usuario;
import com.despesas.controle.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImp implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImp(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // O QUE FAZ: Cadastra um novo usuário no sistema, garantindo que o e-mail seja único e a senha atenda aos critérios de segurança.
    // COMO FAZ: Verifica a existência do e-mail no banco e valida a senha via Regex antes de tentar salvar encapsulado em um bloco try/catch.
    @Override
    public void criarUsuario(Usuario usuario) {
        if (buscarUsuario(usuario.getEmail()) == null) {
            if (verificarSenha(usuario.getSenha())) {
                try {
                    usuarioRepository.save(usuario);
                } catch (Exception e) {
                    throw new RuntimeException("Erro ao salvar usuario. Erro: ", e);
                }
            } else {
                throw new RuntimeException("Senha inválida.");
            }
        } else {
            throw new RuntimeException("Usuário já cadastrado.");
        }
    }

    // O QUE FAZ: Busca um usuário específico pelo seu identificador único (ID).
    // COMO FAZ: Utiliza o repositório JPA para buscar a entidade, extraindo do Optional ou retornando nulo.
    @Override
    public Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // O QUE FAZ: Busca um usuário específico pelo seu endereço de e-mail.
    // COMO FAZ: Utiliza o query method do repositório JPA para buscar a entidade por e-mail, extraindo do Optional ou retornando nulo.
    @Override
    public Usuario buscarUsuario(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    // O QUE FAZ: Atualiza os dados de um usuário já existente no banco de dados.
    // COMO FAZ: Busca o registro original, substitui apenas os campos não nulos enviados no objeto atualizado e persiste as alterações em um bloco try/catch.
    @Override
    public void atualizarUsuario(Long idOrigem, Usuario usuarioAtualizado) {
        if (usuarioAtualizado.getNome() == null) {
            throw new RuntimeException("Erro ao atualizar usuario;");
        }
        
        Usuario usuarioOriginal = buscarUsuario(idOrigem);
        
        if (usuarioOriginal != null) {
            try {
                if (usuarioAtualizado.getNome() != null) {
                    usuarioOriginal.setNome(usuarioAtualizado.getNome());
                }
                if (usuarioAtualizado.getEmail() != null) {
                    usuarioOriginal.setEmail(usuarioAtualizado.getEmail());
                }
                if (verificarSenha(usuarioAtualizado.getSenha())) {
                    usuarioOriginal.setSenha(usuarioAtualizado.getSenha());
                }
                if (usuarioAtualizado.getSalarioMensal() != null) {
                    usuarioOriginal.setSalarioMensal(usuarioAtualizado.getSalarioMensal());
                }
                usuarioRepository.save(usuarioOriginal);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao atualizar usuario. Erro: ", e);
            }
        }
    }

    // O QUE FAZ: Remove um usuário do banco de dados a partir do seu ID.
    // COMO FAZ: Checa a existência do registro e executa o comando de deleção dentro de um bloco try/catch para capturar falhas de integridade ou conexão.
    @Override
    public void apagarUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            try {
                usuarioRepository.deleteById(id);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao apagar usuario. Erro: ", e);
            }
        } else {
            throw new RuntimeException("Erro ao apagar usuario.");
        }
    }

    // O QUE FAZ: Valida se a senha informada atende aos critérios de segurança do sistema.
    // COMO FAZ: Compara a string recebida contra uma expressão regular que exige de 8 a 20 caracteres, incluindo maiúsculas, minúsculas, números e símbolos.
    private boolean verificarSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            return false;
        }
        String regexSeguranca = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,20}$";
        return senha.matches(regexSeguranca);
    }
}