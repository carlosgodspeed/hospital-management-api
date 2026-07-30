# 🏥 Hospital Management API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-green)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![MySQL](https://img.shields.io/badge/MySQL-8-blue)

---

## Visão Geral do Projeto

### Objetivo do Sistema

Desenvolver uma plataforma robusta e escalável para gerenciamento de consultas médicas, integrando gestão de pacientes, médicos e administradores em um único sistema com controle granular de permissões e fluxos otimizados de agendamento.

### Problema que Resolve

- **Conflitos de horário** em agendamentos manuais
- **Falta de controle centralizado** sobre disponibilidade de médicos
- **Ausência de rastreabilidade** em cancelamentos e remarcações
- **Dificuldade de acesso** a informações por diferentes perfis de usuário
- **Processos manuais** suscetíveis a erros humanos

### Público-Alvo

| Perfil | Necessidades | Acesso |
| --- | --- | --- |
| **Administrador** | Gestão completa do sistema, criação de usuários, relatórios | Total |
| **Médico** | Visualizar agenda, confirmar consultas, acessar histórico | Dados próprios + pacientes atendidos |
| **Paciente** | Agendar consultas, visualizar histórico, remarcar/cancelar | Dados pessoais apenas |

### Tecnologias Utilizadas

**Backend:**

- Java 17
- Spring Boot 3.2.5
- Spring Data JPA (Hibernate)
- Spring Security + JWT (jjwt)
- MySQL 8.x
- BCrypt para hash de senhas

**Frontend (em desenvolvimento):**

- Protótipos no Figma
- Integração REST planejada

### Diferenciais do Projeto

- Arquitetura em camadas bem definida
- Sistema de autorização baseado em perfis
- Modelagem de domínio com relacionamentos JPA
- API RESTful seguindo boas práticas
- Tratamento de erros centralizado (`@RestControllerAdvice`)
- Design system validado em protótipos Figma

---

## 📁 Estrutura do projeto

```
hospital-management-api/
├─ database/
│  └─ schema.sql
├─ src/
│  └─ main/
│     ├─ java/
│     │  └─ hospital/system/
│     │     ├─ config/
│     │     │  └─ DataInitializer.java
│     │     ├─ controller/
│     │     │  ├─ AuthController.java
│     │     │  ├─ CompromissoController.java
│     │     │  ├─ MedicoController.java
│     │     │  ├─ PacienteController.java
│     │     │  └─ UsuarioController.java
│     │     ├─ dto/
│     │     │  ├─ LoginRequest.java
│     │     │  └─ LoginResponse.java
│     │     ├─ exception/
│     │     │  └─ GlobalExceptionHandler.java
│     │     ├─ model/
│     │     │  ├─ Compromisso.java
│     │     │  ├─ Medico.java
│     │     │  ├─ Paciente.java
│     │     │  ├─ Role.java
│     │     │  └─ Usuario.java
│     │     ├─ repository/
│     │     │  ├─ CompromissoRepository.java
│     │     │  ├─ MedicoRepository.java
│     │     │  ├─ PacienteRepository.java
│     │     │  └─ UsuarioRepository.java
│     │     ├─ security/
│     │     │  ├─ JwtFilter.java
│     │     │  ├─ JwtUtil.java
│     │     │  ├─ SecurityConfig.java
│     │     │  └─ SecurityExceptionHandler.java
│     │     ├─ service/
│     │     │  ├─ AuthService.java
│     │     │  ├─ CompromissoService.java
│     │     │  ├─ MedicoService.java
│     │     │  ├─ NotificacaoService.java
│     │     │  ├─ PacienteService.java
│     │     │  └─ UsuarioService.java
│     │     └─ HospitalManagementApplication.java
│     └─ resources/
│        └─ application.properties
└─ pom.xml
```

## Arquitetura Técnica

### Arquitetura em Camadas

```
┌─────────────────────────────────────────┐
│           Controller Layer              │  ← Endpoints REST
├─────────────────────────────────────────┤
│            Service Layer                │  ← Lógica de negócio
├─────────────────────────────────────────┤
│          Repository Layer               │  ← Acesso a dados (JPA)
├─────────────────────────────────────────┤
│            Entity Layer                 │  ← Modelo de domínio
├─────────────────────────────────────────┤
│          Security Layer                 │  ← Autenticação/Autorização
└─────────────────────────────────────────┘
                    ↓
              MySQL Database
```

### Controller Layer

**Responsabilidade:** Expor endpoints REST, validar entradas, retornar respostas HTTP adequadas.

**Endpoints principais:**

- `POST /api/auth/login` — Login e geração de token JWT
- `POST /api/usuarios` — Criar usuário (ADMIN)
- `POST /api/medicos` · `GET /api/medicos` · `GET /api/medicos/{id}` · `DELETE /api/medicos/{id}`
- `POST /api/pacientes` · `GET /api/pacientes` · `GET /api/pacientes/{id}` · `DELETE /api/pacientes/{id}`
- `POST /api/compromissos` — Criar consulta
- `GET /api/compromissos` — Listar todas
- `GET /api/compromissos/medico/{medicoId}?data=` — Buscar por médico e data
- `GET /api/compromissos/paciente/{pacienteId}` — Buscar por paciente
- `PUT /api/compromissos/{id}/status` — Atualizar status
- `PUT /api/compromissos/{id}/remarcar` — Remarcar consulta

### Service Layer

**Responsabilidade:** Implementar regras de negócio, orquestrar operações, validar consistência de dados.

**Implementado:**

- Bloqueio de conflitos de horário (`existsByMedicoIdAndDataAndHora`)
- Notificação (via log) ao remarcar ou mudar status de um compromisso
- Proteção contra exclusão de médico/paciente com compromissos vinculados

### Repository Layer

- `PacienteRepository`
- `MedicoRepository`
- `CompromissoRepository`
- `UsuarioRepository`

**Queries customizadas:**

- Busca por médico e data
- Busca por paciente
- Verificação de horário ocupado
- Verificação de vínculo com compromissos (para exclusão segura)

### Entity Layer

**Responsabilidade:** Modelagem do domínio com relacionamentos JPA.

**Entidades principais:**

| Entidade | Atributos Chave | Relacionamentos |
| --- | --- | --- |
| **Usuario** | username (único), password (BCrypt), role | — |
| **Medico** | nome, especialidade | `@OneToOne` com Usuario |
| **Paciente** | nome, email, telefone | `@OneToOne` com Usuario |
| **Compromisso** | data, hora, status | `@ManyToOne` com Paciente e Medico |

### Security Layer

**Fluxo de Autenticação:**

1. Cliente envia credenciais para `POST /api/auth/login`
2. Sistema valida a senha com `PasswordEncoder.matches()` (BCrypt)
3. Token JWT é gerado e retornado
4. Cliente inclui o token (`Authorization: Bearer <token>`) em requisições subsequentes
5. `JwtFilter` valida o token e popula o contexto de segurança com a role do usuário
6. `SecurityConfig` concede ou nega acesso com base na role

**Controle de Acesso por recurso (`SecurityConfig`):**

| Rota | ADMIN | MEDICO | PACIENTE |
| --- | --- | --- | --- |
| `/api/auth/**` | público | público | público |
| `/api/usuarios/**` | ✓ | ✗ | ✗ |
| `/api/medicos/**` | ✓ | ✓ | ✗ |
| `/api/pacientes/**` | ✓ | ✗ | ✓ |
| `/api/compromissos/**` | ✓ | ✓ | ✓ |

> A permissão hoje é por recurso, não por ação — por exemplo, um MEDICO pode criar e excluir médicos, não apenas visualizar. Refinar isso por verbo HTTP é um próximo passo natural (ver Fase 4).

---

## 🛠️ Changelog — correções recentes

O projeto passou por uma rodada de correção de bugs que impediam seu funcionamento:

- **Login nunca autenticava.** A senha era comparada em texto puro contra o hash BCrypt salvo no banco. Corrigido para usar `PasswordEncoder.matches()`.
- **Criação de usuário sempre falhava com `rawPassword cannot be null`.** O campo `password` em `Usuario` usava `@JsonIgnore`, que bloqueia tanto a saída quanto a entrada de dados — a senha nunca chegava ao servidor. Trocado por `@JsonProperty(access = WRITE_ONLY)`.
- **Exclusão de médico/paciente com compromisso vinculado quebrava o sistema** (`JpaObjectRetrievalFailureException`). Agora bloqueada com validação que retorna `409 Conflict` e mensagem clara.
- **Endpoint de login duplicado e inacessível** em `/api/usuarios/login` foi removido; o login é centralizado em `/api/auth/login`.
- **Tratamento de erros centralizado adicionado** (`GlobalExceptionHandler`), trocando stacktraces em respostas 500 por JSON de erro consistente.
- **Segredos movidos para variáveis de ambiente** (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`), removendo credenciais fixas do código.

---

## 🗺️ Roadmap Técnico Detalhado

### ✅ Fase 1 — Base do Sistema (Concluído)

CRUD completo de Paciente, Médico, Usuario e Compromisso, com relacionamentos JPA e queries customizadas (busca por médico/data, por paciente, verificação de horário ocupado).

### ✅ Fase 2 — Autenticação (Concluído)

Login via `/api/auth/login`, geração de JWT, senha com hash BCrypt e verificação via `PasswordEncoder.matches()`.

### ✅ Fase 3 — Autorização e Segurança (Concluído)

Spring Security + `JwtFilter` + controle de acesso por role (`ADMIN`, `MEDICO`, `PACIENTE`) e tratamento centralizado de erros de acesso negado e exceções gerais.

### 🚧 Fase 4 — Regras de Negócio (Em desenvolvimento)

**O que já existe:**
- Bloqueio de horário duplicado para o mesmo médico
- Bloqueio de exclusão de médico/paciente com compromissos vinculados

**O que falta:**
- Validação de entrada com Bean Validation (`@Valid` + DTOs de request)
- Permissões mais finas por ação (ex: MEDICO só visualizar, não excluir)
- Limite de consultas por período

### ⏳ Fase 5 — Notificações (Pendente)

Hoje `NotificacaoService` apenas imprime no console. Falta:
- Persistência de histórico de notificações
- Envio real por e-mail

### 🎨 Fase 7 — Front-end (Design concluído / Desenvolvimento pendente)

Protótipos prontos no Figma; falta integração com a API.

---

## Tela Inicial

<img width="1249" height="707" alt="Captura de Tela (54)" src="https://github.com/user-attachments/assets/f11e1200-e398-4d83-bee7-c498714b6b9f" />

## Tela Paciente

<img width="1481" height="837" alt="Captura de Tela (55)" src="https://github.com/user-attachments/assets/389b5e04-1135-4e17-adb8-8ebd6b8b5551" />

## Tela Medico

<img width="1461" height="841" alt="Captura de Tela (56)" src="https://github.com/user-attachments/assets/a828e774-5f5a-4a4e-8af6-fadd2e8276d8" />

## Tela Administrador

<img width="1465" height="844" alt="Captura de Tela (58)" src="https://github.com/user-attachments/assets/a04c461a-b3b7-41a4-81d1-01a454caefe9" />
