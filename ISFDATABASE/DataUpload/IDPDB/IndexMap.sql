create table indexMap
(IndexMapFileName  		CHAR(20)
 ,ISFAssignedTextNo  		CHAR(13)
 ,MainTextOrPublcnNo  		CHAR(20)
 ,TextDivision   		CHAR(50)
 ,TextSubDivision  		Char(20)
 ,TextView    			CHAR(40))
tablespace isfdata
STORAGE (
 INITIAL 120K
 NEXT  60K
 PCTINCREASE 10
 MINEXTENTS 2
 MAXEXTENTS UNLIMITED
)
        PCTFREE 10
        PCTUSED 75
/
