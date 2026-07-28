import com.despesas.controle.models.Usuario;

public interface UsuarioService {

    Usuario criarUsuario(Usuario usuario);

    Usuario buscarUsuario(Long id);

    Usuario atualizarUsuario(Usuario usuario);

    void deletarUsuario(Long id);

}
