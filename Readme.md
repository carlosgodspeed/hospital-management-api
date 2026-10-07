# 🏥 Hospital Management API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-green)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![MySQL](https://img.shields.io/badge/MySQL-8-blue)

---

## Visão Geral do Projeto

### Objetivo do Sistema

Plataforma para gerenciamento de consultas médicas, integrando gestão de pacientes, médicos e administradores em um único sistema com controle de permissões por perfil e fluxos de agendamento.

### Público-Alvo

| Perfil | Necessidades | Acesso |
| --- | --- | --- |
| **Administrador** | Gestão completa do sistema, criação de usuários | Total |
| **Médico** | Visualizar agenda, confirmar/cancelar consultas | Dados próprios + pacientes atendidos |
| **Paciente** | Agendar consultas, visualizar histórico | Dados pessoais apenas |

### Tecnologias

**Backend:** Java 17 · Spring Boot 3.2.5 · Spring Data JPA (Hibernate) · Spring Security + JWT (jjwt) · MySQL 8 · BCrypt

**Frontend:** React (JavaScript) + Vite, em repositório separado: [hospital-management-frontend](https://github.com/carlosgodspeed/Hospital-Managmenet-API---FRONT-END)

---

## 📁 Estrutura do projeto

```
hospital-management-api/
├─ database/
│  └─ schema.sql
├─ src/
│  └─ main/
│     ├─ java/hospital/system/
│     │  ├─ config/
│     │  │  └─ DataInitializer.java
│     │  ├─ controller/
│     │  │  ├─ AuthController.java
│     │  │  ├─ CompromissoController.java
│     │  │  ├─ MedicoController.java
│     │  │  ├─ NotificacaoController.java
│     │  │  ├─ PacienteController.java
│     │  │  └─ UsuarioController.java
│     │  ├─ dto/
│     │  │  ├─ LoginRequest.java
│     │  │  └─ LoginResponse.java
│     │  ├─ exception/
│     │  │  └─ GlobalExceptionHandler.java
│     │  ├─ model/
│     │  │  ├─ Compromisso.java
│     │  │  ├─ Medico.java
│     │  │  ├─ Notificacao.java
│     │  │  ├─ Paciente.java
│     │  │  ├─ Role.java
│     │  │  └─ Usuario.java
│     │  ├─ repository/
│     │  │  ├─ CompromissoRepository.java
│     │  │  ├─ MedicoRepository.java
│     │  │  ├─ NotificacaoRepository.java
│     │  │  ├─ PacienteRepository.java
│     │  │  └─ UsuarioRepository.java
│     │  ├─ security/
│     │  │  ├─ JwtFilter.java
│     │  │  ├─ JwtUtil.java
│     │  │  ├─ SecurityConfig.java
│     │  │  └─ SecurityExceptionHandler.java
│     │  ├─ service/
│     │  │  ├─ AuthService.java
│     │  │  ├─ CompromissoService.java
│     │  │  ├─ EmailService.java
│     │  │  ├─ MedicoService.java
│     │  │  ├─ NotificacaoService.java
│     │  │  ├─ PacienteService.java
│     │  │  └─ UsuarioService.java
│     │  └─ HospitalManagementApplication.java
│     └─ resources/
│        └─ application.properties
└─ pom.xml
```

## Arquitetura em Camadas

```
Controller → Service → Repository → Entity
                ↑
            Security (JWT + roles)
```

### Endpoints principais

- `POST /api/auth/login` — Login e geração de token JWT
- `POST /api/usuarios` — Criar usuário (ADMIN)
- `GET/POST/DELETE /api/medicos/**` — CRUD de médicos (ADMIN cria/exclui; ADMIN e MEDICO visualizam)
- `GET/POST/DELETE /api/pacientes/**` — CRUD de pacientes (ADMIN cria/exclui; ADMIN e PACIENTE visualizam)
- `POST /api/compromissos` — Criar consulta (notifica paciente e médico)
- `GET /api/compromissos` · `GET /api/compromissos/medico/{id}?data=` · `GET /api/compromissos/paciente/{id}`
- `PUT /api/compromissos/{id}/status` — Atualizar status (ADMIN/MEDICO apenas; notifica paciente)
- `PUT /api/compromissos/{id}/remarcar` — Remarcar consulta (notifica paciente)
- `GET /api/notificacoes/paciente/{id}` · `GET /api/notificacoes/medico/{id}` — Listar notificações
- `PUT /api/notificacoes/{id}/lida` — Marcar como lida
- `GET /api/notificacoes/paciente/{id}/nao-lidas/count` · `GET /api/notificacoes/medico/{id}/nao-lidas/count`

### Entidades

| Entidade | Atributos Chave | Relacionamentos |
| --- | --- | --- |
| **Usuario** | username (único), password (BCrypt), role | — |
| **Medico** | nome, especialidade | `@OneToOne` com Usuario |
| **Paciente** | nome, email, telefone | `@OneToOne` com Usuario |
| **Compromisso** | data, hora, status (`AGENDADO`/`CONFIRMADO`/`CANCELADO`) | `@ManyToOne` com Paciente e Medico |
| **Notificacao** | mensagem, dataHora, lida | `@ManyToOne` com Paciente **OU** Medico (nunca os dois — regra em código, sem CHECK no banco) |

### Controle de Acesso por verbo HTTP

| Rota | Verbo | ADMIN | MEDICO | PACIENTE |
| --- | --- | --- | --- | --- |
| `/api/usuarios/**` | qualquer | ✓ | ✗ | ✗ |
| `/api/medicos/**` | GET | ✓ | ✓ | ✗ |
| `/api/medicos/**` | POST / DELETE | ✓ | ✗ | ✗ |
| `/api/pacientes/**` | GET | ✓ | ✗ | ✓ |
| `/api/pacientes/**` | POST / DELETE | ✓ | ✗ | ✗ |
| `/api/compromissos` | POST | ✓ | ✗ | ✓ |
| `/api/compromissos/**` | GET | ✓ | ✓ | ✓ |
| `/api/compromissos/**` | PUT / DELETE | ✓ | ✓ | ✗ |
| `/api/notificacoes/**` | GET / PUT | ✓ | ✓ | ✓ |

**CORS:** liberado para `http://localhost:5173` (front-end), via `corsConfigurationSource()` em `SecurityConfig`.

> **Ponto em aberto:** nenhuma rota checa se o usuário autenticado é "dono" do recurso (ex: paciente A consegue ver notificações do paciente B só trocando o id na URL). Não endereçado ainda.

---

## 🐛 Bugs corrigidos

- **`@Future` travava updates em compromissos passados.** Era revalidado em todo `save()`, não só na criação — bloqueava `PUT /status` em consultas já ocorridas. Corrigido: validação de "data futura" movida para checagem manual em `CompromissoService`, só na criação/remarcação.
- **Login não autenticava** (comparação de senha em texto puro) — corrigido com `PasswordEncoder.matches()`.
- **Criação de usuário falhava** (`@JsonIgnore` bloqueava entrada da senha) — trocado por `@JsonProperty(access = WRITE_ONLY)`.
- **Exclusão de médico/paciente vinculado quebrava o sistema** — agora bloqueada com `409 Conflict`.

---

## 🗺️ Roadmap

### ✅ Concluído

- **Fase 1** — CRUD completo de Paciente, Médico, Usuário e Compromisso
- **Fase 2** — Autenticação JWT + BCrypt
- **Fase 3** — Autorização por role
- **Fase 4** — Validação de entrada, permissões por verbo HTTP, limite de 12 compromissos/médico/dia
- **Fase 5** — Notificações persistidas no banco (Paciente e Médico), e-mail simulado, contagem de não lidas
- **CORS** configurado para o front-end

### 🔜 Próxima fase — back-end precisa crescer junto com o front-end

O front-end (ver roadmap dele) ganhou uma lista grande de funcionalidades novas que **dependem de mudanças aqui no back-end** antes de poderem ser construídas na tela. Nada disso está implementado ainda — é o planejamento pra próxima sessão:

**1. Paciente cancelar a própria consulta**
Hoje `PUT /api/compromissos/{id}/status` é restrito a `ADMIN`/`MEDICO`. Precisa de uma regra nova que permita o `PACIENTE` cancelar (nunca confirmar) uma consulta que seja dele mesmo — isso exige também checar "dono do recurso" (ver ponto em aberto acima), não só a role.

**2. Fluxo de solicitação → aprovação de consulta**
Hoje toda consulta criada já nasce `AGENDADO`. A ideia nova: o paciente **solicita** um horário, e o médico **aprova ou recusa**. Isso precisa de:
- Um novo estado no `enum Status` (ex: `SOLICITADO`, antes de `AGENDADO`)
- Um endpoint de aprovação/recusa restrito ao médico dono daquela consulta
- Repensar quem pode chamar `POST /api/compromissos` e com qual status inicial

**3. Prontuário médico (anexos: fotos, laudos, exames, anotações, receitas, remédios)**
A maior peça nova. Precisa de:
- Uma entidade nova (ex: `RegistroMedico` ou `Anexo`) com tipo (`FOTO`, `LAUDO`, `EXAME`, `ANOTACAO`, `RECEITA`, `REMEDIO`), vínculo com Paciente e com o Médico que criou, data, e o arquivo em si
- Endpoint de upload (`multipart/form-data`) e de listagem por paciente
- **Decisão pendente:** onde guardar os arquivos — sistema de arquivos local (simples, mas não escala) vs. serviço de storage (S3 ou similar)
- Regra de acesso: médico só anexa/vê de seus próprios pacientes; paciente só vê os próprios

**4. Perfil do usuário com foto e dados de contato**
- Campo de foto de perfil (caminho/URL) em `Paciente` e `Medico`
- `Medico` ainda não tem telefone — avaliar se deve ganhar
- Endpoint de atualização do próprio perfil (`PUT /api/pacientes/me`, `/api/medicos/me`)

**5. Endpoint `/api/me`**
Hoje o login devolve só o `id` do `Usuario`, não o `id` do `Paciente`/`Medico` vinculado. O front-end contorna isso com um workaround (ver Readme do front-end). Um endpoint `/api/me` que devolva o perfil completo do usuário logado eliminaria essa gambiarra e destrava os itens 1 e 4 de forma mais limpa.

> O item "Admin cadastrar médicos e pacientes pela interface" **não precisa de nada novo aqui** — `POST /api/medicos` e `POST /api/pacientes` já existem e já são restritos a `ADMIN`. A lacuna é só no front-end (ver o Readme dele).

---

## 📸 Capturas de tela

### Tela de Login

<img src="https://github.com/user-attachments/assets/3f2083aa-ee42-4781-847a-a853d76816f5" width="600"/>

### Dashboard (Admin)

<img src="https://github.com/user-attachments/assets/2a8ec3ff-5f00-4f13-ab88-65a96c8842d1" width="600" />

### Tela de Consultas

<img src="https://github.com/user-attachments/assets/4b2cd7ad-d342-4a66-b4db-2848fb4d860a" width="600" />

### Tela de Notificações

<img src="https://github.com/user-attachments/assets/30db8a25-4320-4950-be8a-d562974c6de5" width="600" />
