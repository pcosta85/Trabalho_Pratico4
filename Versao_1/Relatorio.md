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
├── docs/images/
├── recibos/
├── faturas/
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

### Funcionalidades
- Registo de venda
- Associação de produtos
- Cálculo automático do total
- Suporte a múltiplas formas de pagamento
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


