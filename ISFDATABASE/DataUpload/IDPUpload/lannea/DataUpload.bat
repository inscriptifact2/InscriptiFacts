sqlplus isfdev2/isfdevusc2@lannea  @execcleantmp > log/execcleantmp.log
sqlplus isfdev2/isfdevusc2@lannea @cleanup.sql > log/cleanup.log
call corpusobject
call indexmap
call spatialreferencing
call textaliases
call textdivision
call textobject
call timeperiodobject
call photoobject

sqlplus isfdev2/isfdevusc2@lannea @execconversion > log/execconversion.log

sqlplus isfdev2/isfdevusc2@lannea @dbpatch2.sql > log/dbpatch2.log
