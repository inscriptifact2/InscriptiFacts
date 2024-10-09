@echo off
Echo Updating Data

sqlplus idptest/idptest@isfdb  @dbpatch2.sql > dbpatch2.log


Echo Data Updation Complete




echo loading images


f:
cd isfdownload\imageupload

call ListFiles


echo loading sid images
 call LoadSid



