ECHO   Cleaning Text Objects fields

sqlplus isfdev2/isfdevusc2@lannea @cleantext.sql > log/cleantext.log

rem ECHO INSERTING RECORDS INTO TEMPORARY TEXTOBJECT COLUMN

 call textobject

ECHO Converting and putting into the main TextObject table...

rem  sqlplus isfdev2/isfdevusc2@lannea @execTOU.sql > log/execTOU.log

ECHO  Completed TextObject Imports.......

pause

