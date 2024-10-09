sqlplus ISFDEV2/ISFDEVUSC2@isfdb  @execcleantmp > execcleantmp.log
sqlplus ISFDEV2/ISFDEVUSC2@isfdb @cleanup.sql > cleanup.log
call corpusobject
call indexmap
call spatialreferencing
call textaliases
call textdivision
call textobject
call timeperiodobject
call photoobject

sqlplus ISFDEV2/ISFDEVUSC2@isfdb @execconversion > execconversion.log

sqlplus ISFDEV2/ISFDEVUSC2@isfdb @dbpatch2.sql > dbpatch2.log
