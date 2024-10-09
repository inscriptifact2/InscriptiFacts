 sqlplus isfdev2/isfdevusc2@lannea  @cleancorpa.sql > log/cleancorpa.log
call corpusobject

sqlplus isfdev2/isfdevusc2@lannea  @cleanaliases.sql > log/cleanaliases.log
call textaliases

sqlplus isfdev2/isfdevusc2@lannea  @cleantext.sql > log/cleantext.log
call textobject

sqlplus isfdev2/isfdevusc2@lannea  @cleanphoto.sql > log/cleanphoto.log
call photoobject

 sqlplus isfdev2/isfdevusc2@lannea @execCTTP.sql > log/execCTTP.log