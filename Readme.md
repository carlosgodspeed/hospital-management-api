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


### Diferenciais do Projeto

- Arquitetura em camadas bem definida
- Sistema de autorização baseado em perfis
- Modelagem de domínio com relacionamentos JPA
- API RESTful seguindo boas práticas
- Tratamento de erros centralizado (`@RestControllerAdvice`)

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
│     │     │  ├─ NotificacaoController.java
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
│     │     │  ├─ Notificacao.java
│     │     │  ├─ Paciente.java
│     │     │  ├─ Role.java
│     │     │  └─ Usuario.java
│     │     ├─ repository/
│     │     │  ├─ CompromissoRepository.java
│     │     │  ├─ MedicoRepository.java
│     │     │  ├─ NotificacaoRepository.java
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
- `GET /api/notificacoes/paciente/{pacienteId}` — Listar notificações de um paciente (mais recentes primeiro)
- `PUT /api/notificacoes/{id}/lida` — Marcar notificação como lida

### Service Layer

**Responsabilidade:** Implementar regras de negócio, orquestrar operações, validar consistência de dados.

**Implementado:**

- Bloqueio de conflitos de horário (`existsByMedicoIdAndDataAndHora`), tanto para o médico quanto para o paciente
- Limite de 12 compromissos por médico no mesmo dia
- Validação de entrada com Bean Validation (`@Valid` + anotações nas entidades), com erros formatados pelo `GlobalExceptionHandler`
- Permissões por verbo HTTP (não só por recurso) via `SecurityConfig`
- Notificação persistida no banco (`Notificacao`) ao remarcar ou mudar status de um compromisso
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
| **Notificacao** | mensagem, dataHora, lida | `@ManyToOne` com Paciente |

### Security Layer

**Fluxo de Autenticação:**

1. Cliente envia credenciais para `POST /api/auth/login`
2. Sistema valida a senha com `PasswordEncoder.matches()` (BCrypt)
3. Token JWT é gerado e retornado
4. Cliente inclui o token (`Authorization: Bearer <token>`) em requisições subsequentes
5. `JwtFilter` valida o token e popula o contexto de segurança com a role do usuário
6. `SecurityConfig` concede ou nega acesso com base na role

**Controle de Acesso por recurso (`SecurityConfig`):**

| Rota | Verbo | ADMIN | MEDICO | PACIENTE |
| --- | --- | --- | --- | --- |
| `/api/auth/**` | qualquer | público | público | público |
| `/api/usuarios/**` | qualquer | ✓ | ✗ | ✗ |
| `/api/medicos/**` | GET | ✓ | ✓ | ✗ |
| `/api/medicos/**` | POST / DELETE | ✓ | ✗ | ✗ |
| `/api/pacientes/**` | GET | ✓ | ✗ | ✓ |
| `/api/pacientes/**` | POST / DELETE | ✓ | ✗ | ✗ |
| `/api/compromissos` | POST | ✓ | ✗ | ✓ |
| `/api/compromissos/**` | GET | ✓ | ✓ | ✓ |
| `/api/compromissos/**` | PUT / DELETE | ✓ | ✓ | ✗ |
| `/api/notificacoes/**` | GET / PUT | ✓ | ✓ | ✓ |

> A permissão já é por verbo HTTP (Fase 4), não mais por recurso inteiro. Ponto em aberto: `/api/notificacoes/paciente/{id}` não checa se o paciente autenticado é o dono daquele `id` — qualquer usuário autenticado pode consultar notificações de qualquer paciente trocando o id na URL. Essa falta de checagem de "dono do recurso" também existe hoje em `/api/pacientes/**` e não foi endereçada nesta fase.

---

## 🛠️ Changelog — correções recentes

O projeto passou por uma rodada de correção de bugs que impediam seu funcionamento:

- **Login nunca autenticava.** A senha era comparada em texto puro contra o hash BCrypt salvo no banco. Corrigido para usar `PasswordEncoder.matches()`.
- **Criação de usuário sempre falhava com `rawPassword cannot be null`.** O campo `password` em `Usuario` usava `@JsonIgnore`, que bloqueia tanto a saída quanto a entrada de dados — a senha nunca chegava ao servidor. Trocado por `@JsonProperty(access = WRITE_ONLY)`.
- **Exclusão de médico/paciente com compromisso vinculado quebrava o sistema** (`JpaObjectRetrievalFailureException`). Agora bloqueada com validação que retorna `409 Conflict` e mensagem clara.
- **Endpoint de login duplicado e inacessível** em `/api/usuarios/login` foi removido; o login é centralizado em `/api/auth/login`.
- **Tratamento de erros centralizado adicionado** (`GlobalExceptionHandler`), trocando stacktraces em respostas 500 por JSON de erro consistente.
- **Segredos movidos para variáveis de ambiente** (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`), removendo credenciais fixas do código.
- **Fase 4 concluída:** validação de entrada com Bean Validation, permissões refinadas por verbo HTTP em `SecurityConfig`, e limite de 12 compromissos por médico/dia.
- **Fase 5 iniciada:** notificações agora são persistidas na tabela `notificacoes` (antes só logavam no console). Novos endpoints `GET /api/notificacoes/paciente/{id}` e `PUT /api/notificacoes/{id}/lida`.

---

## ⚠️ Bug conhecido — `@Future` bloqueia updates em compromissos com data passada

O campo `data` em `Compromisso` usa `@Future`, que é revalidado **toda vez que a entidade é salva**, não só na criação. Isso significa que qualquer `PUT` (`/status` ou `/remarcar`) em um compromisso cuja data já passou falha com `400 - Could not commit JPA transaction`, porque a validação dispara de novo no update.

**Impacto real:** depois que a data de uma consulta passa, não é mais possível marcar como `CONFIRMADO`/`CANCELADO` nem remarcar — o registro fica "congelado".

**Correção pendente:** mover a validação de "data futura" para acontecer só na criação (checagem manual no `CompromissoService`, ou um DTO de request separado para criação), e remover o `@Future` da entidade em si.

---

## 🗺️ Roadmap Técnico Detalhado

### ✅ Fase 1 — Base do Sistema (Concluído)

CRUD completo de Paciente, Médico, Usuario e Compromisso, com relacionamentos JPA e queries customizadas (busca por médico/data, por paciente, verificação de horário ocupado).

### ✅ Fase 2 — Autenticação (Concluído)

Login via `/api/auth/login`, geração de JWT, senha com hash BCrypt e verificação via `PasswordEncoder.matches()`.

### ✅ Fase 3 — Autorização e Segurança (Concluído)

Spring Security + `JwtFilter` + controle de acesso por role (`ADMIN`, `MEDICO`, `PACIENTE`) e tratamento centralizado de erros de acesso negado e exceções gerais.

### ✅ Fase 4 — Regras de Negócio (Concluído)

- Bloqueio de horário duplicado para o mesmo médico e para o mesmo paciente
- Bloqueio de exclusão de médico/paciente com compromissos vinculados
- Validação de entrada com Bean Validation (`@Valid` nas entidades, erros formatados pelo `GlobalExceptionHandler`)
- Permissões por verbo HTTP em `SecurityConfig` (ex: MEDICO só visualiza médicos, não cria/exclui)
- Limite de 12 compromissos por médico por dia

> Bug conhecido introduzido/exposto durante os testes desta fase: ver seção "Bug conhecido" acima sobre `@Future` em `Compromisso.data`.

### 🚧 Fase 5 — Notificações (Em desenvolvimento)

**O que já existe:**
- Entidade `Notificacao` persistida na tabela `notificacoes`
- Notificação criada automaticamente ao remarcar um compromisso ou alterar seu status
- `GET /api/notificacoes/paciente/{pacienteId}` — histórico por paciente, mais recente primeiro
- `PUT /api/notificacoes/{id}/lida` — marcar como lida

**O que falta:**
- Envio real por e-mail (`spring-boot-starter-mail` + `JavaMailSender`)
- Notificar também na **criação** do compromisso (hoje só notifica em remarcação/mudança de status)
- Notificar o **médico**, não só o paciente
- Endpoint de contagem de não lidas (`.../nao-lidas/count`) para uso em UI
- Checagem de "dono do recurso" (hoje qualquer autenticado lê notificações de qualquer paciente)

### ⏳ Fase 6 — Front-end (Ainda não iniciado)
