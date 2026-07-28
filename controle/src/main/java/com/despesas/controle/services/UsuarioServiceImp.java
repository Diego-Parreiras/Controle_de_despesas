package com.despesas.controle.services;

import org.springframework.stereotype.Service;

import com.despesas.controle.interfaces.UsuarioService;
import com.despesas.controle.models.Usuario;
import com.despesas.controle.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImp implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    public UsuarioServiceImp(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario criarUsuario(Usuario usuario) {
        throw new RuntimeException("Erro ao criar usuario;");
    }

    @Override
    public Usuario buscarUsuario(Long id) {
        throw new RuntimeException("Erro ao buscar usuario;");

    }

    @Override
    public Usuario atualizarUsuario(Usuario usuario) {
        throw new RuntimeException("Erro ao atualizar usuario;");

    }

    @Override
    public void deletarUsuario(Long id) {
        throw new RuntimeException("Erro ao deletar usuario;");
    }

}
