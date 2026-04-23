package Restaurante_Bar;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class RestauranteSistema {

    // ==================== PRODUTOS ====================

    public boolean cadastrarProduto(String nome, double preco) {
        String sql = "INSERT INTO produtos (nome, preco) VALUES (?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome.trim());
            stmt.setDouble(2, preco);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarProduto(int id, String nome, double preco) {
        String sql = "UPDATE produtos SET nome = ?, preco = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome.trim());
            stmt.setDouble(2, preco);
            stmt.setInt(3, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarProduto(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Object[]> listarProdutos() {
        List<Object[]> lista = new ArrayList<Object[]>();
        String sql = "SELECT id, nome, preco FROM produtos ORDER BY nome";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Object[] obterProduto(String nome) {
        String sql = "SELECT id, nome, preco FROM produtos WHERE nome = ? LIMIT 1";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Object[]{
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getDouble("preco")
                    };
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ==================== VENDAS ====================

    public boolean finalizarVenda(String usuario, String forma, Double recebido, Double troco, List<Object[]> carrinho) {
        String sqlVenda = "INSERT INTO venda (data, usuario, forma_pagamento, valor_recebido, troco) VALUES (NOW(), ?, ?, ?, ?)";
        String sqlCompra = "INSERT INTO compra (produto, quantidade, preco_unitario, total, forma_pagamento, id_venda) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = Conexao.conectar();
            conn.setAutoCommit(false);

            int idVenda;

            try (PreparedStatement stmtVenda = conn.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS)) {
                stmtVenda.setString(1, usuario);
                stmtVenda.setString(2, forma);
                stmtVenda.setObject(3, recebido);
                stmtVenda.setObject(4, troco);
                stmtVenda.executeUpdate();

                try (ResultSet rs = stmtVenda.getGeneratedKeys()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return false;
                    }
                    idVenda = rs.getInt(1);
                }
            }

            for (Object[] item : carrinho) {
                try (PreparedStatement stmtCompra = conn.prepareStatement(sqlCompra)) {
                    stmtCompra.setString(1, (String) item[0]);
                    stmtCompra.setInt(2, (Integer) item[1]);
                    stmtCompra.setDouble(3, (Double) item[2]);
                    stmtCompra.setDouble(4, (Double) item[3]);
                    stmtCompra.setString(5, forma);
                    stmtCompra.setInt(6, idVenda);
                    stmtCompra.executeUpdate();
                }
            }

            conn.commit();

            String caminhoRecibo = ReciboVenda.gerar(usuario, forma, recebido, troco, carrinho);

            if (caminhoRecibo != null) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    new VisualizadorPDF(caminhoRecibo).setVisible(true);
                });
            }

            return true;

        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ==================== RELATÓRIOS ====================

    public List<Integer> listarAnosVenda() {
        List<Integer> anos = new ArrayList<Integer>();
        String sql = "SELECT DISTINCT YEAR(data) AS ano FROM venda ORDER BY ano DESC";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                anos.add(rs.getInt("ano"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return anos;
    }

    public List<Object[]> listarRelatorioVendas(int ano, int mes) {
        List<Object[]> lista = new ArrayList<Object[]>();

        String sql = "SELECT c.produto, c.quantidade, c.preco_unitario, c.total, c.forma_pagamento, DATE(v.data) AS data " +
                     "FROM compra c INNER JOIN venda v ON c.id_venda = v.id_venda " +
                     "WHERE YEAR(v.data) = ? AND MONTH(v.data) = ? " +
                     "ORDER BY v.data DESC";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ano);
            stmt.setInt(2, mes);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Object[]{
                            rs.getString("produto"),
                            rs.getInt("quantidade"),
                            rs.getDouble("preco_unitario"),
                            rs.getDouble("total"),
                            rs.getString("forma_pagamento"),
                            rs.getDate("data").toString()
                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Object[]> totalPorFormaPagamento(int ano, int mes) {
        List<Object[]> lista = new ArrayList<Object[]>();

        String sql = "SELECT c.forma_pagamento, SUM(c.total) AS total " +
                     "FROM compra c INNER JOIN venda v ON c.id_venda = v.id_venda " +
                     "WHERE YEAR(v.data) = ? AND MONTH(v.data) = ? " +
                     "GROUP BY c.forma_pagamento ORDER BY total DESC";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ano);
            stmt.setInt(2, mes);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Object[]{
                            rs.getString("forma_pagamento"),
                            rs.getDouble("total")
                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Object[]> totalPorProduto(int ano, int mes) {
        List<Object[]> lista = new ArrayList<Object[]>();

        String sql = "SELECT c.produto, SUM(c.total) AS total " +
                     "FROM compra c INNER JOIN venda v ON c.id_venda = v.id_venda " +
                     "WHERE YEAR(v.data) = ? AND MONTH(v.data) = ? " +
                     "GROUP BY c.produto ORDER BY total DESC";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ano);
            stmt.setInt(2, mes);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Object[]{
                            rs.getString("produto"),
                            rs.getDouble("total")
                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ==================== COMPRAS DO DIA ====================

    public List<Object[]> listarComprasPorData(String data) {
        List<Object[]> lista = new ArrayList<Object[]>();

        String sql = "SELECT v.id_venda, DATE(v.data) AS data, c.produto, c.quantidade, c.total, v.forma_pagamento " +
                     "FROM venda v INNER JOIN compra c ON v.id_venda = c.id_venda " +
                     "WHERE DATE(v.data) = ? ORDER BY v.id_venda DESC";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, data);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Object[]{
                            rs.getInt("id_venda"),
                            rs.getString("data"),
                            rs.getString("produto"),
                            rs.getInt("quantidade"),
                            rs.getDouble("total"),
                            rs.getString("forma_pagamento")
                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean eliminarVenda(int idVenda) {
        Connection conn = null;

        try {
            conn = Conexao.conectar();
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM compra WHERE id_venda = ?")) {
                stmt1.setInt(1, idVenda);
                stmt1.executeUpdate();
            }

            try (PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM venda WHERE id_venda = ?")) {
                stmt2.setInt(1, idVenda);
                stmt2.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ==================== DEFINIÇÕES / SENHA ====================

    public boolean alterarSenha(String username, String senhaAtual, String novaSenha) {
        String sqlSelect = "SELECT password FROM usuarios WHERE username = ?";
        String sqlUpdate = "UPDATE usuarios SET password = ? WHERE username = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect)) {

            stmtSelect.setString(1, username);

            try (ResultSet rs = stmtSelect.executeQuery()) {
                if (!rs.next()) {
                    return false;
                }

                String senhaGuardada = rs.getString("password");
                if (!senhaGuardada.equals(senhaAtual)) {
                    return false;
                }
            }

            try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
                stmtUpdate.setString(1, novaSenha);
                stmtUpdate.setString(2, username);
                return stmtUpdate.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ==================== UTILIZADORES ====================

    public List<Object[]> listarUsuarios() {
        List<Object[]> lista = new ArrayList<Object[]>();
        String sql = "SELECT id, username, password, nivel FROM usuarios ORDER BY username";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("nivel")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean criarUsuario(String username, String password, String nivel) {
        String sql = "INSERT INTO usuarios (username, password, nivel) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username.trim());
            stmt.setString(2, password.trim());
            stmt.setString(3, nivel.trim().toUpperCase());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarUsuario(int id, String username, String password, String nivel) {
        String sql = "UPDATE usuarios SET username = ?, password = ?, nivel = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username.trim());
            stmt.setString(2, password.trim());
            stmt.setString(3, nivel.trim().toUpperCase());
            stmt.setInt(4, id);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ==================== EXPORTAÇÃO ====================

    public boolean exportarRelatorioCSV(String caminho, int ano, int mes) {
        try (PrintWriter writer = new PrintWriter(caminho, "UTF-8")) {
            writer.println("Produto;Quantidade;Preço Unitário;Total;Forma Pagamento;Data");

            List<Object[]> lista = listarRelatorioVendas(ano, mes);
            for (Object[] row : lista) {
                writer.println(
                        row[0] + ";" +
                        row[1] + ";" +
                        row[2] + ";" +
                        row[3] + ";" +
                        row[4] + ";" +
                        row[5]
                );
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean exportarRelatorioExcel(String caminho, int ano, int mes) {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Relatorio");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Produto");
            header.createCell(1).setCellValue("Quantidade");
            header.createCell(2).setCellValue("Preço Unitário");
            header.createCell(3).setCellValue("Total");
            header.createCell(4).setCellValue("Forma Pagamento");
            header.createCell(5).setCellValue("Data");

            List<Object[]> lista = listarRelatorioVendas(ano, mes);
            int rowNum = 1;

            for (Object[] rowData : lista) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rowData[0].toString());
                row.createCell(1).setCellValue(Integer.parseInt(rowData[1].toString()));
                row.createCell(2).setCellValue(Double.parseDouble(rowData[2].toString()));
                row.createCell(3).setCellValue(Double.parseDouble(rowData[3].toString()));
                row.createCell(4).setCellValue(rowData[4].toString());
                row.createCell(5).setCellValue(rowData[5].toString());
            }

            for (int i = 0; i < 6; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream out = new FileOutputStream(caminho)) {
                wb.write(out);
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}