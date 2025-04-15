# 📝 Task Manager API

Jednoduché REST API pro správu úkolů (To-Do list) postavené na Spring Boot.  
Umožňuje vytváření, čtení, aktualizaci a mazání úkolů. Součástí je Swagger dokumentace a validace vstupů.

---

## ⚙️ Použité technologie

- ✅ Java 17
- ✅ Spring Boot 3.4.4
- ✅ Spring Web (REST)
- ✅ Spring Data JPA (Hibernate)
- ✅ H2 Database (in-memory)
- ✅ Jakarta Validation (`@NotBlank`)
- ✅ Springdoc OpenAPI 3 (Swagger UI)
- ✅ Lombok
- ✅ Maven

---

## 🚀 Jak spustit projekt

###  run TaskmanagerApplication

---

## 🔍 Swagger dokumentace

Po spuštění aplikace otevři:

👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Zde najdeš přehled všech dostupných endpointů, můžeš si API rovnou vyzkoušet (GET, POST, PUT, DELETE) a vidíš i vstupní/výstupní JSONy.

---

## 🗂️ API Endpointy – konkrétní příklady
✅ Získat všechny úkoly

GET http://localhost:8080/api/tasks

✅ Filtrovat podle dokončenosti

GET http://localhost:8080/api/tasks?completed=true

GET http://localhost:8080/api/tasks?completed=false

✅ Získat úkol podle ID

GET http://localhost:8080/api/tasks/1

✅ Vytvořit nový úkol

POST http://localhost:8080/api/tasks

---

Tělo požadavku (JSON):

{

"title": "auto",

"description": "skoda",

"completed": false

}

---

##
✅ Aktualizovat úkol

PUT http://localhost:8080/api/tasks/1

✅ Smazat úkol

DELETE http://localhost:8080/api/tasks/1

---

## 💾 H2 databáze
Konzole databáze:
http://localhost:8080/h2-console

## Nastavení:

JDBC URL: jdbc:h2:mem:testdb

Username: sa

Password: (nech prázdné)

---


 