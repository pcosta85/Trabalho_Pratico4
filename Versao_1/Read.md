# Relatório do Sistema Restaurante/Bar
### Curso: Engenharia Informática
### Instituição: Universidade de Santiago
### Unidade Curricular: Programação Orientada a Objetos
### Tema: Sistema de Gestão de Restaurante/Bar


<br>

## 1. Introdução
   
   O presente projeto consiste no desenvolvimento de um sistema desktop para gestão de um restaurante/bar, implementado na linguagem **Java**, utilizando **Swing** para interface gráfica e **MySQL** para persistência de dados.

   ### O sistema permite:
   - Gestão de utilizadores
   - Gestão de produtos
   - Registo de vendas
   - Geração de recibos em PDF
   - Visualização de relatórios
  
  <br>
  
## 2. Objetivos

   ### Objetivo Geral

   Desenvolver um sistema completo de gestão com interface gráfica e base de dados.
 
   **Objetivos Específicos**

   - Implementar autenticação de utilizadores
   - Aplicar operações CRUD
   - Gerar relatórios e exportações
   - Integrar base de dados MySQL
   - Aplicar diferentes níveis de acesso

<br>

## 3. Arquitetura do Sistema
   
  ### O sistema segue uma arquitetura modular composta por:
   
| Camada              | Descrição                       |
|--------------------|----------------------------------|
| Interface (View)   | Interface gráfica com Swing      |
| Lógica (Controller)| Classe **`RestauranteSistema`**  |
| Dados (Model)      | Classe **`Usuario`**                   |
| Base de Dados      | MySQL Workbench                  |

<br>

## 4. Estrutura do Projeto
```bash
Versao_1/
 ├── src/main/java/Restaurante_Bar/
 │    ├── LoginFrame.java
 │    ├── RestauranteMain.java
 │    ├── RestauranteSistema.java
 │    ├── Conexao.java
 │    ├── Autenticacao.java
 │    ├── Usuario.java
 │    ├── ReciboVenda.java
 │    ├── VisualizadorPDF.java
 │
 ├── docs/images/
 ├── recibos/
 ├── faturas/
 ├── pom.xml
```

<br>

## 5. Autenticação de Utilizadores

### A autenticação é feita através da classe:
- VUsa `PreparedStatement` para evitar SQL Injection
- Valida o utilizador na base de dados
- Retorna um objeto `Usuario` com nível de acesso

```bash
</> Java
public Usuario login(String username, String password) {
    String sql = "SELECT id, username, password, nivel FROM usuarios WHERE username = ? AND password = ?";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, username.trim());
        stmt.setString(2, password.trim());

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Usuario(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("nivel")
            );
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}
```
### Atributos da Classe
```bash
</>Java
private int id;
private String username;
private String password;
private String nivelAcesso;
```
- `id` - Identificador único do utilizador
- `username` - Nome de utilizador
- `password` - Palavra-passe
- `nivelAcesso` - Define permissões no sistema

### Construtor
```bash
</>Java
public Usuario(int id, String username, String password, String nivelAcesso)
```
- Inicializa o objeto com dados vindos da base de dados
- Usado após autenticação

### Métodos Getters
```bash
</>Java
public int getId()
public String getUsername()
public String getPassword()
public String getNivelAcesso()
```
- Permitem aceder aos dados do utilizador
- Mantêm o encapsulamento (boa prática de CAA)

### Papel no Sistema
**A classe Usuario é utilizada em:**
  - Autenticação (Autenticacao.java)
  - Controle de acesso
  - Interface gráfica (menu dinâmico)

### Funcionalidades:
- Criar utilizador
- Atualizar utilizador
- Eliminar utilizador
- Listar utilizadores

### Níveis de Acesso:
|Nível     |	Permissões                       |
|----------|-------------------------------------|
|ADMIN	   |Total (CRUD utilizadores e sistema)  |
|GESTOR	   |Gestão de produtos e relatórios      |
|ATENDENTE |Apenas vendas                        |

```bash
</>Java
public boolean criarUsuario(String username, String password, String nivel)
```
- Permite criar novos utilizadores
- Define nível de acesso


### Controle de Acesso
```bash
</>Java
if (nivel.equals("ADMIN")) {
    // acesso total
}
```
### Explicação
- Define permissões no sistema
- Controla funcionalidades disponíveis


**SQL** - Comando para criação de tabela no MYSQL Worckbench de Utilizadores e Niveis de Acesso

```bash
</>SQL

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100),
    password VARCHAR(100),
    nivel VARCHAR(20)
);
```

### Autenticação de Utilizadores
A classe `Autenticacao` é responsável por validar o acesso dos utilizadores ao sistema, verificando as credenciais na base de dados.

### Código
```bash
</>Java
package Restaurante_Bar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Autenticacao {

    public Usuario login(String username, String password) {
        String sql = "SELECT id, username, password, nivel FROM usuarios WHERE username = ? AND password = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username.trim());
            stmt.setString(2, password.trim());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("nivel")
                    );
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao autenticar utilizador: " + e.getMessage(), e);
        }

        return null;
    }
}
```


### Consulta SQL
```bash
</>Java
SELECT id, username, password, nivel FROM usuarios WHERE username = ? AND password = ?
```
- Procura o utilizador na base de dados
- Filtra pelo username e password
- Retorna o nível de acesso (`nivel`)

### Uso de PreparedStatement
```bash
</>Java
stmt.setString(1, username.trim());
stmt.setString(2, password.trim());
```
- Evita SQL Injection
- Substitui parâmetros de forma segura
- `trim()` remove espaços extras

### Execução da Query
```bash
</>Java
ResultSet rs = stmt.executeQuery();
```
- Executa a consulta no banco
- Retorna resultados em forma de tabela

### Validação do Utilizador
```bash
</>Java
if (rs.next())
```
- Verifica se encontrou um utilizador
- Se existir, cria objeto `Usuario`

### Criação do Objeto Usuario
```bash
</>Java
return new Usuario(
    rs.getInt("id"),
    rs.getString("username"),
    rs.getString("password"),
    rs.getString("nivel")
);
```
- Converte dados da base em objeto Java
- Permite usar no sistema (controle de acesso)

#### Tratamento de Erros
```bash
</>Java
throw new RuntimeException("Erro ao autenticar utilizador: " + e.getMessage(), e);
```
- Captura erros de conexão ou SQL
- Lança exceção com mensagem clara
  
<br>

## 6. Conexão com Base de Dados

### Características:
- Ligação via JDBC
- Uso do driver MySQL
- Configuração dinâmica de host, porta e base de dados

### Conexão com Base de Dados
A classe `Conexao` é responsável por estabelecer a ligação entre a aplicação Java e a base de dados **MySQL**, utilizando a tecnologia **JDBC**.
```bash
</>Java
package Restaurante_Bar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String HOST = "192.168.38.30";
    private static final String PORTA = "3306";
    private static final String BANCO = "restaurante";
    private static final String USER = "pj";
    private static final String PASSWORD = "loucoste9850053";

    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PORTA + "/" + BANCO +
            "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC do MySQL não encontrado.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com MySQL: " + e.getMessage(), e);
        }
    }
}
```
### Configuração da Conexão
```bash
</>Java
private static final String HOST = "Localhost";
private static final String PORTA = "3306";
private static final String BANCO = "restaurante";
```
- Define o endereço do servidor MySQL
- Porta padrão do MySQL: 3306
- Nome da base de dados utilizada

### Credenciais de Acesso
```bash
</>Java
private static final String USER = "pj";
private static final String PASSWORD = "loucoste9850053";
```
- Utilizador da base de dados
- Palavra-passe de autenticação

### URL de Conexão
```bash
</>Java
jdbc:mysql://HOST:PORTA/BANCO
```
**Parâmetros adicionais:**
- useSSL=false - desativa SSL
- serverTimezone=UTC - evita erros de fuso horário
- allowPublicKeyRetrieval=true - permite autenticação segura
- characterEncoding=UTF-8 - suporte a caracteres especiais

### Método de Conexão
```bash
</>Java
public static Connection conectar()
```
**Função:**
- Carrega o driver JDBC
- Estabelece ligação com o MySQL
- Retorna um objeto Connection

### Tratamento de Erros
```bash
</>Java
catch (ClassNotFoundException e)
catch (SQLException e)
```
- Garante que erros são capturados
- Lança exceções com mensagens claras

<br>

## 7. Gestão de Produtos (CRUD)

### CRUD 
É um acrónimo para Create, Read, Update e Delete, que representam as quatro operações fundamentais realizadas sobre dados num sistema informático. Essas operações permitem criar novos registos, consultar informações existentes, atualizar dados e eliminar registos quando necessário.

### C – Create
Criar ou cadastrar um novo registo.

### R – Read
Ler, consultar ou visualizar os dados já existentes.

### U – Update
Atualizar ou alterar um registo existente.

### D – Delete
Eliminar ou remover um registo.

### Operações:
- Create: cadastrar um novo produto
- Read: listar os produtos cadastrados
- Update: alterar o preço de um produto
- Delete: remover um produto do sistema

<br>

## 8. Gestão de Vendas

### Funcionalidades:
- Registo de venda
- Associação de produtos
- Cálculo automático de total
- Suporte a múltiplos pagamentos

<br>

## 9. Geração de Recibos (PDF)

### Características:
- Geração automática de PDF
- Detalhamento de produtos
- Cálculo de total, recebido e troco
- Armazenamento na pasta `recibos`

```bash
</> Java
String caminhoRecibo = ReciboVenda.gerar(usuario, forma, recebido, troco, carrinho);
```
### Explicação
- Cria recibo automaticamente
- Guarda em ficheiro

<br>

## 10. Pré-visualização de Recibos

- Visualização do PDF dentro do sistema
- Uso da biblioteca **`PDFBox`**
- Interface com **`scroll`** para melhor navegação

```bash
</>Java 
PDDocument document = Loader.loadPDF(new File(caminhoPdf));
PDFRenderer renderer = new PDFRenderer(document);
BufferedImage imagem = renderer.renderImageWithDPI(0, 150);
```
## Explicação
- Abre o PDF com PDFBox
- Converte a página em imagem
- Mostra na interface Swing

<br>

## 11. Relatórios

Consulta de vendas
```bash
</>Java
String sql = "SELECT c.produto, c.quantidade, c.total FROM compra c INNER JOIN venda v ON c.id_venda = v.id_venda";
```

### Explicação
- Junta tabelas compra e venda
- Permite gerar relatórios completos

### Tipos:
- Relatório por ano
- Total por produto
- Total por forma de pagamento

### Exportações:
- CSV
- Excel (Apache POI)

### Exportação CSV
```bash
</>Java
writer.println("Produto;Quantidade;Preço;Total");
```
### Explicação
- Cria ficheiro CSV
- Permite abrir no Excel

### Exportação Excel
```bash
</>Java
Workbook wb = new XSSFWorkbook();
Sheet sheet = wb.createSheet("Relatorio");
```
### Explicação
- Cria ficheiro .xlsx
- Usa Apache POI

### Nota
- Cria ficheiro CSV
- Permite abrir no Excel


<br>

## 12. Interface Gráfica

### Características:
- Menu lateral dinâmico
- Layout com `CardLayout`
- Interface adaptada ao nível de utilizador
- Sistema responsivo

### Código (Janela Principal)

```bash
</>Java
setTitle("Sistema Restaurante/Bar");
setSize(1000, 700);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null);
```
### Explicação
- `setTitle` - Define o título da janela
- `setSize` - Define dimensões da interface
- `setLocationRelativeTo(null)` - Centraliza a janela no ecrã
  
### Layout com CardLayout
```bash
</>Java
CardLayout cardLayout = new CardLayout();
JPanel painelConteudo = new JPanel(cardLayout);
```
### Explicação
- `CardLayout` permite alternar entre várias telas
- Cada funcionalidade (produtos, vendas, relatórios) é um “card”
- Facilita navegação sem abrir novas janelas

### Menu Lateral
```bash
</>Java
JPanel menu = new JPanel();
menu.setLayout(new GridLayout(0, 1));

JButton btnProdutos = new JButton("Produtos");
JButton btnVendas = new JButton("Vendas");

menu.add(btnProdutos);
menu.add(btnVendas);
```
### Explicação
- Menu organizado verticalmente
- Cada botão representa uma funcionalidade
- `GridLayout` organiza os elementos automaticamente

### Troca de Telas
```bash
</>Java
btnProdutos.addActionListener(e -> cardLayout.show(painelConteudo, "produtos"));
btnVendas.addActionListener(e -> cardLayout.show(painelConteudo, "vendas"));
```
### Explicação
- Cada botão altera o conteúdo exibido
- `cardLayout.show(...)` muda a tela ativa
- Permite navegação dinâmica

### Formulários
```bash
</>Java
JTextField txtNome = new JTextField();
JTextField txtPreco = new JTextField();
JButton btnSalvar = new JButton("Salvar");
```
### Explicação
- Campos de entrada de dados
- Permitem inserir produtos e utilizadores
- Associados a eventos (botões)

### Integração com Sistema

### Exemplo de ação:
```bash
</>Java
btnSalvar.addActionListener(e -> {
    sistema.cadastrarProduto(txtNome.getText(), Double.parseDouble(txtPreco.getText()));
});
```
### Explicação
- Liga a interface à lógica do sistema
- Chama métodos do backend (`RestauranteSistema`)
- Executa operações no banco de dados

### Scroll (JScrollPane)
```bash
</>Java
JScrollPane scroll = new JScrollPane(tabela);
```
### Explicação
- Permite rolar conteúdo grande
- Essencial para tabelas e PDFs

<br>

## 13. Implementação do CRUD

|Entidade	  |Operações                     |
|-------------|------------------------------|
|Produtos	  |Create, Read, Update, Delete  |
|Utilizadores |Create, Read, Update, Delete  |
|Vendas       |Create, Read, Delete          |

## Exemplo:

### Criar Produto
```bash
</>java
public boolean cadastrarProduto(String nome, double preco) {
    String sql = "INSERT INTO produtos (nome, preco) VALUES (?, ?)";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, nome.trim());
        stmt.setDouble(2, preco);
        return stmt.executeUpdate() > 0;
    }
}
```
## Explicação
- Insere novo produto na base de dados
- Retorna true se for bem sucedido

## Listar Produtos
```bash
public List<Object[]> listarProdutos() {
    List<Object[]> lista = new ArrayList<>();
    String sql = "SELECT id, nome, preco FROM produtos";

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
    }

    return lista;
}
```
## Explicação
- Usa ArrayList para armazenar resultados
- Cada linha é um Object[]
- Usado para preencher tabelas (JTable)

<br>

## 14. Estruturas de Dados Utilizadas

- `List<Object[]>` - armazenamento dinâmico de dados
- `ArrayList` - manipulação de coleções
- `DefaultTableModel` - tabelas na interface

```bash
</>Java
List<Object[]> lista = new ArrayList<>();
```
### Explicação
- Estrutura dinâmica
- Usada para tabelas e relatórios

<br>

## 15. Contribuição dos Elementos

|Membro	      |  Contribuição                       |
|--------------|------------------------------------|
|Desenvolvedor |Implementação completa do sistema   |
|	           |Integração com MySQL                |
|	           |Interface gráfica                   |
|              |Geração de relatórios               |

<br>

## 16. Tecnologias Utilizadas

- Java (JDK 8+)
- Swing (GUI)
- MySQL Workbench
- JDBC
- Maven
- OpenPDF
- Apache POI
- PDFBox

<br>

## 17. Resultados Obtidos

- Sistema funcional completo
- Interface amigável
- Integração com base de dados
- Geração automática de recibos
- Exportação de relatórios

<br>  

## 18. Melhorias Futuras

- Implementar criptografia de senha
- Dashboard com gráficos
- Controle de estoque

<br>

## 19. Conclusão

O sistema desenvolvido cumpre os objetivos propostos, apresentando uma solução funcional e eficiente para gestão de restaurante/bar. A aplicação demonstra domínio de conceitos fundamentais de programação orientada a objetos, integração com bases de dados e desenvolvimento de interfaces gráficas.