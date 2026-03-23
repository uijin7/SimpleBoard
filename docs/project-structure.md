# Project Structure

## Current package layout

```text
src/main/java/com/example/simpleboard
- board/
  - controller/
  - entity/
  - model/
  - repository/
  - service/
- member/
  - controller/
  - entity/
  - model/
  - repository/
  - service/
- post/
  - controller/
  - entity/
  - model/
  - repository/
  - service/
- reply/
  - controller/
  - entity/
  - model/
  - repository/
  - service/
- global/
  - api/
  - crud/
  - pagination/
- web/
  - controller/
  - interceptor/
- config/
- SimpleBoardApplication.java
```

## Package rules

- `board`, `member`, `post`, `reply`: domain-based packages
- `controller`: HTTP endpoint layer
- `service`: business logic
- `model`: request/response DTO
- `entity`: JPA entity
- `repository`: Spring Data repository
- `web`: page controller and interceptor
- `config`: Spring configuration only
- `global/api`: shared API response wrapper
- `global/pagination`: shared pagination model
- `global/crud`: generic CRUD abstraction

## Resource layout

```text
src/main/resources
- templates/
- openapi/
  - api-docs.yaml
- application.yaml
- data.sql
```

## Cleanup notes

- Keep package names lowercase only.
- Place generated or exported OpenAPI YAML under `resources/openapi`, not inside Java source folders.
- Put page-routing controllers in `web/controller` and request interceptors in `web/interceptor`.
- Keep `config` limited to `@Configuration` classes.
