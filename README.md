## Run in One

Run everything in one command
```bash
docker-compose -f docker-compose.yaml -f docker-compose.app.yaml up -d
```

## Setup the dev environment
```bash
docker-compose -f docker-compose.yaml up -d

cd ./projects/ui/
npm install
npm run generate
```

Start backend
```bash
./gradlew bootRun
```

Start frontend
```bash
cd ./projects/ui/
npm run start
```

## URLs
 - UI: http://localhost:4200
 - Backend: http://localhost:8080
 - Swagger: http://localhost:8080/swagger-ui.html