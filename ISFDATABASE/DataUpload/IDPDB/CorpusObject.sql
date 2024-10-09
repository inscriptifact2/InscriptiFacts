create table corpusobject
(ISFASSIGNEDCORPUSIDNO  		char(13) 
 ,corpusname				char(60)
 ,corpusDesc				char(120)
 ,corpusCategory			corpusCategory_nt
 ,Constraint corpusobject_pk1 primary key (ISFASSIGNEDCORPUSIDNO)
   using INDEX tablespace ISFINDX storage ( initial 64K
            next 32K pctincrease 10 minextents 2 maxextents unlimited)
)
tablespace isfdata
STORAGE (
 INITIAL 240K
 NEXT  120K
 PCTINCREASE 0
 MINEXTENTS 2
 MAXEXTENTS UNLIMITED
)
	PCTFREE 10
        PCTUSED 75
nested table corpuscategory store as corpusCategory_nt_tab
(nested table corpussubcategory store as corpusSubCategory_nt_tab)
/
