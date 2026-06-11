# Jamipariseva API v2

Spring Boot REST API for the Jamipariseva citizen UI (Tripura land/revenue services).

## Base URL

```
http://localhost:8081/jamipariseva/api
```

## Endpoints

| Method | Path | Purpose |
|--------|------|---------|
| POST | `/location` | Revenue hierarchy (district → village) |
| POST | `/getservices` | Service list for citizen/role |
| POST | `/servicerequest` | Save service request |
| POST | `/ror/verify` | Verify RoR (owner / khatian / plot) |
| POST | `/acknowledgement` | Acknowledgement details |
| POST | `/download` | PDF URL (success requests only) |
| POST | `/request` | Pending/success list or detail |

## Run locally

### 1. Install Java 17 (required)

Download and install **JDK 17** from [Adoptium](https://adoptium.net/) or Oracle.

Verify in a **new** terminal:

```powershell
java -version
```

You should see version 17.x. If `java` is not found, the API cannot start.

### 2. Install PostgreSQL and create database

```sql
CREATE DATABASE jamipariseva;
```

Update `src/main/resources/application-dev.properties` with your DB username/password.

### 3. Start the API

```powershell
cd jamipariseva
.\mvnw.cmd spring-boot:run
```

Uses **dev** profile by default (PostgreSQL + sample data).

**Swagger UI:** http://localhost:8081/jamipariseva/swagger-ui.html

**Run tests:**

```powershell
.\mvnw.cmd test
```

Wait until you see: `Started JamiparisevaApiApplication`.

### 4. Test in browser (health check only)

Open:

```
http://localhost:8081/jamipariseva/api/health
```

You should see JSON with `"status":"UP"`.

**Note:** Most endpoints are **POST** (not GET). The address bar only does GET, so `/api/location` will **not** work in the browser. Use **Postman** or **curl** for POST APIs.

### Troubleshooting “localhost refused to connect”

| Cause | Fix |
|-------|-----|
| Server not started | Run `mvnw spring-boot:run` and wait for “Started …” |
| Java not installed | Install JDK 17, reopen terminal |
| PostgreSQL not running / wrong password | Start Postgres; fix `application.properties` |
| Wrong port | App uses port **8081** (see `server.port`) |

On first run, Hibernate creates tables and `data.sql` seeds sample location, service, and RoR data.

## Sample requests

**Districts**

```json
POST /jamipariseva/api/location
{"lgd_dist_code":"","request_for":"district"}
```

**Subdivisions**

```json
{"lgd_dist_code":"272","request_for":"subdivision"}
```

**Circles**

```json
{"lgd_subdiv_code":"6696","request_for":"circle"}
```

**Tehsils**

```json
{"lgd_circle_code":"56","request_for":"tehsil"}
```

**Villages / mouja**

```json
{"lgd_tehsil_code":"8817","request_for":"village"}
```

**Services**

```json
POST /jamipariseva/api/getservices
{"citizen_id":"2823","role_id":"6"}
```

**RoR verify (khatian)**

```json
POST /jamipariseva/api/ror/verify
{"search_by":"khatian","khatian_no":"11/1","lgd_village_code":"922855"}
```

## Production

Run with prod profile (no seed data):

```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=prod
```

Set real DB URL/credentials via environment or `application-prod.properties`.

## Production notes

- Map `RevenueLocationEntity` to `db1.view_revenue_location_master` (change `@Table` name or use a DB view synonym).
- Disable `spring.sql.init.mode=always` when using real master data.
- Connect `/ror/verify` to MSSQL db2 if RoR data lives there (add second datasource).
- Align response JSON with UI team if they require a different wrapper than `{ success, message, data }`.
