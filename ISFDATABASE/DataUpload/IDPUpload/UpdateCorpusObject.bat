ECHO   Cleaning Text Objects fields

sqlplus ISFDEV2/ISFDEVUSC2@isfdb @cleancorpa.sql > cleancorpa.log

ECHO INSERTING RECORDS INTO TEMPORARY corpus COLUMN

call corpusobject

ECHO Converting and putting into the main CorpusObject table...

sqlplus ISFDEV2/ISFDEVUSC2@isfdb @execCOI.sql > execCOI.log

ECHO  Completed CorpusObject Imports.......

pause

