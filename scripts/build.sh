#!/bin/bash

# =================
# GIGALIKE BUILD SCRIPT WRAPPER
# =================
# This is a wrapper script that calls build-all.sh
# Used for backwards compatibility

set -e

# Get the directory where this script is located
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo "🔧 Calling build-all.sh for complete build process..."

# Call the main build script
"$SCRIPT_DIR/build-all.sh" "$@"
