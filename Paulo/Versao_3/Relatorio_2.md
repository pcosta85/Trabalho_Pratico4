# Relatório do Sistema AQStore

### Curso: Engenharia Informática
### Instituição: Universidade de Santiago
### Unidade Curricular: Conceção e Análise de Algoritmos
### Tema: Sistema de Gestão Comercial AQStore

---

## Participantes

- Paulo Costa Nº 7517
- Kévin Guiomar Nº 7543
- Claudia Martins Nº 7751

## Docente

- Professor Valério Semedo

---

# Índice

1. [Introdução](#1-introducao)
2. [Objetivos](#2-objetivos)
3. [Arquitetura do Sistema](#3-arquitetura-do-sistema)
4. [Estrutura do Projeto](#4-estrutura-do-projeto)
5. [Autenticação e Gestão de Utilizadores](#5-autenticacao-e-gestao-de-utilizadores)
6. [Conexão com a Base de Dados](#6-conexao-com-a-base-de-dados)
7. [Gestão de Produtos (CRUD)](#7-gestao-de-produtos-crud)
8. [Gestão de Vendas](#8-gestao-de-vendas)
9. [Controlo Automático de Stock](#9-controlo-automatico-de-stock)
10. [Compras do Dia](#10-compras-do-dia)
11. [Relatórios](#11-relatorios)
12. [Geração de Recibos PDF](#12-geracao-de-recibos-pdf)
13. [Pré-visualização de Recibos](#13-pre-visualizacao-de-recibos)
14. [Interface Gráfica](#14-interface-grafica)
15. [Implementação do CRUD](#15-implementacao-do-crud)
16. [Estruturas de Dados Utilizadas](#16-estruturas-de-dados-utilizadas)
17. [Controlo de Permissões](#17-controlo-de-permissoes)
18. [Tecnologias Utilizadas](#18-tecnologias-utilizadas)
19. [Resultados Obtidos](#19-resultados-obtidos)
20. [Conclusão](#20-conclusao)

---

# 1. Introdução

O sistema **AQStore – Loja Aquapet Lda** foi desenvolvido com o objetivo de apoiar a gestão comercial de uma loja de produtos para animais. A aplicação permite gerir produtos, utilizadores, vendas, relatórios, recibos em PDF e controlo de stock.

O projeto foi desenvolvido em **Java**, utilizando **JavaFX** para a interface gráfica, **MySQL** para armazenamento dos dados, **JDBC** para ligação à base de dados, **OpenPDF** para geração de recibos e **PDFBox** para pré-visualização dos documentos.

A aplicação implementa funcionalidades fundamentais para um sistema de gestão comercial, incluindo:

- Autenticação de utilizadores;
- Controlo de permissões por nível de acesso;
- Gestão de produtos através de operações CRUD;
- Registo e processamento de vendas;
- Consulta de compras realizadas;
- Geração de relatórios por ano e mês;
- Exportação de dados para CSV e Excel;
- Emissão automática de recibos PDF;
- Pré-visualização dos recibos diretamente na aplicação.

---

## Enquadramento do Projeto

Atualmente, muitas pequenas e médias empresas ainda recorrem a métodos manuais ou ferramentas limitadas para efetuar o controlo das suas vendas e produtos. Esta situação pode originar dificuldades na gestão da informação, erros de registo e perda de produtividade.

Com o objetivo de minimizar estes problemas, surgiu a necessidade de desenvolver uma aplicação que permitisse automatizar as principais tarefas relacionadas com a atividade comercial da loja, oferecendo uma plataforma simples, segura e eficiente para a gestão diária das operações.

O AQStore foi desenvolvido precisamente para responder a essa necessidade, disponibilizando funcionalidades que permitem centralizar informações, controlar acessos, emitir recibos e gerar relatórios de forma rápida e organizada.

---

## Justificação do Projeto

A escolha deste projeto deve-se à sua relevância prática e à possibilidade de integrar diversos conteúdos estudados ao longo do curso.

O desenvolvimento do sistema permitiu aplicar conhecimentos relacionados com:

- Programação Java;
- Programação Orientada a Objetos;
- Bases de Dados MySQL;
- JDBC;
- Interfaces Gráficas JavaFX;
- Estruturas de Dados;
- Gestão de Utilizadores;
- Geração de Documentos PDF;
- Exportação de Dados.

Além disso, o projeto proporcionou uma experiência próxima de um ambiente empresarial real, permitindo compreender os desafios associados ao desenvolvimento de aplicações de gestão.

---

## Metodologia Utilizada

Para o desenvolvimento do sistema foram seguidas as seguintes etapas:

1. Levantamento dos requisitos;
2. Modelação da solução;
3. Criação da base de dados;
4. Desenvolvimento da interface gráfica;
5. Implementação das regras de negócio;
6. Integração com a base de dados;
7. Implementação dos relatórios;
8. Geração de recibos PDF;
9. Testes e validações;
10. Documentação do projeto.

Cada etapa contribuiu para a construção gradual do sistema, permitindo obter uma solução organizada, modular e de fácil manutenção.

---

## Estrutura do Relatório

O presente relatório encontra-se organizado em vários capítulos, abordando os diferentes aspetos do projeto, desde a sua conceção até aos resultados obtidos.

São apresentados:

- Objetivos do projeto;
- Arquitetura do sistema;
- Estrutura do projeto;
- Autenticação e gestão de utilizadores;
- Conexão com a base de dados;
- Implementação do CRUD;
- Gestão de vendas;
- Relatórios;
- Geração e pré-visualização de recibos;
- Estruturas de dados utilizadas;
- Tecnologias empregues;
- Resultados obtidos;
- Conclusões e propostas de melhoria.

Esta organização permite uma visão completa do desenvolvimento do sistema AQStore, evidenciando as principais decisões técnicas e os resultados alcançados.

---

# 2. Objetivos

O desenvolvimento do sistema AQStore teve como principal objetivo criar uma aplicação de gestão comercial para a Loja Aquapet Lda, permitindo controlar produtos, vendas, utilizadores e relatórios através de uma interface gráfica intuitiva.

Os objetivos específicos do projeto foram:

- Desenvolver uma aplicação desktop utilizando JavaFX;
- Implementar autenticação de utilizadores;
- Criar diferentes níveis de acesso (ADMIN, GESTOR e ATENDENTE);
- Implementar operações CRUD para gestão de produtos;
- Registar vendas e respetivos produtos vendidos;
- Gerar recibos em formato PDF;
- Disponibilizar pré-visualização dos recibos dentro da aplicação;
- Implementar consultas de compras por data;
- Criar relatórios de vendas por ano e mês;
- Exportar relatórios para CSV e Excel;
- Integrar a aplicação com uma base de dados MySQL;
- Aplicar boas práticas de Programação Orientada a Objetos.

---

# 3. Arquitetura do Sistema

O AQStore foi desenvolvido seguindo uma arquitetura modular, separando as responsabilidades em diferentes classes para facilitar a manutenção, organização e reutilização do código.

A arquitetura do sistema é composta por quatro camadas principais:
```text
Utilizador 
    | 
    v 
Interface JavaFX 
    | 
    v 
AQStoreSistema 
    | 
    v 
Conexao JDBC 
    | 
    v 
Base de Dados MySQL
```

A interface gráfica recebe os dados do utilizador, a classe AQStoreSistema processa as regras de negócio e a classe Conexao estabelece a comunicação com o MySQL.

## Interface Gráfica

Responsável pela interação com o utilizador.

Classes:

- LoginApp
- AQStoreMainFX
- VisualizadorPDFFX

Funções:

- Receber dados do utilizador;
- Apresentar menus;
- Exibir tabelas e relatórios;
- Mostrar mensagens e alertas;
- Apresentar recibos PDF.

---

## Lógica de Negócio

Responsável pelo processamento das regras do sistema.

Classes:

- AQStoreSistema
- Autenticacao

Funções:

- Validar utilizadores;
- Processar vendas;
- Gerir produtos;
- Gerir utilizadores;
- Gerar relatórios.

---

## Persistência de Dados

Responsável pela comunicação com a base de dados MySQL.

Classe:

- Conexao

Funções:

- Abrir ligações JDBC;
- Executar consultas SQL;
- Inserir, atualizar e eliminar dados.

---

## Documentação e Relatórios

Responsável pela criação e visualização de documentos.

Classes:

- ReciboVenda
- VisualizadorPDFFX

Funções:

- Gerar recibos PDF;
- Visualizar recibos;
- Exportar relatórios.

---

## Diagrama Simplificado
```text
+----------------------+
|      Utilizador      |
+----------+-----------+
           |
           v
+----------------------+
| Interface JavaFX     |
| Login / Menus        |
+----------+-----------+
           |
           v
+----------------------+
| AQStoreSistema       |
| Regras de Negócio    |
+----------+-----------+
           |
           v
+----------------------+
| JDBC / MySQL         |
| Base de Dados        |
+----------------------+

           |
           v

+----------------------+
| PDF / Relatórios     |
+----------------------+
```

---

# 4. Estrutura do Projeto

O projeto encontra-se organizado em diferentes classes, cada uma responsável por uma funcionalidade específica.

Estrutura Geral
```bash
AQStore
│
├── LoginApp.java
├── AQStoreMainFX.java
├── AQStoreSistema.java
├── Autenticacao.java
├── Usuario.java
├── Conexao.java
├── ReciboVenda.java
├── VisualizadorPDFFX.java
│
├── recibos/
│
└── resources/
    └── icon.png
```
## Descrição das Classes
### LoginApp.java

Responsável pelo ecrã de autenticação.

Principais funções:
- Receber credenciais;
- Validar login;
- Abrir a janela principal;
- Apresentar mensagens de erro.

### AQStoreMainFX.java

Classe principal da interface gráfica.

Principais funções:
- Construção dos menus;
- Gestão das páginas;
- Navegação entre funcionalidades;
- Controlo de permissões.

### AQStoreSistema.java

Responsável pelas regras de negócio.

Principais funções:
- Gestão de produtos;
- Gestão de utilizadores;
- Registo de vendas;
- Relatórios;
- Compras do dia.

### Autenticacao.java

Responsável pela validação dos utilizadores.

Principais funções:
- Verificar credenciais;
- Identificar o nível de acesso;
- Criar objetos Usuario.

### Usuario.java

Representa os utilizadores do sistema.

Atributos:
- id
- username
- password
- nivelAcesso

### Conexao.java

Responsável pela ligação à base de dados.

Principais funções:
- Abrir conexão JDBC;
- Fornecer ligação às restantes classes.

## ReciboVenda.java

Responsável pela geração dos recibos PDF.

Principais funções:
- Criar documento PDF;
- Inserir produtos;
- Inserir totais;
- Guardar recibos.

### VisualizadorPDFFX.java

Responsável pela pré-visualização dos recibos.

Principais funções:
- Abrir ficheiros PDF;
- Converter PDF em imagem;
- Apresentar numa janela JavaFX.

### Organização Modular

A divisão do sistema em várias classes permitiu:
- Melhor organização do código;
- Facilidade de manutenção;
- Reutilização de funcionalidades;
- Maior escalabilidade;
- Separação de responsabilidades.

Cada classe possui uma função específica, reduzindo a dependência entre módulos e facilitando futuras evoluções do sistema.

---

# 5. Autenticação e Gestão de Utilizadores

A autenticação é uma das funcionalidades mais importantes do sistema AQStore, pois garante que apenas utilizadores autorizados conseguem aceder às funcionalidades da aplicação.

O sistema utiliza uma tabela denominada `usuarios`, armazenada na base de dados MySQL, onde são guardadas as credenciais de acesso e o respetivo nível de permissão de cada utilizador. 

---

## Objetivo da Autenticação

O módulo de autenticação tem como principais objetivos:

- Validar as credenciais dos utilizadores;
- Controlar o acesso às funcionalidades do sistema;
- Identificar o nível de acesso do utilizador;
- Garantir a segurança dos dados;
- Impedir acessos não autorizados.

---

## Níveis de Acesso Implementados

O sistema implementa três níveis distintos de acesso:

| Nível     | Permissões                                   |
|-----------|----------------------------------------------|
| ADMIN     | Acesso total ao sistema                      |
| GESTOR    | Gestão de produtos, vendas e relatórios      |
| ATENDENTE | Operações de caixa e consulta de informações |

Após o login, o sistema identifica automaticamente o perfil do utilizador e disponibiliza apenas as funcionalidades permitidas para esse nível de acesso. 

---

## Classe Usuario

A classe `Usuario` é responsável por representar cada utilizador do sistema.

### Código

```java
public class Usuario {

    private int id;
    private String username;
    private String password;
    private String nivelAcesso;

    public Usuario(int id, String username,
                   String password,
                   String nivelAcesso) {

        this.id = id;
        this.username = username;
        this.password = password;
        this.nivelAcesso = nivelAcesso;
    }
}
```

### Explicação

Esta classe funciona como um modelo de dados (Model), armazenando as informações do utilizador autenticado.

Os seus atributos possuem as seguintes funções:

| Atributo    | Descrição                         |
|-------------|-----------------------------------|
| id          | Identificador único do utilizador |
| username    | Nome de utilizador                |
| password    | Palavra-passe                     |
| nivelAcesso | Perfil de acesso do utilizador    |

Após o login, os dados do utilizador ficam disponíveis para o restante sistema.

---

## Classe Autenticacao

A validação das credenciais é realizada através da classe `Autenticacao`.

### Consulta SQL Utilizada

```java
String sql =
"SELECT id, username, password, nivel
 FROM usuarios
 WHERE username = ? AND password = ?";
```

### Explicação

Esta consulta procura na tabela `usuarios` um registo que possua simultaneamente:

- o nome de utilizador indicado;
- a palavra-passe indicada.

Caso exista correspondência, o utilizador é considerado válido.

Os símbolos `?` representam parâmetros dinâmicos que serão preenchidos durante a execução da consulta.

A utilização de `PreparedStatement` aumenta a segurança da aplicação e reduz o risco de SQL Injection.

---

## Validação das Credenciais

### Código

```java
stmt.setString(1, username.trim());
stmt.setString(2, password.trim());
```

### Explicação

Os dados introduzidos pelo utilizador são enviados para a consulta SQL.

O método `trim()` remove espaços em branco antes e depois do texto, evitando erros de autenticação causados por caracteres desnecessários.

---

## Execução da Consulta

### Código

```java
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
```

### Explicação

Após executar a consulta:

- Se existir um registo correspondente, é criado um objeto da classe `Usuario`;
- Os dados encontrados são carregados para memória;
- O objeto é devolvido à aplicação.

Caso não exista qualquer correspondência, o método devolve:

```java
return null;
```

indicando que o login falhou.

---

## Interface de Login

O ecrã de autenticação foi desenvolvido utilizando JavaFX.

### Código

```java
Label lblUsuario = new Label("Usuário:");
Label lblSenha = new Label("Senha:");

txtUsuario = new TextField();
txtSenha = new PasswordField();

Button btnEntrar = new Button("Entrar");
```

### Explicação

A interface disponibiliza:

- Campo para introdução do utilizador;
- Campo para introdução da palavra-passe;
- Botão para iniciar o processo de autenticação.

O componente:

```java
PasswordField
```

Oculta automaticamente os caracteres introduzidos pelo utilizador, aumentando a segurança do sistema.

---

## Processamento do Login

### Código

```java
btnEntrar.setOnAction(
    e -> fazerLogin(stage)
);
```

### Explicação

Quando o utilizador pressiona o botão "Entrar", é executado o método:

```java
fazerLogin()
```

Este método:

1. Obtém os dados introduzidos;
2. Valida se os campos estão preenchidos;
3. Chama a classe `Autenticacao`;
4. Verifica o resultado;
5. Abre a janela principal do sistema.

---

## Validação de Campos Vazios

### Código

```java
if (usuario.isEmpty() || senha.isEmpty()) {

    mostrarAlerta(
        Alert.AlertType.WARNING,
        "Aviso",
        "Preencha usuário e senha."
    );

    return;
}
```

### Explicação

Antes de efetuar a autenticação, o sistema verifica se os campos foram preenchidos.

Caso algum campo esteja vazio:

- o login é cancelado;
- é apresentada uma mensagem de aviso ao utilizador.

---

## Login Bem-Sucedido

### Código

```java
if (u != null) {

    AQStoreMainFX main =
        new AQStoreMainFX(u);

    Stage novoStage =
        new Stage();

    main.start(novoStage);

    stageAtual.close();
}
```

### Explicação

Quando a autenticação é válida:

- É criada uma nova instância da janela principal;
- O utilizador autenticado é enviado para o sistema;
- A janela de login é encerrada;
- O utilizador passa a utilizar o AQStore.

---

## Login Inválido

### Código

```java
mostrarAlerta(
    Alert.AlertType.ERROR,
    "Erro",
    "Usuário ou senha inválidos."
);
```

### Explicação

Caso as credenciais não existam na base de dados:

- O acesso é recusado;
- É apresentada uma mensagem de erro;
- O utilizador permanece na janela de login.

---

## Segurança Implementada

Durante a implementação da autenticação foram aplicadas diversas medidas de segurança:

- Utilização de PreparedStatement;
- Controlo de níveis de acesso;
- Validação de campos obrigatórios;
- Ocultação da palavra-passe;
- Tratamento de exceções;
- Restrição de funcionalidades por perfil.

Estas medidas contribuem para tornar o sistema mais robusto e seguro.

---

# 6. Conexão com a Base de Dados

A comunicação entre o sistema AQStore e a base de dados MySQL é realizada através da classe `Conexao`.

Esta classe é responsável por:

- Estabelecer a ligação com o servidor MySQL;
- Disponibilizar uma conexão para as restantes classes do sistema;
- Centralizar os parâmetros de acesso à base de dados;
- Tratar possíveis erros de conexão.

A utilização de uma classe específica para a conexão permite uma melhor organização do código e facilita futuras alterações na configuração do banco de dados. 

---

## Objetivo da Classe Conexao

A principal função desta classe é fornecer uma ligação ativa entre a aplicação Java e a base de dados MySQL.

Através desta ligação, o sistema consegue:

- Consultar dados;
- Inserir novos registos;
- Atualizar informações;
- Eliminar dados;
- Executar relatórios.

Sem esta ligação, nenhuma funcionalidade que depende da base de dados poderia funcionar.

---

## Importação das Bibliotecas JDBC

### Código

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
```

### Explicação

Estas bibliotecas fazem parte da API JDBC (Java Database Connectivity).

Cada uma possui uma função específica:

| Biblioteca    | Função                                 |
|---------------|----------------------------------------|
| Connection    | Representa uma ligação à base de dados |
| DriverManager | Responsável por criar ligações         |
| SQLException  | Trata erros relacionados com SQL       |

Estas bibliotecas permitem que o Java comunique diretamente com o MySQL.

---

## Definição dos Parâmetros de Conexão

### Código

```java
private static final String HOST = "localhost";
private static final String PORTA = "3306";
private static final String BANCO = "aqstore";
private static final String USER = "root";
private static final String PASSWORD = "+++++++++";
```

### Explicação

Estas constantes armazenam os parâmetros necessários para aceder à base de dados.

#### HOST

```java
private static final String HOST = "localhost";
```

Define o servidor onde a base de dados está instalada.

No caso do projeto:

```text
localhost
```

Significa que o MySQL está instalado no mesmo computador da aplicação.

---

#### PORTA

```java
private static final String PORTA = "3306";
```

A porta 3306 é a porta padrão utilizada pelo MySQL.

---

#### BANCO

```java
private static final String BANCO = "aqstore";
```

Indica o nome da base de dados utilizada pelo sistema.

---

#### USER

```java
private static final String USER = "root";
```

Define o utilizador que possui permissões para aceder à base de dados.

---

#### PASSWORD

```java
private static final String PASSWORD = "+++++++++";
```

Define a palavra-passe utilizada para autenticação. Por segurança, este valor não deve ficar escrito diretamente no código; o ideal é usar variáveis de ambiente ou um ficheiro de configuração protegido.

---

## Construção da URL JDBC

### Código

```java
private static final String URL =
        "jdbc:mysql://" + HOST + ":" + PORTA + "/" + BANCO +
        "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
```

### Explicação

Esta instrução constrói automaticamente o endereço de ligação ao MySQL.

O resultado final será semelhante a:

```text
jdbc:mysql://localhost:3306/aqstore
```

---

### Parâmetros Utilizados

#### useSSL=false

```java
useSSL=false
```

Desativa a utilização de SSL.

Como a aplicação funciona numa rede local, não é necessário utilizar certificados de segurança.

---

#### serverTimezone=UTC

```java
serverTimezone=UTC
```

Define o fuso horário utilizado pelo MySQL.

Evita erros relacionados com datas e horas.

---

#### allowPublicKeyRetrieval=true

```java
allowPublicKeyRetrieval=true
```

Permite a autenticação em versões recentes do MySQL.

---

#### characterEncoding=UTF-8

```java
characterEncoding=UTF-8
```

Permite guardar corretamente:

- acentos;
- cedilhas;
- caracteres especiais.

Sem esta configuração poderiam ocorrer erros na apresentação dos dados.

---

## Método de Conexão

### Código

```java
public static Connection conectar() {
```

### Explicação

Este método é responsável por criar e devolver uma ligação ativa à base de dados.

Sempre que uma classe necessita de aceder ao MySQL, chama este método.

---

## Carregamento do Driver JDBC

### Código

```java
Class.forName("com.mysql.cj.jdbc.Driver");
```

### Explicação

Antes de criar a ligação, o Java precisa carregar o driver MySQL.

Este driver funciona como um tradutor entre:

```text
Java
      »«
Driver JDBC
      »«
MySQL
```

Sem o driver, o Java não conseguiria comunicar com a base de dados.

---

## Criação da Ligação

### Código

```java
return DriverManager.getConnection(
        URL,
        USER,
        PASSWORD
);
```

### Explicação

O método `getConnection()` recebe:

- URL;
- Utilizador;
- Palavra-passe.

Caso os dados estejam corretos, devolve um objeto do tipo:

```java
Connection
```

Que será utilizado para executar comandos SQL.

---

## Tratamento de Erros

### Driver Não Encontrado

#### Código

```java
catch (ClassNotFoundException e) {
```

### Explicação

Este erro ocorre quando o driver MySQL não está disponível no projeto.

Exemplos:

- Dependência Maven ausente;
- Driver JDBC não instalado;
- Configuração incorreta.

---

### Erro de Ligação

#### Código

```java
catch (SQLException e) {
```

### Explicação

Este erro pode ocorrer quando:

- O MySQL está desligado;
- O utilizador não existe;
- A palavra-passe está incorreta;
- A base de dados não existe;
- O servidor não está acessível.

---

## Exemplo de Utilização

### Código

```java
try (Connection conn = Conexao.conectar()) {

    String sql =
        "SELECT * FROM usuarios";

}
```

### Explicação

Neste exemplo:

1. O sistema obtém uma ligação;
2. Executa uma consulta SQL;
3. Obtém os resultados da tabela `usuarios`.

Esta mesma ligação é utilizada por:

- Autenticacao;
- AQStoreSistema;
- Gestão de Produtos;
- Gestão de Utilizadores;
- Compras do Dia;
- Relatórios.

---

## Vantagens da Classe Conexao

A implementação desta classe oferece várias vantagens:

### Centralização

Todos os parâmetros de ligação encontram-se num único local.

---

### Reutilização

Qualquer módulo pode utilizar a mesma ligação.

---

### Facilidade de Manutenção

Caso seja necessário alterar:

- servidor;
- utilizador;
- base de dados;

A alteração é realizada apenas nesta classe.

---

### Organização

Mantém o código mais organizado e modular.

---

## Fluxo da Conexão

```text
AQStore
    │
    v
Conexao.java
    │
    v
Driver JDBC
    │
    v
MySQL
    │
    v
Base de Dados AQStore
```

A classe `Conexao` é um dos componentes mais importantes do sistema AQStore, pois estabelece a comunicação entre a aplicação Java e a base de dados MySQL.

Através da utilização de JDBC foi possível criar uma ligação segura, reutilizável e eficiente, permitindo que todas as funcionalidades do sistema possam consultar e manipular os dados armazenados na base de dados.

Sem esta classe não seria possível implementar funcionalidades como:

- Login;
- Gestão de Produtos;
- Gestão de Utilizadores;
- Registo de Vendas;
- Relatórios;
- Compras do Dia.

---

# 7. Gestão de Produtos (CRUD)

A gestão de produtos é uma das funcionalidades centrais do sistema AQStore, pois permite controlar todos os produtos disponíveis para venda na loja.

O módulo foi desenvolvido seguindo o conceito **CRUD** (Create, Read, Update e Delete), permitindo ao utilizador efetuar todas as operações fundamentais sobre os produtos armazenados na base de dados.

CRUD significa:

| Operação  | Descrição            |
|-----------|----------------------|
| Create    | Criar novos produtos |
| Read      | Consultar produtos   |
| Update    | Atualizar produtos   |
| Delete    | Eliminar produtos    |

A implementação deste módulo encontra-se principalmente na classe `AQStoreSistema`, responsável pela comunicação entre a aplicação e a base de dados MySQL.

---

## Objetivos do Módulo

O módulo de produtos foi desenvolvido para permitir:

- Cadastrar novos produtos;
- Consultar produtos existentes;
- Atualizar informações;
- Eliminar produtos;
- Disponibilizar produtos para venda;
- Manter os dados organizados na base de dados.

---

## Estrutura da Tabela Produtos

A tabela responsável por armazenar os produtos possui a seguinte estrutura:

| Campo | Tipo    |
|-------|---------|
| id    | INT     |
| nome  | VARCHAR |
| preco | DECIMAL |
| stock | INT     |

---

## CREATE – Inserção de Produtos

O método `cadastrarProduto()` é responsável por realizar o cadastro de um novo produto na base de dados do sistema AQStore. Esta funcionalidade corresponde à operação **Create** do CRUD (Create, Read, Update e Delete), permitindo adicionar novos produtos à tabela `produtos`.

## Código

```java
public boolean cadastrarProduto(String nome, double preco, int stock) {
        String sql = "INSERT INTO produtos (nome, preco, stock) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome.trim());
            stmt.setDouble(2, preco);
            stmt.setInt(3, stock);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
```
---

## Explicação
Este método recebe três parâmetros:

| Parâmetro | Tipo     | Descrição                      |
|-----------|----------|--------------------------------|
| `nome`    | `String` | Nome do produto                |
| `preco`   | `double` | Preço unitário do produto      |
| `stock`   | `int`    | Quantidade disponível em stock |

O objetivo do método é inserir estes dados na tabela **produtos** da base de dados MySQL.

---

## Declaração do Método

```java
public boolean cadastrarProduto(String nome, double preco, int stock)
```
---

## Consulta SQL

```java
String sql =
"INSERT INTO produtos (nome, preco, stock)
VALUES (?, ?, ?)";
```

### Explicação

Esta instrução SQL é responsável por inserir um novo registo na tabela **produtos**.

Os símbolos **?** representam parâmetros que serão substituídos pelos valores recebidos pelo método.

Equivalente em SQL:

```sql
INSERT INTO produtos(nome, preco, stock)
VALUES ('Ração Premium', 3500, 20);
```

A utilização de parâmetros torna a consulta mais segura e evita ataques do tipo **SQL Injection**.

---

## Ligação à Base de Dados

```java
Connection conn = Conexao.conectar();
```

### Explicação

Esta instrução estabelece uma ligação entre a aplicação Java e a base de dados MySQL através da classe `Conexao`.

A ligação será utilizada para executar a instrução SQL.

---

## Criação do PreparedStatement

```java
PreparedStatement stmt =
        conn.prepareStatement(sql);
```

### Explicação

O objeto `PreparedStatement` prepara a instrução SQL antes da sua execução.

As principais vantagens são:

- Melhor desempenho;
- Maior segurança;
- Evita SQL Injection;
- Permite reutilizar consultas.

---

## Associação dos Parâmetros

### Nome

```java
stmt.setString(1, nome.trim());
```

O método:

```java
trim()
```

remove espaços em branco existentes antes ou depois do nome.

Exemplo:

Antes:

```text
"   Ração Premium   "
```

Depois:

```text
"Ração Premium"
```

---

### Preço

```java
stmt.setDouble(2, preco);
```

Define o preço do produto.

Exemplo:

```text
3500.00
```

---

### Stock

```java
stmt.setInt(3, stock);
```

Define a quantidade disponível em stock.

Exemplo:

```text
25 unidades
```

---

## Execução da Consulta

```java
return stmt.executeUpdate() > 0;
```

### Explicação

O método:

```java
executeUpdate()
```

Executa a instrução SQL.

O valor devolvido corresponde ao número de linhas afetadas.

Por exemplo:

```text
1
```

Significa que um produto foi inserido com sucesso.

A condição:

```java
> 0
```

Transforma esse resultado num valor booleano.

| Resultado              | Valor devolvido |
|------------------------|-----------------|
| 1 linha inserida       | `true`          |
| Nenhuma linha inserida | `false`         |

---

## Tratamento de Exceções

```java
catch (Exception e)
```

### Explicação

Caso ocorra algum erro durante a execução, o sistema entra no bloco `catch`.

Exemplos de erros:

- Falha na ligação à base de dados;
- Base de dados indisponível;
- Erro na instrução SQL;
- Dados inválidos.

---

## Impressão do Erro

```java
e.printStackTrace();
```

Esta instrução apresenta no terminal todas as informações sobre o erro ocorrido, facilitando a identificação e correção de problemas durante o desenvolvimento.

---

## Retorno em Caso de Erro

```java
return false;
```

Caso a operação não seja concluída com sucesso, o método devolve:

```text
false
```

Permitindo que a interface gráfica informe o utilizador de que o produto não foi registado.

---

## Fluxograma do Método

```text
Receber Dados
      │
      v
Criar Consulta SQL
      │
      v
Abrir Ligação MySQL
      │
      v
Criar PreparedStatement
      │
      v
Definir Nome
      │
      v
Definir Preço
      │
      v
Definir Stock
      │
      v
Executar INSERT
      │
      v
Inserção realizada?
      │
 ┌────┴─────┐
 │          │
Sim        Não
 │          │
 v          v
true      false
```

---

## Exemplo de Funcionamento

Entrada:

```text
Nome: Ração Premium
Preço: 3500
Stock: 25
```

Consulta executada:

```sql
INSERT INTO produtos
(nome, preco, stock)

VALUES
('Ração Premium',3500,25);
```

Resultado na base de dados:

| ID | Nome          | Preço   | Stock        |
|----|---------------|--------:|-------------:|
| 15 | Ração Premium | 3500.00 | 25           |

---

## READ – Consulta de Produtos

A operação READ permite visualizar todos os produtos registados.

## Código

```java
public List<Object[]> listarProdutos() {
        List<Object[]> lista = new ArrayList<Object[]>();
        String sql = "SELECT id, nome, preco, stock FROM produtos ORDER BY nome";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("stock")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
```

---

## Explicação

A consulta utilizada é:

```sql
SELECT * FROM produtos
ORDER BY nome
```

Esta consulta:

- Obtém todos os produtos;
- Organiza os resultados alfabeticamente.

O ciclo:

```java
while (rs.next())
```

Permite percorrer todos os registos encontrados.

---

## Estrutura dos Dados

Cada produto é armazenado num vetor:

```java
new Object[]{
    id,
    nome,
    preco,
    stock
}
```

---

## UPDATE – Atualização de Produtos

A operação UPDATE permite alterar os dados de um produto já existente.

## Código

```java
 public boolean atualizarProduto(int id, String nome, double preco, int stock) {
        String sql = "UPDATE produtos SET nome = ?, preco = ?, stock = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome.trim());
            stmt.setDouble(2, preco);
            stmt.setInt(3, stock);
            stmt.setInt(4, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
```

---

## Explicação

A consulta SQL utilizada é:

```sql
UPDATE produtos
SET nome=?, preco=?, stock=?
WHERE id=?
```

Esta operação permite:

- Alterar o nome;
- Alterar o preço;
- Alterar o stock;
- Manter o mesmo identificador.

---

## Exemplo

Antes:

```text
ID: 1
Produto: Ração
Preço: 2500
Stock: 10
```

Depois:

```text
ID: 1
Produto: Ração Premium
Preço: 3500
Stock: 15
```

---

## DELETE – Eliminação de Produtos

A operação DELETE permite remover produtos da base de dados.

## Código

```java
public boolean eliminarProduto(int id) {

    String sql =
        "DELETE FROM produtos WHERE id=?";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);

        return stmt.executeUpdate() > 0;
    }
}
```

---

## Explicação

A consulta SQL utilizada é:

```sql
DELETE FROM produtos
WHERE id=?
```

A operação remove permanentemente o produto selecionado.

---

## Exemplo

Antes:

```text
ID: 3
Produto: Coleira
Preço: 1200
Stock: 10
```

Depois:

```text
Produto removido.
```

O registo deixa de existir na base de dados.

---

## Interface Gráfica da Gestão de Produtos

A gestão de produtos foi integrada na interface JavaFX.

O utilizador pode:

- Inserir novos produtos;
- Consultar produtos;
- Atualizar informações;
- Eliminar produtos.

---

## Componentes Utilizados

| Componente | Função                  |
|------------|-------------------------|
| TextField  | Introdução de dados     |
| TableView  | Listagem de produtos    |
| Button     | Operações CRUD          |
| Alert      | Mensagens ao utilizador |

---

## Fluxo de Funcionamento

```text
Utilizador
      │
      v
Interface JavaFX
      │
      v
AQStoreSistema
      │
      v
Conexao
      │
      v
MySQL
```

---

## Segurança Implementada

Para garantir maior segurança, todas as operações CRUD utilizam:

```java
PreparedStatement
```

Esta abordagem:

- Evita SQL Injection;
- Melhora o desempenho;
- Facilita a manutenção do código.

---

## Vantagens da Implementação CRUD

A implementação CRUD trouxe diversos benefícios:

### Organização

Os produtos ficam armazenados de forma estruturada.

### Facilidade de Atualização

Permite alterar preços e descrições sem modificar código.

### Escalabilidade

Novos produtos podem ser adicionados sem limitações.

### Integração

Os produtos ficam imediatamente disponíveis para o módulo de vendas.

---

A implementação do CRUD de produtos permitiu criar um sistema completo de gestão de inventário para a Loja Aquapet.

Através da integração entre JavaFX, JDBC e MySQL foi possível disponibilizar funcionalidades de criação, consulta, atualização e eliminação de produtos, garantindo uma gestão eficiente e organizada dos dados.

Este módulo constitui a base operacional do sistema AQStore, pois todos os processos de venda dependem diretamente dos produtos registados na base de dados.

---

# 8. Gestão de Vendas

A gestão de vendas constitui uma das funcionalidades mais importantes do sistema AQStore, pois é através deste módulo que são efetuadas todas as transações comerciais da loja.

Este módulo permite:

- Selecionar produtos;
- Adicionar produtos ao carrinho;
- Definir quantidades;
- Calcular subtotais;
- Calcular o valor total da venda;
- Processar diferentes formas de pagamento;
- Calcular o troco;
- Registar a venda na base de dados;
- Gerar recibos em PDF.

Todo o processo foi desenvolvido para simular o funcionamento de um sistema de caixa real.

---

## Objetivos do Módulo

O módulo de vendas foi desenvolvido com os seguintes objetivos:

- Automatizar o processo de faturação;
- Reduzir erros de cálculo;
- Registar todas as transações realizadas;
- Emitir comprovativos de venda;
- Disponibilizar informações para relatórios futuros.

---

## Estrutura de Dados Utilizada

Para armazenar temporariamente os produtos selecionados pelo cliente, o sistema utiliza uma lista dinâmica.

## Código

```java
List<Object[]> carrinho =
        new ArrayList<>();
```

## Explicação

A variável `carrinho` funciona como um armazenamento temporário dos produtos da venda.

Cada posição da lista contém:

```java
new Object[]{
    produto,
    quantidade,
    preco,
    total
}
```

---

## Estrutura do Carrinho

| Posição | Informação     |
|---------|----------------|
| 0       | Produto        |
| 1       | Quantidade     |
| 2       | Preço Unitário |
| 3       | Total          |

---

## Exemplo

```java
new Object[]{
    "Ração Premium",
    2,
    3500.00,
    7000.00
}
```

---

## Adição de Produtos ao Carrinho

Quando o utilizador seleciona um produto e define uma quantidade, o sistema adiciona automaticamente o item ao carrinho.

## Processo

```text
Produto Selecionado
        │
        v
Quantidade
        │
        v
Cálculo do Total
        │
        v
Adicionar ao Carrinho
```

---

## Cálculo do Total

O valor total do item é calculado através da fórmula:

```text
Total = Quantidade × Preço Unitário
```

### Exemplo

```text
Quantidade = 3
Preço = 1500

Total = 4500
```

---

## Formas de Pagamento

O sistema suporta diferentes formas de pagamento.

## Opções Disponíveis

| Forma de Pagamento |
|--------------------|
| Dinheiro           |
| Cartão             |
| PIX                |

Estas opções são apresentadas ao operador da caixa durante o processo de venda.

---

## Pagamento em Dinheiro

Quando o cliente paga em dinheiro, o sistema solicita o valor recebido.

## Fórmula Utilizada

```text
Troco = Valor Recebido - Total da Venda
```

### Exemplo

```text
Total da Venda = 7500

Valor Recebido = 10000

Troco = 2500
```

O troco é calculado automaticamente pelo sistema.

---

## Finalização da Venda

Após a confirmação dos dados, o sistema executa o processo de finalização.

## Objetivos

- Guardar a venda;
- Guardar os produtos vendidos;
- Gerar recibo;
- Atualizar os relatórios.

---

## Método Principal

```java
public boolean finalizarVenda(
        String usuario,
        String forma,
        Double recebido,
        Double troco,
        List<Object[]> carrinho)
```

## Parâmetros

| Parâmetro | Descrição                     |
|-----------|-------------------------------|
| usuario   | Operador que realizou a venda |
| forma     | Forma de pagamento            |
| recebido  | Valor recebido                |
| troco     | Troco calculado               |
| carrinho  | Lista de produtos vendidos    |

---

## Registo da Venda

A primeira operação consiste em gravar a venda principal.

## Estrutura da Tabela

| Campo           |
|-----------------|
| id_venda        |
| data            |
| usuario         |
| forma_pagamento |
| valor_recebido  |
| troco           |

---

## Exemplo

```text
ID Venda: 1001
Data: 2026-06-18
Utilizador: enderson
Forma: Dinheiro
Recebido: 10000
Troco: 2500
```

---

## Registo dos Produtos Vendidos

Após criar a venda principal, o sistema grava cada produto vendido na tabela de compras.

## Estrutura

| Campo           |
|-----------------|
| produto         |
| quantidade      |
| preco_unitario  |
| total           |
| forma_pagamento |
| id_venda        |

---

## Exemplo

```text
Produto: Ração Premium
Quantidade: 2
Preço: 3500
Total: 7000
ID Venda: 1001
```

---

## Transações na Base de Dados

O sistema utiliza transações para garantir a integridade dos dados.

## Funcionamento

```text
Iniciar Transação
        │
        v
Inserir Venda
        │
        v
Inserir Produtos
        │
        v
Commit
```

---

## Commit

Quando todas as operações são concluídas com sucesso:

```java
conn.commit();
```

A venda fica definitivamente registada.

---

## Rollback

Se ocorrer algum erro:

```java
conn.rollback();
```

Todas as operações são canceladas.

Esta abordagem evita inconsistências na base de dados.

---

## Geração Automática do Recibo

Após a venda ser registada com sucesso, o sistema gera automaticamente um recibo PDF.

## Informações do Recibo

- Nome da loja;
- Operador;
- Forma de pagamento;
- Produtos vendidos;
- Quantidades;
- Totais;
- Valor recebido;
- Troco.

---

## Pré-visualização do Recibo

Após gerar o PDF, o sistema abre automaticamente uma janela de pré-visualização.

## Benefícios

- Permite verificar o documento;
- Evita erros de impressão;
- Melhora a experiência do utilizador.

---

## Fluxo Completo da Venda

```text
Selecionar Produto
        │
        v
Adicionar ao Carrinho
        │
        v
Selecionar Forma de Pagamento
        │
        v
Calcular Troco
        │
        v
Finalizar Venda
        │
        v
Guardar na Base de Dados
        │
        v
Gerar Recibo PDF
        │
        v
Pré-visualizar Recibo
```

---

## Vantagens da Implementação

A implementação do módulo de vendas oferece diversas vantagens:

### Rapidez

Permite efetuar vendas em poucos segundos.

### Precisão

Reduz erros de cálculo.

### Segurança

Utiliza transações na base de dados.

### Organização

Mantém o histórico de vendas armazenado.

### Integração

Alimenta automaticamente os relatórios do sistema.

---

O módulo de gestão de vendas representa o núcleo operacional do sistema AQStore.

Através da integração entre JavaFX, JDBC, MySQL, OpenPDF e PDFBox, foi possível criar uma solução completa para registo de vendas, cálculo de pagamentos, emissão de recibos e armazenamento permanente das transações.

Este módulo garante rapidez, fiabilidade e organização no processo de faturação da Loja Aquapet.

---
# 9. Controlo Automático de Stock

A versão atual do sistema implementa controlo automático de stock.

## Verificar Stock
```java
String sqlVerificarStock = "SELECT stock FROM produtos WHERE nome = ? FOR UPDATE";
```

## Atualizar Stock
```java
String sqlAtualizarStock = "UPDATE produtos SET stock = stock - ? WHERE nome = ?";
```

## Código
```java
for (Object[] item : carrinho) { 
    String produto = (String) item[0]; 
    int quantidade = (Integer) item[1]; 
    
    try (PreparedStatement stmtStock = conn.prepareStatement(sqlVerificarStock)) { 
        stmtStock.setString(1, produto); 
        try (ResultSet rs = stmtStock.executeQuery()) { 
            if (!rs.next()) { 
                conn.rollback(); 
                return false; 
                } 
                int stockAtual = rs.getInt("stock"); 
                if (quantidade > stockAtual) { 
                    conn.rollback(); 
                    return false; 
                    } 
                } 
            } 
        }
```

## Explicação

O sistema verifica o stock antes de confirmar a venda. Caso não exista stock suficiente, a operação é cancelada com rollback.

---

# 10. Compras do Dia

O módulo **Compras do Dia** foi desenvolvido para permitir a consulta rápida das vendas efetuadas numa determinada data.

Esta funcionalidade é especialmente útil para:

- Acompanhar as vendas diárias;
- Verificar o movimento da loja;
- Conferir operações realizadas pelos operadores;
- Identificar vendas específicas;
- Efetuar correções quando necessário.

O módulo encontra-se integrado na interface principal do sistema AQStore e está diretamente ligado à base de dados MySQL.

---

## Objetivos do Módulo

A funcionalidade Compras do Dia foi criada com os seguintes objetivos:

- Consultar vendas por data;
- Visualizar os produtos vendidos;
- Consultar valores faturados;
- Facilitar auditorias internas;
- Permitir a eliminação de vendas quando autorizado.

---

## Interface Gráfica

A consulta das vendas é realizada através de uma página específica do sistema.

O utilizador pode:

- Informar uma data;
- Filtrar os resultados;
- Consultar as vendas encontradas;
- Eliminar uma venda (quando permitido).

---

## Componentes Utilizados

| Componente | Função                  |
|------------|-------------------------|
| TextField  | Introdução da data      |
| Button     | Filtrar dados           |
| TableView  | Exibir resultados       |
| Alert      | Mensagens ao utilizador |

---

## Pesquisa por Data

A consulta das vendas é realizada através da data indicada pelo utilizador.

## Exemplo

```text
2026-06-18
```

Após informar a data, o sistema consulta automaticamente a base de dados.

---

## Fluxo da Consulta

```text
Utilizador
      │
      v
Introduz Data
      │
      v
Botão Filtrar
      │
      v
AQStoreSistema
      │
      v
MySQL
      │
      v
Resultados
      │
      v
TableView
```

---

## Consulta na Base de Dados

A pesquisa é realizada utilizando consultas SQL.

## Objetivo

Obter todas as vendas registadas numa determinada data.

---

## Informações Apresentadas

O sistema apresenta:

| Informação         |
|--------------------|
| ID da Venda        |
| Produto            |
| Quantidade         |
| Preço Unitário     |
| Total              |
| Forma de Pagamento |
| Data               |

---

## Exemplo de Resultado

```text
ID Venda: 105
Produto: Ração Premium
Quantidade: 2
Preço: 3500.00
Total: 7000.00
Forma: Dinheiro
Data: 2026-06-18
```

---

## Estrutura dos Dados

Os dados apresentados são obtidos através da tabela de compras.

## Campos Utilizados

| Campo           |
|-----------------|
| id_compra       |
| produto         |
| quantidade      |
| preco_unitario  |
| total           |
| forma_pagamento |
| id_venda        |

Estes dados encontram-se associados à venda principal através do campo:

```text
id_venda
```

---

## Utilização da TableView

Os resultados da consulta são apresentados numa tabela JavaFX.

## Vantagens

- Organização visual dos dados;
- Facilidade de leitura;
- Navegação intuitiva;
- Seleção rápida de registos.

---

## Eliminação de Vendas

O sistema permite remover vendas registadas.

Contudo, esta funcionalidade encontra-se protegida por níveis de acesso.

---

## Permissões

| Perfil    | Eliminar Venda |
|-----------|----------------|
| ADMIN     | Sim            |
| GESTOR    | Sim            |
| ATENDENTE | Não            |

---

## Controlo de Acesso

Antes de eliminar uma venda, o sistema verifica o perfil do utilizador autenticado.

## Lógica Aplicada

```text
ADMIN
      │
      ├── Pode eliminar
      │
GESTOR
      │
      ├── Pode eliminar
      │
ATENDENTE
      │
      └── Não pode eliminar
```

---

## Justificação da Restrição

A remoção de vendas é uma operação crítica.

Permitir que todos os utilizadores eliminem registos poderia causar:

- Perda de informação;
- Inconsistências financeiras;
- Erros de auditoria;
- Problemas de controlo interno.

Por esse motivo, apenas perfis com maior responsabilidade possuem esta permissão.

---

## Processo de Eliminação

Quando um utilizador autorizado seleciona uma venda:

```text
Selecionar Venda
       │
       v
Confirmar Eliminação
       │
       v
Executar DELETE
       │
       v
Atualizar Tabela
```

---

## Atualização Automática

Após a eliminação de uma venda:

- Os dados são removidos da base de dados;
- A tabela é atualizada automaticamente;
- Os resultados permanecem sincronizados.

Isto evita que o utilizador tenha de sair e voltar a entrar no sistema para visualizar as alterações.

---

## Benefícios do Módulo

A implementação do módulo Compras do Dia trouxe diversas vantagens.

## Controlo Diário

Permite acompanhar as vendas realizadas ao longo do dia.

---

## Auditoria

Facilita a conferência de operações efetuadas pelos operadores.

---

## Correção de Erros

Permite remover registos incorretos quando autorizado.

---

## Organização

Mantém todas as vendas acessíveis através de uma única interface.

---

## Fluxo Completo

```text
Introduzir Data
       │
       v
Consultar Base de Dados
       │
       v
Apresentar Resultados
       │
       v
Selecionar Venda
       │
       v
Eliminar (Se Permitido)
       │
       v
Atualizar Tabela
```

---

## Integração com Outros Módulos

O módulo Compras do Dia está diretamente ligado a:

- Gestão de Vendas;
- Relatórios;
- Base de Dados MySQL;
- Gestão de Utilizadores.

Qualquer venda efetuada no sistema passa automaticamente a estar disponível para consulta neste módulo.

---

O módulo Compras do Dia foi desenvolvido para facilitar a consulta e gestão das vendas diárias da Loja Aquapet.

Através da integração com JavaFX e MySQL, foi possível criar uma funcionalidade simples, rápida e eficiente para pesquisa, visualização e controlo das vendas realizadas.

O controlo de permissões implementado garante maior segurança e protege a integridade dos dados armazenados no sistema, assegurando que apenas utilizadores autorizados possam efetuar alterações críticas.

---

# 11. Relatórios

O módulo de **Relatórios** foi desenvolvido para fornecer informações detalhadas sobre as vendas realizadas na Loja Aquapet.

Através deste módulo, o utilizador consegue acompanhar o desempenho comercial da loja, analisar tendências de vendas e obter informações importantes para apoio à tomada de decisões.

Os relatórios são gerados com base nos dados armazenados na base de dados MySQL e podem ser exportados para diferentes formatos.

---

## Objetivos do Módulo

O módulo de relatórios foi desenvolvido com os seguintes objetivos:

- Consultar vendas realizadas;
- Analisar faturação;
- Identificar produtos mais vendidos;
- Avaliar formas de pagamento utilizadas;
- Gerar relatórios mensais e anuais;
- Exportar dados para CSV e Excel;
- Apoiar a gestão da loja.

---

## Critérios de Pesquisa

O sistema permite gerar relatórios utilizando dois filtros principais:

## Ano

Permite consultar todas as vendas realizadas durante um determinado ano.

### Exemplo

```text
2026
```

---

## Mês

Permite filtrar os resultados para um mês específico.

### Exemplo

```text
Janeiro
Fevereiro
Março
...
Dezembro
```

---

## Consulta por Ano e Mês

A versão atual do sistema permite combinar os dois filtros.

### Exemplo

```text
Ano: 2026
Mês: Junho
```

Neste caso serão apresentadas apenas as vendas efetuadas durante o mês de Junho de 2026.

Esta abordagem melhora significativamente a precisão dos relatórios.

---

## Fluxo de Funcionamento

```text
Selecionar Ano
        │
        v
Selecionar Mês
        │
        v
Gerar Relatório
        │
        v
Consulta SQL
        │
        v
MySQL
        │
        v
Apresentação dos Dados
```

---

## Informações Apresentadas

O relatório apresenta diversas informações relacionadas com as vendas.

## Dados Disponíveis

| Campo |
|---------|
| Produto |
| Quantidade |
| Preço Unitário |
| Total |
| Forma de Pagamento |
| Data |
| Operador |

---

## Exemplo de Resultado

```text
Produto: Ração Premium
Quantidade: 3
Preço Unitário: 3500.00
Total: 10500.00
Forma de Pagamento: Dinheiro
Data: 2026-06-18
Operador: paulo
```

---

## Estrutura dos Dados

Os dados apresentados no relatório são obtidos a partir da combinação de duas tabelas:

## Tabela Venda

Responsável por armazenar:

- Data da venda;
- Utilizador;
- Forma de pagamento;
- Valor recebido;
- Troco.

---

## Tabela Compra

Responsável por armazenar:

- Produto;
- Quantidade;
- Preço unitário;
- Total.

---

## Apresentação dos Relatórios

Os resultados são exibidos através de uma tabela JavaFX (`TableView`).

## Vantagens

- Fácil visualização;
- Organização dos dados;
- Possibilidade de filtragem;
- Atualização dinâmica.

---

## Exportação para CSV

O sistema permite exportar os relatórios para ficheiros CSV.

## Objetivo

Permitir:

- Partilha dos dados;
- Análise em Excel;
- Arquivamento de relatórios.

---

## Estrutura do Ficheiro

```csv
Produto;Quantidade;Preço Unitário;Total;Forma Pagamento;Data
```

---

## Benefícios

- Compatibilidade com Microsoft Excel;
- Compatibilidade com LibreOffice Calc;
- Facilidade de importação para outros sistemas.

---

## Exportação para Excel

Além do formato CSV, o sistema também suporta exportação para Excel (.xlsx).

---

## Biblioteca Utilizada

```text
Apache POI
```

---

## Funcionalidades

- Criação automática de folhas Excel;
- Organização dos dados em colunas;
- Compatibilidade com Microsoft Excel;
- Facilidade de análise posterior.

---

## Cálculo de Totais

O sistema calcula automaticamente:

## Quantidade Total Vendida

```text
Soma das quantidades
```

---

## Valor Total Faturado

```text
Soma dos totais das vendas
```

---

## Distribuição por Forma de Pagamento

Permite identificar:

- Total pago em dinheiro;
- Total pago por cartão;
- Total pago por PIX.

---

## Benefícios para a Gestão

A implementação dos relatórios oferece diversas vantagens.

## Controlo Financeiro

Permite acompanhar a faturação da loja.

---

## Apoio à Decisão

Ajuda a identificar:

- Produtos mais vendidos;
- Produtos menos vendidos;
- Tendências de consumo.

---

## Planeamento

Facilita a gestão de:

- Compras;
- Stock;
- Promoções.

---

## Integração com Outros Módulos

O módulo de relatórios recebe informações de:

```text
Gestão de Produtos
        │
        v
Gestão de Vendas
        │
        v
Compras do Dia
        │
        v
Relatórios
```

Desta forma, qualquer venda realizada no sistema passa automaticamente a fazer parte dos relatórios.

---

## Exemplo Prático

Considere as seguintes vendas:

| Produto       | Quantidade | Total |
|---------------|------------|-------|
| Ração Premium | 3          | 10500 |
| Coleira       | 2          | 2400  |
| Brinquedo     | 1          | 800   |

O relatório apresentará:

```text
Quantidade Total Vendida: 6

Valor Total Faturado: 13700
```

---

## Vantagens da Implementação

A implementação deste módulo trouxe diversos benefícios.

### Automatização

Elimina a necessidade de cálculos manuais.

### Rapidez

Geração instantânea dos relatórios.

### Precisão

Reduz erros de processamento.

### Flexibilidade

Permite diferentes critérios de consulta.

### Exportação

Facilita o armazenamento e partilha dos resultados.

---

O módulo de Relatórios constitui uma importante ferramenta de apoio à gestão da Loja Aquapet.

Através da integração com a base de dados MySQL e da utilização de JavaFX, Apache POI e exportação CSV, foi possível criar uma solução eficiente para consulta e análise das vendas realizadas.

Os relatórios fornecem informações fundamentais para o controlo financeiro, acompanhamento das operações e apoio à tomada de decisões estratégicas dentro da organização.

---

# 12. Geração de Recibos PDF

A geração automática de recibos é uma das funcionalidades mais importantes do sistema AQStore, pois permite emitir um comprovativo de venda imediatamente após a conclusão de uma transação.

O recibo é gerado em formato PDF, possibilitando:

- Impressão do comprovativo;
- Arquivamento digital;
- Controlo das vendas realizadas;
- Entrega ao cliente;
- Consulta posterior das transações.

Esta funcionalidade foi implementada através da classe `ReciboVenda`, utilizando a biblioteca **OpenPDF** para criação dinâmica de documentos PDF.

---

## Objetivos do Módulo

O módulo de geração de recibos foi desenvolvido com os seguintes objetivos:

- Emitir comprovativos automáticos;
- Reduzir o tempo de atendimento;
- Garantir a rastreabilidade das vendas;
- Disponibilizar um documento profissional para o cliente;
- Facilitar futuras auditorias.

---

## Biblioteca Utilizada

A geração dos recibos é realizada através da biblioteca:

```text
OpenPDF
```

Esta biblioteca permite:

- Criar documentos PDF;
- Inserir textos formatados;
- Criar tabelas;
- Aplicar estilos;
- Guardar documentos automaticamente.

---

## Estrutura da Classe ReciboVenda

A classe responsável pela geração dos recibos é:

```java
public class ReciboVenda
```

O seu principal objetivo é receber os dados da venda e criar um ficheiro PDF contendo todas as informações necessárias.

---

## Pasta de Armazenamento

Os recibos são guardados automaticamente numa pasta específica.

## Código

```java
private static final String PASTA_RECIBOS = "recibos";
```

## Explicação

Todos os documentos PDF gerados pelo sistema são armazenados na pasta:

```text
recibos/
```

Caso a pasta não exista, o sistema cria-a automaticamente.

---

## Criação da Pasta

## Código

```java
File pasta = new File(PASTA_RECIBOS);

if (!pasta.exists()) {
    pasta.mkdirs();
}
```

## Explicação

Este bloco verifica se a pasta existe.

Se não existir:

```java
pasta.mkdirs();
```

cria automaticamente a estrutura necessária para armazenar os recibos.

---

## Geração do Nome do Ficheiro

Cada recibo recebe um nome único.

## Código

```java
String nomeArquivo =
    "Recibo_" +
    System.currentTimeMillis() +
    ".pdf";
```

## Explicação

O método:

```java
System.currentTimeMillis()
```

gera um valor baseado na data e hora atual.

Exemplo:

```text
Recibo_1776513458912.pdf
```

Desta forma evita-se a duplicação de ficheiros.

---

## Criação do Documento PDF

## Código

```java
Document document = new Document();

PdfWriter.getInstance(
    document,
    new FileOutputStream(caminho)
);
```

## Explicação

Nesta etapa:

- É criado um novo documento PDF;
- É definida a localização onde será guardado;
- É estabelecida a ligação entre o documento e o ficheiro.

---

## Abertura do Documento

## Código

```java
document.open();
```

## Explicação

Este método abre o documento para permitir a inserção de conteúdo.

Após esta instrução é possível adicionar:

- textos;
- tabelas;
- títulos;
- totais.

---

## Definição de Fontes

## Código

```java
Font titulo =
    new Font(
        Font.HELVETICA,
        16,
        Font.BOLD
    );

Font normal =
    new Font(
        Font.HELVETICA,
        11
    );
```

## Explicação

Foram criados diferentes estilos de letra para melhorar a apresentação do documento.

### Fonte Título

Utilizada para:

```text
AQStore - Loja Aquapet Lda
```

### Fonte Normal

Utilizada para:

- Produtos;
- Quantidades;
- Valores;
- Informações da venda.

---

## Cabeçalho do Recibo

## Código

```java
Paragraph cab =
    new Paragraph(
        "AQStore - Loja Aquapet Lda",
        titulo
    );

cab.setAlignment(
    Element.ALIGN_CENTER
);
```

## Explicação

O cabeçalho identifica a empresa responsável pela venda.

O texto é apresentado:

- Em negrito;
- Centralizado;
- Com tamanho superior ao restante conteúdo.

---

## Informações da Venda

## Código

```java
document.add(
    new Paragraph(
        "Utilizador: " + usuario,
        normal
    )
);

document.add(
    new Paragraph(
        "Forma de Pagamento: "
        + formaPagamento,
        normal
    )
);
```

## Explicação

O recibo apresenta:

- Nome do operador;
- Forma de pagamento utilizada.

Estas informações permitem identificar quem realizou a venda e como foi efetuado o pagamento.

---

## Criação da Tabela de Produtos

## Código

```java
PdfPTable tabela =
    new PdfPTable(4);
```

## Explicação

É criada uma tabela com quatro colunas:

| Coluna     |
|------------|
| Produto    |
| Quantidade |
| Preço      |
| Total      |

---

## Cabeçalhos da Tabela

## Código

```java
tabela.addCell(
    new Phrase(
        "Produto",
        negrito
    )
);

tabela.addCell(
    new Phrase(
        "Qtd",
        negrito
    )
);
```

## Explicação

Define os títulos das colunas que irão organizar os produtos vendidos.

---

## Inserção dos Produtos

## Código

```java
for (Object[] item : carrinho) {

    String produto =
        item[0].toString();

    int qtd =
        (Integer) item[1];

    double preco =
        (Double) item[2];

    double total =
        (Double) item[3];
}
```

## Explicação

O ciclo percorre todos os produtos armazenados no carrinho.

Para cada item são obtidos:

- Produto;
- Quantidade;
- Preço;
- Total.

Estas informações são inseridas na tabela do PDF.

---

## Cálculo do Total Geral

## Código

```java
double totalGeral = 0.0;

totalGeral += total;
```

## Explicação

O sistema soma todos os subtotais dos produtos para obter o valor final da venda.

---

## Exemplo

```text
Ração = 7000

Coleira = 1200

Brinquedo = 800

Total Geral = 9000
```

---

## Valor Recebido e Troco

Quando o pagamento é efetuado em dinheiro, o sistema também apresenta:

## Código

```java
document.add(
    new Paragraph(
        "Recebido: "
        + df.format(recebido)
    )
);

document.add(
    new Paragraph(
        "Troco: "
        + df.format(troco)
    )
);
```

## Explicação

Estas informações permitem ao cliente confirmar o valor entregue e o troco recebido.

---

## Mensagem Final

## Código

```java
document.add(
    new Paragraph(
        "Obrigado pela preferência!",
        normal
    )
);
```

## Explicação

Mensagem de agradecimento apresentada ao cliente no final do recibo.

---

## Fecho do Documento

## Código

```java
document.close();
```

## Explicação

Finaliza o documento e grava todas as informações no ficheiro PDF.

Após esta operação o recibo fica pronto para ser visualizado ou impresso.

---

## Exemplo de Recibo

```text
AQStore - Loja Aquapet Lda

Utilizador: heriksandro
Forma de Pagamento: Dinheiro

Produto          Qtd   Preço   Total
--------------------------------------
Ração Premium     2    3500    7000
Coleira           1    1200    1200

TOTAL: 8200

Recebido: 10000
Troco: 1800

Obrigado pela preferência!
```

---

## Benefícios da Implementação

A geração automática de recibos oferece diversas vantagens.

### Rapidez

O documento é criado instantaneamente.

### Organização

Todos os recibos ficam armazenados digitalmente.

### Segurança

Evita perda de informação.

### Profissionalismo

Fornece um comprovativo formal ao cliente.

### Integração

Funciona automaticamente após cada venda.

---

## Fluxo Completo

```text
Finalizar Venda
        │
        v
Obter Dados do Carrinho
        │
        v
Criar PDF
        │
        v
Inserir Produtos
        │
        v
Calcular Totais
        │
        v
Guardar Ficheiro
        │
        v
Abrir Pré-visualização
```

---

O módulo de geração de recibos PDF permite transformar automaticamente cada venda realizada num documento digital profissional.

A utilização da biblioteca OpenPDF possibilitou criar recibos organizados, contendo todas as informações relevantes da transação, contribuindo para um melhor controlo das vendas e para uma maior qualidade do serviço prestado ao cliente.

---

# 13. Pré-visualização de Recibos

Após a geração do recibo em formato PDF, o sistema AQStore apresenta automaticamente uma janela de pré-visualização do documento.

Esta funcionalidade foi implementada para permitir que o operador visualize o recibo antes da sua impressão ou armazenamento, proporcionando uma melhor experiência de utilização e reduzindo possíveis erros.

A pré-visualização foi desenvolvida através da classe `VisualizadorPDFFX`, utilizando a biblioteca **Apache PDFBox** em conjunto com **JavaFX**.

---

## Objetivos do Módulo

A funcionalidade de pré-visualização foi criada com os seguintes objetivos:

- Visualizar o recibo imediatamente após a venda;
- Evitar a abertura de aplicações externas;
- Confirmar os dados antes da impressão;
- Melhorar a experiência do utilizador;
- Integrar todo o processo de faturação numa única aplicação.

---

## Biblioteca Utilizada

Para a visualização dos documentos PDF foi utilizada a biblioteca:

```text
Apache PDFBox
```

Esta biblioteca permite:

- Abrir ficheiros PDF;
- Ler páginas de documentos;
- Converter páginas PDF em imagens;
- Integrar documentos PDF em aplicações Java.

---

## Classe Responsável

A funcionalidade encontra-se implementada na classe:

```java
VisualizadorPDFFX.java
```

Esta classe é responsável por:

- Abrir o PDF gerado;
- Converter o conteúdo em imagem;
- Apresentar a imagem numa janela JavaFX;
- Disponibilizar scroll para navegação.

---

## Abertura do Documento PDF

Após a geração do recibo, o sistema recebe o caminho do ficheiro PDF.

## Código

```java
PDDocument document =
    Loader.loadPDF(
        new File(caminhoPdf)
    );
```

## Explicação

A classe:

```java
PDDocument
```

representa um documento PDF carregado em memória.

O método:

```java
Loader.loadPDF()
```

Abre o ficheiro localizado no caminho indicado.

---

## Exemplo

```text
recibos/Recibo_1776513458912.pdf
```

Após a abertura, o documento fica disponível para processamento.

---

## Criação do Renderizador

Depois de abrir o documento, o sistema cria um renderizador.

## Código

```java
PDFRenderer renderer =
    new PDFRenderer(document);
```

## Explicação

O objeto:

```java
PDFRenderer
```

É responsável por transformar páginas PDF em imagens.

Esta conversão é necessária porque o JavaFX não possui suporte nativo para exibir ficheiros PDF diretamente.

Assim, o sistema converte a página em imagem antes da apresentação.

---

## Conversão da Página em Imagem

## Código

```java
BufferedImage imagem =
    renderer.renderImageWithDPI(
        0,
        150
    );
```

## Explicação

O método:

```java
renderImageWithDPI()
```

converte uma página PDF numa imagem.

### Parâmetros

```java
0
```

Indica a primeira página do documento.

```java
150
```

Define a resolução da imagem em DPI (Dots Per Inch).

---

## Resultado

```text
PDF
  │
  v
BufferedImage
```

A imagem gerada apresenta uma qualidade suficiente para leitura e impressão.

---

## Conversão para JavaFX

O JavaFX não utiliza diretamente objetos do tipo `BufferedImage`.

Por esse motivo é necessária uma conversão.

## Código

```java
Image imagemFx =
    SwingFXUtils.toFXImage(
        imagem,
        null
    );
```

## Explicação

A classe:

```java
SwingFXUtils
```

faz a conversão entre:

```text
BufferedImage
        │
        v
JavaFX Image
```

Esta etapa permite que a imagem seja apresentada nos componentes gráficos do JavaFX.

---

## Criação do ImageView

Após a conversão, a imagem é colocada num componente gráfico.

## Código

```java
ImageView imageView =
    new ImageView(imagemFx);
```

## Explicação

O componente:

```java
ImageView
```

É responsável por exibir imagens na interface JavaFX.

Neste caso será utilizado para mostrar o recibo.

---

## Ajuste Automático da Imagem

## Código

```java
imageView.setPreserveRatio(true);
imageView.setFitWidth(700);
```

## Explicação

### Preserve Ratio

```java
setPreserveRatio(true)
```

Mantém as proporções originais do documento.

---

### Fit Width

```java
setFitWidth(700)
```

Define a largura máxima da imagem.

Isto garante que o recibo seja apresentado de forma adequada dentro da janela.

---

## Utilização de ScrollPane

Alguns recibos podem ser maiores do que a área disponível na janela.

Para resolver este problema foi utilizado um `ScrollPane`.

## Código

```java
ScrollPane scrollPane =
    new ScrollPane(imageView);
```

## Explicação

O componente:

```java
ScrollPane
```

Permite deslocar o conteúdo verticalmente e horizontalmente.

---

## Benefícios

- Melhor navegação;
- Visualização completa do documento;
- Suporte para recibos longos.

---

## Criação da Janela

Depois de preparar todos os componentes, o sistema cria a janela de visualização.

## Código

```java
Scene scene =
    new Scene(
        scrollPane,
        800,
        600
    );
```

## Explicação

A cena define:

| Parâmetro | Valor   |
|-----------|---------|
| Largura   | 800     |
| Altura    | 600     |

Estes valores garantem uma área confortável para leitura do recibo.

---

## Configuração do Stage

## Código

```java
Stage stage = new Stage();

stage.setTitle(
    "Pré-visualização do Recibo"
);

stage.setScene(scene);
```

## Explicação

O objeto:

```java
Stage
```

representa uma nova janela JavaFX.

A janela recebe:

- Título;
- Conteúdo;
- Dimensões.

---

## Exibição da Janela

## Código

```java
stage.show();
```

## Explicação

Este método torna a janela visível ao utilizador.

A partir deste momento o recibo pode ser visualizado.

---

## Fluxo Completo da Pré-visualização

```text
Venda Finalizada
        │
        v
Recibo PDF Gerado
        │
        v
Abrir Documento
        │
        v
Converter PDF em Imagem
        │
        v
Converter para JavaFX
        │
        v
Criar ImageView
        │
        v
Inserir em ScrollPane
        │
        v
Mostrar Janela
```

---

## Benefícios da Implementação

A funcionalidade de pré-visualização oferece várias vantagens.

## Integração

Todo o processo ocorre dentro da aplicação.

---

## Rapidez

O recibo é apresentado imediatamente após a venda.

---

## Facilidade de Utilização

Não é necessário abrir programas externos.

---

## Melhor Experiência do Utilizador

O operador consegue confirmar os dados antes de imprimir.

---

## Organização

Permite verificar rapidamente o documento gerado.

---

## Exemplo de Funcionamento

```text
Finalizar Venda
        │
        v
Gerar PDF
        │
        v
Abrir Janela
        │
        v
Mostrar Recibo
```

O operador visualiza imediatamente:

- Produtos vendidos;
- Quantidades;
- Totais;
- Forma de pagamento;
- Troco.

---

A funcionalidade de pré-visualização de recibos representa uma importante melhoria na usabilidade do sistema AQStore.

Através da utilização da biblioteca Apache PDFBox e dos componentes JavaFX foi possível criar uma solução simples, eficiente e totalmente integrada ao processo de faturação.

Esta funcionalidade permite visualizar os recibos gerados sem recorrer a aplicações externas, aumentando a produtividade e a qualidade do atendimento ao cliente.

---

# 14. Interface Gráfica

A interface gráfica do sistema AQStore foi desenvolvida utilizando a biblioteca **JavaFX**, permitindo criar uma aplicação moderna, intuitiva e de fácil utilização.

A escolha do JavaFX deveu-se à sua capacidade de fornecer componentes visuais avançados, boa integração com Java e suporte para interfaces responsivas.

A interface foi projetada para facilitar o trabalho dos operadores da loja, permitindo acesso rápido às funcionalidades principais do sistema.

---

## Objetivos da Interface

A interface gráfica foi desenvolvida com os seguintes objetivos:

- Facilitar a interação do utilizador com o sistema;
- Organizar as funcionalidades em menus intuitivos;
- Melhorar a produtividade dos operadores;
- Reduzir erros operacionais;
- Apresentar informações de forma clara e organizada.

---

## Estrutura Geral da Interface

A aplicação está dividida em duas áreas principais:

## Menu Lateral

Responsável pela navegação entre os módulos.

---

## Área de Conteúdo

Responsável pela apresentação das funcionalidades selecionadas.

---

## Estrutura Visual

```text
+---------------------------------------+
|                AQStore                |
+-----------+---------------------------+
|           |                           |
|  MENU     |       CONTEÚDO            |
|           |                           |
|           |                           |
+-----------+---------------------------+
```

---

## Janela de Login

A primeira interface apresentada ao utilizador é a janela de autenticação.

## Código

```java
Label lblUsuario = new Label("Usuário:");
Label lblSenha = new Label("Senha");

TextField txtUsuario = new TextField();
PasswordField txtSenha = new PasswordField();

Button btnEntrar = new Button("Entrar");
```

## Explicação

Nesta interface são utilizados os seguintes componentes:

| Componente    | Função               |
|---------------|----------------------|
| Label         | Exibir textos        |
| TextField     | Receber o utilizador |
| PasswordField | Receber a senha      |
| Button        | Executar login       |

O componente:

```java
PasswordField
```

Oculta automaticamente os caracteres digitados pelo utilizador.

---

## Botão de Login

## Código

```java
btnEntrar.setOnAction(
    e -> fazerLogin(stage)
);
```

## Explicação

Quando o utilizador pressiona o botão **Entrar**, o sistema:

1. Obtém os dados inseridos;
2. Valida os campos;
3. Consulta a base de dados;
4. Verifica as credenciais;
5. Abre a janela principal.

---

## Janela Principal

Após autenticação bem-sucedida, o sistema apresenta a interface principal.

## Código

```java
Stage stage = new Stage();

stage.setTitle("AQStore");
stage.setScene(scene);
stage.show();
```

## Explicação

A classe:

```java
Stage
```

Representa uma janela JavaFX.

Cada janela da aplicação é criada através deste componente.

---

## Menu Lateral

O menu lateral permite navegar entre os módulos do sistema.

## Código

```java
Button btnLoja =
    criarBotaoMenu(
        "🛒",
        "Loja / Caixa"
    );

Button btnProdutos =
    criarBotaoMenu(
        "📦",
        "Produtos"
    );

Button btnComprasDia =
    criarBotaoMenu(
        "🧾",
        "Compras do Dia"
    );

Button btnRelatorios =
    criarBotaoMenu(
        "📊",
        "Relatórios"
    );
```

## Explicação

Cada botão abre uma funcionalidade específica.

### Loja / Caixa

Permite efetuar vendas.

---

### Produtos

Permite gerir produtos.

---

### Compras do Dia

Permite consultar vendas diárias.

---

### Relatórios

Permite gerar relatórios.

---

## Controlo de Permissões na Interface

A interface adapta-se automaticamente ao perfil do utilizador.

## Código

```java
if (isAdmin()) {
    menu.getChildren().add(btnUsuarios);
}
```

## Explicação

Apenas utilizadores do tipo:

```text
ADMIN
```

visualizam o módulo:

```text
Utilizadores
```

Esta abordagem aumenta a segurança do sistema.

---

## Utilização do BorderPane

A interface principal utiliza o componente:

```java
BorderPane root = new BorderPane();
```

## Explicação

O BorderPane divide a janela em cinco áreas:

```text
TOP
LEFT
CENTER
RIGHT
BOTTOM
```

No AQStore:

| Região   | Utilização |
|----------|------------|
| LEFT     | Menu       |
| CENTER   | Conteúdo   |
| TOP      | Cabeçalho  |

---

## Utilização de VBox

O menu lateral foi criado através de uma VBox.

## Código

```java
VBox menu = new VBox(10);
```

## Explicação

A VBox organiza os componentes verticalmente.

Resultado:

```text
Loja
Produtos
Compras do Dia
Relatórios
Utilizadores
```

---

## Utilização de HBox

Em várias áreas do sistema foi utilizado o componente:

```java
HBox topo = new HBox(10);
```

## Explicação

O HBox organiza elementos horizontalmente.

Exemplo:

```text
[Pesquisar] [Filtrar] [Eliminar]
```

---

## Utilização de TableView

Grande parte dos dados do sistema são apresentados através de tabelas.

## Código

```java
TableView<Object[]> tabela =
    new TableView<>();
```

## Explicação

O componente TableView permite:

- Exibir dados;
- Selecionar registos;
- Organizar informações;
- Atualizar conteúdos.

---

## Exemplo de Utilização

Na gestão de produtos:

```text
+----+----------------+---------+
| ID | Produto        | Preço   |
+----+----------------+---------+
| 1  | Ração Premium  | 3500    |
| 2  | Coleira        | 1200    |
+----+----------------+---------+
```

---

## Utilização de Alert

O sistema utiliza caixas de diálogo para comunicar com o utilizador.

## Código

```java
Alert alert =
    new Alert(
        Alert.AlertType.INFORMATION
    );
```

## Explicação

Os Alert são utilizados para:

- Informações;
- Avisos;
- Erros;
- Confirmações.

---

## Exemplo

```java
alert.setTitle("Sucesso");
alert.setContentText(
    "Produto cadastrado com sucesso."
);
```

Resultado:

```text
+----------------------+
| Sucesso              |
| Produto cadastrado   |
| com sucesso.         |
+----------------------+
```

---

## Interface do Carrinho

A área de vendas apresenta os produtos adicionados ao carrinho.

## Estrutura

```text
Produto
Quantidade
Preço
Total
```

Os dados são atualizados automaticamente sempre que um novo produto é adicionado.

---

## Interface dos Relatórios

O módulo de relatórios apresenta:

- Ano;
- Mês;
- Produtos vendidos;
- Quantidades;
- Totais.

Tudo organizado numa tabela dinâmica.

---

## Interface da Pré-visualização PDF

Após gerar o recibo, o sistema abre uma nova janela.

## Código

```java
ImageView imageView =
    new ImageView(imagemFx);

ScrollPane scrollPane =
    new ScrollPane(imageView);
```

## Explicação

O PDF é convertido em imagem e apresentado ao utilizador.

O ScrollPane permite navegar pelo documento.

---

## Benefícios da Interface Implementada

A interface desenvolvida apresenta diversas vantagens.

## Facilidade de Utilização

Menus simples e intuitivos.

---

## Organização

Funcionalidades separadas por módulos.

---

## Rapidez

Acesso rápido às operações principais.

---

## Segurança

Funcionalidades controladas por nível de acesso.

---

## Integração

Todos os módulos encontram-se integrados numa única aplicação.

---

## Fluxo de Navegação

```text
Login
   │
   v
Menu Principal
   │
   ├── Loja
   │
   ├── Produtos
   │
   ├── Compras do Dia
   │
   ├── Relatórios
   │
   └── Utilizadores
```

---

A interface gráfica desenvolvida em JavaFX permitiu criar uma aplicação moderna, organizada e intuitiva para a gestão da Loja Aquapet.

A utilização de componentes como BorderPane, VBox, HBox, TableView, Alert e ScrollPane contribuiu para uma melhor experiência do utilizador, facilitando a navegação e tornando o sistema mais eficiente e profissional.

A integração da interface com os restantes módulos do sistema garantiu uma solução completa para gestão de vendas, produtos, utilizadores e relatórios.

---

# 15. Implementação do CRUD

O sistema AQStore implementa o conceito **CRUD (Create, Read, Update e Delete)** para permitir a gestão completa das informações armazenadas na base de dados.

CRUD representa as quatro operações fundamentais utilizadas em praticamente todos os sistemas de informação:

| Operação | Significado | Função                   |
|----------|-------------|--------------------------|
| Create   | Criar       | Inserir novos dados      |
| Read     | Ler         | Consultar dados          |
| Update   | Atualizar   | Alterar dados existentes |
| Delete   | Eliminar    | Remover dados            |

No AQStore, estas operações são aplicadas principalmente aos módulos de:

- Produtos;
- Utilizadores;
- Compras do Dia;
- Relatórios.

A implementação foi realizada utilizando:

- Java;
- JDBC;
- MySQL;
- JavaFX.

---

## Objetivos da Implementação CRUD

O CRUD foi desenvolvido para:

- Automatizar a gestão dos dados;
- Facilitar a manutenção da informação;
- Garantir integridade dos registos;
- Permitir operações rápidas e seguras;
- Integrar a aplicação com a base de dados.

---

## CREATE – Inserção de Dados

A operação CREATE é responsável pela criação de novos registos.

No sistema AQStore esta operação é utilizada principalmente para:

- Cadastrar produtos;
- Criar utilizadores;
- Registar vendas;
- Registar compras.

---

## Exemplo: Inserção de Produtos

### Código

```java
public boolean cadastrarProduto(
        String nome,
        double preco) {

    String sql =
        "INSERT INTO produtos(nome, preco, stock) VALUES (?, ?, ?)";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt =
             conn.prepareStatement(sql)) {

        stmt.setString(1, nome);
        stmt.setDouble(2, preco);
        stmt.setDouble(3, stock);

        return stmt.executeUpdate() > 0;
    }
}
```

---

## Explicação

A consulta SQL utilizada é:

```sql
INSERT INTO produtos(nome, preco, stock)
VALUES (?, ?, ?)
```

Esta consulta cria um novo registo na tabela de produtos.

O método:

```java
stmt.executeUpdate()
```

executa a operação e devolve o número de linhas afetadas.

Se o resultado for superior a zero:

```java
return true;
```

significa que o produto foi registado com sucesso.

---

## Exemplo Prático

```text
Produto: Ração Premium
Preço: 3500
Stock: 20
```

Resultado:

```text
Produto inserido na base de dados.
```

---

## READ – Consulta de Dados

A operação READ permite consultar informações armazenadas na base de dados.

É utilizada em:

- Produtos;
- Utilizadores;
- Compras do Dia;
- Relatórios.

---

## Exemplo: Consulta de Produtos

### Código

```java
public List<Object[]> listarProdutos() {

    List<Object[]> lista =
        new ArrayList<>();

    String sql =
        "SELECT * FROM produtos ORDER BY nome";
```

---

## Explicação

A consulta SQL utilizada é:

```sql
SELECT *
FROM produtos
ORDER BY nome
```

Esta consulta recupera todos os produtos registados.

Os dados são armazenados numa lista:

```java
List<Object[]>
```

para posterior apresentação na interface gráfica.

---

## Processamento dos Resultados

### Código

```java
while (rs.next()) {

    lista.add(new Object[]{
        rs.getInt("id"),
        rs.getString("nome"),
        rs.getDouble("preco")
    });
}
```

---

## Explicação

O ciclo percorre todos os registos devolvidos pela base de dados.

Cada produto é convertido para:

```java
Object[]
```

e armazenado na lista.

---

## Resultado

```text
ID    Produto          Preço    Stock

1     Ração Premium    3500     10
2     Coleira          1200     15
3     Brinquedo         800     20
```

---

## UPDATE – Atualização de Dados

A operação UPDATE permite alterar informações já existentes.

É utilizada para:

- Atualizar produtos;
- Atualizar utilizadores;
- Alterar preços;
- Alterar níveis de acesso.

---

## Exemplo: Atualizar Produto

### Código

```java
public boolean atualizarProduto(
        int id,
        String nome,
        double preco,
        int stock,) {

    String sql =
        "UPDATE produtos " +
        "SET nome=?, preco=?, stock=? " +
        "WHERE id=?";
```

---

## Explicação

A consulta SQL utilizada é:

```sql
UPDATE produtos
SET nome=?, preco=?, stock=?
WHERE id=?
```

Esta operação procura um produto através do seu ID e altera os respetivos dados.

---

## Exemplo

Antes:

```text
ID: 1
Produto: Ração
Preço: 2500
Stock: 20
```

Depois:

```text
ID: 1
Produto: Ração Premium
Preço: 3500
Stock: 25
```

---

## DELETE – Eliminação de Dados

A operação DELETE permite remover registos da base de dados.

No AQStore é utilizada em:

- Produtos;
- Utilizadores;
- Compras do Dia.

---

## Exemplo: Eliminar Produto

### Código

```java
public boolean eliminarProduto(int id) {

    String sql =
        "DELETE FROM produtos WHERE id=?";
```

---

## Explicação

A consulta SQL utilizada é:

```sql
DELETE FROM produtos
WHERE id=?
```

Esta operação elimina permanentemente o registo selecionado.

---

## Exemplo

Antes:

```text
ID: 3
Produto: Brinquedo
Preço: 800
```

Depois:

```text
Produto removido.
```

O registo deixa de existir na base de dados.

---

## Utilização do PreparedStatement

Todas as operações CRUD utilizam:

```java
PreparedStatement
```

---

## Código

```java
PreparedStatement stmt =
    conn.prepareStatement(sql);
```

---

## Vantagens

### Segurança

Evita SQL Injection.

---

### Desempenho

Melhora a execução das consultas.

---

### Organização

Facilita a manutenção do código.

---

## Integração com a Interface JavaFX

As operações CRUD estão integradas diretamente na interface gráfica.

---

## Inserir

```text
Botão Adicionar
```

---

## Consultar

```text
TableView
```

---

## Atualizar

```text
Botão Atualizar
```

---

## Eliminar

```text
Botão Eliminar
```

---

## Fluxo de Funcionamento

```text
Utilizador
      │
      v
Interface JavaFX
      │
      v
AQStoreSistema
      │
      v
Conexao
      │
      v
MySQL
```

---

## Benefícios da Implementação CRUD

A implementação do CRUD trouxe várias vantagens ao sistema.

## Organização

Todos os dados ficam estruturados.

---

## Facilidade de Manutenção

Permite alterar informações rapidamente.

---

## Escalabilidade

Novos registos podem ser adicionados sem limitações.

---

## Integração

Todas as funcionalidades utilizam a mesma lógica.

---

## Segurança

Utilização de PreparedStatement em todas as operações.

---

## Aplicação do CRUD nos Módulos

| Módulo         | Create | Read | Update | Delete |
|----------------|--------|------|--------|--------|
| Produtos       | V      | V    | V      | V      |
| Utilizadores   | V      | V    | V      | V      |
| Compras do Dia | X      | V    | X      | V      |
| Relatórios     | X      | V    | X      | X      |
| Vendas         | V      | V    | X      | X      |

---

## Exemplo Completo

## Produto

### Inserir

```text
Ração Premium
3500
```

### Consultar

```text
ID: 1
Produto: Ração Premium
Preço: 3500
```

### Atualizar

```text
Preço: 4000
```

### Eliminar

```text
Produto removido.
```

---

A implementação do CRUD permitiu criar uma gestão completa e eficiente dos dados do sistema AQStore.

Através da integração entre Java, JDBC, JavaFX e MySQL foi possível disponibilizar operações de inserção, consulta, atualização e eliminação de forma simples, segura e organizada.

O CRUD constitui a base funcional do sistema, sendo utilizado em praticamente todos os módulos da aplicação e garantindo a correta manipulação das informações armazenadas na base de dados.

---

# 16. Estruturas de Dados Utilizadas

As estruturas de dados desempenham um papel fundamental no desenvolvimento de qualquer sistema informático, pois são responsáveis pela forma como os dados são organizados, armazenados e manipulados durante a execução do programa.

No desenvolvimento do AQStore foram utilizadas diversas estruturas de dados disponibilizadas pela linguagem Java, sendo a principal delas a **Lista Dinâmica (ArrayList)**.

Estas estruturas permitiram armazenar temporariamente informações como:

- Produtos;
- Carrinho de compras;
- Relatórios;
- Utilizadores;
- Resultados de consultas.

A correta utilização destas estruturas contribuiu para melhorar a organização do código, a eficiência das operações e a integração com a interface gráfica JavaFX.

---

## Conceito de Estrutura de Dados

Uma estrutura de dados é uma forma organizada de armazenar informações em memória, permitindo:

- Inserção de dados;
- Pesquisa de informações;
- Atualização de registos;
- Remoção de elementos;
- Percurso dos dados armazenados.

A escolha da estrutura adequada influencia diretamente o desempenho e a facilidade de manutenção do sistema.

---

## Estrutura Principal Utilizada

O AQStore utiliza principalmente a estrutura:

```java
ArrayList
```

A classe `ArrayList` faz parte da biblioteca Java Collections Framework e representa uma lista dinâmica.

---

## Implementação da Lista Dinâmica

## Código

```java
List<Object[]> carrinho =
        new ArrayList<>();
```

## Explicação

Esta instrução cria uma lista dinâmica denominada:

```text
carrinho
```

responsável por armazenar os produtos selecionados durante uma venda.

A lista pode crescer ou diminuir dinamicamente conforme a necessidade do sistema.

---

## Funcionamento da Lista

Cada elemento armazenado na lista corresponde a um produto adicionado ao carrinho.

## Estrutura

```java
new Object[]{
    produto,
    quantidade,
    preco,
    total
}
```

---

## Organização dos Dados

| Posição | Informação     |
|---------|----------------|
| 0       | Produto        |
| 1       | Quantidade     |
| 2       | Preço Unitário |
| 3       | Total          |

---

## Exemplo

```java
new Object[]{
    "Ração Premium",
    2,
    3500.00,
    7000.00
}
```

Neste exemplo:

- Produto = Ração Premium
- Quantidade = 2
- Preço = 3500
- Total = 7000

---

## Inserção de Elementos

A inserção de produtos na lista é realizada através do método:

## Código

```java
carrinho.add(
    new Object[]{
        produto,
        quantidade,
        preco,
        total
    }
);
```

## Explicação

O método:

```java
add()
```

Adiciona um novo elemento ao final da lista.

---

## Resultado

Antes:

```text
Carrinho vazio
```

Depois:

```text
[Produto 1]
```

Após nova inserção:

```text
[Produto 1]
[Produto 2]
```

---

## Percurso da Lista

Para percorrer os elementos armazenados utiliza-se um ciclo.

## Código

```java
for (Object[] item : carrinho) {

}
```

## Explicação

O ciclo percorre todos os elementos presentes na lista.

Cada elemento corresponde a um produto do carrinho.

---

## Exemplo

```text
Produto 1
Produto 2
Produto 3
```

O ciclo visitará cada um destes produtos.

---

## Acesso aos Dados

Os valores são obtidos através das posições do vetor.

## Código

```java
String produto =
    item[0].toString();

int quantidade =
    (Integer) item[1];

double preco =
    (Double) item[2];

double total =
    (Double) item[3];
```

## Explicação

Cada posição contém uma informação específica.

O sistema utiliza estes valores para:

- Gerar recibos;
- Calcular totais;
- Apresentar relatórios;
- Guardar vendas.

---

## Estruturas Utilizadas nos Relatórios

Os relatórios também utilizam listas dinâmicas.

## Código

```java
List<Object[]> relatorio =
        new ArrayList<>();
```

## Explicação

Cada linha do relatório é armazenada temporariamente numa lista antes de ser apresentada na interface.

---

## Estruturas Utilizadas na Consulta de Produtos

Os produtos obtidos da base de dados também são armazenados em listas.

## Código

```java
List<Object[]> produtos =
        new ArrayList<>();
```

## Explicação

A lista recebe os dados provenientes da consulta SQL e disponibiliza-os para a interface gráfica.

---

## Integração com JavaFX

A estrutura ArrayList integra-se facilmente com os componentes JavaFX.

## Exemplo

```java
tableView.getItems().addAll(lista);
```

## Explicação

Os dados armazenados na lista são enviados diretamente para a tabela apresentada ao utilizador.

---

## Porque Não Foi Utilizada uma Pilha?

Uma pilha segue o princípio:

```text
LIFO
Last In First Out
```

Ou seja:

```text
Último a entrar
Primeiro a sair
```

---

## Exemplo de Pilha

```text
Push A
Push B
Push C

Pop

Resultado: C
```

No AQStore não existe a necessidade de remover sempre o último produto inserido.

Por este motivo a pilha não foi considerada adequada.

---

## Porque Não Foi Utilizada uma Fila?

Uma fila segue o princípio:

```text
FIFO
First In First Out
```

Ou seja:

```text
Primeiro a entrar
Primeiro a sair
```

---

## Exemplo de Fila

```text
Inserir A
Inserir B
Inserir C

Remover

Resultado: A
```

No AQStore os produtos podem ser removidos livremente do carrinho, sem respeitar esta ordem.

Por essa razão a fila também não foi adotada.

---

## Comparação das Estruturas

| Estrutura  | Característica               |
|------------|------------------------------|
| Pilha      | Último entra, primeiro sai   |
| Fila       | Primeiro entra, primeiro sai |
| ArrayList  | Acesso livre aos elementos   |

---

## Justificação da Escolha

A utilização de `ArrayList` apresentou várias vantagens:

## Flexibilidade

Permite adicionar e remover elementos em qualquer posição.

---

## Simplicidade

Implementação simples e intuitiva.

---

## Integração

Compatibilidade direta com JavaFX.

---

## Desempenho

Boa performance para listas de dimensão moderada.

---

## Organização

Facilita a manipulação dos dados.

---

## Fluxo de Utilização

```text
Produto Selecionado
        │
        v
Adicionar à Lista
        │
        v
Carrinho
        │
        v
Venda
        │
        v
Recibo
        │
        v
Relatório
```

---

## Vantagens da Estrutura Escolhida

A implementação através de listas dinâmicas trouxe diversos benefícios:

### Facilidade de Programação

Código mais simples e organizado.

---

### Escalabilidade

Permite trabalhar com qualquer quantidade de produtos.

---

### Manutenção

Facilita futuras alterações ao sistema.

---

### Integração com Base de Dados

Permite armazenar resultados de consultas SQL.

---

### Integração com Interface Gráfica

Facilita a apresentação dos dados em tabelas JavaFX.

---

## Exemplo Prático

Considere os seguintes produtos:

```text
Ração Premium
Coleira
Brinquedo
```

Após inserção:

```java
carrinho.add(produto1);
carrinho.add(produto2);
carrinho.add(produto3);
```

Resultado:

```text
[0] Ração Premium
[1] Coleira
[2] Brinquedo
```

Todos os elementos ficam disponíveis para:

- consulta;
- atualização;
- remoção;
- exportação.

---

O AQStore utiliza principalmente a estrutura de dados **Lista Dinâmica (ArrayList)** para armazenar e manipular informações durante a execução do sistema.

A escolha desta estrutura permitiu implementar de forma eficiente funcionalidades como:

- Carrinho de compras;
- Relatórios;
- Consulta de produtos;
- Gestão de utilizadores;
- Processamento de vendas.

Embora conceitos como Pilha e Fila sejam importantes no estudo de Estruturas de Dados, as necessidades específicas do sistema tornaram a utilização de listas dinâmicas a solução mais adequada, oferecendo maior flexibilidade, simplicidade e integração com os restantes módulos da aplicação.

---

# 17. Controlo de Permissões

O controlo de permissões é um mecanismo fundamental de segurança implementado no sistema AQStore, responsável por restringir o acesso às funcionalidades de acordo com o perfil de cada utilizador.

Este mecanismo garante que cada utilizador tenha acesso apenas às operações necessárias para desempenhar as suas funções dentro da organização.

A implementação do controlo de permissões contribui para:

- Proteger os dados do sistema;
- Evitar alterações indevidas;
- Reduzir erros operacionais;
- Melhorar a segurança da aplicação;
- Garantir a integridade das informações.

---

## Objetivos do Controlo de Permissões

O sistema foi desenvolvido para:

- Identificar o perfil do utilizador autenticado;
- Restringir funcionalidades críticas;
- Diferenciar responsabilidades;
- Proteger os dados da base de dados;
- Garantir maior segurança operacional.

---

## Níveis de Acesso Implementados

O AQStore possui três níveis de acesso distintos:

| Nível     | Descrição                |
|-----------|--------------------------|
| ADMIN     | Administrador do sistema |
| GESTOR    | Responsável operacional  |
| ATENDENTE | Operador de caixa        |

Cada perfil possui permissões específicas.

---

## Perfil ADMIN

O perfil ADMIN possui acesso total ao sistema.

## Funcionalidades Disponíveis

- Login;
- Loja / Caixa;
- Gestão de Produtos;
- Compras do Dia;
- Relatórios;
- Gestão de Utilizadores;
- Alteração de Senha;
- Eliminação de Vendas;
- Gestão completa da aplicação.

---

## Responsabilidades

O administrador é responsável por:

- Gerir utilizadores;
- Definir permissões;
- Corrigir informações;
- Supervisionar o funcionamento do sistema.

---

## Perfil GESTOR

O perfil GESTOR possui permissões intermédias.

## Funcionalidades Disponíveis

- Loja / Caixa;
- Produtos;
- Compras do Dia;
- Relatórios;
- Alteração de Senha;
- Eliminação de Vendas.

---

## Restrições

O gestor não possui acesso ao módulo:

```text
Gestão de Utilizadores
```

Apenas administradores podem criar, atualizar ou remover utilizadores.

---

## Perfil ATENDENTE

O perfil ATENDENTE possui acesso limitado.

## Funcionalidades Disponíveis

- Loja / Caixa;
- Consulta de Produtos;
- Consulta de Compras;
- Alteração da própria senha.

---

## Restrições

O atendente não pode:

- Eliminar produtos;
- Criar utilizadores;
- Eliminar utilizadores;
- Aceder aos relatórios administrativos;
- Eliminar vendas.

---

## Armazenamento das Permissões

O nível de acesso encontra-se armazenado na tabela:

```text
usuarios
```

---

## Estrutura

| Campo    |
|----------|
| id       |
| username |
| password |
| nivel    |

---

## Exemplo

```text
ID: 1
Username: admin
Nivel: ADMIN
```

```text
ID: 2
Username: gestor
Nivel: GESTOR
```

```text
ID: 3
Username: caixa
Nivel: ATENDENTE
```

---

## Identificação do Perfil

Após o login, o sistema cria um objeto da classe `Usuario`.

## Código

```java
return new Usuario(
    rs.getInt("id"),
    rs.getString("username"),
    rs.getString("password"),
    rs.getString("nivel")
);
```

## Explicação

Nesta etapa são carregadas todas as informações do utilizador autenticado.

O campo:

```java
nivel
```

É utilizado posteriormente para controlar o acesso às funcionalidades.

---

## Verificação de Permissões

O sistema utiliza métodos específicos para verificar o perfil do utilizador.

## Código

```java
private boolean isAdmin() {

    return "ADMIN".equalsIgnoreCase(
        usuarioLogado.getNivelAcesso()
    );
}
```

## Explicação

Este método verifica se o utilizador autenticado possui o perfil:

```text
ADMIN
```

Se possuir, devolve:

```java
true
```

Caso contrário:

```java
false
```

---

## Verificação do Perfil Gestor

## Código

```java
private boolean isGestor() {

    return "GESTOR".equalsIgnoreCase(
        usuarioLogado.getNivelAcesso()
    );
}
```

## Explicação

Este método identifica se o utilizador pertence ao grupo dos gestores.

---

## Utilização das Permissões

As permissões são verificadas sempre que uma funcionalidade é carregada.

---

## Exemplo: Gestão de Utilizadores

### Código

```java
if (isAdmin()) {

    menu.getChildren().add(
        btnUsuarios
    );
}
```

### Explicação

Apenas administradores visualizam o botão:

```text
Utilizadores
```

Os restantes perfis não possuem acesso a esta funcionalidade.

---

## Exemplo: Compras do Dia

No módulo Compras do Dia apenas administradores e gestores podem eliminar vendas.

## Código

```java
if (isAdmin() || isGestor()) {

    topo.getChildren().add(
        btnEliminar
    );
}
```

## Explicação

A condição:

```java
isAdmin() || isGestor()
```

significa:

```text
ADMIN OU GESTOR
```

Apenas estes perfis conseguem visualizar o botão de eliminação.

---

## Proteção Adicional

Além de ocultar o botão, o sistema também verifica as permissões durante a execução da ação.

## Código

```java
if (!isAdmin() && !isGestor()) {

    mostrarAviso(
        "Não possui permissão para eliminar vendas."
    );

    return;
}
```

## Explicação

Mesmo que um utilizador tente executar a funcionalidade por outro meio, o sistema bloqueia a operação.

Esta abordagem aumenta significativamente a segurança da aplicação.

---

## Fluxo de Verificação

```text
Login
   │
   v
Obter Perfil
   │
   v
ADMIN ?
   │
 ┌─┴─────┐
 │       │
SIM     NÃO
 │       │
 v       v
Acesso  Verificar Gestor
Total
```

---

## Exemplo de Funcionamento

## Utilizador ADMIN

```text
Login: admin
```

Funcionalidades:

```text
V Produtos
V Compras do Dia
V Relatórios
V Utilizadores
V Eliminar Vendas
```

---

## Utilizador GESTOR

```text
Login: gestor
```

Funcionalidades:

```text
V Produtos
V Compras do Dia
V Relatórios
V Eliminar Vendas

X Utilizadores
```

---

## Utilizador ATENDENTE

```text
Login: caixa
```

Funcionalidades:

```text
V Loja
V Consulta de Produtos

X Relatórios
X Utilizadores
X Eliminar Vendas
```

---

## Benefícios do Controlo de Permissões

A implementação deste mecanismo trouxe diversas vantagens.

## Segurança

Protege informações sensíveis.

---

## Organização

Cada utilizador visualiza apenas o necessário.

---

## Integridade

Reduz alterações indevidas.

---

## Facilidade de Gestão

Permite controlar responsabilidades.

---

## Auditoria

Facilita a identificação de operações realizadas.

---

## Integração com Outros Módulos

O controlo de permissões encontra-se integrado com:

```text
Login
    │
    v
Utilizador
    │
    v
Controlo de Permissões
    │
    ├── Produtos
    ├── Compras do Dia
    ├── Relatórios
    └── Utilizadores
```

Desta forma, todas as funcionalidades respeitam as permissões definidas para cada perfil.

---

O módulo de controlo de permissões é um componente essencial do sistema AQStore, garantindo que cada utilizador tenha acesso apenas às funcionalidades compatíveis com o seu perfil.

Através da implementação de verificações por nível de acesso foi possível criar uma aplicação mais segura, organizada e adequada ao ambiente empresarial.

A utilização dos perfis ADMIN, GESTOR e ATENDENTE permitiu distribuir responsabilidades e proteger operações críticas, contribuindo para a integridade e confiabilidade do sistema.

---

# 18. Tecnologias Utilizadas

O desenvolvimento do sistema AQStore exigiu a utilização de diversas tecnologias, bibliotecas e ferramentas de software que permitiram implementar as funcionalidades de gestão comercial, autenticação, armazenamento de dados, geração de relatórios e emissão de recibos.

A escolha destas tecnologias teve como principal objetivo garantir:

- Facilidade de desenvolvimento;
- Boa performance;
- Escalabilidade;
- Segurança;
- Integração entre módulos;
- Facilidade de manutenção.

---

## Linguagem de Programação

## Java

A linguagem principal utilizada no desenvolvimento do sistema foi o **Java**.

### Características

- Orientada a Objetos;
- Portável;
- Segura;
- Robusta;
- Multiplataforma.

### Aplicação no Projeto

O Java foi utilizado para implementar:

- Interface gráfica;
- Regras de negócio;
- Gestão de utilizadores;
- Gestão de produtos;
- Processamento de vendas;
- Relatórios;
- Geração de recibos.

---

## Exemplo de Código

```java
public class Usuario {

    private int id;
    private String username;
    private String password;
    private String nivelAcesso;

}
```

### Explicação

A Programação Orientada a Objetos permitiu representar entidades reais através de classes e objetos, tornando o sistema mais organizado e reutilizável.

---

## Interface Gráfica

## JavaFX

A interface gráfica foi desenvolvida utilizando JavaFX.

JavaFX é uma framework moderna utilizada para criação de aplicações desktop.

---

## Funcionalidades Utilizadas

- Janelas;
- Menus;
- Botões;
- Tabelas;
- Alertas;
- Formulários;
- Imagens;
- ScrollPane.

---

## Exemplo

```java
Button btnEntrar =
        new Button("Entrar");
```

---

## Exemplo

```java
TableView<Object[]> tabela =
        new TableView<>();
```

---

## Benefícios

### Interface Moderna

Permite criar aplicações visualmente atrativas.

---

### Integração

Integra-se facilmente com Java.

---

### Produtividade

Facilita o desenvolvimento de interfaces complexas.

---

## Base de Dados

## MySQL

O sistema utiliza MySQL para armazenamento permanente dos dados.

---

## Dados Armazenados

- Utilizadores;
- Produtos;
- Vendas;
- Compras;
- Relatórios.

---

## Estrutura da Base de Dados

```text
usuarios
produtos
venda
compra
```

---

## Exemplo de Consulta

```sql
SELECT *
FROM produtos;
```

---

## Benefícios

### Segurança

Armazenamento confiável.

---

### Organização

Dados estruturados em tabelas.

---

### Escalabilidade

Permite trabalhar com grandes volumes de informação.

---

## JDBC

## Java Database Connectivity

A comunicação entre Java e MySQL foi realizada através do JDBC.

---

## Objetivo

Permitir que a aplicação execute:

- SELECT;
- INSERT;
- UPDATE;
- DELETE.

---

## Exemplo

```java
Connection conn =
    DriverManager.getConnection(
        URL,
        USER,
        PASSWORD
    );
```

---

## Explicação

O JDBC cria uma ligação entre a aplicação Java e o servidor MySQL.

---

## OpenPDF

## Biblioteca de Geração de PDFs

Para a emissão dos recibos foi utilizada a biblioteca:

```text
OpenPDF
```

---

## Funcionalidades

- Criar documentos PDF;
- Inserir textos;
- Criar tabelas;
- Exportar documentos.

---

## Exemplo

```java
Document document =
        new Document();

PdfWriter.getInstance(
        document,
        new FileOutputStream(
            caminho
        )
);
```

---

## Aplicação

Utilizada na classe:

```text
ReciboVenda.java
```

---

## Apache PDFBox

## Biblioteca de Visualização PDF

A biblioteca PDFBox foi utilizada para abrir e visualizar os recibos gerados.

---

## Funcionalidades

- Abrir PDFs;
- Ler documentos;
- Converter páginas em imagens;
- Integrar PDFs com JavaFX.

---

## Exemplo

```java
PDDocument document =
    Loader.loadPDF(
        new File(caminhoPdf)
    );
```

---

## Aplicação

Utilizada na classe:

```text
VisualizadorPDFFX.java
```

---

## Apache POI

## Biblioteca para Excel

A exportação de relatórios para Excel foi implementada através da biblioteca Apache POI.

---

## Funcionalidades

- Criar ficheiros XLSX;
- Criar folhas de cálculo;
- Inserir dados em células;
- Exportar relatórios.

---

## Exemplo

```java
Workbook workbook =
        new XSSFWorkbook();

Sheet sheet =
        workbook.createSheet(
            "Relatorio"
        );
```

---

## Aplicação

Utilizada no módulo:

```text
Relatórios
```

---

## CSV

## Exportação de Dados

O sistema também permite exportar relatórios para o formato CSV.

---

## Exemplo

```java
writer.println(
"Produto;Quantidade;"
+ "Preço;Total"
);
```

---

## Benefícios

- Compatibilidade com Excel;
- Compatibilidade com LibreOffice;
- Facilidade de partilha.

---

## Maven

## Gestão de Dependências

O projeto utiliza Maven para gerir bibliotecas e dependências.

---

## Benefícios

### Automatização

Instala automaticamente as bibliotecas necessárias.

---

### Organização

Centraliza todas as dependências no ficheiro:

```text
pom.xml
```

---

## Exemplo

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>
        mysql-connector-j
    </artifactId>
    <version>8.3.0</version>
</dependency>
```

---

## IDE Utilizada

## Visual Studio Code

O desenvolvimento foi realizado utilizando:

```text
Visual Studio Code
```

---

## Recursos Utilizados

- Extensão Java;
- Maven;
- Debugger;
- GitHub;
- JavaFX Support.

---

## Git e GitHub

## Controlo de Versões

O projeto utiliza Git para controlo de versões.

---

## Benefícios

- Histórico de alterações;
- Recuperação de versões;
- Trabalho colaborativo;
- Backup do código.

---

## Comandos Utilizados

```bash
git add .
git commit -m "Atualização"
git push
```

---

## Sistema Operativo

Durante o desenvolvimento foram utilizados:

## Windows 10/11

Ambiente principal de desenvolvimento.

---

## Tecnologias Integradas

O sistema AQStore integra as seguintes tecnologias:

```text
Java
   │
   v
JavaFX
   │
   v
JDBC
   │
   v
MySQL
   │
   v
OpenPDF
   │
   v
PDFBox
   │
   v
Apache POI
```

---

## Tabela Resumo

| Tecnologia | Finalidade                  |
|------------|-----------------------------|
| Java       | Linguagem principal         |
| JavaFX     | Interface gráfica           |
| MySQL      | Base de dados               |
| JDBC       | Ligação à base de dados     |
| OpenPDF    | Geração de recibos PDF      |
| PDFBox     | Pré-visualização de PDFs    |
| Apache POI | Exportação para Excel       |
| CSV        | Exportação de relatórios    |
| Maven      | Gestão de dependências      |
| Git        | Controlo de versões         |
| GitHub     | Repositório remoto          |
| VS Code    | Ambiente de desenvolvimento |

---

## Benefícios da Arquitetura Tecnológica

A combinação destas tecnologias proporcionou:

### Modularidade

Separação clara dos componentes.

---

### Escalabilidade

Facilidade de expansão do sistema.

---

### Manutenção

Código mais organizado e reutilizável.

---

### Segurança

Controlo de acessos e utilização de PreparedStatement.

---

### Produtividade

Automatização de diversas tarefas.

---

O desenvolvimento do AQStore envolveu a integração de várias tecnologias modernas, permitindo criar uma aplicação robusta, segura e eficiente para gestão comercial.

A utilização de Java, JavaFX, MySQL, JDBC, OpenPDF, PDFBox e Apache POI possibilitou implementar funcionalidades completas de autenticação, gestão de produtos, vendas, relatórios e geração de recibos, tornando o sistema adequado para utilização num ambiente empresarial.

A combinação destas ferramentas contribuiu significativamente para a qualidade, desempenho e escalabilidade da solução desenvolvida.

---

# 19. Resultados Obtidos

O desenvolvimento do sistema AQStore permitiu atingir os objetivos inicialmente definidos para o projeto, resultando numa aplicação funcional capaz de gerir produtos, utilizadores, vendas, relatórios e recibos eletrónicos.

A integração entre JavaFX, MySQL, JDBC e bibliotecas auxiliares possibilitou a criação de uma solução completa para apoio à gestão comercial da Loja Aquapet.

Os resultados obtidos demonstram a aplicação prática dos conhecimentos adquiridos ao longo do curso, especialmente nas áreas de:

- Conceção e Análise de Algoritmos;
- Bases de Dados;
- Interfaces Gráficas;
- Estruturas de Dados;
- Desenvolvimento de Aplicações Desktop.

---

## Funcionalidades Implementadas

Durante o desenvolvimento do projeto foram implementadas diversas funcionalidades que permitiram alcançar os objetivos propostos.

## Autenticação de Utilizadores

Foi implementado um sistema de autenticação capaz de:

- Validar credenciais;
- Identificar utilizadores;
- Controlar níveis de acesso;
- Restringir funcionalidades.

### Resultado

```text
V Funcionalidade concluída
```

---

## Gestão de Produtos

Foi implementado o CRUD completo de produtos.

### Funcionalidades

- Inserir produtos;
- Consultar produtos;
- Atualizar produtos;
- Eliminar produtos.

### Resultado

```text
V CRUD totalmente funcional
```

---

## Gestão de Vendas

Foi implementado um sistema de faturação que permite:

- Selecionar produtos;
- Adicionar produtos ao carrinho;
- Calcular totais;
- Processar pagamentos;
- Registar vendas.

### Resultado

```text
V Processo de venda automatizado
```

---

## Compras do Dia

Foi criada uma funcionalidade para consulta das vendas diárias.

### Funcionalidades

- Pesquisa por data;
- Consulta de vendas;
- Eliminação de vendas (com permissões).

### Resultado

```text
V Consulta rápida das operações diárias
```

---

## Relatórios

Foi implementado um módulo de relatórios com filtros por:

- Ano;
- Mês.

### Funcionalidades

- Consulta de vendas;
- Totais faturados;
- Exportação para CSV;
- Exportação para Excel.

### Resultado

```text
V Relatórios dinâmicos e exportáveis
```

---

## Recibos PDF

Foi implementada a geração automática de recibos.

### Funcionalidades

- Criação automática de PDF;
- Armazenamento dos recibos;
- Identificação das vendas.

### Resultado

```text
V Emissão automática de comprovativos
```

---

## Pré-visualização de Recibos

Foi implementada uma funcionalidade para visualizar o recibo dentro da própria aplicação.

### Funcionalidades

- Abertura automática;
- Conversão PDF para imagem;
- Scroll para navegação.

### Resultado

```text
V Visualização integrada no sistema
```

---

## Integração dos Módulos

Um dos principais resultados alcançados foi a integração completa entre todos os módulos.

## Fluxo Implementado

```text
Login
   │
   v
Produtos
   │
   v
Venda
   │
   v
Recibo PDF
   │
   v
Compras do Dia
   │
   v
Relatórios
```

Todos os módulos comunicam entre si utilizando a mesma base de dados.

---

## Resultados na Base de Dados

Durante os testes realizados foi possível verificar o correto funcionamento das operações SQL.

## Operações Testadas

| Operação  | Resultado |
|-----------|-----------|
| INSERT    | V         |
| SELECT    | V         |
| UPDATE    | V         |
| DELETE    | V         |

### Resultado

```text
CRUD totalmente operacional
```

---

## Resultados da Interface Gráfica

A interface desenvolvida em JavaFX apresentou resultados satisfatórios.

## Características Observadas

- Fácil navegação;
- Menus organizados;
- Resposta rápida;
- Integração com a base de dados;
- Atualização automática dos dados.

### Resultado

```text
V Interface funcional e intuitiva
```

---

## Resultados do Controlo de Permissões

Foram realizados testes com diferentes tipos de utilizadores.

## Perfis Testados

### ADMIN

```text
V Acesso total
```

---

### GESTOR

```text
V Produtos
V Relatórios
V Compras do Dia

X Gestão de Utilizadores
```

---

### ATENDENTE

```text
V Loja
V Consulta

X Eliminar vendas
X Gestão de utilizadores
X Relatórios administrativos
```

### Resultado

```text
V Permissões aplicadas corretamente
```

---

## Resultados da Exportação

Foram realizados testes de exportação dos relatórios.

## CSV

```text
V Exportação concluída
```

---

## Excel

```text
V Exportação concluída
```

---

## Compatibilidade

Os ficheiros gerados foram abertos com sucesso em:

- Microsoft Excel;

---

## Resultados dos Recibos PDF

Foram gerados diversos recibos durante os testes.

## Informações Impressas

- Nome da loja;
- Operador;
- Produtos;
- Quantidades;
- Totais;
- Forma de pagamento;
- Troco.

### Resultado

```text
V PDFs gerados corretamente
```

---

## Resultados da Pré-visualização

Foram realizados testes com diferentes recibos.

### Observações

- PDF carregado corretamente;
- Conversão para imagem concluída;
- Janela aberta automaticamente;
- Scroll funcional.

### Resultado

```text
V Visualização sem aplicações externas
```

---

## Desempenho do Sistema

Durante os testes realizados, o sistema apresentou um comportamento estável.

## Avaliação

| Critério           | Resultado   |
|--------------------|-------------|
| Velocidade         |    Boa      |
| Estabilidade       |    Boa      |
| Consumo de Memória |    Adequado |
| Usabilidade        |    Boa      |
| Integração         |    Boa      |

---

## Benefícios Obtidos

A implementação do AQStore trouxe diversos benefícios.

## Automatização

Redução de tarefas manuais.

---

## Organização

Melhor gestão das informações.

---

## Segurança

Controlo de acessos por perfil.

---

## Rapidez

Processamento automático das vendas.

---

## Fiabilidade

Registo permanente das operações.

---

## Aplicação dos Conhecimentos Académicos

O projeto permitiu aplicar conhecimentos adquiridos em várias unidades curriculares.

## Programação

- Java;
- Estruturas de Dados.

---

## Bases de Dados

- MySQL;
- JDBC;
- SQL.

---

## Conceção e Análise de Algoritmos

- Modularização;
- Organização de código;
- Boas práticas.

---

## Interfaces Gráficas

- JavaFX;
- Eventos;
- Componentes visuais.

---

## Avaliação Geral dos Resultados

Após a conclusão dos testes e validações, verificou-se que o sistema AQStore conseguiu cumprir os objetivos inicialmente definidos.

### Funcionalidades Implementadas

```text
V Login
V Produtos
V Vendas
V Compras do Dia
V Relatórios
V PDF
V Pré-visualização
V Permissões
V Exportação
```

---

Os resultados obtidos demonstram que o sistema AQStore é uma solução funcional para gestão comercial, permitindo controlar produtos, vendas, utilizadores e relatórios de forma integrada.

A aplicação apresentou um desempenho satisfatório durante os testes realizados, evidenciando a correta utilização das tecnologias selecionadas e a aplicação prática dos conhecimentos adquiridos ao longo do curso de Engenharia Informática.

O projeto cumpriu os objetivos propostos, disponibilizando uma plataforma moderna, organizada e preparada para futuras evoluções.

---

# 20. Conclusão

O desenvolvimento do sistema **AQStore – Loja Aquapet Lda** permitiu consolidar e aplicar conhecimentos adquiridos ao longo do curso de Engenharia Informática, integrando conceitos da Conceção e Análise de Algoritmos, Bases de Dados, Interfaces Gráficas, Estruturas de Dados e Engenharia de Software num único projeto funcional.

O sistema foi concebido com o objetivo de apoiar a gestão comercial da loja, disponibilizando funcionalidades que permitem controlar produtos, utilizadores, vendas, relatórios e recibos eletrónicos de forma integrada e eficiente.

Ao longo do desenvolvimento foram implementadas diversas funcionalidades essenciais, entre as quais:

- Sistema de autenticação de utilizadores;
- Controlo de permissões por perfil;
- Gestão de produtos através de CRUD;
- Registo e processamento de vendas;
- Consulta de compras do dia;
- Geração de relatórios por ano e mês;
- Exportação para CSV e Excel;
- Geração automática de recibos PDF;
- Pré-visualização dos recibos dentro da aplicação.

A utilização da linguagem **Java**, da framework **JavaFX**, da base de dados **MySQL** e de bibliotecas como **OpenPDF**, **Apache PDFBox** e **Apache POI** permitiu desenvolver uma aplicação moderna, robusta e preparada para futuras evoluções.

Relativamente às estruturas de dados, o sistema fez uso principalmente de **listas dinâmicas (ArrayList)**, utilizadas para armazenar temporariamente informações relacionadas com produtos, carrinhos de compras, vendas e relatórios. Esta escolha permitiu uma implementação simples, flexível e adequada às necessidades do projeto.

Durante os testes realizados verificou-se o correto funcionamento dos módulos implementados, destacando-se:

| Funcionalidade         | Estado          |
|------------------------|-----------------|
| Login                  | V Implementado |
| Gestão de Produtos     | V Implementado |
| Gestão de Utilizadores | V Implementado |
| Vendas                 | V Implementado |
| Compras do Dia         | V Implementado |
| Relatórios             | V Implementado |
| Exportação CSV         | V Implementado |
| Exportação Excel       | V Implementado |
| Recibos PDF            | V Implementado |
| Pré-visualização PDF   | V Implementado |
| Controlo de Permissões | V Implementado |

Os resultados obtidos demonstram que os objetivos inicialmente definidos foram alcançados com sucesso, permitindo disponibilizar uma solução funcional para gestão comercial, com uma interface intuitiva e uma estrutura organizada.

Além de resolver um problema real de gestão de vendas e produtos, o projeto proporcionou uma experiência prática no desenvolvimento de aplicações empresariais, permitindo compreender a importância da integração entre software, bases de dados e interfaces gráficas.

Por fim, considera-se que o sistema AQStore possui potencial para futuras melhorias, tais como:

- Implementação de controlo de stock automático;
- Dashboard com gráficos estatísticos;
- Backup automático da base de dados;
- Sistema de recuperação de palavra-passe;
- Gestão de fornecedores;
- Gestão de clientes;
- Integração com leitores de código de barras;
- Impressão direta de recibos em impressoras térmicas;
- Disponibilização de uma versão web.

Desta forma, conclui-se que o projeto atingiu os objetivos propostos, constituindo uma solução sólida, organizada e adequada para apoio à gestão comercial da Loja Aquapet Lda, evidenciando a aplicação prática dos conhecimentos adquiridos ao longo da formação académica.
