package eventify.api_spring.factory.usuario;

import eventify.api_spring.domain.usuario.Usuario;

public class UsuarioFactory {

    public static Usuario usuario() {
        Usuario usuario = new Usuario();

        usuario.setId(1);
        usuario.setNome("Gabriel Santos");
        usuario.setEmail("gabriel@santos.com");
        usuario.setIsAtivo(true);
        usuario.setIsBanido(false);
        usuario.setTipoUsuario(1);
        usuario.setSenha("123456");
        usuario.setCpf("31509983015");
        return usuario;
    }

    public static Usuario usuarioAtualizado(){
        final Usuario usuario = new Usuario();

        usuario.setId(1);
        usuario.setNome("Paulo José");
        usuario.setEmail("paulin@jose.com");
        usuario.setIsAtivo(true);
        usuario.setIsBanido(false);
        usuario.setTipoUsuario(1);
        usuario.setSenha("#SenhaDaora");
        usuario.setCpf("14389563556");

        return usuario;
    }

    public static Usuario usuarioNaoAutenticado(){
        Usuario usuario = new Usuario();
        usuario.setEmail("gabriel@santos.com");
        usuario.setSenha("123456");
        usuario.setIsBanido(false);
        usuario.setIsAtivo(false);

        return usuario;
    }
}
