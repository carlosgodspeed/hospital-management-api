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

- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- Spring Security
- MySQL 8.x
- Bean Validation

**Frontend (em desenvolvimento):**

- Protótipos no Figma
- Integração REST planejada

### Diferenciais do Projeto

- Arquitetura em camadas bem definida
- Sistema de autorização baseado em perfis
- Modelagem de domínio rica com relacionamentos JPA
- API RESTful seguindo boas práticas
- Preparado para escalabilidade horizontal
- Design system validado em protótipos Figma

---

## 📁 Estrutura do projeto

```
hospital-management/
├─ src/
│  └─ main/
│     ├─ java/
│     │  └─ hospital/system/
│     │     ├─ controller/
│     │     │  ├─ AuthController.java
│     │     │  ├─ CompromissoController.java
│     │     │  ├─ MedicoController.java
│     │     │  └─ PacienteController.java
│     │     │  └─ UsuarioController.java
│     │     ├─ dto/
│     │     │  └─ LoginRequest.java
│     │     │  └─ LoginResponse.java
│     │     ├─ model/
│     │     │  ├─ Compromisso.java
│     │     │  ├─ Medico.java
│     │     │  └─ Paciente.java
│     │     │  └─ Usuario.java
│     │     ├─ repository/
│     │     │  ├─ CompromissoRepository.java
│     │     │  ├─ MedicoRepository.java
│     │     │  └─ PacienteRepository.java
│     │     │  └─ UsuarioRepository.java
│     │     ├─ security/
|     |     |  └─ JwtFilter.java
|     |     |  └─ JwtUtil.java
|     |     |  └─ SecurityConfig.java
|     |     |  └─ SecurityExceptionHandler.java
│     │     ├─ service/
│     │     │  ├─ AuthService.java
│     │     │  ├─ CompromissoService.java
│     │     │  ├─ MedicoService.java
│     │     │  └─ PacienteService.java
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

**Características:**

- Anotações `@RestController`
- Validação com Bean Validation (`@Valid`)
- Tratamento de exceções centralizado
- DTOs para entrada/saída de dados

**Exemplo de endpoints:**

- `POST /api/compromissos` - Criar consulta
- `GET /api/compromissos/{id}` - Buscar consulta
- `PUT /api/compromissos/{id}` - Atualizar consulta
- `DELETE /api/compromissos/{id}` - Cancelar consulta

### Service Layer

**Responsabilidade:** Implementar regras de negócio, orquestrar operações, validar consistência de dados.

**Funções principais:**

- Validação de conflitos de horário (planejado)
- Verificação de disponibilidade médica (planejado)
- Aplicação de regras de cancelamento (planejado)
- Coordenação entre múltiplas entidades

### Repository Layer

**Responsabilidade:** Abstração de acesso a dados usando Spring Data JPA.

**Implementações:**

- `PacienteRepository`
- `MedicoRepository`
- `CompromissoRepository`
- `UsuarioRepository`

**Queries customizadas:**

- Busca por médico e data
- Busca por paciente
- Filtros de status
- Consultas por período

### Entity Layer

**Responsabilidade:** Modelagem do domínio com relacionamentos JPA.

**Entidades principais:**

| Entidade | Atributos Chave | Relacionamentos |
| --- | --- | --- |
| **Paciente** | CPF, nome, telefone, email | `@OneToMany` com Compromisso |
| **Medico** | CRM, especialidade | `@OneToMany` com Compromisso |
| **Compromisso** | dataHora, status, observacoes | `@ManyToOne` com Paciente e Medico |
| **Usuario** | login, senha, perfil | - |

### Security Layer

**Fluxo de Autenticação:**

1. Cliente envia credenciais para `/api/login`
2. Sistema valida usuário e senha
3. Token JWT é gerado e retornado
4. Cliente inclui token em requisições subsequentes
5. Filtro de segurança valida token e perfil
6. Acesso é concedido ou negado baseado em permissões

**Controle de Acesso:**

| Endpoint | ADMIN | MEDICO | PACIENTE |
| --- | --- | --- | --- |
| Criar usuários | ✓ | ✗ | ✗ |
| Visualizar todas consultas | ✓ | ✗ | ✗ |
| Visualizar próprias consultas | ✓ | ✓ | ✓ |
| Agendar consulta | ✓ | ✗ | ✓ |
| Confirmar consulta | ✓ | ✓ | ✗ |

---

## 🗺️ Roadmap Técnico Detalhado

### ✅ Fase 1 — Base do Sistema (Concluído)

**Objetivo Técnico:** Estabelecer fundação arquitetural e operações CRUD básicas.

**O que foi implementado:**

- Configuração do projeto Spring Boot com dependências essenciais
- Conexão com MySQL via Spring Data JPA
- Modelagem completa de entidades:
    - `Paciente` com validações de CPF e dados pessoais
    - `Medico` com CRM e especialidade
    - `Compromisso` com relacionamentos bidirecionais
    - `Usuario` com estrutura para autenticação
- Repositories com queries derivadas e customizadas
- CRUD completo para todas entidades
- Sistema de agendamento básico com operações:
    - Criação de consultas
    - Listagem geral
    - Busca por médico e data
    - Busca por paciente
    - Remarcação de consultas
    - Atualização de status (Agendada, Confirmada, Cancelada, Concluída)

**Impacto no Sistema:**

Criação da estrutura base que suporta todas as funcionalidades futuras. Estabelecimento de padrões de código e organização de pacotes que facilitam manutenção e evolução.

**Decisões Técnicas Tomadas:**

- Uso de JPA para abstração de persistência
- Relacionamentos bidirecionais para facilitar navegação entre entidades
- Enumerações para status de compromissos (type-safe)
- Separação clara entre camadas desde o início

**O que ainda falta:**

Nenhuma pendência nesta fase. Base sólida estabelecida.

---

### ✅ Fase 2 — Autenticação (Concluído)

**Objetivo Técnico:** Implementar sistema de identificação de usuários com suporte a múltiplos perfis.

**O que foi implementado:**

- Entidade `Usuario` com campos login e senha
- Enum `Perfil` com três níveis: ADMIN, MEDICO, PACIENTE
- Endpoint `POST /api/login` para autenticação
- Geração de tokens JWT
- Validação de credenciais contra banco de dados
- Retorno de token com informações do perfil

**Impacto no Sistema:**

Implementação da primeira camada de segurança da aplicação, permitindo identificação segura de usuários e preparando a base para autorização por perfil. O sistema agora consegue autenticar usuários e fornecer tokens para acesso às funcionalidades protegidas.

**Decisões Técnicas Tomadas:**

- Uso de autenticação stateless com JWT para melhor escalabilidade
- Separação entre autenticação e autorização
- Uso de enums para controle de perfis
- Preparação da entidade `Usuario` para integração com Spring Security
- Estrutura preparada para expansão futura de permissões

**O que ainda falta:**

Nenhuma pendência crítica nesta fase. Estrutura de autenticação concluída.

---

### ✅ Fase 3 — Autorização e Segurança (Concluído)

**Objetivo Técnico:**

Implementar controle de acesso baseado em perfis e proteger endpoints críticos da API.

**O que foi implementado:**

- Configuração completa do Spring Security
- Filtro JWT para interceptação de requisições
- Validação automática de token em endpoints protegidos
- Controle de acesso baseado em roles:
    - `ADMIN` → acesso total ao sistema
    - `MEDICO` → gerenciamento de agenda e consultas
    - `PACIENTE` → visualização das próprias consultas
- Configuração de rotas públicas e privadas
- Tratamento global para:
    - Token inválido
    - Token expirado
    - Usuário não autenticado
    - Acesso negado

**Impacto no Sistema:**

O sistema passa a operar com segurança em nível de produção, garantindo que cada usuário acesse apenas recursos permitidos pelo seu perfil.

**Decisões Técnicas Tomadas:**

- Uso de filtros personalizados no Spring Security
- Estratégia stateless para evitar sessões no servidor
- Centralização de tratamento de erros de segurança
- Uso de annotations para autorização declarativa

**O que ainda falta:**

Nenhuma pendência nesta fase.

---

### 🚧 Fase 4 — Regras de Negócio (Em desenvolvimento)

**Objetivo Técnico:**

Implementar validações de domínio para garantir consistência dos dados e integridade das operações médicas.

**O que será implementado:**

- Bloqueio de conflitos de horário para consultas
- Validação de disponibilidade do médico
- Limite de consultas por período
- Regras para cancelamento e remarcação
- Validação de entrada com DTOs
- Uso de Bean Validation

**Impacto Esperado no Sistema:**

Maior confiabilidade das operações, redução de inconsistências e alinhamento com regras reais de um ambiente hospitalar.

**Decisões Técnicas Planejadas:**

- Uso de DTOs para separar camada externa do domínio
- Centralização de regras na camada Service
- Uso de exceptions customizadas para regras de negócio

**O que ainda falta:**

Implementação completa das regras de domínio e cenários de validação.

---

### ⏳ Fase 5 — Notificações (Pendente)

**Objetivo Técnico:**

Criar sistema de comunicação automática entre plataforma e usuários.

**O que será implementado:**

- Notificação ao criar consulta
- Notificação ao remarcar consulta
- Notificação ao cancelar consulta
- Histórico interno de notificações
- Futuro envio por e-mail

**Impacto Esperado no Sistema:**

Melhora na experiência do usuário, redução de faltas e comunicação mais eficiente.

**Decisões Técnicas Planejadas:**

- Arquitetura desacoplada para eventos
- Possível uso de filas ou eventos assíncronos
- Preparação para integração com serviços externos de e-mail

**O que ainda falta:**

Implementação completa da infraestrutura de notificações.

---

### 🎨 Fase 7 — Front-end (Design concluído / Desenvolvimento pendente)

**Objetivo Técnico:**

Criar interface visual para consumo da API e experiência completa do usuário.

**O que já foi implementado:**

- Prototipação completa das telas no Figma
- Definição de fluxos de navegação
- Estrutura visual dos dashboards por perfil
- Tela de login desenhada

**O que será implementado:**

- Integração com API backend
- Persistência de sessão
- Consumo de endpoints autenticados
- Dashboards funcionais:
    - Admin
    - Médico
    - Paciente

**Impacto Esperado no Sistema:**

Transformação da API em produto utilizável por usuários finais.

**Decisões Técnicas Planejadas:**

- Integração com tecnologias front-end como React ou interface web tradicional
- Consumo via API REST
- Controle de rotas protegidas no front-end

**O que ainda falta:**

Implementação completa da interface e integração com backend.

## Tela Inicial

<img width="1249" height="707" alt="Captura de Tela (54)" src="https://github.com/user-attachments/assets/f11e1200-e398-4d83-bee7-c498714b6b9f" />

## Tela Paciente 

<img width="1481" height="837" alt="Captura de Tela (55)" src="https://github.com/user-attachments/assets/389b5e04-1135-4e17-adb8-8ebd6b8b5551" />

## Tela Medico

<img width="1461" height="841" alt="Captura de Tela (56)" src="https://github.com/user-attachments/assets/a828e774-5f5a-4a4e-8af6-fadd2e8276d8" />

## Tela Administrador

<img width="1465" height="844" alt="Captura de Tela (58)" src="https://github.com/user-attachments/assets/a04c461a-b3b7-41a4-81d1-01a454caefe9" />

