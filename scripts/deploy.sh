#!/bin/bash

# =================
# GIGALIKE MICROSERVICES DEPLOYMENT SCRIPT
# =================

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

print_header() {
    echo -e "${BLUE}================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}================================${NC}"
}

# Check if .env file exists
check_env_file() {
    if [ ! -f ".env" ]; then
        print_warning ".env file not found. Creating from template..."
        cp env.example .env
        print_status ".env file created. Please edit it with your configuration."
        echo
        print_warning "Please configure .env file before proceeding:"
        echo "- Database passwords"
        echo "- JWT secrets"
        echo "- OAuth2 credentials"
        echo "- Payment gateway keys"
        echo "- Email settings"
        echo
        read -p "Press Enter after configuring .env file..."
    fi
}

# Create necessary directories
create_directories() {
    print_status "Creating necessary directories..."
    mkdir -p monitoring/{prometheus,grafana/{dashboards,provisioning},alertmanager,logstash/{config,pipeline},fluent-bit,clair}
    mkdir -p nginx/{conf.d,ssl}
    mkdir -p ssl
    mkdir -p scripts
    mkdir -p logs
}

# Check Docker and Docker Compose
check_docker() {
    if ! command -v docker &> /dev/null; then
        print_error "Docker is not installed"
        exit 1
    fi
    
    if ! command -v docker-compose &> /dev/null; then
        print_error "Docker Compose is not installed"
        exit 1
    fi
    
    print_status "Docker and Docker Compose are available"
}

# Deploy core services
deploy_core() {
    print_header "DEPLOYING CORE SERVICES"
    
    print_status "Creating network..."
    docker network create gigalike-network 2>/dev/null || print_warning "Network gigalike-network already exists"
    
    print_status "Starting core microservices..."
    docker-compose up -d
    
    print_status "Waiting for services to be healthy..."
    sleep 30
    
    # Health checks
    print_status "Checking service health..."
    
    services=("eureka-server:8761" "config-server:8888" "api-gateway:8080" "mysql:3306")
    
    for service in "${services[@]}"; do
        IFS=':' read -r name port <<< "$service"
        if curl -f -s "http://localhost:$port/actuator/health" > /dev/null 2>&1 || curl -f -s "http://localhost:$port" > /dev/null 2>&1; then
            print_status "✅ $name is healthy"
        else
            print_warning "⚠️ $name may not be ready yet"
        fi
    done
}

# Deploy monitoring stack
deploy_monitoring() {
    print_header "DEPLOYING MONITORING STACK"
    
    print_status "Creating monitoring network..."
    docker network create monitoring-network 2>/dev/null || print_warning "Network monitoring-network already exists"
    
    print_status "Starting monitoring services..."
    docker-compose -f docker-compose.monitoring.yml up -d
    
    print_status "Monitoring services started:"
    echo "- Prometheus: http://localhost:${PROMETHEUS_PORT:-9090}"
    echo "- Grafana: http://localhost:${GRAFANA_PORT:-3000} (admin/admin)"
    echo "- Kibana: http://localhost:${KIBANA_PORT:-5601}"
    echo "- AlertManager: http://localhost:${ALERTMANAGER_PORT:-9093}"
}

# Deploy additional services
deploy_services() {
    print_header "DEPLOYING ADDITIONAL SERVICES"
    
    print_status "Starting deployment services..."
    docker-compose -f docker-compose.deploy.yml up -d
    
    print_status "Additional services started:"
    echo "- Swagger UI: http://localhost:${SWAGGER_UI_PORT:-8889}"
    echo "- Uptime Kuma: http://localhost:${UPTIME_KUMA_PORT:-3001}"
    echo "- MinIO Console: http://localhost:${MINIO_CONSOLE_PORT:-9001}"
}

# Show status
show_status() {
    print_header "SERVICE STATUS"
    
    echo "Core Services:"
    docker-compose ps
    
    echo
    echo "Monitoring Services:"
    docker-compose -f docker-compose.monitoring.yml ps 2>/dev/null || echo "Monitoring stack not deployed"
    
    echo
    echo "Additional Services:"
    docker-compose -f docker-compose.deploy.yml ps 2>/dev/null || echo "Additional services not deployed"
    
    echo
    print_header "SERVICE URLS"
    echo "🚪 API Gateway: http://localhost:${API_GATEWAY_PORT:-8080}"
    echo "🔍 Eureka Dashboard: http://localhost:${EUREKA_SERVER_PORT:-8761}"
    echo "⚙️ Config Server: http://localhost:${CONFIG_SERVER_PORT:-8888}"
    echo "📊 Grafana: http://localhost:${GRAFANA_PORT:-3000}"
    echo "📈 Prometheus: http://localhost:${PROMETHEUS_PORT:-9090}"
    echo "🔍 Kibana: http://localhost:${KIBANA_PORT:-5601}"
    echo "💾 MinIO Console: http://localhost:${MINIO_CONSOLE_PORT:-9001}"
    echo "📝 RabbitMQ Management: http://localhost:${RABBITMQ_MANAGEMENT_PORT:-15672}"
    echo "📚 Swagger UI: http://localhost:${SWAGGER_UI_PORT:-8889}"
    echo "❤️ Uptime Kuma: http://localhost:${UPTIME_KUMA_PORT:-3001}"
}

# Stop all services
stop_all() {
    print_header "STOPPING ALL SERVICES"
    
    docker-compose down 2>/dev/null || true
    docker-compose -f docker-compose.monitoring.yml down 2>/dev/null || true
    docker-compose -f docker-compose.deploy.yml down 2>/dev/null || true
    
    print_status "All services stopped"
}

# Remove all data (DANGEROUS)
clean_all() {
    print_header "CLEANING ALL DATA"
    print_warning "This will remove all data including databases, logs, and configurations!"
    read -p "Are you sure? Type 'YES' to continue: " confirm
    
    if [ "$confirm" = "YES" ]; then
        stop_all
        docker-compose down -v
        docker-compose -f docker-compose.monitoring.yml down -v
        docker-compose -f docker-compose.deploy.yml down -v
        docker system prune -f
        print_status "All data cleaned"
    else
        print_status "Operation cancelled"
    fi
}

# Show help
show_help() {
    echo "Gigalike Microservices Deployment Script"
    echo
    echo "Usage: $0 [COMMAND]"
    echo
    echo "Commands:"
    echo "  core        Deploy core microservices (default)"
    echo "  monitoring  Deploy monitoring stack (ELK + Prometheus)"
    echo "  services    Deploy additional services"
    echo "  all         Deploy everything"
    echo "  status      Show service status and URLs"
    echo "  logs        Show logs for all services"
    echo "  stop        Stop all services"
    echo "  clean       Remove all data (DANGEROUS)"
    echo "  help        Show this help message"
    echo
    echo "Examples:"
    echo "  $0                 # Deploy core services"
    echo "  $0 all             # Deploy everything"
    echo "  $0 monitoring      # Deploy monitoring only"
    echo "  $0 status          # Show service status"
}

# Main execution
main() {
    print_header "GIGALIKE MICROSERVICES DEPLOYMENT"
    
    # Load environment variables
    if [ -f ".env" ]; then
        export $(cat .env | grep -v '^#' | xargs)
    fi
    
    case "${1:-core}" in
        "core")
            check_docker
            check_env_file
            create_directories
            deploy_core
            show_status
            ;;
        "monitoring")
            check_docker
            check_env_file
            create_directories
            deploy_monitoring
            ;;
        "services")
            check_docker
            check_env_file  
            create_directories
            deploy_services
            ;;
        "all")
            check_docker
            check_env_file
            create_directories
            deploy_core
            deploy_monitoring
            deploy_services
            show_status
            ;;
        "status")
            show_status
            ;;
        "logs")
            docker-compose logs -f --tail=100
            ;;
        "stop")
            stop_all
            ;;
        "clean")
            clean_all
            ;;
        "help"|"-h"|"--help")
            show_help
            ;;
        *)
            print_error "Unknown command: $1"
            echo
            show_help
            exit 1
            ;;
    esac
}

# Run main function
main "$@"
