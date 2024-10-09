create or replace procedure IndexMapInsert is

begin

	insert into IndexMap (  INDEXMAPFILENAME       
				,ISFASSIGNEDTEXTNO      
				,MAINTEXTORPUBLCNNO
				,TEXTDIVISION           
				,TEXTSUBDIVISION        
				,TEXTVIEW)
 		select  INDEXMAPFILENAME       
			,ISFASSIGNEDTEXTNO      
			,MAINTEXTORPUBLCNNO
			,TEXTDIVISION           
			,TEXTSUBDIVISION        
			,TEXTVIEW
		from tmp_IndexMap
		where (	ISFASSIGNEDTEXTNO 
			,TEXTDIVISION 
			,TEXTSUBDIVISION 
			,textview) not in (select distinct isfassignedtextno 
							   ,textdivision
							   ,textsubdivision
							   ,textview
					   from indexmap) ;								
			
     commit ;	

end IndexMapInsert ;
/
