# HealthCareApp-CDA-2025: Quick Setup & Deployment

## Overview
A microservices-based healthcare application for managing patient records and clinical notes with a web interface.

## Prerequisites
- Docker Engine & Docker Compose
- Git
- 4GB RAM minimum

## Preparation

### 1. Clone Repository
```bash
git clone https://github.com/Esteban-Bare/HealthCareApp-CDA-2025.git
cd HealthCareApp-CDA-2025
```

### 2. Configure Environment
Create a `.env` file in project root:
```env
# Required configuration
CONFIG_SERVER_TOKEN=your-config-server-token
MYSQL_ROOT_PASSWORD=secure-password
MYSQL_DATABASE=patientdb
MYSQL_USER=healthcareapp
MYSQL_PASSWORD=secure-password
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/patientdb
SPRING_DATASOURCE_USERNAME=healthcareapp
SPRING_DATASOURCE_PASSWORD=secure-password
SPRING_DATA_MONGODB_URI=mongodb://mongodb:27017/notesdb
```

## Deployment

### 1. Start Application
```bash
cd docker-compose
docker-compose up -d
```

### 2. Verify Deployment
```bash
docker-compose ps
```
All services should show "Up" with "healthy" status.

### 3. Access Application
- Web Interface: http://localhost:8083

## Maintenance Commands

### Service Management
```bash
# Stop services (preserve data)
docker-compose stop

# Remove containers (data lost)
docker-compose down

# View logs
docker-compose logs [service-name]

# Update services
docker-compose pull
docker-compose up -d
```

## Troubleshooting
- Check service logs: `docker-compose logs [failing-service]`
- Verify environment variables in `.env`
- Ensure proper startup sequence (databases → config → discovery → services → web)
