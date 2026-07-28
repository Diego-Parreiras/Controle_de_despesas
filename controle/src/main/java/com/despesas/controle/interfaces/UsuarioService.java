package com.despesas.controle.interfaces;
import com.despesas.controle.models.Usuario;

public interface UsuarioService {

    void criarUsuario(Usuario usuario);

    Usuario buscarUsuario(Long id);
    
    Usuario buscarUsuario(String email);

    void atualizarUsuario(Long id, Usuario usuario);

    void apagarUsuario(Long id);

}
