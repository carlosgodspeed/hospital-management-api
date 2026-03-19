# Hospital Management API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-green)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![MySQL](https://img.shields.io/badge/MySQL-8-blue)
![Swagger](https://img.shields.io/badge/Swagger-Documentation-yellow)

## Sobre o projeto
API de gerenciamento hospitalar desenvolvida em **Java 17** com **Spring Boot**, utilizando **MySQL** para persistência de dados.  
O sistema permite gerenciar pacientes, médicos, compromissos e autenticação para diferentes perfis (admin, médico e paciente).

## Funcionalidades
- Cadastro de pacientes e médicos  
- Consulta e agendamento de compromissos  
- Autenticação por perfis (admin, médico e paciente)  
- Listagem e pesquisa de pacientes e médicos  
- Documentação de API com Swagger  

## Tecnologias utilizadas
- **Java 17**  
- **Spring Boot 3.x**  
- **Maven**  
- **MySQL 8**  
- **Spring Data JPA**  
- **Lombok** (opcional, para reduzir boilerplate)  
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
│     │     ├─ model/
│     │     │  ├─ Compromisso.java
│     │     │  ├─ Medico.java
│     │     │  └─ Paciente.java
│     │     ├─ repository/
│     │     │  ├─ CompromissoRepository.java
│     │     │  ├─ MedicoRepository.java
│     │     │  └─ PacienteRepository.java
│     │     ├─ service/
│     │     │  ├─ CompromissoService.java
│     │     │  ├─ MedicoService.java
│     │     │  └─ PacienteService.java
│     │     └─ HospitalManagementApplication.java
│     └─ resources/
│        └─ application.properties
└─ pom.xml
```