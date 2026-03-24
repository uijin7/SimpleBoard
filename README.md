# SimpleBoard

Spring Boot 기반 게시판 프로젝트입니다.  
게시판, 게시글, 댓글, 회원 인증 기능을 구현했고 `Thymeleaf` 화면과 `REST API`를 함께 제공합니다.

현재 실행 환경은 아래 기준으로 정리되어 있습니다.

- 배포: `Render`
- 데이터베이스: `Supabase PostgreSQL`
- API 문서: `Swagger UI`
- 로컬 실행: `application-secret.yaml` 또는 환경 변수 기반

## Links

- Service: https://simpleboard-k994.onrender.com
- Swagger UI: https://simpleboard-k994.onrender.com/swagger-ui.html
- GitHub Repository: https://github.com/uijin7/SimpleBoard

## 주요 기능

- 회원가입, 로그인, 로그아웃, 현재 로그인 사용자 조회
- 게시판 생성 및 조회
- 게시글 작성, 조회, 수정, 삭제, 목록 조회
- 댓글 CRUD
- 세션 기반 로그인 체크 인터셉터
- Validation 및 공통 예외 응답 처리
- Swagger UI 기반 API 문서화

## 기술 스택

### Backend

- Java 17
- Spring Boot 2.7.18
- Spring Web
- Spring Data JPA
- Spring Validation
- Thymeleaf
- Spring Security Crypto
- Springdoc OpenAPI UI

### Database

- Supabase PostgreSQL

### Infra / DevOps

- Render
- Docker
- GitHub Actions
- Gradle

## 실행 방법

### 1. 비밀 설정 파일 준비

루트 경로에 `application-secret.yaml` 파일을 두고 실제 Supabase 접속 정보를 입력합니다.

```powershell
Copy-Item .\application-secret.yaml.example .\application-secret.yaml
```

예시:

```yaml
spring:
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://db.<project-ref>.supabase.co:5432/postgres?sslmode=require
    username: postgres
    password: <your-supabase-password>

  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect

server:
  port: 8082
```

### 2. 실행

Windows:

```powershell
.\gradlew.bat bootRun
```

또는

```powershell
.\scripts\run-local.ps1
```

macOS / Linux:

```bash
./gradlew bootRun
```

### 3. 확인

- Home: `http://localhost:8082/`
- Swagger UI: `http://localhost:8082/swagger-ui.html`

## 로컬 설정 관련 파일

- `application-secret.yaml`
  - 실제 실행에 사용하는 로컬 비밀 설정 파일
- `application-secret.yaml.example`
  - 템플릿 파일
- `scripts/run-local.ps1`
  - 로컬 실행 보조 스크립트

`application-secret.yaml`은 실제 비밀값이 들어가므로 Git에 올리면 안 됩니다.

## 패키지 구조

```text
src/main/java/com/example/simpleboard
- board/
- member/
- post/
- reply/
- global/
- web/
- config/
```

## 구현 포인트

- `board`, `post`, `reply`, `member` 도메인 기준 패키지 분리
- `CrudApiController`, `CrudService`, `CrudConverter`를 통한 공통 CRUD 추상화
- `LoginCheckInterceptor`를 통한 글 작성/수정 경로 보호
- 로컬과 배포 환경 모두 Supabase PostgreSQL 기준으로 정리
- 기본 게시판이 없을 때 자동으로 생성되도록 초기화 로직 추가

## 화면 예시

![Main view](docs/screenshots/main-view.svg)

![Login view](docs/screenshots/login-view.svg)

![Editor view](docs/screenshots/editor-view.svg)
