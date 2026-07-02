create database sistema_compras;

use sistema_compras;

-- Tabela de produtos
create table if not exists produtos (
	id int primary key auto_increment,
    nome varchar(500) not null,
    preco decimal(10,2) not null,
    qtd_estoque int not null
);

insert into produtos (nome, preco, qtd_estoque) 
values
('Teclado Mecânico RGB', 279.99, 15),
('Mouse Gamer 12000 DPI', 155.00, 30),
('Monitor 24" Full HD', 849.50, 8),
('Headset com Cancelamento de Ruído', 320.00, 12),
('Webcam 1080p', 193.48, 25);

-- Tabela de clientes registrados
create table if not exists clientes (
	cpf varchar(11) not null primary key,
    nome varchar(100) not null
);

insert into clientes (cpf, nome) 
values
('11122233344', 'Michel Silva'),
('55566677788', 'Ana Beatriz Costa'),
('99988877766', 'Carlos Henrique Souza');

-- Tabela de endereços linkados ao cpf do cliente
create table if not exists endereco_clientes (
	id int primary key auto_increment,
    cpf_cliente varchar(11),
    foreign key (cpf_cliente) references clientes (cpf),
    estado varchar(100) not null,
    cidade varchar(100) not null,
    bairro varchar(100) not null,
    rua varchar(100) not null,
    numero int
);

insert into endereco_clientes (cpf_cliente, estado, cidade, bairro, rua, numero) 
values
('11122233344', 'MG', 'Belo Horizonte', 'Centro', 'Av. Afonso Pena', 1500),
('55566677788', 'SP', 'São Paulo', 'Pinheiros', 'Rua dos Pinheiros', 45),
('99988877766', 'RJ', 'Rio de Janeiro', 'Copacabana', 'Av. Atlântica', 210);

-- Tabela de compras realizadas
create table if not exists compras (
	id int auto_increment primary key,
    id_produto int, 
    foreign key (id_produto) references produtos (id),
    cpf_cliente varchar(11),
    foreign key (cpf_cliente) references clientes (cpf),
    data_compra timestamp default current_timestamp,
    metodo_pagamento varchar(7) -- Credito ou PIX
);

insert into compras (id_produto, cpf_cliente, metodo_pagamento) 
values
(1, '11122233344', 'PIX'),
(2, '11122233344', 'PIX'),
(3, '55566677788', 'Credito'),
(5, '99988877766',  'PIX');