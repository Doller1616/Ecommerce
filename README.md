# 🚀 E-Commerce Application - Docker Setup Guide

This project demonstrates how to run an e-commerce application along with PostgreSQL and PGAdmin using Docker.
It also supports **automatic Docker image creation using Spring Boot (no manual Dockerfile required)**.

---

# 🐳 Prerequisites

Make sure you have the following installed:

* Java 17+
* Maven
* Docker
* Docker Compose (optional but recommended)

---

# ⚡ Build Docker Image (No Dockerfile Required)

If you're using **Spring Boot**, you don’t need to manually create a Dockerfile.
The **spring-boot-maven-plugin** can generate a Docker image automatically.

---

## ✅ Add Plugin in `pom.xml`

```xml id="p1xml"
<build>
  <plugins>
    <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
      <configuration>
        <image>
          <name>abhishek1600/ecom-application</name>
        </image>
      </configuration>
    </plugin>
  </plugins>
</build>
```

---

## ▶️ Build Docker Image

Mac
```bash id="p2cmd"
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=<IMAGE-NAME>

./mvnw spring-boot:build-image "-Dspring-boot.build-image.imageName=USERNAME/jobappimage"
```

This will create a Docker image automatically using **Cloud Native Buildpacks**.

---

## ▶️ Run Application Container

```bash id="p3cmd"
docker run -d -p 8080:8080 abhishek1600/ecom-application
```

Access the application at:

```id="p4url"
http://localhost:8080
```

---

# 🗄️ PostgreSQL Setup

```bash id="p5cmd"
docker run -d \
  --name db \
  -e POSTGRES_PASSWORD=mysecretpassword \
  postgres:14
```

---

# 🖥️ PGAdmin Setup

```bash id="p6cmd"
docker run -d \
  --name pgadmin \
  -e PGADMIN_DEFAULT_EMAIL=user@domain.com \
  -e PGADMIN_DEFAULT_PASSWORD=SuperSecret \
  -p 5050:80 \
  dpage/pgadmin4
```

Access PGAdmin at:

```id="p7url"
http://localhost:5050
```

---

# 🔗 Enable Container Communication

By default, Docker containers cannot communicate with each other.
To enable communication, create a custom Docker network.

---

## ✅ Step 1: Create Network

```bash id="p8cmd"
docker network create postgres-network
```

---

## ✅ Step 2: Run PostgreSQL on Network

```bash id="p9cmd"
docker run -d \
  --name db \
  --network postgres-network \
  -e POSTGRES_PASSWORD=mysecretpassword \
  postgres:14
```

---

## ✅ Step 3: Run PGAdmin on Network

```bash id="p10cmd"
docker run -d \
  --name pgadmin \
  --network postgres-network \
  -e PGADMIN_DEFAULT_EMAIL=user@domain.com \
  -e PGADMIN_DEFAULT_PASSWORD=SuperSecret \
  -p 5050:80 \
  dpage/pgadmin4
```

---

## 🧪 Test Connectivity

```bash id="p11cmd"
docker exec -it pgadmin ping db
```

---

# ⚙️ PGAdmin Configuration

1. Open PGAdmin in browser
2. Login using:

    * Email: `user@domain.com`
    * Password: `SuperSecret`
3. Add a new server:

    * Hostname: `db`
    * Port: `5432`
    * Username: `root`
    * Password: `root`

---

# 🧹 Cleanup

```bash id="p12cmd"
docker rm -f db pgadmin
```

---

# ⭐ Recommended: Use Docker Compose

Managing multiple containers is easier with Docker Compose.

---

## 📄 docker-compose.yml

```yaml id="p13yaml"
services:
  postgres:
    container_name: postgres_container
    image: postgres:14
    environment:
      POSTGRES_USER: root
      POSTGRES_PASSWORD: root
      PGDATA: /data/postgres
    volumes:
      - postgres:/data/postgres
    ports:
      - "5432:5432"
    networks:
      - postgres-network
    restart: unless-stopped
  pgadmin:
    container_name: pgadmin_container
    image: dpage/pgadmin4
    environment:
      PGADMIN_DEFAULT_EMAIL: ${PGADMIN_DEFAULT_EMAIL:-pgadmin4@pgadmin.org}
      PGADMIN_DEFAULT_PASSWORD: ${PGADMIN_DEFAULT_PASSWORD:-admin}
      PGADMIN_CONFIG_SERVER_MODE: 'False'
    volumes:
      - pgadmin:/var/lob/pgadmin
    ports:
      - "5050:80"
    networks:
      - postgres-network
    restart: unless-stopped

networks:
  postgres-network:
    driver: bridge

volumes:
  postgres:
  pgadmin:
```

---

## ▶️ Run All Services

```bash id="p14cmd"
docker-compose up -d
```

---

## 🛑 Stop All Services

```bash id="p15cmd"
docker-compose down
```

---

# 📌 Notes

* No need to manually write a Dockerfile when using Spring Boot Buildpacks
* Use container name `db` as hostname inside the network
* Use volumes for persistent database storage
* Avoid hardcoding credentials in production
* Prefer Docker Compose for managing multi-container apps

---

# 🎯 Summary

* Build image using Maven plugin (no Dockerfile)
* Run app on port **8080**
* PGAdmin runs on port **5050**
* PostgreSQL runs internally
* Use Docker network for communication
* Use Docker Compose for simplicity

---

If you want:

* Production-ready Docker setup
* Kubernetes deployment
* CI/CD pipeline integration

Just ask 👍
