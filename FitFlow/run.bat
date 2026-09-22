@echo off
setlocal enabledelayedexpansion

echo ===============================================================
echo       FITFLOW: ADVANCED OOP FITNESS & WELLNESS PLATFORM
echo ===============================================================
echo.

cd /d "%~dp0"

:: Check Java compiler
where javac >nul 2>nul
if %errorlevel% neq 0 (
    echo [ERROR] 'javac' was not found in your PATH!
    echo Please ensure Java 21 JDK is installed and configured in your Environment Variables.
    pause
    exit /b 1
)

echo [1/3] Compiling Java backend classes...
if not exist "bin" mkdir "bin"

javac -d bin src\model\enums\*.java src\model\*.java src\exception\*.java src\calculator\*.java src\recommendation\*.java src\util\*.java src\service\*.java src\controller\*.java src\Main.java

if %errorlevel% neq 0 (
    echo [ERROR] Compilation failed! Check error messages above.
    pause
    exit /b 1
)

echo [2/3] Backend compilation successful!
echo.
echo [3/3] Launching FitFlow Embedded Server on port 8080...
echo.

:: Automatically open default web browser after 2 seconds in the background
start "" cmd /c "timeout /t 2 /nobreak >nul & start http://localhost:8080/"

:: Run the Java Server with optimized lightweight memory flags
java -Xms16m -Xmx64m -cp bin Main 8080

pause
