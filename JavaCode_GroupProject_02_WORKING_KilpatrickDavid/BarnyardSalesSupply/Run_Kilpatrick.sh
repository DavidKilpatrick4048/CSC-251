#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
if ! command -v java >/dev/null 2>&1; then
  echo "Java was not found. Install or select your JDK (17 or later recommended)." >&2
  exit 1
fi
if [ ! -f Barnyard_Johnson_Kilpatrick.jar ]; then
  sh Build_Kilpatrick.sh
fi
exec java -jar Barnyard_Johnson_Kilpatrick.jar
