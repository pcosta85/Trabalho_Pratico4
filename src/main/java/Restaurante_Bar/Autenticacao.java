package Restaurante_Bar;

import java.util.ArrayList;
import java.util.List;

public class Autenticacao {
    private List<Usuario> usuarios;

    public Autenticacao() {
        usuarios = new ArrayList<>();

        // Utilizadores de exemplo
        usuarios.add(new Usuario("admin", "1234", "ADMIN"));
        usuarios.add(new Usuario("func", "1234", "FUNCIONARIO"));
    }

    public Usuario login(String username, String password) {
        for (Usuario u : usuarios) {
            if (u.autenticar(username, password)) {
                return u;
            }
        }
        return null;
    }
}