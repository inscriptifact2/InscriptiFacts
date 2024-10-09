ECHO   Cleaning Text Objects fields

sqlplus isfdev2/isfdevusc2@lannea @cleancorpa.sql > log/cleancorpa.log

ECHO INSERTING RECORDS INTO TEMPORARY corpus COLUMN

call corpusobject

ECHO Converting and putting into the main CorpusObject table...

sqlplus isfdev2/isfdevusc2@lannea @execCOI.sql > log/execCOI.log

ECHO  Completed CorpusObject Imports.......

pause

