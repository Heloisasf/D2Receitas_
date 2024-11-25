CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

INSERT IGNORE INTO roles (nome) VALUES 
('EDITOR'),
('DEGUSTADOR'),
('COZINHEIRO');

CREATE TABLE IF NOT EXISTS medidas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

INSERT IGNORE INTO medidas (nome) VALUES 
('ml'),
('l'),
('kg'),
('g'),
('unidade'),
('colher de sopa'),
('colher de chá'),
('xícara'),
('pitada');

CREATE TABLE IF NOT EXISTS categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

INSERT IGNORE INTO categorias (nome) VALUES 
('Aves'),
('Carnes'),
('Peixes'),
('Sobremesas'),
('Saladas'),
('Sopas'),
('Massas'),
('Bebidas'),
('Lanches'),
('Vegetariano'),
('Café da Manhã');

CREATE TABLE IF NOT EXISTS ingredientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

INSERT IGNORE INTO ingredientes (nome) VALUES 
('Frango'),
('Carne Bovina'),
('Ovo'),
('Leite'),
('Farinha de Trigo'),
('Açúcar'),
('Sal'),
('Óleo'),
('Arroz'),
('Feijão'),
('Cebola'),
('Alho'),
('Tomate'),
('Pimentão'),
('Cenoura'),
('Batata'),
('Manteiga'),
('Queijo'),
('Presunto'),
('Chocolate');