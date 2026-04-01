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

### 🔐 Autenticação
- Login de usuário  
- Validação de credenciais  
- Retorno seguro com DTO (sem exposição de senha)  
- Estrutura preparada para JWT  

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
- Autenticação com JWT  
- Proteção de rotas  
- Controle de acesso por perfil (role-based access)  

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
- **Maven**  
- **MySQL 8**  
- **Spring Data JPA**  
- **Spring Web**  
- **Lombok** (presente no projeto, mas ainda não utilizado)

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

📈 Roadmap
 CRUD Pacientes
 CRUD Médicos
 CRUD Compromissos
 Status e remarcação
 Sistema de usuários
 Login (autenticação básica)
 JWT
 Controle de acesso
 Regras de agenda
 Notificações
 Front-end
