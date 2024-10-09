CREATE OR REPLACE  PROCEDURE ISFLOGIN (IUserIdentity IN 
    char, IUserPassword IN char) as
MyUserCount            ISFUSRLIMIT.NO_MAX_USERS%type ;
myMaxUserCount          exception ;
InvalidUser             exception ;
MyUserIdentity          ISFUsers.UserIdentity%type ;
MyUserPassword          ISFUSers.UserPassword%type ;
MyLimit                 ISFUSRLIMIT.NO_MAX_USERS%type ;
logstatus               isfusers.loginstatus%type ; 
begin
        begin
                select count(*) into MyUserCount
                from ISFUsers
                where LoginStatus = 'T' and userrole='user';
                select no_max_users into MyLimit from isfusrlimit;
                if nvl(MyUserCount, 0) >= MyLimit then
                        Raise MyMaxUserCount ;
   
                 end if;
        exception
                when no_data_found then
                        null ;
                When MyMaxUserCount then
                        raise_application_error(-20001, 'Max Users Logged In. Please Try Again') ;
         end;
               begin
                select  loginstatus into logstatus  from ISFUsers
                where userIdentity = IUserIdentity ;
                 if nvl(logstatus,'')='T' or nvl(logstatus,'') ='D' then
                       Raise InvalidUser;
              end if;
        exception
                when no_data_found then
                        raise_application_error(-20002, 'User Not Found') ;
                When InvalidUser then
                        raise_application_error(-20004,'User Already Logged In.');                          
        end ;
        begin
                select UserPassword into MyUserPassword
               		from ISFUsers
               	 	where userIdentity = IUserIdentity
  	      	  	and UserPassword = IUserPassword ;
		insert into logindetails(LOGINSEQNO, USERIDENTITY, LOGINTIME )
			values (loginSeqNo.nextval, IUserIdentity, sysdate()) ;
		update isfusers set  LoginStatus = 'T', LastAccessedTime = sysdate()
			where UserIdentity = IUserIdentity  ;
		commit ;
		
        exception
                when no_data_found then
                        raise_application_error(-20003, 'Password Wrong Please Re-enter the Password') ;
        end ;
end
/