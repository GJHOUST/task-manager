# 📝 Task Manager API

Jednoduché REST API pro správu úkolů (To-Do list) postavené na Spring Boot.  
Umožňuje vytváření, čtení, aktualizaci a mazání úkolů. Obsahuje Swagger dokumentaci, validaci vstupních dat a podporu DTO.

---

## ⚙️ Použité technologie

- ✅ Java 17
- ✅ Spring Boot 3.4.4
- ✅ Spring Web (REST API)
- ✅ Spring Data JPA + Hibernate
- ✅ H2 Database (in-memory)
- ✅ Jakarta Validation (`@NotBlank`)
- ✅ Lombok
- ✅ Springdoc OpenAPI 3 (Swagger UI)
- ✅ Maven

---

## 🚀 Jak spustit projekt

> **IDE:** Otevři třídu `TaskmanagerApplication` a spusť ji jako Java aplikaci.

> **Terminál (CLI):
```bash
./mvnw spring-boot:run
```

Po spuštění běží API na:  
📍 `http://localhost:8080`

---

## 🔍 Swagger dokumentace

Po startu otevři prohlížeč na:  
👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Získáš přehled všech dostupných endpointů, vstupních/výstupních struktur a možnost testování.

---

## 📀 API Endpointy – konkrétní příklady

### ✅ Získání všech úkolů
```http
GET /api/tasks
```

### 🔎 Filtrování podle dokončení
```http
GET /api/tasks?completed=true
GET /api/tasks?completed=false
```

### 📄 Získání úkolu podle ID
```http
GET /api/tasks/{id}
```

### ➕ Vytvoření nového úkolu
```http
POST /api/tasks
```
**Tělo požadavku (JSON):**
```json
{
  "title": "auto",
  "description": "skoda",
  "completed": false
}
```

### 🔄 Aktualizace úkolu
```http
PUT /api/tasks/{id}
```

### ❌ Smazání úkolu
```http
DELETE /api/tasks/{id}
```

---

## 💾 H2 databáze

Přístup do databázové konzole:  
👉 [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

### Přihlašovací údaje:

- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** _(nech prázdné)_

---
---

## 🔍 html stranka

prohlížeč na:  
👉 [http://localhost:8080/index.html)



---
## 🔧 Testování

Aplikace obsahuje základní unit testy a integrační testy controlleru pomocí `MockMvc`.  
Testy najdeš ve složce `src/test/java`.

- ✅ `TaskControllerTest` – testy REST endpointů (validace, JSON odpovědi)
- ✅ `TaskServiceImplTest` – logika služby (CRUD operace, not found handling)