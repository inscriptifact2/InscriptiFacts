create table Textobject
( ISFAssignedTextNo		CHAR(13)
 ,AltTextTitle			AltTextTitle_va
 ,AncientCityOrLocation		CHAR(40)
 ,AncientStructure		CHAR(40)
 ,DateOfInscriptionNote		CHAR(60)
 ,ExcavationDescription		CHAR(520)
 ,ExcavationNo			ExcavationNo_va
 ,GeographicalCoverageNote	CHAR(60)
 ,IndexMapFileName		IndexMapFileName_va
 ,IsPartOf			CHAR(60)
 ,IsPartOfCorpus		IsPartOfCorpus_va
 ,IsPartOfCorpusCategory	IsPartOfCorpusCategory_va
 ,IsPartOfCorpusSubCategory	IsPartOfCorpusSubCategory_va
 ,IsVersionOf			CHAR(60)
 ,ISFFIndsite			CHAR(40)
 ,keywordsOrPhrases		keywordsOrPhrases_va
 ,Language			language_va
 ,LocationOfOriginal		LocationOfOriginal_va
 ,MainTextOrPublcnNo		CHAR(50)
 ,MainTextOrPublcnNoPrefix	CHAR(25)
 ,MainTextOrPublcnNoSuffix	CHAR(25)
 ,Medium			CHAR(20)
 ,ModernCityOrLocation		CHAR(40)
 ,ModernCountry			CHAR(30)
 ,MuseumAccessionNo		MuseumAccessionNo_va
 ,NamedTimePeriod		NAMEDTIMEPERIOD_VA
 ,PhysicalObjectDescription	CHAR(250)
 ,PhysicalObjectNote		PhysicalObjectNote_va
 ,Region			CHAR(40)
 ,RelevantTimeLine		CHAR(20)
 ,RightsPhysicalObject		RightsPhysicalObject_va
 ,Script			script_nt
 ,TextDescription		CHAR(250)
 ,TextDescriptiveTitle		CHAR(130)
 ,TextOrPublcnNoNote		TextOrPublcnNoNote_va
 ,TextSearchAndDisplayCode	CHAR(1)
 ,TextView			TextView_va
 ,TextUninscribedArtifact	CHAR(40)	
 ,TypeOfItemCateloged		CHAR(20)
 ,Textaliases			textaliases_nt
 ,textdivision			textdivision_nt
 ,LANGUAGENOTE                  char(50)
 ,SCRIPTNOTE                    char(50)
 , constraint Textobject_pk1 primary key(ISFAssignedTextNo)
       using index tablespace ISFINDX storage ( Initial 256K  Next 128K
                   PCTINCREASE 10 MINEXTENTS 2 MAXEXTENTS UNLIMITED)
)
  tablespace txtobj
  STORAGE (
	 INITIAL 1M
	 NEXT  1M
	 PCTINCREASE 10
	 MINEXTENTS 2
	 MAXEXTENTS UNLIMITED)
  PCTFREE 10
  PCTUSED 75
 nested table script store as script_nt_tab,
 nested table textaliases store as textaliases_nt_tab,
 nested table textdivision store as textdivision_nt_tab
 (nested table textdivisionSubdivision store as textdivisionSubdivision_nt_tab)
/
