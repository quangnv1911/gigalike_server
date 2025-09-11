# 🚀 Gigalike Microservices Platform

A complete, production-ready microservices architecture for e-commerce platform built with Spring Boot, Docker, and comprehensive monitoring stack.

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Frontend      │────│   Nginx Proxy   │────│   API Gateway   │
│                 │    │   SSL/Load Bal  │    │   (Port 8080)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                       │
                              ┌─────────────────────────┼─────────────────────────┐
                              │                         │                         │
                    ┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
                    │ Config Server   │    │ Eureka Server   │    │   Services      │
                    │ (Port 8888)     │    │ (Port 8761)     │    │   Cluster       │
                    └─────────────────┘    └─────────────────┘    └─────────────────┘
                              │
                    ┌─────────────────┐
                    │   .env File     │ ←── Single source of config
                    │  Config Source  │
                    └─────────────────┘
```

## 🎯 Key Features

- ✅ **Centralized Configuration** - All services configured via `.env` file
- ✅ **Complete Monitoring** - ELK Stack + Prometheus + Grafana
- ✅ **Object Storage** - MinIO for file storage
- ✅ **Security Scanning** - Clair for vulnerability scanning
- ✅ **Health Monitoring** - Uptime Kuma for service health
- ✅ **Auto Backup** - Automated database backups to S3
- ✅ **Log Aggregation** - Fluent Bit for centralized logging
- ✅ **API Documentation** - Swagger UI auto-generated
- ✅ **SSL/TLS Ready** - Nginx reverse proxy with SSL
- ✅ **Production Ready** - Blue-green deployment support

## 📦 Services Portfolio

### Core Services
| Service | Port | Database | Description |
|---------|------|----------|-------------|
| 🚪 API Gateway | 8080 | - | Entry point & routing |
| 🔍 Eureka Server | 8761 | - | Service discovery |
| ⚙️ Config Server | 8888 | - | Configuration management |
| 🔐 Auth Service | 8081 | MySQL | Authentication & JWT |
| 📦 Product Service | 8082 | MySQL | Product catalog |
| 🛒 Order Service | 8083 | MySQL | Order processing |
| 💳 Payment Service | 8084 | MongoDB | Payment gateway |
| 🌐 Platform Service | 8086 | MongoDB + Redis | Notifications & OTP |

### Infrastructure
| Service | Port | Purpose |
|---------|------|---------|
| 🗄️ MySQL | 3306 | Primary database |
| 🍃 MongoDB | 27017 | Document storage |
| 🔴 Redis | 6379 | Caching & sessions |
| 🐰 RabbitMQ | 5672/15672 | Message queue |
| 💾 MinIO | 9000/9001 | Object storage |

### Monitoring Stack
| Service | Port | Purpose |
|---------|------|---------|
| 📊 Grafana | 3000 | Metrics dashboard |
| 📈 Prometheus | 9090 | Metrics collection |
| 🔍 Kibana | 5601 | Log visualization |
| ⚠️ AlertManager | 9093 | Alert management |
| 📝 Swagger UI | 8889 | API documentation |
| ❤️ Uptime Kuma | 3001 | Service health monitoring |

## 🚀 Quick Start

### 1. Setup Environment
```bash
# Copy environment template
cp .env.local.example .env

# Edit configuration (REQUIRED)
nano .env  # or your favorite editor
```

### 2. Deploy Services
```bash
# Deploy everything
bash deploy.sh all

# Or deploy specific stacks
bash deploy.sh core        # Core microservices only
bash deploy.sh monitoring  # Monitoring stack only
bash deploy.sh services    # Additional services only
```

### 3. Check Status
```bash
bash deploy.sh status
```

## 🔧 Configuration

### Environment Variables Categories

**🔑 Security & Authentication**
- JWT secrets and expiration
- OAuth2 credentials (Google)
- Database passwords

**🗄️ Databases**  
- MySQL connection settings
- MongoDB credentials
- Redis configuration

**💳 Payment Integration**
- Stripe keys (test/live)
- PayPal credentials
- Vietnamese payment methods

**📧 Communications**
- SMTP email settings
- Telegram bot configuration
- Notification preferences

**🎯 Service URLs**
- Internal service communication
- CORS settings
- Frontend URLs

**📊 Monitoring**
- Grafana credentials
- Prometheus retention
- Log aggregation settings

## 📊 Monitoring & Observability

### Dashboards Access
- **Grafana**: http://localhost:3000 (admin/admin)
  - Service metrics, JVM stats, database performance
- **Kibana**: http://localhost:5601
  - Centralized logs, error tracking, audit trails
- **Prometheus**: http://localhost:9090
  - Raw metrics, query interface, alert rules

### Health Checks
```bash
# API Gateway health
curl http://localhost:8080/actuator/health

# Individual service health
curl http://localhost:8081/api/health  # Auth
curl http://localhost:8082/api/health  # Product
curl http://localhost:8083/api/health  # Order
curl http://localhost:8084/api/health  # Payment
curl http://localhost:8086/api/health  # Platform
```

## 🔒 Security Features

- **JWT Authentication** with refresh token rotation
- **OAuth2 Integration** (Google Sign-In)
- **API Rate Limiting** via API Gateway
- **Container Security Scanning** with Clair
- **SSL/TLS Termination** at Nginx layer
- **Network Isolation** via Docker networks
- **Secret Management** via environment variables

## 💾 Backup & Recovery

```bash
# Manual backup
docker exec gigalike-backup /backup.sh

# Automated backups run daily at 2 AM
# Backups stored in S3 bucket (configure in .env)
```

## 🚦 Production Deployment

### Prerequisites
- Docker & Docker Compose
- Valid SSL certificates
- S3 bucket for backups
- External database (recommended)
- Load balancer (for multi-instance)

### Production Checklist
- [ ] Update all default passwords
- [ ] Configure proper JWT secrets (32+ chars)
- [ ] Set up SSL certificates
- [ ] Configure external databases
- [ ] Set up S3 backup bucket
- [ ] Configure monitoring alerts
- [ ] Test disaster recovery procedures

## 🛠️ Development

### Local Development
```bash
# Start infrastructure only
docker-compose up -d mysql mongodb redis rabbitmq eureka-server config-server

# Run services in your IDE for debugging
# Services will automatically register with Eureka
```

### API Testing
```bash
# Register new user
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","email":"test@example.com","password":"password123","firstName":"Test","lastName":"User"}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"password123"}'
```

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

- 📧 Email: support@gigalike.com
- 📖 Wiki: [Project Wiki](https://github.com/gigalike/microservices/wiki)
- 🐛 Issues: [GitHub Issues](https://github.com/gigalike/microservices/issues)
- 💬 Discord: [Community Server](https://discord.gg/gigalike)

---

Made with ❤️ by the Gigalike Team