create table PhotoTextTextDivision 
( ISFAssignedTextNo		char(13)
 ,ISFDigitalObjectIdentifier	char(13)
 ,MainTextOrPublcnNo		char(20)
 ,PhotoGraphDescription		char(200)
 ,PhotographIdentificationNo	char(20)
 ,TextDivision			char(50)
 ,TextDivisionSubDivision	char(50)
 )
 tablespace isfdata 
STORAGE (
 INITIAL 256K
 NEXT  128K
 PCTINCREASE 10
 MINEXTENTS 2
 MAXEXTENTS UNLIMITED
)
   PCTFREE 10
  PCTUSED 75
/

