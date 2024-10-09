@echo off
Echo Updating Data

sqlplus idptest/idptest@lannea  @dbpatch2.sql > log/dbpatch2.log


Echo Data Updation Complete




echo loading images


f:
cd isfdownload\imageupload

call ListFiles


echo loading sid images
 call LoadSid



