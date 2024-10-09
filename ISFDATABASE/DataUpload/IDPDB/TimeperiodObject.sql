create table TimePeriodObject
(NamedTimePeriod			CHAR(50)  
 ,TimeLineName				CHAR(20)
 ,TimePeriodBeginDateBCE_CE		CHAR(6)
 ,TimePeriodBeginDateYear		CHAR(6)
 ,TimePeriodEndDateBCE_CE		CHAR(6)
 ,TimePeriodEndDateYear			CHAR(6)
 ,TimePeriodExplanationOrDesc		CHAR(50)
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


