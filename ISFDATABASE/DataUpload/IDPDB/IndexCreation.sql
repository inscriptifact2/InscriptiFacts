Create  index IX_CP_CorpusSeqNum ON
corpusCategory_nt_tab(NESTED_TAble_id,CorpusCategorySeqNo ASC) 
Tablespace INDXTS1
STORAGE( initial 20k next 20k  minextents 2 pctincrease 10)
/

Create  index IX_CP_CorpusName ON
CorpusObject (CorpusName ASC) 
Tablespace INDXTS1
STORAGE( initial 20k next 20k  minextents 2 pctincrease 10)
/

Create index IX_PH_ISFFindSite ON
PhotoObject (ISFFindSite ASC) 
Tablespace INDXTS2
STORAGE( initial 20k next 20k  minextents 2 pctincrease 10)
/

Create index IX_PH_DateOfPhotograph ON
PhotoObject (DateOfPhotograph ASC) 
Tablespace INDXTS6
STORAGE( initial 20k next 20k  minextents 2 pctincrease 10)
/

Create index IX_PH_MagnificationCode ON
PhotoObject (MagnificationCode ASC) 
Tablespace INDXTS7
STORAGE( initial 10k next 10k  minextents 2 pctincrease 10)
/

Create index IX_PH_FilmTypeCode ON
PhotoObject (FilmTypeCode ASC) 
Tablespace INDXTS8
STORAGE( initial 10k next 10k  minextents 2 pctincrease 10)
/

Create index IX_PH_PGRAPHID_NO ON
PhotoObject (PHOTOGRAPHIDENTIFICATIONNO ASC) 
Tablespace INDXTS8
STORAGE( initial 10k next 10k  minextents 2 pctincrease 10)
/

Create  index IX_TX_Medium ON
TextObject (Medium ASC) 
Tablespace INDXTS9
STORAGE( initial 10k next 10k  minextents 2 pctincrease 10)
/

Create  index IX_TX_ISFFindSite ON
TextObject(ISFFindSite ASC) 
Tablespace INDXTS10
STORAGE( initial 20k next 20k  minextents 2 pctincrease 10)
/

Create  index IXTXMainTxtOrPublicNoPref ON
TextObject (MainTextOrPublcnNoPrefix ASC)
Tablespace INDXTS11
STORAGE( initial 20k next 20k  minextents 2 pctincrease 10)
/

Create index IX_TX_MainTextOrPublcnNo ON
TextObject (MainTextOrPublcnNo ASC) 
Tablespace INDXTS12
STORAGE( initial 20k next 20k  minextents 2 pctincrease 10)
/

Create index IX_TX_SpatialSearchCode ON
textdivision_nt_tab(NESTED_TAble_id,SpatialSearchCode ASC) 
Tablespace INDXTS13
STORAGE( initial 20k next 20k  minextents 2 pctincrease 10)
/

Create index IX_TM_TimePeriod ON
TimePeriodObject (NamedTimePeriod ASC, TimeLineName ASC) 
Tablespace INDXTS14
STORAGE( initial 60k next 60k  minextents 2 pctincrease 10)
/
Create index IX_TM_TimeLineName ON
TimePeriodObject (TimeLineName ASC) 
Tablespace INDXTS14
STORAGE( initial 60k next 60k  minextents 2 pctincrease 10)
/


Create index IX_TM_TimePeriodBeginDate ON
TimePeriodObject (TimePeriodBeginDateBCE_CE ASC, TimePeriodBeginDateYear ASC) 
Tablespace INDXTS14
STORAGE( initial 10k next 10k  minextents 2 pctincrease 10)
/


Create index IXCorpusSeqcat ON
corpusCategory_nt_tab(NESTED_TAble_id,CorpusCategorySeqNo ASC,corpuscategory) 
Tablespace indxts1
STORAGE( initial 20k next 20k  minextents 2 pctincrease 10)
/

Create  index IXCorpsubcat ON
CorpusSubcategory_nt_tab (nested_table_id, CorpusSubCategorySeqNo ASC,corpusSubcategory ASC) 
Tablespace INDXTS1
STORAGE( initial 20k next 20k  minextents 2 pctincrease 10)
/

Create index IXindexmap ON
Indexmap (ISFAssignedTextNo ASC,TextDivision, TextSubDivision ASC,TextView) 
Tablespace INDXTS2
STORAGE( initial 20k next 20k  minextents 2 pctincrease 10)
/

Create index IXtxtdivnt ON
 textdivision_nt_tab(NESTED_TAble_id,TextDivision,TextDivisionSeqNo) 
Tablespace INDXTS3
STORAGE( initial 20k next 20k  minextents 2 pctincrease 10)
/

Create index IXtxtdivsubdivnt ON
 TextDivisionSubDivision_nt_tab(NESTED_TAble_id,TextDivisionSubDivision,TexTDivisionSubDivisionSeqNo) 
Tablespace INDXTS3
STORAGE( initial 20k next 20k  minextents 2 pctincrease 10)
/

Create index IXTEXTALIASESNT ON
TEXTALIASES_NT_TAB( AltTextOrPublcnNo) 
Tablespace INDXTS4
STORAGE( initial 60k next 60k  minextents 2 pctincrease 10)
/

Create index IXSCRIPTNT	 ON
script_nt_tab(Script, ScriptSubLevel) 
Tablespace INDXTS5
STORAGE( initial 10k next 10k  minextents 2 pctincrease 10)
/
