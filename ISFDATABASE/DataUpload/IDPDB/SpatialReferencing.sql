create table spatialReferencing
(referenceFile			CHAR(20)
 ,DetailedFile			CHAR(20)	
 ,DetailedLeft			CHAR(4)
 ,DetailedBottom		CHAR(4)
 ,DetailedWidth			CHAR(4)
 ,DetailedHeight 		CHAR(4)
 ,constraint spatialreferencing_PK5 Primary key(REFERENCEFILE
						,DETAILEDLEFT
						,DETAILEDBOTTOM
						,DETAILEDWIDTH
						,DETAILEDHEIGHT)
  using INDEX TABLESPACE ISFINDX storage( initial 128K
				          next 64K 
					  pctincrease 10
					  minextents 2 
					  maxextents unlimited)
 )
tablespace users
STORAGE (
 INITIAL 256K
 NEXT  64K
 PCTINCREASE 10
 MINEXTENTS 2
 MAXEXTENTS UNLIMITED
)
  PCTFREE 10
  PCTUSED 75
/


