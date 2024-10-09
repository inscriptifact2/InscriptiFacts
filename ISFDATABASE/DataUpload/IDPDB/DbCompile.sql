DROP USER ISFDEV2 CASCADE
/

Create user ISFDev2
identified by ISFDEVUSC2
default tablespace ISFDATA 
temporary tablespace IDPtemp
Quota Unlimited on ISFDATA
Quota Unlimited on ISFINDX
Quota Unlimited on TXTOBJ
Quota Unlimited on ITHUMBNAIL
Quota Unlimited on IQUICKLOOK
Quota Unlimited on IMAGESID
Quota Unlimited on INDXMAPIMAGE
Quota Unlimited on INDXTS1
Quota Unlimited on INDXTS3
Quota Unlimited on INDXTS4
Quota Unlimited on INDXTS5
Quota Unlimited on INDXTS6
Quota Unlimited on INDXTS7
Quota Unlimited on INDXTS8
Quota Unlimited on INDXTS9
Quota Unlimited on INDXTS10
Quota Unlimited on INDXTS11
Quota Unlimited on INDXTS12
Quota Unlimited on INDXTS13
Quota Unlimited on INDXTS14
Quota Unlimited on IDPTEMP
Quota Unlimited on INDXTS2
/

grant connect, resource to iSFDEV2
/

Connect ISFDEV2/ISFDEVUSC2@isfdb


PROMPT Creation of Objects
@C:\IDPDB\ObjectCreation

PROMPT Creation of ISFUSERS
@C:\IDPDB\isfusers

Prompt Creation of LoginDetails
@C:\IDPDB\logindetails

PROMPT Creation of CorpusObject 
@C:\IDPDB\CorpusObject


Prompt Creation of LoginSequenceNumber
@C:\IDPDB\loginsequenceCreation

PROMPT  ISF USers Limit table Creation
@c:\IDPDB\isfusrlimit.sql


Prompt Creation Of Login Validation Procedure 
@C:\IDPDB\ISFLoginProcedure


PROMPT Creation of IndexMap
@C:\IDPDB\indexmap

Prompt Creation of TextObject
@C:\IDPDB\TextObject

Prompt Creation of Photoobject
@C:\IDPDB\Photoobject

Prompt Creation of Phototextdivision 
@C:\IDPDB\phototexttextdivision

Prompt Creation of Spatialreferencing 
@C:\IDPDB\spatialreferencing

Prompt Creation of TimeperiodObject
@C:\IDPDB\timeperiodobject

Prompt Creation of Object Views
@C:\IDPDB\ObjectViewsCreation


Prompt Creation of Indexes
@C:\IDPDB\IndexCreation


PROMPT Creation of Data Migration Scripts


PROMPT Creation of TmpTablesCreation procedure
@C:\IDPDB\TmpTablesCreation

PROMPT Creation of CorpusObjectInsert procedure
@C:\IDPDB\CorpusObjectInsert

PROMPT Creation of IndexMapInsert procedure
@C:\IDPDB\IndexMapInsert

PROMPT Creation of PhotoobjectInsert procedure
@C:\IDPDB\PhotoobjectInsert

PROMPT Creation of SpatialreferencingInsert procedure
@C:\IDPDB\SpatialreferencingInsert

PROMPT Creation of textobjectInsert procedure
@C:\IDPDB\textobjectInsert

PROMPT Creation of TimeperiodObjectInsert Procedure
@C:\IDPDB\TimeperiodObjectInsert


PROMPT Temporary tables cleaning Procedures Creation

PROMPT Tmp_corpusObject Clean Procedure creation
@C:\IDPDB\CleanTmp_CorpusObject

PROMPT Tmp_IndexMap Clean Procedure creation
@C:\IDPDB\CleanTmp_IndexMap

PROMPT Tmp_Photoobject Procedure creation
@C:\IDPDB\CleanTmp_Photoobject

PROMPT Tmp_spatialreferencing Procedure creation
@C:\IDPDB\CleanTmp_spatialreferencing

PROMPT Tmp_spatialreferencing Procedure creation
@C:\IDPDB\CleanTmp_TimeperiodObject

PROMPT Tmp_spatialreferencing Procedure creation
@C:\IDPDB\CleanTmp_TextAliases

PROMPT Tmp_spatialreferencing Procedure creation
@C:\IDPDB\CleanTmp_TextDivision

PROMPT Tmp_spatialreferencing Procedure creation
@C:\IDPDB\CleanTmp_TextObject


set linesize 300
select substr(object_name,1,50), object_type, timestamp from user_objects 
/

Exit









