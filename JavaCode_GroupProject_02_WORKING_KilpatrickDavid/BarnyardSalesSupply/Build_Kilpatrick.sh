#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
mkdir -p build/classes
if command -v javac >/dev/null 2>&1; then
  javac --release 8 -encoding UTF-8 -d build/classes src/*.java
else
  java com.sun.tools.javac.Main --release 8 -encoding UTF-8 -d build/classes src/*.java
fi
if command -v jar >/dev/null 2>&1; then
  jar cfe Barnyard_Johnson_Kilpatrick.jar FarmBusinessGUI_Kilpatrick -C build/classes .
else
  java -m jdk.jartool/sun.tools.jar.Main cfe Barnyard_Johnson_Kilpatrick.jar FarmBusinessGUI_Kilpatrick -C build/classes .
fi
echo "Build complete. Run sh Run_Kilpatrick.sh to open the app."
