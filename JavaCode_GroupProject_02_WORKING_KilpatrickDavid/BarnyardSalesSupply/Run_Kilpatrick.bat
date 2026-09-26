@echo off
setlocal
cd /d "%~dp0"
where java >nul 2>nul
if errorlevel 1 (
  echo Java was not found. Install or select your school's JDK and add its bin folder to PATH.
  echo Java 17 or later is recommended. Then run this file again.
  pause
  exit /b 1
)
if not exist "Barnyard_Johnson_Kilpatrick.jar" (
  call Build_Kilpatrick.bat
  if errorlevel 1 exit /b 1
)
java -jar "Barnyard_Johnson_Kilpatrick.jar"
if errorlevel 1 pause
endlocal
