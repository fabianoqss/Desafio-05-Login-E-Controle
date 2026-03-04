# DSCommerce - API REST

API RESTful de e-commerce desenvolvida como desafio do curso **DevSuperior**, com foco na implementação de autenticação e autorização via **OAuth2 + JWT**.

## Tecnologias

- Java 21
- Spring Boot 3.4.4
- Spring Security (OAuth2 Authorization Server + Resource Server)
- Spring Data JPA
- H2 Database (perfil de teste)
- Maven
- Bean Validation

## Funcionalidades

- Autenticação com OAuth2 e tokens JWT (grant type: password)
- Controle de acesso por perfis: `ROLE_ADMIN` e `ROLE_CLIENT`
- CRUD de produtos (somente Admin)
- Listagem de categorias
- Criação e consulta de pedidos
- Endpoint `/users/me` para dados do usuário autenticado
- Validação de acesso próprio ou admin (`validateSelfOrAdmin`)
- Tratamento global de erros

## Regras de Acesso

| Endpoint              | Método | Permissão              |
|-----------------------|--------|------------------------|
| `/products`           | GET    | Público (autenticado)  |
| `/products/{id}`      | GET    | Público (autenticado)  |
| `/products`           | POST   | ROLE_ADMIN             |
| `/products/{id}`      | PUT    | ROLE_ADMIN             |
| `/products/{id}`      | DELETE | ROLE_ADMIN             |
| `/categories`         | GET    | Público (autenticado)  |
| `/orders/{id}`        | GET    | ROLE_ADMIN, ROLE_CLIENT|
| `/orders`             | POST   | ROLE_CLIENT            |
| `/users/me`           | GET    | ROLE_ADMIN, ROLE_CLIENT|

## Como executar

### Pré-requisitos

- Java 21+
- Maven

### Rodando o projeto

```bash
# Clone o repositório
git clone <url-do-repositorio>

# Acesse o diretório do projeto
cd dscommerce

# Execute com Maven
./mvnw spring-boot:run
```

A aplicação sobe no perfil `test` com banco H2 em memória.

Console H2 disponível em: `http://localhost:8080/h2-console`

### Obtendo token de acesso

```
POST /oauth2/token
Content-Type: application/x-www-form-urlencoded

grant_type=password&username={email}&password={senha}
```

## Estrutura do Projeto

```
src/main/java/.../dscommerce/
├── config/          # Configurações de segurança (OAuth2, Resource Server, CORS)
├── controllers/     # Endpoints REST
├── dto/             # Objetos de transferência de dados
├── entities/        # Entidades JPA (User, Product, Order, Category...)
├── projections/     # Interfaces de projeção JPA
├── repositories/    # Repositórios Spring Data
└── services/        # Regras de negócio
```
