REM Mr. A. Maganlal
REM Computer Science 2A 2015–2024
REM Updated by N. Vally Omar

@echo off
cls
setlocal enabledelayedexpansion

REM ============================
REM Configuration
REM ============================

REM Path to JDK — change if your system path differs
set JAVA_HOME="C:\jdk-21"
set PATH=%JAVA_HOME%\bin;%PATH%

REM JavaFX setup (set to false unless needed)
set USE_JAVAFX=false
set JAVAFX_HOME="C:\javafx-sdk-21"
set JAVAFX_MODULES=javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media
set JAVAFX_ARGS=
if %USE_JAVAFX%==true (set JAVAFX_ARGS=--module-path %JAVAFX_HOME%\lib --add-modules=%JAVAFX_MODULES%)

REM ============================
REM Project Paths
REM ============================

cd ..
set SRC_DIR=.\src
set BIN_DIR=.\bin
set DOCS_DIR=.\docs
set LIB_DIR=.\lib\*
set JAVADOC_DIR=%DOCS_DIR%\JavaDoc
set DATA_FILE=.\data\house.csv

set ERRMSG=

echo ~~~ Checking Java Version ~~~
javac -version || (set ERRMSG=Error checking Java compiler version && goto ERROR)
java -version || (set ERRMSG=Error checking Java runtime version && goto ERROR)

echo.
echo === Cleaning project ===
if exist %BIN_DIR% del /S /Q %BIN_DIR%\*.class
if exist %JAVADOC_DIR% rmdir /S /Q %JAVADOC_DIR%

echo.
echo === Compiling project ===
if not exist %BIN_DIR% mkdir %BIN_DIR%
javac %JAVAFX_ARGS% -sourcepath %SRC_DIR% -cp %BIN_DIR%;%LIB_DIR% -d %BIN_DIR% %SRC_DIR%\main\Main.java
if %errorlevel% neq 0 (
    set ERRMSG=Error compiling project
    goto ERROR
)

echo.
echo === Running project ===
java %JAVAFX_ARGS% -cp %BIN_DIR%;%LIB_DIR% main.Main %DATA_FILE%
if %errorlevel% neq 0 (
    set ERRMSG=Error running project
    goto ERROR
)

goto END

:ERROR
echo.
echo ❌ %ERRMSG%
pause
goto END

:END
echo.
echo === Build Complete ===
cd %DOCS_DIR%
pause
