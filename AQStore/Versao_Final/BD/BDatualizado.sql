CREATE DATABASE AQStore;
USE AQStore;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nivel VARCHAR(20) NOT NULL
);

INSERT INTO usuarios (username, password, nivel) VALUES
('admin', '123', 'ADMIN'),
('gestor', '123', 'GESTOR'),
('atendente', '123', 'ATENDENTE');

CREATE TABLE produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    stock int default 0
);
Describe produtos;
    Alter table produtos
    Add column stock int default 0;
    
UPDATE produtos SET stock = 20 WHERE nome='Arreia';
UPDATE produtos SET stock = 15 WHERE nome='Pedra Porosa';
UPDATE produtos SET stock = 30 WHERE nome='Tetra Min';
UPDATE produtos SET stock = 10 WHERE nome='Rede';
UPDATE produtos SET stock = 25 WHERE nome='Cascalho';
select * from produtos;
    describe produtos;
INSERT INTO produtos (nome, preco, stock) VALUES
('Arreia', 250.00, 20),
('Pedra Porosa', 120.00, 15),
('Tetra Min', 350.00, 30),
('Rede', 300.00, 10),
('Cascalho', 200.00, 25);

select * from produtos;

CREATE TABLE venda (
    id_venda INT AUTO_INCREMENT PRIMARY KEY,
    data DATETIME DEFAULT CURRENT_TIMESTAMP,
    usuario VARCHAR(100),
    forma_pagamento VARCHAR(30),
    valor_recebido DECIMAL(10,2),
    troco DECIMAL(10,2)
);

CREATE TABLE compra (
    id_compra INT AUTO_INCREMENT PRIMARY KEY,
    produto VARCHAR(150),
    quantidade INT,
    preco_unitario DECIMAL(10,2),
    total DECIMAL(10,2),
    forma_pagamento VARCHAR(30),
    id_venda INT,

    CONSTRAINT fk_venda
    FOREIGN KEY (id_venda)
    REFERENCES venda(id_venda)
    ON DELETE CASCADE
);

SHOW TABLES;

