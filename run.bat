@echo off
echo Compiling Java Hotel Management System...
if not exist bin mkdir bin
javac -cp ".;lib/*" -d bin src/db/*.java src/models/*.java src/dao/*.java src/ui/*.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Compilation Failed! Please check for syntax errors.
    pause
    exit /b %ERRORLEVEL%
)

echo Compilation Successful!
echo.
echo Launching Hotel Management System...
java -cp "bin;lib/*" ui.MainFrame

pause
