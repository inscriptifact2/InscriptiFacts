create or replace procedure TimePeriodObjectInsert is

cursor CurTimeperiod is 
		select distinct NAMEDTIMEPERIOD                
				,TIMELINENAME                   
		from 	tmp_TimeperiodObject ;

MyErrorCode			number ;
MyErrorMessage			varchar2(200) ;

begin
    for i in CurTimeperiod loop
      begin	
	insert into timeperiodobject(	NAMEDTIMEPERIOD                
					,TIMELINENAME                   
 					,TIMEPERIODBEGINDATEBCE_CE      
 					,TIMEPERIODBEGINDATEYEAR        
 					,TIMEPERIODENDDATEBCE_CE        
 					,TIMEPERIODENDDATEYEAR          
 					,TIMEPERIODEXPLANATIONORDESC )
		select  NAMEDTIMEPERIOD                
 			,TIMELINENAME                   
 			,TIMEPERIODBEGINDATEBCE_CE      
 			,TIMEPERIODBEGINDATEYEAR        
 			,TIMEPERIODENDDATEBCE_CE        
 			,TIMEPERIODENDDATEYEAR          
 			,TIMEPERIODEXPLANATIONORDESC
		from 	tmp_timeperiodobject
		where 	namedtimeperiod = i.namedtimeperiod
			and timelinename = i.timelinename ;

     	Exception
		when others then
			MyErrorCode := SQLCODE ;
			MyErrorMessage := SQLERRM ;
			insert into err_TimeperiodObject (	NAMEDTIMEPERIOD                
 								,TIMELINENAME                   
								,ERRORLOGTIME   
								,ERRORCODE      
								,REASON         
								,ERRORMESSAGE)
				values(  i.NAMEDTIMEPERIOD                
 					,i.TIMELINENAME                   
					,sysdate()
					,mYErrorCode
					,'Insert Failed'
					,MyErrorMessage) ;
			null ;
   	end ;
	commit ;
    end loop ;  	

end TimePeriodObjectInsert ;		
/

