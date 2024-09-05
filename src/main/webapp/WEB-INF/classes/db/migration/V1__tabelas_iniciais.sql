CREATE TABLE public.cargo(
    id int PRIMARY KEY,
    nome varchar(100)
);

CREATE TABLE public.pessoa (
    id int PRIMARY KEY,
    nome varchar(100),
    cidade varchar(100),
    email varchar(255),
    cep varchar(9),
    endereco varchar(255),
    pais varchar(100),
    usuario varchar(100),
    telefone varchar(20),
    data_nascimento date,
    cargo_id int references cargo(id)
);

CREATE TABLE public.vencimento(
    id int PRIMARY KEY,
    descricao varchar(255),
    valor decimal,
    tipo varchar(50)
);

CREATE TABLE public.cargo_vencimento(
    id int PRIMARY KEY,
    cargo_id int references cargo(id),
    vencimento_id int references vencimento(id)
);

CREATE TABLE public.pessoa_salario_consolidado(
    id int PRIMARY KEY,
    pessoa_id int references pessoa(id),
    nome_pessoa varchar(100),
    nome_cargo varchar(100),
    salario decimal
);

CREATE SEQUENCE cargo_id_seq START 1;
CREATE SEQUENCE cargo_ven_id_seq START 1;
CREATE SEQUENCE pessoa_id_seq START 1;
CREATE SEQUENCE salario_id_seq START 1;
CREATE SEQUENCE ven_id_seq START 1;