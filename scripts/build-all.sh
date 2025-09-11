#!/bin/bash

# =================
# GIGALIKE MICROSERVICES BUILD SCRIPT
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

#!/bin/bash

set -e

# Function to build a service
build_service() {
    local service=$1
    SERVICE_DIR="$PROJECT_ROOT/$service"

    if [ -d "$SERVICE_DIR" ]; then
        echo "--------------------------------"
        echo "Building $service ..."
        echo "--------------------------------"
        cd "$SERVICE_DIR" || exit 1
        mvn clean install -DskipTests
        cd - > /dev/null
    else
        echo "[WARNING] Directory $service not found, skipping..."
    fi
}

# Main execution
main() {
    echo "================================"
    echo "Starting Gigalike Microservices Build Process"
    echo "================================"

    # Lấy thư mục gốc của project
    PROJECT_ROOT="$( cd "$( dirname "${BASH_SOURCE[0]}" )/.." && pwd )"

    # Danh sách services
    SERVICES=("api-gateway" "auth-service" "config-server" "eureka-server" \
              "marketing-service" "order-service" "payment-service" \
              "platform-service" "product-service")

    echo "[INFO] Maven version: $(mvn -v | head -n 1)"
    echo "[INFO] Java version: $(java -version 2>&1 | head -n 1)"

    # Build utility-shared trước
    build_service "utility-shared"

    # Build các service còn lại song song
    pids=()
    for service in "${SERVICES[@]}"; do
        ( build_service "$service" ) &
        pids+=($!)
    done

    # Chờ tất cả job hoàn thành
    for pid in "${pids[@]}"; do
        wait $pid || {
            echo "[ERROR] One of the builds failed"
            exit 1
        }
    done

    echo "================================"
    echo "Build Process Completed Successfully"
    echo "================================"
}

main "$@"
