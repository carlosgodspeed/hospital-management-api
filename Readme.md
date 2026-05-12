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

### 🛡️ Fase 3 — Autorização e Segurança (Concluido)

* [x] Controle de acesso por perfil:
  * Admin → acesso total
  * Médico → gerenciar agenda
  * Paciente → visualizar consultas
* [x] Proteção avançada de endpoints
* [x] Tratamento de erros de autenticação

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

