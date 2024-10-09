ECHO   Cleaning Text Objects fields

sqlplus ISFDEV2/ISFDEVUSC2@isfdb @cleantext.sql > cleantext.log

rem ECHO INSERTING RECORDS INTO TEMPORARY TEXTOBJECT COLUMN

rem call textobject

ECHO Converting and putting into the main TextObject table...

sqlplus ISFDEV2/ISFDEVUSC2@isfdb @execTOI.sql > execTOI.log

ECHO  Completed TextObject Imports.......

pause

