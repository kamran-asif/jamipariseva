
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

### 1. Revenue Location Hierarchy (`POST /location`)

**Districts**
```json
POST /jamipariseva/api/location
{"lgd_dist_code":"","request_for":"district"}
```

**Subdivisions**
```json
POST /jamipariseva/api/location
{"lgd_dist_code":"272","request_for":"subdivision"}
```

**Circles**
```json
POST /jamipariseva/api/location
{"lgd_subdiv_code":"6696","request_for":"circle"}
```

**Tehsils**
```json
POST /jamipariseva/api/location
{"lgd_circle_code":"56","request_for":"tehsil"}
```

**Villages / Moujas**
```json
POST /jamipariseva/api/location
{"lgd_tehsil_code":"8817","request_for":"village"}
```

---

### 2. Available Services (`POST /getservices`)

```json
POST /jamipariseva/api/getservices
{"citizen_id":"2823","role_id":"6"}
```

---

### 3. Record of Rights Verification (`POST /ror/verify`)

**Verify by Khatian Number**
```json
POST /jamipariseva/api/ror/verify
{"search_by":"khatian","khatian_no":"11/1","lgd_village_code":"922855"}
```

**Verify by Owner Name**
```json
POST /jamipariseva/api/ror/verify
{"search_by":"owner_name","owner_name":"Narendra Chandra Pal","lgd_village_code":"922855"}
```

**Verify by Plot Number**
```json
POST /jamipariseva/api/ror/verify
{"search_by":"plot","plot_no":"121","lgd_village_code":"922855"}
```

---

### 4. Save Service Request (`POST /servicerequest`)

```json
POST /jamipariseva/api/servicerequest
{
  "citizen_id": "2823",
  "role_id": "6",
  "service_id": "12",
  "payment_multipy_factor": [
    {
      "count": 1
    }
  ],
  "rorinfo": [
    {
      "search_value": "11/1",
      "khatian_no": "11/1",
      "mouja_val": "922855",
      "search_type": "1",
      "is_khatian_required": "1"
    }
  ],
  "applicantinfo": {
    "name": "Narendra Chandra Pal"
  }
}
```

---

### 5. Request Status & Detail (`POST /request`)

**Query List of Successful Requests**
```json
POST /jamipariseva/api/request
{
  "citizen_id": "2823",
  "role_id": "6",
  "request_for": "success"
}
```

**Query Single Request Detail**
```json
POST /jamipariseva/api/request
{
  "citizen_id": "2823",
  "role_id": "6",
  "request_for": "success",
  "request_id": 850946
}
```

---

### 6. Acknowledgement Details (`POST /acknowledgement`)

```json
POST /jamipariseva/api/acknowledgement
{
  "citizen_id": "2823",
  "role_id": "6",
  "request_id": 850946
}
```

---

### 7. PDF Download URL (`POST /download`)

```json
POST /jamipariseva/api/download
{
  "service_id": "10",
  "citizen_id": "2823",
  "role_id": "6",
  "request_id": 850946
}
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
