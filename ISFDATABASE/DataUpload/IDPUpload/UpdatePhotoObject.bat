ECHO   Cleaning photo Objects fields

sqlplus ISFDEV2/ISFDEVUSC2@isfdb @cleanphoto.sql > cleanphoto.log

ECHO INSERTING RECORDS INTO TEMPORARY PHOTOOBJECT COLUMN

call photoobject

ECHO Converting and putting into the main PHOTOObject table...

sqlplus ISFDEV2/ISFDEVUSC2@isfdb @execpOI.sql > execpOI.log

ECHO  Completed PHOTOObject Imports.......

pause

