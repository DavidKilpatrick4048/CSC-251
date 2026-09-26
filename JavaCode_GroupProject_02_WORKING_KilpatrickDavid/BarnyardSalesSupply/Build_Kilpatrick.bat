@echo off
setlocal
cd /d "%~dp0"
where javac >nul 2>nul
if errorlevel 1 (
  echo A JDK is needed to compile. Install or select JDK 17 or later and add its bin folder to PATH.
  pause
  exit /b 1
)
if not exist build\classes mkdir build\classes
javac --release 8 -encoding UTF-8 -d build\classes src\*.java
if errorlevel 1 (
  echo Compilation failed. See the messages above.
  pause
  exit /b 1
)
jar cfe Barnyard_Johnson_Kilpatrick.jar FarmBusinessGUI_Kilpatrick -C build\classes .
if errorlevel 1 exit /b 1
echo Build complete. Run Run_Kilpatrick.bat to open the app.
endlocal
