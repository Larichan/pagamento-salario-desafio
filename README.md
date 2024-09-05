# Desafio Técnico
O desafio foi criar uma aplicação Java com JSF onde, utilizando os dados informados, calcula o valor do salário para cada pessoa cadastrada no sistema e salva as informações em um banco de dados relacional. Foi necessário criar um banco de dados com 5 tabelas, sendo elas Pessoa, Cargo, Vencimento, Cargo_Vencimento e Pessoa_Salario_Consolidado. O banco utilizado foi o PostgreSQL e também foi fornecido uma planilha com dados iniciais a serem utilizados no sistema.

## Começando

### Pré-requisitos

```
Java 17
Maven 3.6.3
Docker
```

### Instruções
Primeiramente será necessário configurar o arquivo .env com as seguintes variáveis:

```
DB_USER="admin"
DB_PASSWORD="123456"
```

Elas serão utilizadas para subir o banco no Docker.
Além disso, é necessário configurar também o application.properties com as seguintes variáveis:

```
spring.application.name=pagamento-salario
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.datasource.username=admin
spring.datasource.password=123456
server.servlet.context-path=/
```

Lembrando que é necessário que o usuário e a senha do banco sejam as mesmas em ambos os arquivos.

Após isso, rode o seguinte comando em um terminal dentro da pasta do projeto para subir um container no Docker rodando o banco:
```
docker-compose up
```

Em um novo terminal, utilize os comandos do maven para instalar as dependências do projeto 
```
mvn install
```

Possa ser que algumas dependências de teste não estejam alinhadas, então rode o comando com skip tests
```
mvn install -DskipTests
```

Após isso, em sua IDE, rode o projeto como sendo um *Java Application*

Ao subir o sistema pela primeira vez, o Flyway irá criar as tabelas no banco. Para popular as tabelas, foi criado uma funcionalidade no sistema para ler o .xlsx fornecido no desafio.

## Melhorias futuras

Além do desafio principal, foram propostos outros desafios opcionais que não foram implementados pelo tempo hábil, mas podem ser melhorias futuras:
- Processamento assíncrono no cálculo
- Tela de login e autenticação
- Testes unitários

Além desses, ao longo do desenvolvimento, outros pontos foram notados como melhorias:
- Utilizar variáveis de ambiente no application-properties, assim como no compose.yml, para ambos utilizarem os mesmos valores
- Tratar melhor as exceções, com exibição de mensagens amigáveis ao usuário no sucesso ou falha de requisições
- Utilizar um servidor como o tomcat para rodar a aplicação
- Paginar consultas
- Validações negociais de dados
- Loading visual

## Tecnologias Utilizadas
```
Java 17
JSF 2
Primefaces 6
Rewrite Servlet
Apache POI
Jasper Reports 7
Maven 3.6.3
Spring Boot 2
Lombok
Flyway
PostgreSQL
Docker
```