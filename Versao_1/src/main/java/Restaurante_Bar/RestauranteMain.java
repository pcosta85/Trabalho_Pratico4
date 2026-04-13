package Restaurante_Bar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RestauranteMain extends JFrame {

    private final Usuario usuarioLogado;
    private final RestauranteSistema sistema = new RestauranteSistema();
    private final JPanel painelPrincipal = new JPanel(new CardLayout());

    public RestauranteMain(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        setTitle("Sistema Restaurante / Bar");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setPreferredSize(new Dimension(220, getHeight()));
        menu.setBackground(new Color(30, 30, 30));

        JLabel lblUser = new JLabel(
                "<html><center>" + usuarioLogado.getUsername() +
                "<br/>" + usuarioLogado.getNivelAcesso() + "</center></html>",
                SwingConstants.CENTER
        );
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Arial", Font.BOLD, 14));
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);

        menu.add(Box.createVerticalStrut(20));
        menu.add(lblUser);
        menu.add(Box.createVerticalStrut(20));

        JButton btnInicio = criarBotaoMenu("Início");
        JButton btnLoja = criarBotaoMenu("Loja / Caixa");
        JButton btnProdutos = criarBotaoMenu("Produtos");
        JButton btnRelatorios = criarBotaoMenu("Relatórios");
        JButton btnComprasDia = criarBotaoMenu("Compras do Dia");
        JButton btnDefinicoes = criarBotaoMenu("Definições");
        JButton btnUsuarios = criarBotaoMenu("Utilizadores");
        JButton btnLogout = criarBotaoMenu("Terminar Sessão");
        JButton btnSair = criarBotaoMenu("Sair");

        menu.add(btnInicio);
        menu.add(Box.createVerticalStrut(10));

        menu.add(btnLoja);
        menu.add(Box.createVerticalStrut(10));

        if (isAdmin() || isGestor()) {
            menu.add(btnProdutos);
            menu.add(Box.createVerticalStrut(10));

            menu.add(btnRelatorios);
            menu.add(Box.createVerticalStrut(10));
        }

        if (isAdmin()) {
            menu.add(btnUsuarios);
            menu.add(Box.createVerticalStrut(10));
        }

        menu.add(btnComprasDia);
        menu.add(Box.createVerticalStrut(10));

        // ADMIN não vê Definições
        if (!isAdmin()) {
            menu.add(btnDefinicoes);
            menu.add(Box.createVerticalStrut(10));
        }

        menu.add(Box.createVerticalGlue());

        menu.add(btnLogout);
        menu.add(Box.createVerticalStrut(10));
        menu.add(btnSair);
        menu.add(Box.createVerticalStrut(10));

        painelPrincipal.add(criarPaginaInicio(), "INICIO");
        painelPrincipal.add(criarPaginaLoja(), "LOJA");
        painelPrincipal.add(criarPaginaProdutos(), "PRODUTOS");
        painelPrincipal.add(criarPaginaRelatorios(), "RELATORIOS");
        painelPrincipal.add(criarPaginaComprasDia(), "COMPRAS_DIA");
        painelPrincipal.add(criarPaginaDefinicoes(), "DEFINICOES");
        painelPrincipal.add(criarPaginaUsuarios(), "USUARIOS");

        btnInicio.addActionListener(e -> mostrar("INICIO"));
        btnLoja.addActionListener(e -> mostrar("LOJA"));
        btnProdutos.addActionListener(e -> mostrar("PRODUTOS"));
        btnRelatorios.addActionListener(e -> mostrar("RELATORIOS"));
        btnComprasDia.addActionListener(e -> mostrar("COMPRAS_DIA"));
        btnUsuarios.addActionListener(e -> mostrar("USUARIOS"));

        if (!isAdmin()) {
            btnDefinicoes.addActionListener(e -> mostrar("DEFINICOES"));
        }

        btnLogout.addActionListener(e -> {
            int op = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja terminar a sessão atual?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );
            if (op == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });

        btnSair.addActionListener(e -> {
            int op = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja sair do sistema?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );
            if (op == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        add(menu, BorderLayout.WEST);
        add(painelPrincipal, BorderLayout.CENTER);

        mostrar("INICIO");
    }

    private JButton criarBotaoMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(50, 50, 50));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(70, 70, 70));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(50, 50, 50));
            }
        });

        return btn;
    }

    private boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(usuarioLogado.getNivelAcesso());
    }

    private boolean isGestor() {
        return "GESTOR".equalsIgnoreCase(usuarioLogado.getNivelAcesso());
    }

    private void mostrar(String nome) {
        ((CardLayout) painelPrincipal.getLayout()).show(painelPrincipal, nome);
    }

    private JPanel criarPaginaInicio() {
        JPanel p = new JPanel(new BorderLayout());

        JLabel lbl = new JLabel(
                "Bem-vindo " + usuarioLogado.getUsername() + " - Perfil: " + usuarioLogado.getNivelAcesso(),
                SwingConstants.CENTER
        );
        lbl.setFont(new Font("Arial", Font.BOLD, 22));

        p.add(lbl, BorderLayout.CENTER);
        return p;
    }

    private JPanel criarPaginaLoja() {
        JPanel p = new JPanel(new BorderLayout());

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> cbProduto = new JComboBox<>();
        JTextField txtQtd = new JTextField(5);
        JComboBox<String> cbPagamento = new JComboBox<>(new String[]{"Dinheiro", "Cartão", "PIX"});
        JTextField txtRecebido = new JTextField(8);

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnFinalizar = new JButton("Finalizar");

        for (Object[] prod : sistema.listarProdutos()) {
            cbProduto.addItem(prod[1].toString());
        }

        topo.add(new JLabel("Produto:"));
        topo.add(cbProduto);
        topo.add(new JLabel("Qtd:"));
        topo.add(txtQtd);
        topo.add(btnAdicionar);

        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"Produto", "Qtd", "Preço", "Total"}, 0
        );
        JTable tabela = new JTable(modelo);
        List<Object[]> carrinho = new ArrayList<>();

        JLabel lblTotal = new JLabel("Total: 0.00");
        JLabel lblTroco = new JLabel("Troco: 0.00");

        JPanel rodape = new JPanel();
        rodape.add(new JLabel("Pagamento:"));
        rodape.add(cbPagamento);
        rodape.add(new JLabel("Recebido:"));
        rodape.add(txtRecebido);
        rodape.add(lblTotal);
        rodape.add(lblTroco);
        rodape.add(btnFinalizar);

        btnAdicionar.addActionListener(e -> {
            try {
                Object[] prod = sistema.obterProduto((String) cbProduto.getSelectedItem());
                if (prod == null) {
                    JOptionPane.showMessageDialog(this, "Produto não encontrado.");
                    return;
                }

                int qtd = Integer.parseInt(txtQtd.getText().trim());
                double preco = (double) prod[2];
                double total = preco * qtd;

                carrinho.add(new Object[]{prod[1], qtd, prod[2], total});
                modelo.addRow(new Object[]{prod[1], qtd, prod[2], total});

                double soma = 0;
                for (Object[] item : carrinho) {
                    soma += (double) item[3];
                }
                lblTotal.setText("Total: " + String.format("%.2f", soma));
                txtQtd.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Quantidade inválida.");
            }
        });

        btnFinalizar.addActionListener(e -> {
            if (carrinho.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Carrinho vazio.");
                return;
            }

            try {
                double totalGeral = 0;
                for (Object[] item : carrinho) {
                    totalGeral += (double) item[3];
                }

                String forma = cbPagamento.getSelectedItem().toString();
                Double recebido = null;
                Double troco = null;

                if ("Dinheiro".equalsIgnoreCase(forma)) {
                    recebido = Double.parseDouble(txtRecebido.getText().trim());
                    troco = recebido - totalGeral;

                    if (troco < 0) {
                        JOptionPane.showMessageDialog(this, "Valor insuficiente.");
                        return;
                    }

                    lblTroco.setText("Troco: " + String.format("%.2f", troco));
                }

                boolean ok = sistema.finalizarVenda(
                        usuarioLogado.getUsername(),
                        forma,
                        recebido,
                        troco,
                        carrinho
                );

                if (ok) {
                    JOptionPane.showMessageDialog(this, "Venda feita com sucesso.");
                    carrinho.clear();
                    modelo.setRowCount(0);
                    lblTotal.setText("Total: 0.00");
                    lblTroco.setText("Troco: 0.00");
                    txtRecebido.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao finalizar venda.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro no valor recebido.");
            }
        });

        p.add(topo, BorderLayout.NORTH);
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);
        p.add(rodape, BorderLayout.SOUTH);
        return p;
    }

    private JPanel criarPaginaProdutos() {
        JPanel p = new JPanel(new BorderLayout());

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtId = new JTextField(5);
        JTextField txtNome = new JTextField(15);
        JTextField txtPreco = new JTextField(10);

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnRecarregar = new JButton("Recarregar");

        topo.add(new JLabel("ID:"));
        topo.add(txtId);
        topo.add(new JLabel("Nome:"));
        topo.add(txtNome);
        topo.add(new JLabel("Preço:"));
        topo.add(txtPreco);
        topo.add(btnCadastrar);
        topo.add(btnAtualizar);
        topo.add(btnEliminar);
        topo.add(btnRecarregar);

        DefaultTableModel modelo = new DefaultTableModel(new String[]{"ID", "Produto", "Preço"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(modelo);
        carregarTabelaProdutos(modelo);

        tabela.getSelectionModel().addListSelectionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                txtId.setText(modelo.getValueAt(linha, 0).toString());
                txtNome.setText(modelo.getValueAt(linha, 1).toString());
                txtPreco.setText(modelo.getValueAt(linha, 2).toString());
            }
        });

        btnCadastrar.addActionListener(e -> {
            try {
                boolean ok = sistema.cadastrarProduto(
                        txtNome.getText().trim(),
                        Double.parseDouble(txtPreco.getText().trim())
                );
                if (ok) {
                    carregarTabelaProdutos(modelo);
                    txtId.setText("");
                    txtNome.setText("");
                    txtPreco.setText("");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dados inválidos.");
            }
        });

        btnAtualizar.addActionListener(e -> {
            try {
                boolean ok = sistema.atualizarProduto(
                        Integer.parseInt(txtId.getText().trim()),
                        txtNome.getText().trim(),
                        Double.parseDouble(txtPreco.getText().trim())
                );
                if (ok) {
                    carregarTabelaProdutos(modelo);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dados inválidos.");
            }
        });

        btnEliminar.addActionListener(e -> {
            try {
                boolean ok = sistema.eliminarProduto(Integer.parseInt(txtId.getText().trim()));
                if (ok) {
                    carregarTabelaProdutos(modelo);
                    txtId.setText("");
                    txtNome.setText("");
                    txtPreco.setText("");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        });

        btnRecarregar.addActionListener(e -> carregarTabelaProdutos(modelo));

        p.add(topo, BorderLayout.NORTH);
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);
        return p;
    }

    private void carregarTabelaProdutos(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        for (Object[] p : sistema.listarProdutos()) {
            modelo.addRow(p);
        }
    }

    private JPanel criarPaginaRelatorios() {
        JPanel p = new JPanel(new BorderLayout());

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<Integer> cbAno = new JComboBox<>();
        JButton btnGerar = new JButton("Gerar");
        JButton btnCSV = new JButton("Exportar CSV");
        JButton btnExcel = new JButton("Exportar Excel");

        for (Integer ano : sistema.listarAnosVenda()) {
            cbAno.addItem(ano);
        }

        topo.add(new JLabel("Ano:"));
        topo.add(cbAno);
        topo.add(btnGerar);
        topo.add(btnCSV);
        topo.add(btnExcel);

        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"Produto", "Qtd", "Preço Unit.", "Total", "Forma Pagamento", "Data"}, 0
        );
        JTable tabela = new JTable(modelo);

        JTextArea resumo = new JTextArea();
        resumo.setEditable(false);

        JSplitPane split = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(tabela),
                new JScrollPane(resumo)
        );
        split.setResizeWeight(0.7);

        btnGerar.addActionListener(e -> {
            Integer ano = (Integer) cbAno.getSelectedItem();
            if (ano == null) {
                JOptionPane.showMessageDialog(this, "Nenhum ano encontrado.");
                return;
            }

            modelo.setRowCount(0);
            for (Object[] row : sistema.listarRelatorioVendas(ano)) {
                modelo.addRow(row);
            }

            StringBuilder sb = new StringBuilder();
            sb.append("=== Total por Forma de Pagamento ===\n");
            for (Object[] row : sistema.totalPorFormaPagamento(ano)) {
                sb.append(row[0]).append(": ").append(String.format("%.2f", (Double) row[1])).append("\n");
            }

            sb.append("\n=== Total por Produto ===\n");
            for (Object[] row : sistema.totalPorProduto(ano)) {
                sb.append(row[0]).append(": ").append(String.format("%.2f", (Double) row[1])).append("\n");
            }

            resumo.setText(sb.toString());
        });

        btnCSV.addActionListener(e -> {
            Integer ano = (Integer) cbAno.getSelectedItem();
            if (ano == null) {
                JOptionPane.showMessageDialog(this, "Nenhum ano selecionado.");
                return;
            }

            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                boolean ok = sistema.exportarRelatorioCSV(
                        chooser.getSelectedFile().getAbsolutePath(),
                        ano
                );
                JOptionPane.showMessageDialog(this, ok ? "CSV exportado." : "Erro ao exportar CSV.");
            }
        });

        btnExcel.addActionListener(e -> {
            Integer ano = (Integer) cbAno.getSelectedItem();
            if (ano == null) {
                JOptionPane.showMessageDialog(this, "Nenhum ano selecionado.");
                return;
            }

            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                boolean ok = sistema.exportarRelatorioExcel(
                        chooser.getSelectedFile().getAbsolutePath(),
                        ano
                );
                JOptionPane.showMessageDialog(this, ok ? "Excel exportado." : "Erro ao exportar Excel.");
            }
        });

        p.add(topo, BorderLayout.NORTH);
        p.add(split, BorderLayout.CENTER);
        return p;
    }

    private JPanel criarPaginaComprasDia() {
        JPanel p = new JPanel(new BorderLayout());

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtData = new JTextField(10);
        JButton btnFiltrar = new JButton("Filtrar");
        JButton btnEliminar = new JButton("Eliminar Venda");

        topo.add(new JLabel("Data (AAAA-MM-DD):"));
        topo.add(txtData);
        topo.add(btnFiltrar);
        topo.add(btnEliminar);

        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"ID Venda", "Data", "Produto", "Qtd", "Total", "Forma"}, 0
        );
        JTable tabela = new JTable(modelo);

        btnFiltrar.addActionListener(e -> {
            modelo.setRowCount(0);
            for (Object[] row : sistema.listarComprasPorData(txtData.getText().trim())) {
                modelo.addRow(row);
            }
        });

        btnEliminar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha < 0) {
                JOptionPane.showMessageDialog(this, "Selecione uma venda.");
                return;
            }

            int idVenda = Integer.parseInt(modelo.getValueAt(linha, 0).toString());

            int op = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja eliminar a venda selecionada?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );

            if (op != JOptionPane.YES_OPTION) {
                return;
            }

            boolean ok = sistema.eliminarVenda(idVenda);
            JOptionPane.showMessageDialog(this, ok ? "Venda eliminada." : "Erro ao eliminar venda.");
        });

        p.add(topo, BorderLayout.NORTH);
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);
        return p;
    }

    private JPanel criarPaginaDefinicoes() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 10, 10, 10);
        gc.anchor = GridBagConstraints.WEST;

        JTextField txtAtual = new JTextField(15);
        JTextField txtNova = new JTextField(15);
        JTextField txtConfirmar = new JTextField(15);
        JButton btnAlterar = new JButton("Alterar Senha");

        gc.gridx = 0; gc.gridy = 0; p.add(new JLabel("Senha Atual:"), gc);
        gc.gridx = 1; p.add(txtAtual, gc);

        gc.gridx = 0; gc.gridy = 1; p.add(new JLabel("Nova Senha:"), gc);
        gc.gridx = 1; p.add(txtNova, gc);

        gc.gridx = 0; gc.gridy = 2; p.add(new JLabel("Confirmar Nova Senha:"), gc);
        gc.gridx = 1; p.add(txtConfirmar, gc);

        gc.gridx = 1; gc.gridy = 3; p.add(btnAlterar, gc);

        btnAlterar.addActionListener(e -> {
            String atual = txtAtual.getText().trim();
            String nova = txtNova.getText().trim();
            String confirmar = txtConfirmar.getText().trim();

            if (!nova.equals(confirmar)) {
                JOptionPane.showMessageDialog(this, "A confirmação não coincide.");
                return;
            }

            boolean ok = sistema.alterarSenha(usuarioLogado.getUsername(), atual, nova);
            JOptionPane.showMessageDialog(this, ok ? "Senha alterada com sucesso." : "Erro ao alterar senha.");
        });

        return p;
    }

    private JPanel criarPaginaUsuarios() {
        JPanel p = new JPanel(new BorderLayout());

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JTextField txtId = new JTextField(5);
        JTextField txtUsername = new JTextField(12);
        JPasswordField txtPassword = new JPasswordField(12);
        JComboBox<String> cbNivel = new JComboBox<>(new String[]{"ADMIN", "GESTOR", "ATENDENTE"});

        JButton btnCriar = new JButton("Criar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnRecarregar = new JButton("Recarregar");

        topo.add(new JLabel("ID:"));
        topo.add(txtId);
        topo.add(new JLabel("Username:"));
        topo.add(txtUsername);
        topo.add(new JLabel("Password:"));
        topo.add(txtPassword);
        topo.add(new JLabel("Nível:"));
        topo.add(cbNivel);
        topo.add(btnCriar);
        topo.add(btnAtualizar);
        topo.add(btnEliminar);
        topo.add(btnRecarregar);

        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"ID", "Username", "Password", "Nível"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(modelo);
        carregarTabelaUsuarios(modelo);

        tabela.getSelectionModel().addListSelectionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                txtId.setText(modelo.getValueAt(linha, 0).toString());
                txtUsername.setText(modelo.getValueAt(linha, 1).toString());
                txtPassword.setText(modelo.getValueAt(linha, 2).toString());
                cbNivel.setSelectedItem(modelo.getValueAt(linha, 3).toString());
            }
        });

        btnCriar.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            String nivel = cbNivel.getSelectedItem().toString();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha username e password.");
                return;
            }

            boolean ok = sistema.criarUsuario(username, password, nivel);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Utilizador criado com sucesso.");
                txtId.setText("");
                txtUsername.setText("");
                txtPassword.setText("");
                cbNivel.setSelectedIndex(0);
                carregarTabelaUsuarios(modelo);
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível criar o utilizador.");
            }
        });

        btnAtualizar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();
                String nivel = cbNivel.getSelectedItem().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Preencha username e password.");
                    return;
                }

                boolean ok = sistema.atualizarUsuario(id, username, password, nivel);

                if (ok) {
                    JOptionPane.showMessageDialog(this, "Utilizador atualizado com sucesso.");
                    carregarTabelaUsuarios(modelo);
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível atualizar o utilizador.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        });

        btnEliminar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());

                int op = JOptionPane.showConfirmDialog(
                        this,
                        "Deseja eliminar o utilizador selecionado?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION
                );

                if (op != JOptionPane.YES_OPTION) {
                    return;
                }

                boolean ok = sistema.eliminarUsuario(id);

                if (ok) {
                    JOptionPane.showMessageDialog(this, "Utilizador eliminado com sucesso.");
                    txtId.setText("");
                    txtUsername.setText("");
                    txtPassword.setText("");
                    cbNivel.setSelectedIndex(0);
                    carregarTabelaUsuarios(modelo);
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível eliminar o utilizador.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        });

        btnRecarregar.addActionListener(e -> carregarTabelaUsuarios(modelo));

        p.add(topo, BorderLayout.NORTH);
        p.add(new JScrollPane(tabela), BorderLayout.CENTER);
        return p;
    }

    private void carregarTabelaUsuarios(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        List<Object[]> lista = sistema.listarUsuarios();
        for (Object[] u : lista) {
            modelo.addRow(u);
        }
    }
}