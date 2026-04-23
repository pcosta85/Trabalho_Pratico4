package Restaurante_Bar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RestauranteMainFX extends Application {

    private Usuario usuarioLogado;
    private final RestauranteSistema sistema = new RestauranteSistema();
    private final StackPane painelPrincipal = new StackPane();

    public RestauranteMainFX() {
    }

    public RestauranteMainFX(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        VBox menu = new VBox(10);
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(220);
        menu.setStyle("-fx-background-color: #1e1e1e;");

        Label lblUser = new Label(usuarioLogado.getUsername() + "\n" + usuarioLogado.getNivelAcesso());
        lblUser.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        lblUser.setAlignment(Pos.CENTER);
        lblUser.setMaxWidth(Double.MAX_VALUE);

        Button btnInicio = criarBotaoMenu("Início");
        Button btnLoja = criarBotaoMenu("Loja / Caixa");
        Button btnProdutos = criarBotaoMenu("Produtos");
        Button btnRelatorios = criarBotaoMenu("Relatórios");
        Button btnComprasDia = criarBotaoMenu("Compras do Dia");
        Button btnDefinicoes = criarBotaoMenu("Definições");
        Button btnUsuarios = criarBotaoMenu("Utilizadores");
        Button btnLogout = criarBotaoMenu("Terminar Sessão");
        Button btnSair = criarBotaoMenu("Sair");

        menu.getChildren().add(lblUser);
        menu.getChildren().add(btnInicio);
        menu.getChildren().add(btnLoja);

        if (isAdmin() || isGestor()) {
            menu.getChildren().add(btnProdutos);
            menu.getChildren().add(btnRelatorios);
        }

        if (isAdmin()) {
            menu.getChildren().add(btnUsuarios);
        }

        menu.getChildren().add(btnComprasDia);

        if (!isAdmin()) {
            menu.getChildren().add(btnDefinicoes);
        }

        menu.getChildren().addAll(btnLogout, btnSair);

        BorderPane inicio = criarPaginaInicio();
        BorderPane loja = criarPaginaLoja();
        BorderPane produtos = criarPaginaProdutos();
        BorderPane relatorios = criarPaginaRelatorios();
        BorderPane comprasDia = criarPaginaComprasDia();
        BorderPane definicoes = criarPaginaDefinicoes();
        BorderPane usuarios = criarPaginaUsuarios();

        painelPrincipal.getChildren().addAll(inicio, loja, produtos, relatorios, comprasDia, definicoes, usuarios);
        mostrar(inicio);

        btnInicio.setOnAction(e -> mostrar(inicio));
        btnLoja.setOnAction(e -> mostrar(loja));
        btnProdutos.setOnAction(e -> mostrar(produtos));
        btnRelatorios.setOnAction(e -> mostrar(relatorios));
        btnComprasDia.setOnAction(e -> mostrar(comprasDia));
        btnUsuarios.setOnAction(e -> mostrar(usuarios));
        btnDefinicoes.setOnAction(e -> mostrar(definicoes));

        btnLogout.setOnAction(e -> {
            if (confirmar("Deseja terminar a sessão atual?")) {
                try {
                    new LoginApp().start(new Stage());
                    stage.close();
                } catch (Exception ex) {
                    mostrarErro(ex.getMessage());
                }
            }
        });

        btnSair.setOnAction(e -> {
            if (confirmar("Deseja sair do sistema?")) {
                stage.close();
            }
        });

        root.setLeft(menu);
        root.setCenter(painelPrincipal);

        Scene scene = new Scene(root, 1100, 650);
        stage.setTitle("Sistema Restaurante / Bar");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private Button criarBotaoMenu(String texto) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: #323232; -fx-text-fill: white; -fx-font-weight: bold;");
        return btn;
    }

    private void mostrar(javafx.scene.Node pagina) {
        for (javafx.scene.Node node : painelPrincipal.getChildren()) {
            node.setVisible(false);
        }
        pagina.setVisible(true);
    }

    private boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(usuarioLogado.getNivelAcesso());
    }

    private boolean isGestor() {
        return "GESTOR".equalsIgnoreCase(usuarioLogado.getNivelAcesso());
    }

    private BorderPane criarPaginaInicio() {
        BorderPane p = new BorderPane();
        Label lbl = new Label("Bem-vindo " + usuarioLogado.getUsername() + " - Perfil: " + usuarioLogado.getNivelAcesso());
        lbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        p.setCenter(lbl);
        BorderPane.setAlignment(lbl, Pos.CENTER);
        return p;
    }

    private BorderPane criarPaginaLoja() {
        BorderPane p = new BorderPane();

        FlowPane topo = new FlowPane(10, 10);
        topo.setPadding(new Insets(10));

        ComboBox<String> cbProduto = new ComboBox<>();
        TextField txtQtd = new TextField();
        txtQtd.setPrefWidth(70);

        ComboBox<String> cbPagamento = new ComboBox<>();
        cbPagamento.getItems().addAll("Dinheiro", "Cartão", "PIX");
        cbPagamento.getSelectionModel().selectFirst();

        TextField txtRecebido = new TextField();
        txtRecebido.setPrefWidth(90);

        Button btnAdicionar = new Button("Adicionar");
        Button btnFinalizar = new Button("Finalizar");

        for (Object[] prod : sistema.listarProdutos()) {
            cbProduto.getItems().add(prod[1].toString());
        }
        if (!cbProduto.getItems().isEmpty()) {
            cbProduto.getSelectionModel().selectFirst();
        }

        topo.getChildren().addAll(
                new Label("Produto:"), cbProduto,
                new Label("Qtd:"), txtQtd,
                btnAdicionar
        );

        TableView<ItemCarrinho> tabela = new TableView<>();

        TableColumn<ItemCarrinho, String> colProduto = new TableColumn<>("Produto");
        colProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));

        TableColumn<ItemCarrinho, Integer> colQtd = new TableColumn<>("Qtd");
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        TableColumn<ItemCarrinho, Double> colPreco = new TableColumn<>("Preço");
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        TableColumn<ItemCarrinho, Double> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tabela.getColumns().addAll(colProduto, colQtd, colPreco, colTotal);

        List<Object[]> carrinho = new ArrayList<>();

        Label lblTotal = new Label("Total: 0.00");
        Label lblTroco = new Label("Troco: 0.00");

        HBox rodape = new HBox(10);
        rodape.setPadding(new Insets(10));
        rodape.setAlignment(Pos.CENTER_LEFT);
        rodape.getChildren().addAll(
                new Label("Pagamento:"), cbPagamento,
                new Label("Recebido:"), txtRecebido,
                lblTotal, lblTroco, btnFinalizar
        );

        btnAdicionar.setOnAction(e -> {
            try {
                Object[] prod = sistema.obterProduto(cbProduto.getValue());
                if (prod == null) {
                    mostrarAviso("Produto não encontrado.");
                    return;
                }

                int qtd = Integer.parseInt(txtQtd.getText().trim());
                double preco = (double) prod[2];
                double total = preco * qtd;

                carrinho.add(new Object[]{prod[1], qtd, prod[2], total});
                tabela.getItems().add(new ItemCarrinho(prod[1].toString(), qtd, preco, total));

                double soma = 0;
                for (Object[] item : carrinho) {
                    soma += (double) item[3];
                }

                lblTotal.setText(String.format("Total: %.2f", soma));
                txtQtd.clear();

            } catch (Exception ex) {
                mostrarErro("Quantidade inválida.");
            }
        });

        btnFinalizar.setOnAction(e -> {
            if (carrinho.isEmpty()) {
                mostrarAviso("Carrinho vazio.");
                return;
            }

            try {
                double totalGeral = 0;
                for (Object[] item : carrinho) {
                    totalGeral += (double) item[3];
                }

                String forma = cbPagamento.getValue();
                Double recebido = null;
                Double troco = null;

                if ("Dinheiro".equalsIgnoreCase(forma)) {
                    recebido = Double.parseDouble(txtRecebido.getText().trim());
                    troco = recebido - totalGeral;

                    if (troco < 0) {
                        mostrarAviso("Valor insuficiente.");
                        return;
                    }

                    lblTroco.setText(String.format("Troco: %.2f", troco));
                }

                boolean ok = sistema.finalizarVenda(
                        usuarioLogado.getUsername(),
                        forma,
                        recebido,
                        troco,
                        carrinho
                );

                if (ok) {
                    mostrarInfo("Venda feita com sucesso.");
                    carrinho.clear();
                    tabela.getItems().clear();
                    lblTotal.setText("Total: 0.00");
                    lblTroco.setText("Troco: 0.00");
                    txtRecebido.clear();
                } else {
                    mostrarErro("Erro ao finalizar venda.");
                }

            } catch (Exception ex) {
                mostrarErro("Erro no valor recebido.");
            }
        });

        p.setTop(topo);
        p.setCenter(tabela);
        p.setBottom(rodape);
        return p;
    }

    private BorderPane criarPaginaProdutos() {
        BorderPane p = new BorderPane();

        FlowPane topo = new FlowPane(10, 10);
        topo.setPadding(new Insets(10));

        TextField txtId = new TextField();
        txtId.setPrefWidth(60);
        TextField txtNome = new TextField();
        txtNome.setPrefWidth(160);
        TextField txtPreco = new TextField();
        txtPreco.setPrefWidth(100);

        Button btnCadastrar = new Button("Cadastrar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnEliminar = new Button("Eliminar");
        Button btnRecarregar = new Button("Recarregar");

        topo.getChildren().addAll(
                new Label("ID:"), txtId,
                new Label("Nome:"), txtNome,
                new Label("Preço:"), txtPreco,
                btnCadastrar, btnAtualizar, btnEliminar, btnRecarregar
        );

        TableView<ProdutoLinha> tabela = new TableView<>();

        TableColumn<ProdutoLinha, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ProdutoLinha, String> colNome = new TableColumn<>("Produto");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<ProdutoLinha, Double> colPreco = new TableColumn<>("Preço");
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        tabela.getColumns().addAll(colId, colNome, colPreco);

        carregarTabelaProdutos(tabela);

        tabela.setOnMouseClicked(e -> {
            ProdutoLinha linha = tabela.getSelectionModel().getSelectedItem();
            if (linha != null) {
                txtId.setText(String.valueOf(linha.getId()));
                txtNome.setText(linha.getNome());
                txtPreco.setText(String.valueOf(linha.getPreco()));
            }
        });

        btnCadastrar.setOnAction(e -> {
            try {
                boolean ok = sistema.cadastrarProduto(
                        txtNome.getText().trim(),
                        Double.parseDouble(txtPreco.getText().trim())
                );
                if (ok) {
                    carregarTabelaProdutos(tabela);
                    txtId.clear();
                    txtNome.clear();
                    txtPreco.clear();
                }
            } catch (Exception ex) {
                mostrarErro("Dados inválidos.");
            }
        });

        btnAtualizar.setOnAction(e -> {
            try {
                boolean ok = sistema.atualizarProduto(
                        Integer.parseInt(txtId.getText().trim()),
                        txtNome.getText().trim(),
                        Double.parseDouble(txtPreco.getText().trim())
                );
                if (ok) {
                    carregarTabelaProdutos(tabela);
                }
            } catch (Exception ex) {
                mostrarErro("Dados inválidos.");
            }
        });

        btnEliminar.setOnAction(e -> {
            try {
                boolean ok = sistema.eliminarProduto(Integer.parseInt(txtId.getText().trim()));
                if (ok) {
                    carregarTabelaProdutos(tabela);
                    txtId.clear();
                    txtNome.clear();
                    txtPreco.clear();
                }
            } catch (Exception ex) {
                mostrarErro("ID inválido.");
            }
        });

        btnRecarregar.setOnAction(e -> carregarTabelaProdutos(tabela));

        p.setTop(topo);
        p.setCenter(tabela);
        return p;
    }

    private void carregarTabelaProdutos(TableView<ProdutoLinha> tabela) {
        tabela.getItems().clear();
        for (Object[] p : sistema.listarProdutos()) {
            tabela.getItems().add(new ProdutoLinha(
                    (Integer) p[0],
                    p[1].toString(),
                    (Double) p[2]
            ));
        }
    }

    private BorderPane criarPaginaRelatorios() {
        BorderPane p = new BorderPane();

        FlowPane topo = new FlowPane(10, 10);
        topo.setPadding(new Insets(10));

        ComboBox<Integer> cbAno = new ComboBox<>();
        ComboBox<String> cbMes = new ComboBox<>();
        cbMes.getItems().addAll(
                "Janeiro", "Fevereiro", "Março", "Abril",
                "Maio", "Junho", "Julho", "Agosto",
                "Setembro", "Outubro", "Novembro", "Dezembro"
        );
        cbMes.getSelectionModel().selectFirst();

        Button btnGerar = new Button("Gerar");
        Button btnCSV = new Button("Exportar CSV");
        Button btnExcel = new Button("Exportar Excel");

        cbAno.getItems().addAll(sistema.listarAnosVenda());
        if (!cbAno.getItems().isEmpty()) {
            cbAno.getSelectionModel().selectFirst();
        }

        topo.getChildren().addAll(
                new Label("Ano:"), cbAno,
                new Label("Mês:"), cbMes,
                btnGerar, btnCSV, btnExcel
        );

        TableView<RelatorioLinha> tabela = new TableView<>();

        TableColumn<RelatorioLinha, String> c1 = new TableColumn<>("Produto");
        c1.setCellValueFactory(new PropertyValueFactory<>("produto"));

        TableColumn<RelatorioLinha, Integer> c2 = new TableColumn<>("Qtd");
        c2.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        TableColumn<RelatorioLinha, Double> c3 = new TableColumn<>("Preço Unit.");
        c3.setCellValueFactory(new PropertyValueFactory<>("precoUnitario"));

        TableColumn<RelatorioLinha, Double> c4 = new TableColumn<>("Total");
        c4.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<RelatorioLinha, String> c5 = new TableColumn<>("Forma Pagamento");
        c5.setCellValueFactory(new PropertyValueFactory<>("formaPagamento"));

        TableColumn<RelatorioLinha, String> c6 = new TableColumn<>("Data");
        c6.setCellValueFactory(new PropertyValueFactory<>("data"));

        tabela.getColumns().addAll(c1, c2, c3, c4, c5, c6);

        TextArea resumo = new TextArea();
        resumo.setEditable(false);

        VBox centro = new VBox(10, tabela, resumo);
        centro.setPadding(new Insets(10));

        btnGerar.setOnAction(e -> {
            Integer ano = cbAno.getValue();
            int mes = cbMes.getSelectionModel().getSelectedIndex() + 1;

            if (ano == null) {
                mostrarAviso("Nenhum ano encontrado.");
                return;
            }

            tabela.getItems().clear();

            for (Object[] row : sistema.listarRelatorioVendas(ano, mes)) {
                tabela.getItems().add(new RelatorioLinha(
                        row[0].toString(),
                        (Integer) row[1],
                        (Double) row[2],
                        (Double) row[3],
                        row[4].toString(),
                        row[5].toString()
                ));
            }

            StringBuilder sb = new StringBuilder();
            sb.append("=== Relatório de ").append(cbMes.getValue()).append(" / ").append(ano).append(" ===\n\n");
            sb.append("=== Total por Forma de Pagamento ===\n");

            for (Object[] row : sistema.totalPorFormaPagamento(ano, mes)) {
                sb.append(row[0]).append(": ").append(String.format("%.2f", (Double) row[1])).append("\n");
            }

            sb.append("\n=== Total por Produto ===\n");

            for (Object[] row : sistema.totalPorProduto(ano, mes)) {
                sb.append(row[0]).append(": ").append(String.format("%.2f", (Double) row[1])).append("\n");
            }

            resumo.setText(sb.toString());
        });

        btnCSV.setOnAction(e -> {
            Integer ano = cbAno.getValue();
            int mes = cbMes.getSelectionModel().getSelectedIndex() + 1;

            if (ano == null) {
                mostrarAviso("Nenhum ano selecionado.");
                return;
            }

            FileChooser chooser = new FileChooser();
            chooser.setTitle("Guardar CSV");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
            File file = chooser.showSaveDialog(null);

            if (file != null) {
                boolean ok = sistema.exportarRelatorioCSV(file.getAbsolutePath(), ano, mes);
                mostrarInfo(ok ? "CSV exportado." : "Erro ao exportar CSV.");
            }
        });

        btnExcel.setOnAction(e -> {
            Integer ano = cbAno.getValue();
            int mes = cbMes.getSelectionModel().getSelectedIndex() + 1;

            if (ano == null) {
                mostrarAviso("Nenhum ano selecionado.");
                return;
            }

            FileChooser chooser = new FileChooser();
            chooser.setTitle("Guardar Excel");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel", "*.xlsx"));
            File file = chooser.showSaveDialog(null);

            if (file != null) {
                boolean ok = sistema.exportarRelatorioExcel(file.getAbsolutePath(), ano, mes);
                mostrarInfo(ok ? "Excel exportado." : "Erro ao exportar Excel.");
            }
        });

        p.setTop(topo);
        p.setCenter(centro);
        return p;
    }

    private BorderPane criarPaginaComprasDia() {
        BorderPane p = new BorderPane();

        FlowPane topo = new FlowPane(10, 10);
        topo.setPadding(new Insets(10));

        TextField txtData = new TextField();
        txtData.setPrefWidth(120);
        Button btnFiltrar = new Button("Filtrar");
        Button btnEliminar = new Button("Eliminar Venda");

        topo.getChildren().addAll(
                new Label("Data (AAAA-MM-DD):"), txtData,
                btnFiltrar, btnEliminar
        );

        TableView<CompraDiaLinha> tabela = new TableView<>();

        TableColumn<CompraDiaLinha, Integer> c1 = new TableColumn<>("ID Venda");
        c1.setCellValueFactory(new PropertyValueFactory<>("idVenda"));

        TableColumn<CompraDiaLinha, String> c2 = new TableColumn<>("Data");
        c2.setCellValueFactory(new PropertyValueFactory<>("data"));

        TableColumn<CompraDiaLinha, String> c3 = new TableColumn<>("Produto");
        c3.setCellValueFactory(new PropertyValueFactory<>("produto"));

        TableColumn<CompraDiaLinha, Integer> c4 = new TableColumn<>("Qtd");
        c4.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        TableColumn<CompraDiaLinha, Double> c5 = new TableColumn<>("Total");
        c5.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<CompraDiaLinha, String> c6 = new TableColumn<>("Forma");
        c6.setCellValueFactory(new PropertyValueFactory<>("forma"));

        tabela.getColumns().addAll(c1, c2, c3, c4, c5, c6);

        btnFiltrar.setOnAction(e -> {
            tabela.getItems().clear();
            for (Object[] row : sistema.listarComprasPorData(txtData.getText().trim())) {
                tabela.getItems().add(new CompraDiaLinha(
                        (Integer) row[0],
                        row[1].toString(),
                        row[2].toString(),
                        (Integer) row[3],
                        (Double) row[4],
                        row[5].toString()
                ));
            }
        });

        btnEliminar.setOnAction(e -> {
            CompraDiaLinha linha = tabela.getSelectionModel().getSelectedItem();
            if (linha == null) {
                mostrarAviso("Selecione uma venda.");
                return;
            }

            if (!confirmar("Deseja eliminar a venda selecionada?")) {
                return;
            }

            boolean ok = sistema.eliminarVenda(linha.getIdVenda());
            mostrarInfo(ok ? "Venda eliminada." : "Erro ao eliminar venda.");
        });

        p.setTop(topo);
        p.setCenter(tabela);
        return p;
    }

    private BorderPane criarPaginaDefinicoes() {
        BorderPane p = new BorderPane();

        VBox box = new VBox(10);
        box.setPadding(new Insets(20));
        box.setAlignment(Pos.CENTER_LEFT);

        PasswordField txtAtual = new PasswordField();
        PasswordField txtNova = new PasswordField();
        PasswordField txtConfirmar = new PasswordField();

        Button btnAlterar = new Button("Alterar Senha");

        box.getChildren().addAll(
                new Label("Senha Atual:"), txtAtual,
                new Label("Nova Senha:"), txtNova,
                new Label("Confirmar Nova Senha:"), txtConfirmar,
                btnAlterar
        );

        btnAlterar.setOnAction(e -> {
            String atual = txtAtual.getText().trim();
            String nova = txtNova.getText().trim();
            String confirmar = txtConfirmar.getText().trim();

            if (!nova.equals(confirmar)) {
                mostrarAviso("A confirmação não coincide.");
                return;
            }

            boolean ok = sistema.alterarSenha(usuarioLogado.getUsername(), atual, nova);
            mostrarInfo(ok ? "Senha alterada com sucesso." : "Erro ao alterar senha.");
        });

        p.setCenter(box);
        return p;
    }

    private BorderPane criarPaginaUsuarios() {
        BorderPane p = new BorderPane();

        FlowPane topo = new FlowPane(10, 10);
        topo.setPadding(new Insets(10));

        TextField txtId = new TextField();
        txtId.setPrefWidth(60);

        TextField txtUsername = new TextField();
        txtUsername.setPrefWidth(120);

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPrefWidth(120);

        ComboBox<String> cbNivel = new ComboBox<>();
        cbNivel.getItems().addAll("ADMIN", "GESTOR", "ATENDENTE");
        cbNivel.getSelectionModel().selectFirst();

        Button btnCriar = new Button("Criar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnEliminar = new Button("Eliminar");
        Button btnRecarregar = new Button("Recarregar");

        topo.getChildren().addAll(
                new Label("ID:"), txtId,
                new Label("Username:"), txtUsername,
                new Label("Password:"), txtPassword,
                new Label("Nível:"), cbNivel,
                btnCriar, btnAtualizar, btnEliminar, btnRecarregar
        );

        TableView<UsuarioLinha> tabela = new TableView<>();

        TableColumn<UsuarioLinha, Integer> c1 = new TableColumn<>("ID");
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<UsuarioLinha, String> c2 = new TableColumn<>("Username");
        c2.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<UsuarioLinha, String> c3 = new TableColumn<>("Password");
        c3.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<UsuarioLinha, String> c4 = new TableColumn<>("Nível");
        c4.setCellValueFactory(new PropertyValueFactory<>("nivel"));

        tabela.getColumns().addAll(c1, c2, c3, c4);

        carregarTabelaUsuarios(tabela);

        tabela.setOnMouseClicked(e -> {
            UsuarioLinha linha = tabela.getSelectionModel().getSelectedItem();
            if (linha != null) {
                txtId.setText(String.valueOf(linha.getId()));
                txtUsername.setText(linha.getUsername());
                txtPassword.setText(linha.getPassword());
                cbNivel.setValue(linha.getNivel());
            }
        });

        btnCriar.setOnAction(e -> {
            String username = txtUsername.getText().trim();
            String password = txtPassword.getText().trim();
            String nivel = cbNivel.getValue();

            if (username.isEmpty() || password.isEmpty()) {
                mostrarAviso("Preencha username e password.");
                return;
            }

            boolean ok = sistema.criarUsuario(username, password, nivel);

            if (ok) {
                mostrarInfo("Utilizador criado com sucesso.");
                txtId.clear();
                txtUsername.clear();
                txtPassword.clear();
                cbNivel.getSelectionModel().selectFirst();
                carregarTabelaUsuarios(tabela);
            } else {
                mostrarErro("Não foi possível criar o utilizador.");
            }
        });

        btnAtualizar.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                String username = txtUsername.getText().trim();
                String password = txtPassword.getText().trim();
                String nivel = cbNivel.getValue();

                if (username.isEmpty() || password.isEmpty()) {
                    mostrarAviso("Preencha username e password.");
                    return;
                }

                boolean ok = sistema.atualizarUsuario(id, username, password, nivel);

                if (ok) {
                    mostrarInfo("Utilizador atualizado com sucesso.");
                    carregarTabelaUsuarios(tabela);
                } else {
                    mostrarErro("Não foi possível atualizar o utilizador.");
                }

            } catch (NumberFormatException ex) {
                mostrarErro("ID inválido.");
            }
        });

        btnEliminar.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());

                if (!confirmar("Deseja eliminar o utilizador selecionado?")) {
                    return;
                }

                boolean ok = sistema.eliminarUsuario(id);

                if (ok) {
                    mostrarInfo("Utilizador eliminado com sucesso.");
                    txtId.clear();
                    txtUsername.clear();
                    txtPassword.clear();
                    cbNivel.getSelectionModel().selectFirst();
                    carregarTabelaUsuarios(tabela);
                } else {
                    mostrarErro("Não foi possível eliminar o utilizador.");
                }

            } catch (NumberFormatException ex) {
                mostrarErro("ID inválido.");
            }
        });

        btnRecarregar.setOnAction(e -> carregarTabelaUsuarios(tabela));

        p.setTop(topo);
        p.setCenter(tabela);
        return p;
    }

    private void carregarTabelaUsuarios(TableView<UsuarioLinha> tabela) {
        tabela.getItems().clear();
        List<Object[]> lista = sistema.listarUsuarios();
        for (Object[] u : lista) {
            tabela.getItems().add(new UsuarioLinha(
                    (Integer) u[0],
                    u[1].toString(),
                    u[2].toString(),
                    u[3].toString()
            ));
        }
    }

    private boolean confirmar(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    private void mostrarInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void mostrarAviso(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void mostrarErro(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static class ItemCarrinho {
        private final SimpleStringProperty produto;
        private final SimpleIntegerProperty quantidade;
        private final SimpleObjectProperty<Double> preco;
        private final SimpleObjectProperty<Double> total;

        public ItemCarrinho(String produto, int quantidade, double preco, double total) {
            this.produto = new SimpleStringProperty(produto);
            this.quantidade = new SimpleIntegerProperty(quantidade);
            this.preco = new SimpleObjectProperty<>(preco);
            this.total = new SimpleObjectProperty<>(total);
        }

        public String getProduto() { return produto.get(); }
        public int getQuantidade() { return quantidade.get(); }
        public Double getPreco() { return preco.get(); }
        public Double getTotal() { return total.get(); }
    }

    public static class ProdutoLinha {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty nome;
        private final SimpleObjectProperty<Double> preco;

        public ProdutoLinha(int id, String nome, double preco) {
            this.id = new SimpleIntegerProperty(id);
            this.nome = new SimpleStringProperty(nome);
            this.preco = new SimpleObjectProperty<>(preco);
        }

        public int getId() { return id.get(); }
        public String getNome() { return nome.get(); }
        public Double getPreco() { return preco.get(); }
    }

    public static class RelatorioLinha {
        private final SimpleStringProperty produto;
        private final SimpleIntegerProperty quantidade;
        private final SimpleObjectProperty<Double> precoUnitario;
        private final SimpleObjectProperty<Double> total;
        private final SimpleStringProperty formaPagamento;
        private final SimpleStringProperty data;

        public RelatorioLinha(String produto, int quantidade, double precoUnitario, double total, String formaPagamento, String data) {
            this.produto = new SimpleStringProperty(produto);
            this.quantidade = new SimpleIntegerProperty(quantidade);
            this.precoUnitario = new SimpleObjectProperty<>(precoUnitario);
            this.total = new SimpleObjectProperty<>(total);
            this.formaPagamento = new SimpleStringProperty(formaPagamento);
            this.data = new SimpleStringProperty(data);
        }

        public String getProduto() { return produto.get(); }
        public int getQuantidade() { return quantidade.get(); }
        public Double getPrecoUnitario() { return precoUnitario.get(); }
        public Double getTotal() { return total.get(); }
        public String getFormaPagamento() { return formaPagamento.get(); }
        public String getData() { return data.get(); }
    }

    public static class CompraDiaLinha {
        private final SimpleIntegerProperty idVenda;
        private final SimpleStringProperty data;
        private final SimpleStringProperty produto;
        private final SimpleIntegerProperty quantidade;
        private final SimpleObjectProperty<Double> total;
        private final SimpleStringProperty forma;

        public CompraDiaLinha(int idVenda, String data, String produto, int quantidade, double total, String forma) {
            this.idVenda = new SimpleIntegerProperty(idVenda);
            this.data = new SimpleStringProperty(data);
            this.produto = new SimpleStringProperty(produto);
            this.quantidade = new SimpleIntegerProperty(quantidade);
            this.total = new SimpleObjectProperty<>(total);
            this.forma = new SimpleStringProperty(forma);
        }

        public int getIdVenda() { return idVenda.get(); }
        public String getData() { return data.get(); }
        public String getProduto() { return produto.get(); }
        public int getQuantidade() { return quantidade.get(); }
        public Double getTotal() { return total.get(); }
        public String getForma() { return forma.get(); }
    }

    public static class UsuarioLinha {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty username;
        private final SimpleStringProperty password;
        private final SimpleStringProperty nivel;

        public UsuarioLinha(int id, String username, String password, String nivel) {
            this.id = new SimpleIntegerProperty(id);
            this.username = new SimpleStringProperty(username);
            this.password = new SimpleStringProperty(password);
            this.nivel = new SimpleStringProperty(nivel);
        }

        public int getId() { return id.get(); }
        public String getUsername() { return username.get(); }
        public String getPassword() { return password.get(); }
        public String getNivel() { return nivel.get(); }
    }
}