@echo off
setlocal
if not defined JAVA_HOME echo [INFO] JAVA_HOME is not set; Java must still be on PATH.
java -version
mvn -version
mvn clean test
if errorlevel 1 goto fail
mvn javafx:run
goto end
:fail
echo.
echo Build failed. Check docs\SETUP.md and MySQL configuration.
pause
:end
endlocal
