# Hospital Management API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-green)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![MySQL](https://img.shields.io/badge/MySQL-8-blue)
![Swagger](https://img.shields.io/badge/Swagger-Documentation-yellow)

## Sobre o projeto
API de gerenciamento hospitalar desenvolvida em **Java 17** com **Spring Boot**, utilizando **MySQL** para persistência de dados.  
O sistema permite gerenciar pacientes, médicos, compromissos e autenticação para diferentes perfis (admin, médico e paciente).

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

### 🗄️ Banco de Dados
- Modelagem relacional com:
  - `pacientes`
  - `medicos`
  - `compromissos`
  - `usuarios`
- Relacionamentos com JPA (ManyToOne)
- Controle de status e última alteração no compromisso  

### 🔐 Usuários (Base criada)
- Estrutura de usuários implementada  
- Suporte a perfis:
  - ADMIN  
  - MEDICO  
  - PACIENTE  

---

## 🚧 Funcionalidades EM DESENVOLVIMENTO

### 🔐 Autenticação
- Login de usuário  
- Validação de credenciais  
- (Próximo passo) JWT para autenticação segura  

### 🛡️ Controle de acesso
- Permissões por perfil:
  - Admin
  - Médico
  - Paciente  

### 📅 Regras de negócio
- Bloqueio de conflitos de horário  
- Validação de agenda médica  
- Limite de agendamentos por período  

### 🔔 Notificações
- Aviso de remarcação de consulta  
- Confirmação de agendamento  
- (Futuro) envio por e-mail ou sistema interno  

### 🌐 Integração com Front-end
- Interface para:
  - pacientes acompanharem consultas  
  - médicos gerenciarem agenda  
  - admin controlar o sistema  

---

## Tecnologias utilizadas
- **Java 17**  
- **Spring Boot 3.x**  
- **Maven**  
- **MySQL 8**  
- **Spring Data JPA**  
- **Swagger/OpenAPI**  

## Estrutura do projeto
```
hospital-management/
├─ src/
│  └─ main/
│     ├─ java/
│     │  └─ hospital/system/
│     │     ├─ controller/
│     │     │  ├─ CompromissoController.java
│     │     │  ├─ MedicoController.java
│     │     │  └─ PacienteController.java
│     │     │  └─ UsuarioController.java
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
│     │     │  ├─ CompromissoService.java
│     │     │  ├─ MedicoService.java
│     │     │  └─ PacienteService.java
│     │     │  └─ UsuarioService.java
│     │     └─ HospitalManagementApplication.java
│     └─ resources/
│        └─ application.properties
└─ pom.xml
```