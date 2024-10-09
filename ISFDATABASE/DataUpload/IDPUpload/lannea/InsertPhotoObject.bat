ECHO   Cleaning photo Objects fields

sqlplus isfdev2/isfdevusc2@lannea @cleanphoto.sql > log/cleanphoto.log

ECHO INSERTING RECORDS INTO TEMPORARY PHOTOOBJECT COLUMN

call photoobject

ECHO Converting and putting into the main PHOTOObject table...
 sqlplus isfdev2/isfdevusc2@lannea @execPOI.sql > log/execPOI.log

ECHO  Completed PHOTOObject Imports.......

pause

