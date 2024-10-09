-- objects for Linked with tables

create or replace type language_OV_TY as object
(language		char(20))
/

create or replace type LocationOfOriginal_OV_TY as object
(LocationOfOriginal		char(100))
/

create or replace type isffindsite_OV_TY as object
(isffindsite			char(20))
/

create or replace type script_ov_ty as object
( Script			CHAR(30)
 ,ScriptSubLevel		CHAR(20))
/

create or replace Type MainorAlttextorPublcn_OV_ty as object
(
MainTextOrPublcnNoPrefix   CHAR(20),
AltTextOrPublcnNoPrefix    CHAR(20)
)
/

create or replace type KeywordsOrPhrases_OV_TY as object
(KeywordsOrPhrases 	CHAR(50) )
/


create or replace type TEXTALIASES_TY as object
( AltTextOrPublcnNo	 		CHAR(75)
 ,AltTextOrPublcnNoPrefix		CHAR(20)
 ,AltTextOrPublcnNoSuffix		CHAR(50)
 ,TextDescriptiveTitle			varchar2(130)
)
/

create or replace type TEXTALIASES_NT as table of TEXTALIASES_TY
/

create or replace type textdivisionSubDivision_TY as object
(TexTDivisionSubDivisionSeqNo		number(3)
 ,TextDivisionSubDivision		char(50))
/

create or replace type TextDivisionSubDivision_NT as table of TextDivisionSubDivision_TY
/

create or replace type textdivision_ty as object
( indexMapFileName			char(20)
 ,spatialSearchCode			char(3)
 ,TextDescriptiveTitle			char(130)
 ,TextDivision				char(50)
 ,TextDivisionSeqNo			number(4)
 ,TextDivisionDescription		CHAR(100)
 ,TextDivisionMuseumNo			CHAR(100)
 ,TextView				CHAR(70)
 ,TextDivisionSubDivision		TextDivisionSubDivision_nt)
/

create or replace type textdivision_NT as table of textdivision_TY
/

create or replace type corpusSubCategory_ty as object
( CorpusSubCategorySeqNo	       	 number(2)  
 ,corpusSubcategory			 char(60))
/

create or replace type corpusSubCategory_nt as table of corpusSubCategory_ty
/

create or replace type corpuscategory_ty as object
(CorpusCategorySeqNo	          	 number(2)  
 ,corpuscategory			 char(60)
 ,corpusSubCategory			 corpusSubcategory_nt)
/

create or replace type corpuscategory_nt as table of corpuscategory_ty
/

create or replace type AltTextTitle_va as varray(5) of  CHAR(200)
/

create or replace type ExcavationNo_va as varray(10) of  CHAR(30)
/

create or replace type IsPartOfCorpus_va as varray(3) of  CHAR(60)
/

create or replace type IsPartOfCorpusCategory_va as varray(3) of  CHAR(60)
/

create or replace type IsPartOfCorpusSubCategory_va as varray(3) of  CHAR(60)
/

create or replace type keywordsOrPhrases_va as varray(25) of  CHAR(50)
/

create or replace type language_va as varray(4) of  CHAR(50)
/

create or replace type LocationOfOriginal_va as varray(6) of  CHAR(100)
/

create or replace type MuseumAccessionNo_va as varray(25) of  CHAR(30)
/

create or replace type PhysicalObjectNote_va as varray(2) of  CHAR(520)
/

create or replace type RightsPhysicalObject_va as varray(5) of  CHAR(100)
/

create or replace type script_ty as object
( Script			CHAR(30)
 ,ScriptSubLevel		CHAR(20))
/

create or replace type script_nt as table of script_ty
/

create or replace type TextOrPublcnNoNote_va as varray(30) of  CHAR(75)
/

create or replace type AncientTextRepresented_va as varray(30) of  CHAR(20)
/

create or replace type Colaborator_va as varray(5) of  CHAR(40)
/

create or replace type ISFAssignedTextNo_va as varray(30) of  CHAR(13)
/

create or replace type PhotoTextOrPublcnNoNote_va as varray(30) of  CHAR(75)
/

create or replace type Photographer_va as varray(5) of  CHAR(20)
/

create or replace type TextDivision_va as varray(100) of  CHAR(40)
/

create or replace type TextDivisionSubDivision_va as varray(100) of  CHAR(40)
/

create or replace type textview_va as varray(75) of  CHAR(40)
/

create or replace type script_va as varray(4) of  CHAR(30)
/

create or replace type ScriptSublevel_va as varray(4) of  CHAR(30)
/

create or replace type indexmapFileName_va as varray(75) of  CHAR(20)
/
create or replace type photoMuseumAccessionNo_va as varray(25) of  CHAR(50)
/
create or replace type NAMEDTIMEPERIOD_VA as varray(2) of  CHAR(50)
/


-- Objects defined to create or replace Object Views.


create or replace type CorpusObject_ov_ty as object
( CorpusName			CHAR(60)
 ,COrpusCategorySeqNo		NUMBER(2)
 ,CorpusCategory		CHAR(60)
 ,CorpusSubCategorySeqNo	NUMBER(2)
 ,CorpuSubCategory		CHAR(60))
/


create or replace type textdivision_ov_ty as object
( ISFAssignedTextNo				     CHAR(13)
 ,MainTextOrPublcnNo				     CHAR(20)
 ,INDEXMAPFILENAME                                   CHAR(20)
 ,SPATIALSEARCHCODE                                  CHAR(3)
 ,TEXTDESCRIPTIVETITLE                               CHAR(130)
 ,TEXTDIVISION                                       CHAR(30)
 ,TEXTDIVISIONSEQNO                                  NUMBER(4)
 ,TEXTDIVISIONDESCRIPTION                            CHAR(40)
 ,TEXTDIVISIONMUSEUMNO                               CHAR(30)
 ,TEXTVIEW                                           CHAR(70)
 ,TEXTDIVISIONSUBDIVISIONSEQNO                       NUMBER(3)
 ,TEXTDIVISIONSUBDIVISION                            CHAR(40))
/

create or replace type Textobject_ov_ty as object 
( ISFAssignedTextNo		CHAR(13)
 ,MainTextOrPublcnNo		CHAR(20)
 ,IsPartOfCorpus		char(60) 
 ,IsPartOfCorpusCategory	char(60) 
 ,IsPartOfCorpusSubCategory	char(60)
 ,ISFFIndsite			CHAR(40)
 ,keywordsOrPhrases		char(50) 
 ,Language			char(20) 
 ,LocationOfOriginal		char(100) 
 ,MainTextOrPublcnNoPrefix	CHAR(20)
 ,Medium			CHAR(20)
 ,NamedTimePeriod		CHAR(50)
 ,RelevantTimeLine		CHAR(20)
 ,Script			CHAR(30)
 ,ScriptSubLevel		CHAR(20)
 ,TextDescription		CHAR(130)
 ,AltTextOrPublcnNoPrefix	CHAR(20)
 ,MainTextOrPublcnNoSuffix	CHAR(25)
 ,AltTextOrPublcnNoSuffix	CHAR(50)
 ,TempOralCoverageRange		Char(29)
 ,LANGUAGENOTE                  char(50)
 ,SCRIPTNOTE                    char(50))
/

create or replace type phototexttextdivision_OV_TY as object
(ISFASSIGNEDTEXTNO                                  CHAR(13)
 ,ISFDIGITALOBJECTIDENTIFIER                         CHAR(13)
 ,MAINTEXTORPUBLCNNO                                 CHAR(20)
 ,PHOTOGRAPHDESCRIPTION                              CHAR(200)
 ,PHOTOGRAPHIDENTIFICATIONNO                         CHAR(20)
 ,TEXTDIVISION                                       CHAR(50)
 ,TEXTDIVISIONSUBDIVISION                            CHAR(50))
/


create or replace type Textobject_ov_ty1 as object 
( ISFAssignedTextNo		CHAR(13)
 ,MainTextOrPublcnNo		CHAR(20)
 ,IsPartOfCorpus		char(60) 
 ,IsPartOfCorpusCategory	char(60) 
 ,IsPartOfCorpusSubCategory	char(60)
 ,ISFFIndsite			CHAR(40)
 ,keywordsOrPhrases		char(50) 
 ,Language			char(20) 
 ,LocationOfOriginal		char(100) 
 ,MainTextOrPublcnNoPrefix	CHAR(20)
 ,Medium			CHAR(20)
 ,NamedTimePeriod		CHAR(50)
 ,RelevantTimeLine		CHAR(20)
 ,Script			CHAR(30)
 ,ScriptSubLevel		CHAR(20)
 ,TextDescription		CHAR(130)
 ,AltTextOrPublcnNoPrefix	CHAR(20)
 ,MainTextOrPublcnNoSuffix	CHAR(25)
 ,AltTextOrPublcnNoSuffix	CHAR(50)
 ,LANGUAGENOTE                  char(50)
 ,SCRIPTNOTE                    char(50))
/

