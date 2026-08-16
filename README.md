# reference-data-service

Static reference data for the CCB business banking lending platform. Read-only,
in-memory, no database and no `data/` directory — both files are loaded from the
classpath at startup.

Port **8070**. HTTP only.

## Run

```bash
./mvnw spring-boot:run
```

## Test

```bash
./mvnw test
```

## Endpoints

| Method | Path | Returns |
|---|---|---|
| GET | `/reference-data/provinces` | 13 Canadian provinces and territories: `code`, `name`, `countryCode` |
| GET | `/reference-data/exceptions` | 6 credit exceptions: `code`, `name`, `category`, `severity` |

```bash
curl http://localhost:8070/reference-data/provinces
curl http://localhost:8070/reference-data/exceptions
```

Errors use the platform-wide shape:

```json
{
  "timestamp": "2026-08-16T18:16:31.497312Z",
  "status": 404,
  "error": "Not Found",
  "message": "No endpoint at /reference-data/nonsense",
  "path": "/reference-data/nonsense"
}
```

## Changing the data

Edit `src/main/resources/reference/provinces.json` or `exceptions.json` and
restart. There is no write API by design.

## Part of

- [party-service](https://github.com/kumartj/party-service) — :8090
- [application-onboarding](https://github.com/kumartj/application-onboarding) — :8080
