package Restaurante_Bar;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage; // 👈 IMPORTANTE

public class LoginApp extends Application {

    private TextField txtUsuario;
    private PasswordField txtSenha;
    private Autenticacao auth;

    @Override
    public void start(Stage stage) {
        auth = new Autenticacao();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        Label titulo = new Label("Acesso ao Sistema");
        titulo.setFont(Font.font("Arial", 20));
        BorderPane.setAlignment(titulo, Pos.CENTER);
        root.setTop(titulo);

        GridPane painel = new GridPane();
        painel.setHgap(10);
        painel.setVgap(10);
        painel.setPadding(new Insets(20));
        painel.setAlignment(Pos.CENTER);

        Label lblUsuario = new Label("Usuário:");
        Label lblSenha = new Label("Senha:");

        txtUsuario = new TextField();
        txtUsuario.setPrefWidth(180);

        txtSenha = new PasswordField();
        txtSenha.setPrefWidth(180);

        Button btnEntrar = new Button("Entrar");
        btnEntrar.setOnAction(e -> fazerLogin(stage));

        painel.add(lblUsuario, 0, 0);
        painel.add(txtUsuario, 1, 0);
        painel.add(lblSenha, 0, 1);
        painel.add(txtSenha, 1, 1);
        painel.add(btnEntrar, 1, 2);

        root.setCenter(painel);

        Scene scene = new Scene(root, 400, 250);

        stage.setTitle("Login - Restaurante/Bar CENTRAL");

        // ✅ DEFINIR ÍCONE DA JANELA
        try {
            Image icon = new Image(getClass().getResourceAsStream("/icon.png"));
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.out.println("Ícone não encontrado!");
        }

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private void fazerLogin(Stage stageAtual) {
        String usuario = txtUsuario.getText().trim();
        String senha = txtSenha.getText().trim();

        if (usuario.isEmpty() || senha.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Preencha usuário e senha.");
            return;
        }

        try {
            Usuario u = auth.login(usuario, senha);

            if (u != null) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso",
                        "Login efetuado com sucesso!\nNível: " + u.getNivelAcesso());

                RestauranteMainFX main = new RestauranteMainFX(u);
                Stage novoStage = new Stage();

                // ✅ APLICAR ÍCONE TAMBÉM NA JANELA PRINCIPAL
                try {
                    Image icon = new Image(getClass().getResourceAsStream("/icon.png"));
                    novoStage.getIcons().add(icon);
                } catch (Exception e) {
                    System.out.println("Ícone não encontrado!");
                }

                main.start(novoStage);

                stageAtual.close();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Usuário ou senha inválidos.");
            }

        } catch (Exception ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de ligação", ex.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}