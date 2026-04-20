# Relatório do Sistema Restaurante/Bar

### Curso: Engenharia Informática
### Instituição: Universidade de Santiago
### Unidade Curricular: CONCEPÇÃO E ANÁLISE DE ALGORITMOS
### Tema: Sistema de Gestão de Restaurante/Bar

---

## Índice

1. [Introdução](#1-introdução)  
2. [Objetivos](#2-objetivos)  
3. [Arquitetura do Sistema](#3-arquitetura-do-sistema)  
4. [Estrutura do Projeto](#4-estrutura-do-projeto)  
5. [Autenticação e Gestão de Utilizadores](#5-autenticação-e-gestão-de-utilizadores)  
6. [Conexão com Base de Dados](#6-conexão-com-base-de-dados)  
7. [Gestão de Produtos (CRUD)](#7-gestão-de-produtos-crud)  
8. [Gestão de Vendas](#8-gestão-de-vendas)  
9. [Geração de Recibos em PDF](#9-geração-de-recibos-em-pdf)  
10. [Pré-visualização de Recibos](#10-pré-visualização-de-recibos)  
11. [Relatórios](#11-relatórios)  
12. [Interface Gráfica](#12-interface-gráfica)  
13. [Implementação do CRUD](#13-implementação-do-crud)  
14. [Estruturas de Dados Utilizadas](#14-estruturas-de-dados-utilizadas)  
15. [Contribuição dos Elementos](#15-contribuição-dos-elementos)  
16. [Tecnologias Utilizadas](#16-tecnologias-utilizadas)  
17. [Resultados Obtidos](#17-resultados-obtidos)  
18. [Melhorias Futuras](#18-melhorias-futuras)  
19. [Conclusão](#19-conclusão)  

---

## 1. Introdução

O presente projeto consiste no desenvolvimento de um sistema desktop para gestão de um restaurante/bar, implementado na linguagem **Java**, utilizando **Swing** para a interface gráfica e **MySQL** para a persistência de dados.

### Funcionalidades do sistema

- Gestão de utilizadores
- Gestão de produtos
- Registo de vendas
- Geração de recibos em PDF
- Visualização de relatórios

---

## 2. Objetivos

### Objetivo Geral

Desenvolver um sistema completo de gestão com interface gráfica e base de dados.

### Objetivos Específicos

- Implementar autenticação de utilizadores
- Aplicar operações CRUD
- Gerar relatórios e exportações
- Integrar base de dados MySQL
- Aplicar diferentes níveis de acesso

---

## 3. Arquitetura do Sistema

O sistema segue uma arquitetura modular composta pelas seguintes camadas:

| Camada               | Descrição                              |
|----------------------|-----------------------------------------|
| Interface (View)     | Interface gráfica com Swing             |
| Lógica (Controller)  | Classe **`RestauranteSistema`**         |
| Dados (Model)        | Classe **`Usuario`**                    |
| Base de Dados        | MySQL Workbench                         |

---

## 4. Estrutura do Projeto

```bash
Versao_1/
├── src/main/java/Restaurante_Bar/
│   ├── LoginFrame.java
│   ├── RestauranteMain.java
│   ├── RestauranteSistema.java
│   ├── Conexao.java
│   ├── Autenticacao.java
│   ├── Usuario.java
│   ├── ReciboVenda.java
│   ├── VisualizadorPDF.java
├── recibos/
└── pom.xml
```
---

## 5. Autenticação e Gestão de Utilizadores

A autenticação dos utilizadores é realizada através da classe `Autenticacao`, responsável por validar as credenciais inseridas no sistema.

### Principais funções da autenticação

- Utiliza `PreparedStatement` para evitar SQL Injection
- Valida o utilizador na base de dados
- Retorna um objeto `Usuario` com o respetivo nível de acesso

### Código de autenticação

```Java
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

### Classe `Usuario`

A classe `Usuario` representa os dados do utilizador autenticado no sistema.

### Atributos da classe

```Java
private int id;
private String username;
private String password;
private String nivelAcesso;
```

- `id` — identificador único do utilizador
- `username` — nome de utilizador
- `password` — palavra-passe
- `nivelAcesso` — define as permissões no sistema

### Construtor

```Java
public Usuario(int id, String username, String password, String nivelAcesso)
```

- Inicializa o objeto com dados provenientes da base de dados
- É utilizado após a autenticação

### Métodos getters

```Java
public int getId()
public String getUsername()
public String getPassword()
public String getNivelAcesso()
```

- Permitem aceder aos dados do utilizador
- Mantêm o encapsulamento, de acordo com as boas práticas da programação orientada a objetos

### Papel da classe `Usuario` no sistema

A classe Usuario é utilizada em:
- Autenticação (Autenticacao.java)
- Controlo de acesso
- Interface gráfica com menu dinâmico

### Funcionalidades relacionadas com utilizadores

- Criar utilizador
- Atualizar utilizador
- Eliminar utilizador
- Listar utilizadores

### Níveis de acesso

|Nível	  |Permissões                             |
|---------|---------------------------------------|
|ADMIN	  |Total (CRUD de utilizadores e sistema) |
|GESTOR	  |Gestão de produtos e relatórios        |
|ATENDENTE|Apenas vendas                          |

### Exemplo de criação de utilizador

```Java
public boolean criarUsuario(String username, String password, String nivel)
```
Este método permite criar novos utilizadores e definir o respetivo nível de acesso.

### Controlo de acesso

```Java
if (nivel.equals("ADMIN")) {
    // acesso total
}
```
Este controlo permite definir as permissões disponíveis para cada tipo de utilizador.

### Comando SQL para criação da tabela de utilizadores

```SQL
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100),
    password VARCHAR(100),
    nivel VARCHAR(20)
);
```

### Implementação completa da classe `Autenticacao`

```Java
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

### Consulta SQL utilizada

```SQL
SELECT id, username, password, nivel 
FROM usuarios 
WHERE username = ? AND password = ?
```
- Procura o utilizador na base de dados
- Filtra pelos campos username e password
- Retorna o nível de acesso (nivel)

### Uso de `PreparedStatement`

```Java
stmt.setString(1, username.trim());
stmt.setString(2, password.trim());
```
- Evita SQL Injection
- Substitui parâmetros de forma segura
- O método trim() remove espaços extras

### Execução da query

```Java
ResultSet rs = stmt.executeQuery();
```
- Executa a consulta na base de dados
- Retorna os resultados em forma de tabela

### Validação do utilizador

```Java
if (rs.next())
```
- Verifica se foi encontrado um utilizador
- Se existir, cria um objeto `Usuario`

### Criação do objeto `Usuario`

```Java
return new Usuario(
    rs.getInt("id"),
    rs.getString("username"),
    rs.getString("password"),
    rs.getString("nivel")
);
```
- Converte os dados da base de dados num objeto Java
- Permite utilizar o utilizador autenticado no sistema

### Tratamento de erros

```Java
throw new RuntimeException("Erro ao autenticar utilizador: " + e.getMessage(), e);
```
- Captura erros de conexão ou SQL
- Lança uma exceção com mensagem clara
---

## 6. Conexão com Base de Dados

A classe `Conexao` é responsável por estabelecer a ligação entre a aplicação Java e a base de dados **MySQL**, utilizando a tecnologia **JDBC**.

### Características

- Ligação via JDBC
- Uso do driver MySQL
- Configuração dinâmica de host, porta e base de dados

### Código da classe `Conexao`

```Java
package Restaurante_Bar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String HOST = "192.168.38.30";
    private static final String PORTA = "3306";
    private static final String BANCO = "restaurante";
    private static final String USER = "pj";
    private static final String PASSWORD = "********";

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

### Configuração da conexão

```Java
private static final String HOST = "localhost";
private static final String PORTA = "3306";
private static final String BANCO = "restaurante";
```
- Define o endereço do servidor MySQL
- Utiliza a porta padrão do MySQL: `3306`
- Define o nome da base de dados utilizada

### Credenciais de acesso

```Java
private static final String USER = "pj";
private static final String PASSWORD = "********";
```
- Identificam o utilizador da base de dados
- Garantem a autenticação no servidor MySQL

### URL de conexão

```Java
jdbc:mysql://HOST:PORTA/BANCO
```

### Parâmetros adicionais

- `useSSL=false` — desativa SSL
- `serverTimezone=UTC` — evita erros de fuso horário
- `allowPublicKeyRetrieval=true` — permite autenticação segura
- `characterEncoding=UTF-8` — suporta caracteres especiais

### Método de conexão

```Java
public static Connection conectar()
```

### Função do método

- Carrega o driver JDBC
- Estabelece ligação com o MySQL
- Retorna um objeto `Connection`

### Tratamento de erros

```Java
catch (ClassNotFoundException e)
catch (SQLException e)
```
- Garante que erros são capturados
- Lança exceções com mensagens claras

## 7. Gestão de Produtos (CRUD)

### Conceito de CRUD

CRUD é um acrónimo para **Create, Read, Update e Delete**, representando as quatro operações fundamentais realizadas sobre dados num sistema informático.

Essas operações permitem criar novos registos, consultar informações existentes, atualizar dados e eliminar registos quando necessário.

### Operações CRUD
**C — Create**

Criar ou cadastrar um novo registo.

**R — Read**

Ler, consultar ou visualizar os dados já existentes.

**U — Update**

Atualizar ou alterar um registo existente.

**D — Delete**

Eliminar ou remover um registo.

### Operações aplicadas aos produtos
- Create: cadastrar um novo produto
- Read: listar os produtos cadastrados
- Update: alterar o preço de um produto
- Delete: remover um produto do sistema
---

## 8. Gestão de Vendas

A gestão de vendas é uma das funcionalidades centrais do sistema, pois permite registar as transações realizadas no restaurante/bar, associando produtos, quantidades, valores e forma de pagamento. Esta funcionalidade garante maior controlo sobre as operações comerciais e facilita a emissão de recibos e relatórios.

### Funcionalidades
- Registo de vendas no sistema
- Associação de vários produtos a uma venda
- Cálculo automático do total da compra
- Registo da forma de pagamento
- Cálculo do valor recebido e do troco
- Armazenamento das vendas na base de dados
- Integração com geração de recibos e relatórios

### Estrutura da venda no sistema

No processo de venda, o utilizador seleciona os produtos pretendidos, define as respetivas quantidades e adiciona esses itens ao carrinho. Em seguida, o sistema calcula automaticamente o valor total da compra, permite escolher a forma de pagamento e, por fim, regista a venda na base de dados.

### Exemplo de lista de itens no carrinho

```Java
List<Object[]> carrinho = new ArrayList<>();
```

### Explicação

A estrutura `List<Object[]>` pode ser utilizada para armazenar temporariamente os produtos adicionados ao carrinho. Cada posição da lista representa um item da venda, contendo, por exemplo:

- Nome do produto
- Quantidade
- Preço unitário
- Subtotal

### Explicação

Neste caso, cada item da venda é guardado num vetor de objetos (`Object[]`), sendo posteriormente adicionado à lista do carrinho. Isso permite manter todos os produtos da venda organizados até ao momento da gravação no banco de dados.

### Cálculo do subtotal de um item

```Java
double subtotal = quantidade * precoUnitario;
```

### Explicação

O subtotal corresponde ao valor parcial de cada item da venda, sendo calculado pela multiplicação entre a quantidade do produto e o seu preço unitário.

### Cálculo do total da venda

```Java
double total = 0.0;

for (Object[] item : carrinho) {
    total += (double) item[3];
}
```

### Explicação

O valor total da venda é obtido através da soma dos subtotais de todos os itens presentes no carrinho.

### Registo da forma de pagamento

O sistema pode suportar diferentes formas de pagamento, como:
- Dinheiro
- Cartão
- Transferência
- Pagamento misto

### Exemplo de variável para forma de pagamento

```Java
String formaPagamento = "Dinheiro";
```

### Cálculo do troco

```Java
double recebido = 5000.0;
double troco = recebido - total;
```

### Explicação

Quando o pagamento é feito em dinheiro, o sistema pode calcular automaticamente o troco com base no valor recebido pelo cliente.

### Exemplo de inserção da venda na base de dados

```Java
public int registarVenda(double total, String formaPagamento) {
    String sql = "INSERT INTO venda (total, forma_pagamento) VALUES (?, ?)";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        stmt.setDouble(1, total);
        stmt.setString(2, formaPagamento);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }

    } catch (Exception e) {
        throw new RuntimeException("Erro ao registar venda: " + e.getMessage(), e);
    }

    return -1;
}
```

### Explicação

Este método é responsável por registar a `venda` na tabela venda. Após a inserção, o sistema obtém o identificador gerado automaticamente `(id_venda)`, que será utilizado para associar os produtos vendidos a essa transação.

### Exemplo de registo dos itens da venda

```Java
public void registarItensVenda(int idVenda, List<Object[]> carrinho) {
    String sql = "INSERT INTO compra (id_venda, produto, quantidade, preco, total) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        for (Object[] item : carrinho) {
            stmt.setInt(1, idVenda);
            stmt.setString(2, item[0].toString());
            stmt.setInt(3, Integer.parseInt(item[1].toString()));
            stmt.setDouble(4, Double.parseDouble(item[2].toString()));
            stmt.setDouble(5, Double.parseDouble(item[3].toString()));
            stmt.executeUpdate();
        }

    } catch (Exception e) {
        throw new RuntimeException("Erro ao registar itens da venda: " + e.getMessage(), e);
    }
}
```

### Explicação

Depois de registar a venda principal, cada produto vendido é guardado na tabela compra, ficando associado ao identificador da venda. Isso permite saber exatamente quais produtos fizeram parte de cada transação.

### Fluxo do processo de venda

1. O utilizador seleciona os produtos.
2. O sistema adiciona os produtos ao carrinho.
3. O total da venda é calculado automaticamente.
4. O utilizador escolhe a forma de pagamento.
5. O sistema regista a venda na tabela venda.
6. O sistema regista os itens vendidos na tabela compra.
7. É gerado o recibo em PDF.
8. Os dados ficam disponíveis para relatórios posteriores.

### Exemplo de processamento completo da venda

```Java
double total = calcularTotalCarrinho(carrinho);
int idVenda = registarVenda(total, formaPagamento);

if (idVenda != -1) {
    registarItensVenda(idVenda, carrinho);
}
```

### Explicação

Este trecho representa o fluxo principal da venda: primeiro calcula-se o total, depois regista-se a venda e, por fim, são registados os itens associados.

---

## 9. Geração de Recibos em PDF

### Características

- Geração automática de PDF
- Detalhamento de produtos
- Cálculo do total, valor recebido e troco
- Armazenamento na pasta `recibos`

### Exemplo de geração de recibo

```Java
String caminhoRecibo = ReciboVenda.gerar(usuario, forma, recebido, troco, carrinho);
```

### Explicação

O método apresentado permite gerar automaticamente o recibo da venda em formato PDF, guardando o ficheiro na pasta destinada aos recibos emitidos pelo sistema.

---

## 10. Pré-visualização de Recibos

### Funcionalidades

- Visualização do PDF dentro do sistema
- Uso da biblioteca `PDFBox`
- Interface com `scroll` para melhor navegação

### Código de pré-visualização

```Java
PDDocument document = Loader.loadPDF(new File(caminhoPdf));
PDFRenderer renderer = new PDFRenderer(document);
BufferedImage imagem = renderer.renderImageWithDPI(0, 150);
```

### Explicação

- Abre o PDF com a biblioteca PDFBox
- Converte a página em imagem
- Apresenta o conteúdo na interface Swing
---

## 11. Relatórios

A funcionalidade de relatórios permite consultar e exportar dados relacionados com as vendas realizadas no sistema.

### Consulta de vendas

```Java
String sql = "SELECT c.produto, c.quantidade, c.total FROM compra c INNER JOIN venda v ON c.id_venda = v.id_venda";
```

### Explicação

- Junta as tabelas `compra` e `venda`
- Permite gerar relatórios mais completos

### Tipos de relatórios

- Relatório por ano
- Total por produto
- Total por forma de pagamento

### Exportações disponíveis

- CSV
- Excel (`Apache POI`)

### Exportação CSV

```Java
writer.println("Produto;Quantidade;Preço;Total");
```

### Explicação

Permite criar ficheiros no formato CSV, que podem ser posteriormente abertos em programas como o Microsoft Excel.

### Exportação Excel

```Java
Workbook wb = new XSSFWorkbook();
Sheet sheet = wb.createSheet("Relatorio");
```

### Explicação

Permite criar ficheiros no formato `.xlsx`, utilizando a biblioteca Apache POI.

---

## 12. Interface Gráfica

### Características

- Menu lateral dinâmico
- Layout com CardLayout
- Interface adaptada ao nível de utilizador
- Sistema responsivo

### Código da janela principal

```Java
setTitle("Sistema Restaurante/Bar");
setSize(1000, 700);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null);
```

### Explicação

- `setTitle` — define o título da janela
- `setSize` — define as dimensões da interface
- `setLocationRelativeTo(null)` — centraliza a janela no ecrã

### Layout com `CardLayout`

```Java
CardLayout cardLayout = new CardLayout();
JPanel painelConteudo = new JPanel(cardLayout);
```

### Explicação

- `CardLayout` permite alternar entre várias telas
- Cada funcionalidade do sistema é apresentada como um “card”
- Facilita a navegação sem necessidade de abrir novas janelas

### Menu lateral

```Java
JPanel menu = new JPanel();
menu.setLayout(new GridLayout(0, 1));

JButton btnProdutos = new JButton("Produtos");
JButton btnVendas = new JButton("Vendas");

menu.add(btnProdutos);
menu.add(btnVendas);
```

### Explicação

- O menu é organizado verticalmente
- Cada botão representa uma funcionalidade
- GridLayout organiza os elementos automaticamente

### Troca de telas

```Java
btnProdutos.addActionListener(e -> cardLayout.show(painelConteudo, "produtos"));
btnVendas.addActionListener(e -> cardLayout.show(painelConteudo, "vendas"));
```

### Explicação

- Cada botão altera o conteúdo exibido
- `cardLayout.show(...)` muda a tela ativa
- Permite navegação dinâmica dentro da aplicação

### Formulários

```Java
JTextField txtNome = new JTextField();
JTextField txtPreco = new JTextField();
JButton btnSalvar = new JButton("Salvar");
```

### Explicação

- Representam campos de entrada de dados
- Permitem inserir produtos e utilizadores
- São associados a ações através de botões

### Integração com o sistema

### Exemplo de ação

```Java
btnSalvar.addActionListener(e -> {
    sistema.cadastrarProduto(txtNome.getText(), Double.parseDouble(txtPreco.getText()));
});
```

### Explicação

- Liga a interface gráfica à lógica do sistema
- Chama métodos da classe RestauranteSistema
- Executa operações sobre a base de dados

### Scroll com `JScrollPane`

```Java
JScrollPane scroll = new JScrollPane(tabela);
```

### Explicação

- Permite rolar conteúdos extensos
- É essencial para tabelas e visualização de PDFs

---

## 13. Implementação do CRUD

### Tabela de operações por entidade

|Entidade	    |Operações                      |
|---------------|-------------------------------|
|Produtos	    |Create, Read, Update, Delete   |
|Utilizadores	|Create, Read, Update, Delete   |
|Vendas	        |Create, Read, Delete           |

### Exemplo: criar produto

```Java
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

### Explicação

- Insere um novo produto na base de dados
- Retorna `true` se a operação for bem-sucedida

### Exemplo: listar produtos

```Java
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

### Explicação

- Utiliza ArrayList para armazenar os resultados
- Cada linha é representada por um Object[]
- Os dados podem ser usados para preencher tabelas, como JTable

---

## 14. Estruturas de Dados Utilizadas

As principais estruturas de dados utilizadas no sistema são:
- `List<Object[]>` — armazenamento dinâmico de dados
- `ArrayList` — manipulação de coleções
- `DefaultTableModel` — gestão de tabelas na interface gráfica

### Exemplo

```Java
List<Object[]> lista = new ArrayList<>();
```

### Explicação

Trata-se de uma estrutura dinâmica usada para armazenar dados de tabelas e relatórios.

---

## 15. Contribuição dos Elementos

|Membro	       |Contribuição                            |
|--------------|----------------------------------------|
|Desenvolvedor |Implementação completa do sistema, integração com MySQL, interface gráfica e geração de relatórios|

---

## 16. Tecnologias Utilizadas

- Java (JDK 8+)
- Swing (GUI)
- MySQL Workbench
- JDBC
- Maven
- OpenPDF
- Apache POI
- PDFBox

---

## 17. Resultados Obtidos

- Sistema funcional completo
- Interface amigável
- Integração com base de dados
- Geração automática de recibos
- Exportação de relatórios

---

## 18. Melhorias Futuras

- Implementar criptografia de senha
- Desenvolver dashboard com gráficos
- Adicionar controlo de estoque

---

## 19. Conclusão

O sistema desenvolvido cumpre os objetivos propostos, apresentando uma solução funcional e eficiente para a gestão de restaurante/bar, no caso a  aplicação demonstra domínio de conceitos fundamentais de programação orientada a objetos, integração com bases de dados e desenvolvimento de interfaces gráficas.



