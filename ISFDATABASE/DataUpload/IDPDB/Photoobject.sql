create table photoobject
( ISFDigitalObjectIdentifier	char(13) 
 ,AncientTextRepresented	AncientTextRepresented_va
 ,ArchivalFileResolution	char(9)
 ,ArchivalFileSize		char(8)
 ,Colaborator			Colaborator_va
 ,CompressedFormatExtension	char(4)
 ,CompressionRatio		char(8)
 ,CompressionRoutine		char(8)
 ,DateOfDigitalConversion	char(7)
 ,DateOfPhotograph		char(4)
 ,DigitalObjectFormat		char(20)
 ,DigitalObjectTypeNote		char(20)
 ,DigitizationEquipmentSpecs	char(30)
 ,FilmTypeCode			char(2)
 ,IsPartOfWSRProject		char(40)
 ,ISFAssignedTextNo		ISFAssignedTextNo_va
 ,ISFFindSite			char(40)
 ,Language			language_va
 ,MagnificationCode		char(1)
 ,MuseumAccessionNo		MuseumAccessionNo_va
 ,NamedTimeperiod		NAMEDTIMEPERIOD_VA
 ,PhotoDescriptiveTitle		char(40)
 ,PhotoDimensions		char(25)
 ,PhotoTextOrPublcnNoNote	PhotoTextOrPublcnNoNote_va
 ,PhotographDescription		char(200)
 ,PhotographIDentificationNo	char(20)
 ,Photographer			Photographer_va
 ,RelevantTimeLine		char(20)
 ,RightsDigitalObject		char(60)
 ,RighsPhotograph		char(60)
 ,RightsPhysicalObject		RightsPhysicalObject_va
 ,Script			Script_va
 ,ScriptSubLevel		ScriptSublevel_va
 ,TextDivision			TextDivision_va
 ,TextDivisionSubDivision	TextDivisionSubDivision_va
 ,TextVIew			textview_va			
 ,ThumbnailHeader		char(50)
 ,TypeOfItemCateloged		char(20)
 ,MEDIUM         		CHAR(20)
 ,languagenote                  char(50)
 ,scriptnote                    char(50)
 ,PHOTOMUSEUMACCESSIONNO        PHOTOMUSEUMACCESSIONNO_VA
 ,PHOTOMUSEUMACCESSIONNONote    char(75)
 ,IMAGETHUMBNAIL		BLOB
 ,IMAGEQUICKLOOK		BLOB
 ,IMAGESID       		BLOB
 ,INDEXMAPIMAGE  		BLOB
 ,Constraint photoobject_pk1 primary key(ISFDigitalObjectIdentifier)
              using index tablespace ISFINDX STORAGE ( INITIAL 100k
						 NEXT  50k
						 PCTINCREASE 10
						 MINEXTENTS 2
   						 MAXEXTENTS UNLIMITED)
 )
tablespace users
STORAGE (
 INITIAL 1M
 NEXT  1M
 PCTINCREASE 10
 MINEXTENTS 2
 MAXEXTENTS UNLIMITED
)
  PCTFREE 10
  PCTUSED 75
LOB(IMAGETHUMBNAIL)
STORE AS (TABLESPACE ITHUMBNAIL DISABLE STORAGE IN ROW
 STORAGE (INITIAL 1M NEXT 1M)
  PCTVERSION 5)
LOB(IMAGEQUICKLOOK)
STORE AS (TABLESPACE iquicklook DISABLE STORAGE IN ROW 
STORAGE (INITIAL 1M NEXT 1M)
  PCTVERSION 5)
LOB(IMAGESID)
STORE AS (TABLESPACE imagesid DISABLE STORAGE IN ROW 
 STORAGE (INITIAL 1M NEXT 2M)
 PCTVERSION 5)
LOB(INDEXMAPIMAGE)
STORE AS (TABLESPACE INDXMAPIMAGE DISABLE STORAGE IN ROW 
 STORAGE (INITIAL 1M NEXT 1M)
 PCTVERSION 5)
/

