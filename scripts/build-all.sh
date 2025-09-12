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
    print_header "Starting Gigalike Microservices Build Process"

    # Get project root directory
    PROJECT_ROOT="$( cd "$( dirname "${BASH_SOURCE[0]}" )/.." && pwd )"
    
    print_status "Project root: $PROJECT_ROOT"
    print_status "Maven version: $(mvn -v | head -n 1)"
    print_status "Java version: $(java -version 2>&1 | head -n 1)"

    cd "$PROJECT_ROOT" || exit 1

    # Option 1: Build entire project from root (RECOMMENDED)
    print_status "Building entire project from root (includes parent POM)..."
    mvn clean install -DskipTests

    if [ $? -eq 0 ]; then
        print_status "✅ Build completed successfully using root POM approach"
        return 0
    else
        print_warning "Root build failed, falling back to individual module builds..."
    fi

    # Option 2: Fallback - Manual dependency order (if root build fails)
    print_status "Building modules in dependency order..."
    
    # Step 1: Install parent POM first
    print_status "Installing parent POM..."
    mvn clean install -N -DskipTests
    
    # Step 2: Build utility-shared (dependency for other services)
    print_status "Building utility-shared (required dependency)..."
    build_service "utility-shared"

    # Step 3: Build services in parallel (now that dependencies are ready)
    SERVICES=("api-gateway" "auth-service" "config-server" "eureka-server" \
              "marketing-service" "order-service" "payment-service" \
              "platform-service" "product-service")

    print_status "Building remaining services in parallel..."
    pids=()
    for service in "${SERVICES[@]}"; do
        ( build_service "$service" ) &
        pids+=($!)
    done

    # Wait for all parallel jobs to complete
    failed=0
    for pid in "${pids[@]}"; do
        wait $pid || {
            print_error "One of the service builds failed (PID: $pid)"
            failed=1
        }
    done

    if [ $failed -eq 1 ]; then
        print_error "Build process failed!"
        exit 1
    fi

    print_header "✅ Build Process Completed Successfully"
}

main "$@"
