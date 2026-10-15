# AgendaPro API — primeira entrega

Backend Java 21 + Spring Boot 3.5.7 + PostgreSQL 16 + Flyway.

## Requisitos
- JDK 21
- Maven 3.9+ (ou Maven integrado ao IntelliJ)
- Docker Desktop / Docker Engine com Compose

## Executar
```bash
cp .env.example .env
# ajuste DB_PASSWORD em .env se desejar; use o mesmo valor ao iniciar a API
docker compose up -d
mvn clean test
mvn spring-boot:run
```
No Windows PowerShell, use `Copy-Item .env.example .env`.

Health: http://localhost:8080/actuator/health
Swagger UI: http://localhost:8080/swagger-ui.html (ainda sem endpoints de negócio)

Banco local: `localhost:5433`, base `agendapro`, usuário `agendapro`.
O Compose usa a variável `DB_PASSWORD` do arquivo `.env`; o processo Java também deve receber a mesma variável caso a senha seja alterada.

## IntelliJ
1. File > Open e escolha a pasta `agendapro-api` (a que contém pom.xml).
2. Configure Project SDK para JDK 21; importe o projeto Maven.
3. Execute `docker compose up -d` no Terminal.
4. Execute a classe `br.com.agendapro.AgendaProApplication`.
5. Verifique `GET /actuator/health`.

## Organização
- `estabelecimento`, `usuario`, `cliente`, `servico`, `agendamento`, `disponibilidade`: entidades.
- `shared`: BaseEntity, TenantEntity e enums.
- `db/migration`: migrações Flyway; V1 cria oito tabelas, índices, FKs compostas e restrição anti-sobreposição.

## Observações de segurança e escopo
- Todos os endpoints, exceto health, estão NEGADOS por padrão. Ainda não há JWT, controllers, serviços, autorização tenant ou cadastro funcional.
- O `estabelecimento_id` em todas as tabelas tenant e as FKs compostas impedem referências cruzadas entre tenants no banco; isso NÃO substitui autorização na API.
- A constraint de exclusão impede sobreposição entre agendamentos `AGENDADO`/`CONFIRMADO` do mesmo profissional, inclusive sob concorrência.
- Regras de horário de funcionamento, bloqueios, transições de status, validação de membros ativos e limites de plano serão implementadas na camada de serviço.
- A entidade JPA tem setters e é intencionalmente simples nesta etapa; DTOs e validação de entrada virão depois.
- A migration requer permissão para criar a extensão PostgreSQL `btree_gist` (no container local o usuário inicial tem permissão).
- Teste incluído é apenas smoke test; testes reais de integração com PostgreSQL serão adicionados na próxima entrega.
- Senhas do exemplo são somente para ambiente local; nunca reutilize em produção.
