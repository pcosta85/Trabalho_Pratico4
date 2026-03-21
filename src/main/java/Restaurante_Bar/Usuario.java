package Restaurante_Bar;

public class Usuario {
    private String username;
    private String password;
    private String nivelAcesso;

    public Usuario(String username, String password, String nivelAcesso) {
        this.username = username;
        this.password = password;
        this.nivelAcesso = nivelAcesso;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public boolean autenticar(String user, String pass) {
        return this.username.equals(user) && this.password.equals(pass);
    }
}
