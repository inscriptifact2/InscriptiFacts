create or replace procedure SpatialreferencingInsert is

cursor CurspatialInsert is 
		select distinct REFERENCEFILE  
				,DETAILEDLEFT   
 				,DETAILEDBOTTOM
 				,DETAILEDWIDTH  
 				,DETAILEDHEIGHT
		from 	tmp_Spatialreferencing ;

MyErrorCode			number ;
MyErrorMessage			varchar2(200) ;

begin

    for i in CurspatialInsert loop	
    	begin	

		insert into spatialreferencing (REFERENCEFILE  
						,DETAILEDFILE   
						,DETAILEDLEFT   
						,DETAILEDBOTTOM
						,DETAILEDWIDTH  
						,DETAILEDHEIGHT)
				select 	REFERENCEFILE  
					,DETAILEDFILE   
					,DETAILEDLEFT   
					,DETAILEDBOTTOM
					,DETAILEDWIDTH  
					,DETAILEDHEIGHT
				from tmp_spatialreferencing 
				where 	REFERENCEFILE  = i.REFERENCEFILE
					and DETAILEDLEFT = i.DETAILEDLEFT 
 					and DETAILEDBOTTOM = i.DETAILEDBOTTOM
 					and DETAILEDWIDTH = i.DETAILEDWIDTH 
 					and DETAILEDHEIGHT = i.DETAILEDHEIGHT ;
     	Exception
		when others then
			MyErrorCode := SQLCODE ;
			MyErrorMessage := SQLERRM ;
			insert into err_Spatialreferencing (	REFERENCEFILE  
								,DETAILEDLEFT   
								,DETAILEDBOTTOM
								,DETAILEDWIDTH  
								,DETAILEDHEIGHT
								,ERRORLOGTIME   
								,ERRORCODE      
								,REASON         
								,ERRORMESSAGE)
				values(  i.REFERENCEFILE  
					,i.DETAILEDLEFT   
					,i.DETAILEDBOTTOM
					,i.DETAILEDWIDTH  
					,i.DETAILEDHEIGHT
					,sysdate()
					,mYErrorCode
					,'Insert Failed'
					,MyErrorMessage) ;
			null ;
	end ;
	commit ;
    end loop ;  	
	
end SpatialreferencingInsert ;		
/
