# 🏥 Hospital Management API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-green)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![MySQL](https://img.shields.io/badge/MySQL-8-blue)

---

## 📌 Sobre o projeto
API REST de gerenciamento hospitalar desenvolvida com **Java 17 + Spring Boot**, focada em boas práticas de backend e arquitetura limpa.

O sistema permite gerenciar pacientes, médicos e compromissos, além de possuir **autenticação de usuários com diferentes perfis (ADMIN, MÉDICO e PACIENTE)**.

---

## ✅ Funcionalidades IMPLEMENTADAS

### 👤 Pacientes
- Cadastro de pacientes  
- Listagem de pacientes  
- Persistência no banco MySQL  

### 🩺 Médicos
- Cadastro de médicos  
- Listagem de médicos  
- Associação com compromissos  

### 📅 Compromissos (Agendamentos)
- Criação de compromissos  
- Listagem geral  
- Busca por:
  - médico + data  
  - paciente  
- Remarcação de consulta (data e hora)  
- Atualização de status:
  - AGENDADO  
  - CONFIRMADO  
  - CANCELADO  

### 🔐 Autenticação e Segurança
- Login de usuário  
- Validação de credenciais  
- Geração de token JWT  
- Validação de token nas requisições  
- Proteção de rotas com Spring Security  
- Filtro de autenticação (JWT Filter)  
- Retorno seguro (sem exposição de senha)  

### 🗄️ Banco de Dados
- Modelagem relacional com:
  - `pacientes`
  - `medicos`
  - `compromissos`
  - `usuarios`
- Relacionamentos com JPA (ManyToOne)
- Controle de status e última alteração no compromisso  

### 👥 Usuários
- Sistema de usuários implementado  
- Perfis suportados:
  - ADMIN  
  - MEDICO  
  - PACIENTE  

---

## 🚧 Funcionalidades EM DESENVOLVIMENTO

### 🔐 Segurança
- Criptografia de senha com BCrypt  
- Controle de acesso por perfil (role-based access)  
- Refresh Token  

### 👤 Usuários
- Vínculo entre usuário e paciente  
- Endpoint para usuário logado (`/me`)  

### 📅 Regras de negócio
- Bloqueio de conflitos de horário  
- Validação de disponibilidade do médico  
- Restrição de horários inválidos  

### 🔔 Notificações
- Aviso de remarcação de consulta  
- Confirmação de agendamento  
- (Futuro) envio por e-mail ou sistema interno  

### 🌐 Front-end
- Interface para pacientes acompanharem consultas  
- Painel para médicos gerenciarem agenda  
- Painel administrativo  

---

## Tecnologias utilizadas
- **Java 17**  
- **Spring Boot 3.x**  
- **Spring Security**  
- **JWT (JSON Web Token)**  
- **Maven**  
- **MySQL 8**  
- **Spring Data JPA**  
- **Spring Web**  
- **Lombok**  

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


---

## 🗺️ Roadmap do Projeto

### ✅ Fase 1 — Base do Sistema (Concluído)

* [x] Estrutura inicial com Spring Boot
* [x] Configuração do banco de dados MySQL
* [x] Modelagem das entidades
* [x] Relacionamentos com JPA
* [x] CRUD completo
* [x] Sistema de agendamentos

---

### 🔐 Fase 2 — Autenticação (Concluído)

* [x] Estrutura de usuários
* [x] Endpoint de login
* [x] Separação por perfis
* [x] Geração de token JWT
* [x] Validação de token
* [x] Proteção de rotas com Spring Security

---

### 🛡️ Fase 3 — Autorização e Segurança (Em andamento)

* [ ] Criptografia de senha (BCrypt)
* [ ] Controle de acesso por perfil:
  * Admin → acesso total
  * Médico → gerenciar agenda
  * Paciente → visualizar consultas
* [ ] Proteção avançada de endpoints
* [ ] Tratamento de erros de autenticação

---

### 📅 Fase 4 — Regras de Negócio

* [ ] Bloqueio de conflitos de horário
* [ ] Validação de agenda médica
* [ ] Limite de consultas por período
* [ ] Regras para cancelamento/remarcação
* [ ] Validação de dados (DTO + Bean Validation)

---

### 🔔 Fase 5 — Notificações

* [ ] Notificações de eventos de consulta
* [ ] Integração futura com e-mail

---

### 🌐 Fase 6 — Integração com Front-end

* [ ] Interface web
* [ ] Login e autenticação
* [ ] Dashboard por perfil
* [ ] Consumo da API

