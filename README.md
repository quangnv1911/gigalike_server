# Gigalike Microservices - Digital Services Shop

A comprehensive microservice architecture for an online shop selling digital services and accounts, built with Spring Boot 3.x and Spring Cloud.

## 🏗️ Architecture Overview

The system consists of the following microservices:

### Infrastructure Services
- **Eureka Server** (Port 8761) - Service Discovery
- **Config Server** (Port 8888) - Centralized Configuration Management
- **API Gateway** (Port 8080) - Request Routing & Authentication

### Business Services
- **Auth Service** (Port 8081) - User Authentication, JWT, OAuth2
- **Product Service** (Port 8082) - Product CRUD Operations
- **Order Service** (Port 8083) - Cart & Order Management
- **Payment Service** (Port 8084) - Payment Processing with MongoDB
- **Marketing Service** (Port 8085) - Marketing Campaigns & Ads
- **Notification Service** (Port 8086) - Email & Push Notifications with RabbitMQ

### Databases
- **MySQL** - Auth, Product, Order services
- **MongoDB** - Payment, Marketing, Notification services
- **RabbitMQ** - Message Queue for notifications

## 🚀 Quick Start

### Prerequisites
- Java 17
- Docker & Docker Compose
- Maven 3.6+

### Running the Application

1. **Clone the repository:**
```bash
git clone <repository-url>
cd gigalike-microservices
```

2. **Build all services:**
```bash
./build-all.sh
```

3. **Start infrastructure & databases:**
```bash
docker-compose up -d mysql mongodb rabbitmq
```

4. **Start infrastructure services:**
```bash
docker-compose up -d eureka-server config-server
```

5. **Wait for services to be healthy, then start business services:**
```bash
docker-compose up -d api-gateway auth-service product-service order-service payment-service marketing-service notification-service
```

6. **Access the services:**
- API Gateway: http://localhost:8080
- Eureka Dashboard: http://localhost:8761 (eureka/password)
- RabbitMQ Management: http://localhost:15672 (guest/guest)

## 🛠️ Technology Stack

- **Framework:** Spring Boot 3.2.0, Spring Cloud 2023.0.0
- **Security:** Spring Security, JWT, OAuth2 (Google)
- **Databases:** MySQL 8.0, MongoDB 7.0
- **Message Queue:** RabbitMQ 3.12
- **Service Discovery:** Netflix Eureka
- **API Gateway:** Spring Cloud Gateway
- **Configuration:** Spring Cloud Config
- **Communication:** OpenFeign
- **Build Tool:** Maven
- **Containerization:** Docker

## 📊 API Endpoints

### Auth Service (/api/auth)
- `POST /register` - User registration
- `POST /login` - User login
- `POST /refresh` - Refresh token
- `POST /logout` - User logout
- `GET /profile` - Get user profile

### Product Service (/api/products)
- `GET /` - List products with filters
- `GET /public` - Public product listing
- `POST /` - Create product (authenticated)
- `PUT /{id}` - Update product
- `DELETE /{id}` - Delete product
- `GET /category/{category}` - Products by category

### Order Service (/api/orders)
- `GET /cart` - Get user cart
- `POST /cart/items` - Add item to cart
- `PUT /cart/items/{id}` - Update cart item
- `DELETE /cart/items/{id}` - Remove cart item
- `POST /` - Create order from cart
- `GET /` - List user orders
- `GET /{id}` - Get order details

### Payment Service (/api/payments)
- `POST /process` - Process payment
- `GET /history` - Payment history
- `GET /{id}` - Payment details
- `POST /webhook` - Payment webhook

## 🔐 Authentication & Authorization

The system uses JWT-based authentication with the following flow:

1. User authenticates via `/api/auth/login` or Google OAuth2
2. System returns access token (1 hour) and refresh token (7 days)
3. API Gateway validates tokens for protected endpoints
4. User information is passed to services via headers (`X-User-Id`, `X-User-Role`)

### Role-based Access:
- **USER:** Can browse products, manage cart, place orders
- **ADMIN:** Full access to all resources
- **MODERATOR:** Can manage products and orders

## 🗂️ Database Schema

### MySQL (Auth Service)
- `users` - User information
- `refresh_tokens` - JWT refresh tokens

### MySQL (Product Service)
- `products` - Product catalog
- `product_images` - Product images
- `product_features` - Product features

### MySQL (Order Service)
- `carts` - Shopping carts
- `cart_items` - Cart items
- `orders` - Order information
- `order_items` - Order line items

### MongoDB Collections
- **Payment Service:** `payments`, `transactions`, `payment_methods`
- **Marketing Service:** `campaigns`, `advertisements`, `analytics`
- **Notification Service:** `notifications`, `templates`, `delivery_logs`

## 🔧 Configuration

All services use Spring Cloud Config Server for centralized configuration. Configuration files are stored in `/config-server/src/main/resources/config-repo/`.

### Environment Variables

Key environment variables for Docker deployment:

```env
# Database
MYSQL_ROOT_PASSWORD=password
MONGODB_ROOT_PASSWORD=password

# JWT
JWT_SECRET=mySecretKey

# OAuth2
GOOGLE_CLIENT_ID=your-google-client-id
GOOGLE_CLIENT_SECRET=your-google-client-secret

# Email
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password

# Payment Gateways
STRIPE_SECRET_KEY=sk_test_...
PAYPAL_CLIENT_ID=your-paypal-client-id
```

## 🧪 Testing

Run tests for all services:
```bash
./mvnw test
```

## 📝 API Documentation

Once the services are running, API documentation is available at:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI Spec: http://localhost:8080/v3/api-docs

## 🚀 Deployment

### Development
```bash
docker-compose up -d
```

### Production
For production deployment, consider:
- Using external databases (AWS RDS, MongoDB Atlas)
- Implementing proper secrets management
- Setting up monitoring (Prometheus, Grafana)
- Configuring load balancers
- Setting up CI/CD pipelines

## 📊 Monitoring & Health Checks

All services include:
- Spring Boot Actuator endpoints
- Health checks in Docker containers
- Centralized logging
- Metrics collection ready for Prometheus

Access health endpoints:
- http://localhost:8080/actuator/health (API Gateway)
- http://localhost:8081/actuator/health (Auth Service)
- etc.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the documentation wiki

---

**Built with ❤️ using Spring Boot & Spring Cloud**
