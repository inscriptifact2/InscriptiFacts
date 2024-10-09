@echo off

ECHO Creating ISFDEV2 Schema

sqlplus system/manager@isfdb  @dbcompile.sql > dbcompile.log


Echo ISFDEV2 Schema and ISFDEV2 Objects were Created

