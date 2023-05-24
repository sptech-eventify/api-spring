package eventify.api_spring.factory.usuario;

import eventify.api_spring.domain.Usuario;

public class UsuarioFactory {
    public static Usuario usuarioAtualizado(){
        final Usuario usuario = new Usuario();

        usuario.setId(1);
        usuario.setNome("Paulo José");
        usuario.setEmail("paulin@jose.com");
        usuario.setAtivo(true);
        usuario.setBanido(false);
        usuario.setTipoUsuario(1);
        usuario.setSenha("#SenhaDaora");
        usuario.setCpf("14389563556");

        return usuario;
    }

    public static Usuario usuarioFactory() {
        Usuario usuario = new Usuario();

        usuario.setId(1);
        usuario.setNome("Gabriel Santos");
        usuario.setEmail("gabriel@santos.com");
        usuario.setAtivo(true);
        usuario.setBanido(false);
        usuario.setTipoUsuario(1);
        usuario.setSenha("123456");
        usuario.setCpf("31509983015");
        return usuario;
    }
}
