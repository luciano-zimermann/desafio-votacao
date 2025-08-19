# Assembleia de Votação

## Descrição
Este projeto implementa uma API para gerenciamento de assembleias, permitindo o cadastro e consulta de associados, criação de pautas, abertura e encerramento de sessões de votação, registro de votos e extração dos resultados consolidados. A aplicação foi desenvolvida utilizando as seguintes tecnologias: Spring Boot, Java 21, Spring Data JPA, H2 Database, Lombok, SLF4J, MySQL, Swagger, JUnit, Mockito e Docker.

## Regras de Negócio
- Pautas, associados e sessões devem ser criados antes de registrar votos.
- Cada associado só pode votar uma vez por pauta.
- Apenas pautas existentes podem ter sessões de votação abertas.
- Sessões de votação devem estar abertas para receber votos.
- Votos são contabilizados apenas após o encerramento da sessão.


## Tecnologias Aplicadas
- Linguagem de Programação: Java 21
- Framework: Spring Boot
- Persistência: Spring Data JPA
- Banco de Dados em Memória: H2 Database
- Banco de Dados: MySQL 8
- Injeção de Código: Lombok
- Ferramenta de Monitoramento (Log): SLF4J
- Documentação da API: Swagger
- Ferramentas de Testes: JUnit e Mockito
- Container: Docker

## Pré-Requisitos
- **Java 21 - JDK 21**
- **MySQL 8**
- **Maven**
- **Docker** e **Docker Compose** instalados e em execução

## Inicialização
### Subindo o Banco de Dados
No diretório raiz do projeto, execute `docker compose up -d` para iniciar o MySQL necessário para a aplicação em segundo plano.

### Iniciando a Aplicação
Com o banco de dados em execução, inicie a aplicação executando `mvn spring-boot:run`, que irá compilar e rodar a API usando Maven e Spring Boot.

## Testes da API
A API pode ser acessada e testada diretamente pelo **Swagger UI** em `http://localhost:8080/swagger-ui.html` ou importando o arquivo `DesafioVotacao.postman_collection.json` no **Postman** ou **Insomnia** para execução das requisições.

## Escolhas Técnicas
#### Arquitetura
- A escolha da arquitetura da API foi o padrão de camadas, pois esta é uma abordagem padrão de projetos Spring Boot, onde as responsabilidades são separadas e bem definidas.

#### Versionamento
- Optou-se por utilizar a versão na URL com caráter informativo para os utilizadores da API, e a separação de versão por pacotes, facilitando a separação e manutenção das versões.

#### Testes
- JUnit foi escolhido porque é o framework padrão para testes unitários em Java, permitindo verificar se cada parte da aplicação funciona corretamente de forma isolada.
- Mockito foi usado para criar objetos simulados (mocks), permitindo testar a lógica de negócio sem depender de componentes externos, como o banco de dados, garantindo que os testes sejam rápidos e confiáveis.

#### Documentação
- A utilização do Swagger foi adotada devido à simplicidade de implementação e à facilidade de visualização e teste dos endpoints da API.

#### Monitoramento (Logs)
- O SLF4J foi escolhido por fornecer uma abstração de logging que garante registros consistentes e padronizados para depuração e monitoramento da aplicação.