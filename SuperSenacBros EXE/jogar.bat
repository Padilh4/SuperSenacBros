@echo off
title Executando SuperSenacBros
echo Iniciando o jogo...
java -jar SuperSenacBros.jar
if %errorlevel% neq 0 (
    echo.
    echo Ocorreu um erro ao rodar o jogo!
    pause
)