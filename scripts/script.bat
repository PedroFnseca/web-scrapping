@echo off
REM 
javac HtmlAnalyzer.java
REM
IF %ERRORLEVEL% NEQ 0 (
  echo Compilation failed.
  exit /b %ERRORLEVEL%
)
REM
java HtmlAnalyzer LINK_HERE