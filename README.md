# API de Controle de Tarefas

Uma API RESTful desenvolvida com Spring Boot para gerenciamento de tarefas, usuários e categorias. Esta aplicação fornece endpoints completos para operações CRUD (Create, Read, Update, Delete) em um banco de dados PostgreSQL, permitindo o controle de tarefas com vinculação a usuários e categorias.

## 🚀 Funcionalidades

- Gerenciamento de Usuários: Operações completas de CRUD para cadastro de usuários
- Gerenciamento de Categorias: Operações completas de CRUD para controle de categorias
- Gerenciamento de Tarefas: Operações completas de CRUD para controle de tarefas
- Vinculação Tarefa-Usuário: Cada tarefa é vinculada a um usuário específico
- Vinculação Tarefa-Categoria: Cada tarefa é vinculada a uma categoria específica
- Validação de Dados: Validação automática de campos obrigatórios, formato de email e telefone
- Tratamento de Exceções: Tratamento global de erros com mensagens claras
- Nome Único: Garantia de unicidade de nome no cadastro de usuários
- Email Único: Garantia de unicidade de email no cadastro de usuários
- Nome Único: Garantia de unicidade de nome no cadastro de categorias
- Relacionamento 1:N: Um usuário pode ter múltiplas tarefas
- Relacionamento N:1: Uma tarefa pertence a uma categoria
- Status de Tarefa: Controle de status (PENDENTE, EM_ANDAMENTO, CONCLUIDA, CANCELADA)
- Prioridade de Tarefa: Controle de prioridade (BAIXA, MEDIA, ALTA)
- Data de Vencimento: Controle de data limite para conclusão da tarefa
- DTOs: Separação clara entre entidades de banco e objetos de transferência de dados
- Testes Unitários: Cobertura de testes para as camadas de serviço e controller com JUnit e Mockito
- Paginação: Listagem com suporte a paginação
- Filtros Dinâmicos: Consultas dinâmicas usando Specifications
- Auditoria: Controle de datas de criação e atualização dos registros

## 🛠️ Tecnologias Utilizadas

- Java 21
- Spring Boot 4.1.1
- Spring Data JPA
- Spring Web MVC
- Spring Validation
- PostgreSQL
- Lombok
- Maven
- JUnit 5
- Mockito

## 📋 Pré-requisitos

- Java 21 ou superior instalado
- Maven 3.6+ instalado
- PostgreSQL 12+ instalado e rodando
- IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code, etc.)

## 🔧 Instalação e Configuração

### 1. Clone o repositório

```bash
git clone <repository-url>
cd api_controle_tarefas
```

### 2. Configure o Banco de Dados

Crie um banco de dados PostgreSQL:

```sql
CREATE DATABASE controle_tarefas;
```

### 3. Configure as Credenciais

Edite o arquivo `src/main/resources/application.properties` ou configure as variáveis de ambiente:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/controle_tarefas
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```

Ou usando variáveis de ambiente:

```bash
export DB_PASSWORD=sua_senha
```

### 4. Compile o Projeto

```bash
./mvnw clean install
```

Ou usando Maven diretamente:

```bash
mvn clean install
```

## ▶️ Executando a Aplicação

### Via Maven

```bash
./mvnw spring-boot:run
```

### Via JAR

```bash
java -jar target/API-Controle-Tarefas-0.0.1-SNAPSHOT.jar
```

A aplicação estará disponível em: http://localhost:8080

## 📡 Endpoints da API

### Categorias

#### Criar Categoria

```http
POST /categorias
Content-Type: application/json

{
  "nome": "Trabalho",
  "descricao": "Tarefas relacionadas ao trabalho"
}
```

Response: 201 Created

```json
{
  "id": 1,
  "nome": "Trabalho",
  "descricao": "Tarefas relacionadas ao trabalho",
  "createdAt": "2024-11-09T10:00:00",
  "updatedAt": "2024-11-09T10:00:00"
}
```

#### Listar Todas as Categorias

```http
GET /categorias
```

Response: 200 OK

```json
[
  {
    "id": 1,
    "nome": "Trabalho",
    "descricao": "Tarefas relacionadas ao trabalho",
    "createdAt": "2024-11-09T10:00:00",
    "updatedAt": "2024-11-09T10:00:00"
  }
]
```

#### Buscar Categoria por ID

```http
GET /categorias/{id}
```

Response: 200 OK

```json
{
  "id": 1,
  "nome": "Trabalho",
  "descricao": "Tarefas relacionadas ao trabalho",
  "createdAt": "2024-11-09T10:00:00",
  "updatedAt": "2024-11-09T10:00:00"
}
```

#### Atualizar Categoria

```http
PUT /categorias/{id}
Content-Type: application/json

{
  "nome": "Trabalho Atualizado",
  "descricao": "Descrição atualizada"
}
```

Response: 200 OK

```json
{
  "id": 1,
  "nome": "Trabalho Atualizado",
  "descricao": "Descrição atualizada",
  "createdAt": "2024-11-09T10:00:00",
  "updatedAt": "2024-11-09T11:00:00"
}
```

#### Deletar Categoria

```http
DELETE /categorias/{id}
```

Response: 204 No Content

### Usuarios

#### Criar Usuario

```http
POST /usuarios
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "senha123",
  "telefone": "11999999999"
}
```

Response: 201 Created

```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@email.com",
  "telefone": "11999999999",
  "createdAt": "2024-11-09T10:00:00",
  "updatedAt": "2024-11-09T10:00:00"
}
```

#### Listar Todos os Usuarios

```http
GET /usuarios
```

Response: 200 OK

```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "email": "joao@email.com",
    "telefone": "11999999999",
    "createdAt": "2024-11-09T10:00:00",
    "updatedAt": "2024-11-09T10:00:00"
  }
]
```

#### Buscar Usuario por ID

```http
GET /usuarios/{id}
```

Response: 200 OK

```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@email.com",
  "telefone": "11999999999",
  "createdAt": "2024-11-09T10:00:00",
  "updatedAt": "2024-11-09T10:00:00"
}
```

#### Atualizar Usuario

```http
PUT /usuarios/{id}
Content-Type: application/json

{
  "nome": "João Silva Atualizado",
  "email": "joao.novo@email.com",
  "senha": "novasenha123",
  "telefone": "11777777777"
}
```

Response: 200 OK

```json
{
  "id": 1,
  "nome": "João Silva Atualizado",
  "email": "joao.novo@email.com",
  "telefone": "11777777777",
  "createdAt": "2024-11-09T10:00:00",
  "updatedAt": "2024-11-09T11:00:00"
}
```

#### Deletar Usuario

```http
DELETE /usuarios/{id}
```

Response: 204 No Content

### Tarefas

#### Criar Tarefa

```http
POST /tarefas
Content-Type: application/json

{
  "titulo": "Projeto API",
  "descricao": "Desenvolver API de tarefas",
  "status": "PENDENTE",
  "prioridade": "ALTA",
  "dataVencimento": "2024-12-31",
  "usuarioId": 1,
  "categoriaId": 1
}
```

Response: 201 Created

```json
{
  "id": 1,
  "titulo": "Projeto API",
  "descricao": "Desenvolver API de tarefas",
  "status": "PENDENTE",
  "prioridade": "ALTA",
  "dataVencimento": "2024-12-31",
  "usuarioId": 1,
  "categoriaId": 1,
  "createdAt": "2024-11-09T10:00:00",
  "updatedAt": "2024-11-09T10:00:00"
}
```

#### Listar Todos as Tarefas (com filtros)

```http
GET /tarefas?page=0&size=10
```

Response: 200 OK

```json
{
  "content": [
    {
      "id": 1,
      "titulo": "Projeto API",
      "descricao": "Desenvolver API de tarefas",
      "status": "PENDENTE",
      "prioridade": "ALTA",
      "dataVencimento": "2024-12-31",
      "usuarioId": 1,
      "categoriaId": 1,
      "createdAt": "2024-11-09T10:00:00",
      "updatedAt": "2024-11-09T10:00:00"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

Parâmetros opcionais: titulo, descricao, dataVencimento, status, prioridade, usuarioId, categoriaId, page, size

#### Listar Tarefas com Filtros

```http
GET /tarefas?titulo=projeto&status=PENDENTE&prioridade=ALTA&usuarioId=1&categoriaId=1&page=0&size=10
```

Response: 200 OK

```json
{
  "content": [
    {
      "id": 1,
      "titulo": "Projeto API",
      "descricao": "Desenvolver API de tarefas",
      "status": "PENDENTE",
      "prioridade": "ALTA",
      "dataVencimento": "2024-12-31",
      "usuarioId": 1,
      "categoriaId": 1,
      "createdAt": "2024-11-09T10:00:00",
      "updatedAt": "2024-11-09T10:00:00"
    }
  ]
}
```

#### Buscar Tarefa por ID

```http
GET /tarefas/{id}
```

Response: 200 OK

```json
{
  "id": 1,
  "titulo": "Projeto API",
  "descricao": "Desenvolver API de tarefas",
  "status": "PENDENTE",
  "prioridade": "ALTA",
  "dataVencimento": "2024-12-31",
  "usuarioId": 1,
  "categoriaId": 1,
  "createdAt": "2024-11-09T10:00:00",
  "updatedAt": "2024-11-09T10:00:00"
}
```

#### Atualizar Tarefa

```http
PUT /tarefas/{id}
Content-Type: application/json

{
  "titulo": "Projeto API Atualizado",
  "descricao": "API atualizada com sucesso",
  "status": "CONCLUIDA",
  "prioridade": "BAIXA",
  "dataVencimento": "2025-01-15",
  "usuarioId": 1,
  "categoriaId": 1
}
```

Response: 200 OK

```json
{
  "id": 1,
  "titulo": "Projeto API Atualizado",
  "descricao": "API atualizada com sucesso",
  "status": "CONCLUIDA",
  "prioridade": "BAIXA",
  "dataVencimento": "2025-01-15",
  "usuarioId": 1,
  "categoriaId": 1,
  "createdAt": "2024-11-09T10:00:00",
  "updatedAt": "2024-11-09T11:00:00"
}
```

#### Deletar Tarefa

```http
DELETE /tarefas/{id}
```

Response: 204 No Content

## 🧪 Testes

Para executar os testes unitários:

```bash
./mvnw test
```

Ou usando Maven:

```bash
mvn test
```

## 📄 Estrutura do Projeto

```
api_controle_tarefas/
├── src/
│   ├── main/
│   │   ├── java/com/API_Controle_Tarefas/
│   │   │   ├── controller/     # Controladores REST
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── entity/         # Entidades JPA
│   │   │   ├── exception/      # Tratamento de exceções
│   │   │   ├── repository/     # Repositórios JPA
│   │   │   ├── service/        # Lógica de negócio
│   │   │   └── specification/  # Specifications para consultas dinâmicas
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/API_Controle_Tarefas/
│           ├── controller/     # Testes dos controllers
│           └── service/        # Testes dos serviços
├── API_Controle_Tarefas.postman_collection.json
├── pom.xml
└── README.md
```

## 🔍 Validações

### Usuario

- nome: Obrigatório, máximo 100 caracteres, deve ser único
- email: Obrigatório, formato válido de email, deve ser único
- senha: Obrigatório
- telefone: Obrigatório

### Categoria

- nome: Obrigatório, máximo 100 caracteres, deve ser único
- descricao: Obrigatório

### Tarefa

- titulo: Obrigatório
- descricao: Obrigatório
- status: Obrigatório (PENDENTE, EM_ANDAMENTO, CONCLUIDA, CANCELADA)
- prioridade: Obrigatório (BAIXA, MEDIA, ALTA)
- dataVencimento: Obrigatório
- usuarioId: Obrigatório, deve referenciar um usuário existente
- categoriaId: Obrigatório, deve referenciar uma categoria existente

## ⚠️ Tratamento de Erros

A API retorna códigos de status HTTP apropriados:

- 201 Created: Recurso criado com sucesso
- 200 OK: Operação realizada com sucesso
- 204 No Content: Recurso deletado com sucesso
- 400 Bad Request: Dados inválidos
- 404 Not Found: Recurso não encontrado
- 409 Conflict: Nome ou email já cadastrado
- 500 Internal Server Error: Erro interno no servidor

## 📝 Coleção Postman

Uma coleção Postman está disponível no arquivo `API_Controle_Tarefas.postman_collection.json` para facilitar os testes dos endpoints.

Para importar:

1. Abra o Postman
2. Clique em "Import"
3. Selecione o arquivo `API_Controle_Tarefas.postman_collection.json`
4. A coleção será carregada com todos os endpoints organizados

## 🤝 Contribuindo

Contribuições são bem-vindas! Por favor:

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

## 👤 Autor

**Flavio Alexandre Ribeiro**

Desenvolvido como parte do projeto de API de Controle de Tarefas.

## 📧 Contato

- GitHub: https://github.com/FlavioAlexandreRibeiro
- LinkedIn: https://www.linkedin.com/in/flavioalexandreribeiro1986/?isSelfProfile=true

Para dúvidas ou sugestões, abra uma issue no repositório.
