Create table LoginDetails
(LoginSeqNo		number(12)	not null
 ,UserIdentity		char(10)	not null
 ,LoginTime		date		not null
 ,LogoutTime		date		null
 ,Constraint LoginDetails_pk1 primary key(LoginSeqNo)
       using index tablespace ISFINDX 
          STORAGE ( INITIAL 12K
		 NEXT  6K
		 PCTINCREASE 10
		 MINEXTENTS 2
		 MAXEXTENTS UNLIMITED)
)
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

