create table ISFUsers
( userIdentity		char(10)	not null 
 ,USERPASSWORD		CHAR(10)	not null
 ,UserName		CHAR(50)	not null
 ,LastAccessedTime	DATE		null
 ,LoginStatus		CHAR(1)		not null
 ,UserRole		Char(10)	not null
 , constraint  ISFUsers_pk1 primary key(userIdentity)
       using index tablespace ISFINDX storage( initial 10k 
			next 5K PCTINCREASE 10 MINEXTENTS 2
                        MAXEXTENTS UNLIMITED)
)
 tablespace isfdata
STORAGE (
 INITIAL 120K
 NEXT  60K
 PCTINCREASE 10
 MINEXTENTS 2
 MAXEXTENTS UNLIMITED )	
  PCTFREE 10
  PCTUSED 75
/

Insert into ISFUsers values('user1', 'user1', 'Testing User1', sysdate, 'F', 'user') 
/

Insert into ISFUsers values('user2', 'user2', 'Testing User2', sysdate, 'F', 'user') 
/

Insert into ISFUsers values('user3', 'user3', 'Testing User3', sysdate, 'F', 'user') 
/

commit;
/
