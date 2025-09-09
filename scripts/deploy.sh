#!/bin/bash

# Deployment Script for Gigalike Microservices
# Usage: ./scripts/deploy.sh [staging|production]

set -e

ENVIRONMENT=${1:-staging}
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

echo "🚀 Deploying to $ENVIRONMENT environment..."

# Load environment-specific configuration
if [ "$ENVIRONMENT" = "production" ]; then
    echo "🔒 Production deployment requires manual approval"
    read -p "Are you sure you want to deploy to PRODUCTION? (yes/no): " confirm
    if [ "$confirm" != "yes" ]; then
        echo "❌ Deployment cancelled"
        exit 1
    fi
fi

# Set environment variables
case $ENVIRONMENT in
    "staging")
        COMPOSE_FILE="docker-compose.staging.yml"
        ENV_FILE=".env.staging"
        ;;
    "production")
        COMPOSE_FILE="docker-compose.prod.yml"
        ENV_FILE=".env.production"
        ;;
    *)
        echo "❌ Invalid environment: $ENVIRONMENT"
        echo "Usage: $0 [staging|production]"
        exit 1
        ;;
esac

cd "$PROJECT_DIR"

# Check if required files exist
if [ ! -f "$COMPOSE_FILE" ]; then
    echo "❌ Compose file not found: $COMPOSE_FILE"
    exit 1
fi

if [ ! -f "$ENV_FILE" ]; then
    echo "❌ Environment file not found: $ENV_FILE"
    exit 1
fi

# Build and deploy
echo "📦 Building services..."
./build-all.sh

echo "🐳 Pulling latest images..."
docker-compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" pull

echo "🚀 Starting deployment..."
docker-compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" up -d

echo "⏳ Waiting for services to start..."
sleep 60

echo "🔍 Performing health checks..."
if curl -f http://localhost:8080/actuator/health; then
    echo "✅ Deployment successful!"
else
    echo "❌ Health check failed!"
    echo "📋 Service logs:"
    docker-compose -f "$COMPOSE_FILE" --env-file "$ENV_FILE" logs --tail=20
    exit 1
fi

echo "🎉 Deployment to $ENVIRONMENT completed successfully!"
