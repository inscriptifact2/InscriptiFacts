create or replace procedure CorpusObjectInsert is

cursor CurCorpusObjectInsert is 
		select distinct  ISFASSIGNEDCORPUSIDNO, corpusname, corpusdesc
		from 	tmp_CorpusObject ;

MyErrorCode			number ;
MyErrorMessage			varchar2(200) ;

begin

    for i in CurCorpusObjectInsert loop	
      begin	
	insert into corpusobject(ISFASSIGNEDCORPUSIDNO
				,corpusname
				,corpusdesc
				,corpuscategory)
	select 	c.isfassignedcorpusidno
		,c.corpusname
		,c.corpusdesc
		,cast(multiset(select 	 b.corpuscategoryseqno
					,b.corpuscategory
					,cast(multiset(select 	 a.corpusSubcategoryseqno
								,a.corpussubcategory
  							from tmp_corpusobject a 
							where a.ISFASSIGNEDCORPUSIDNO = b.ISFASSIGNEDCORPUSIDNO
							 and a.corpuscategory = b.corpuscategory 
							and a.corpusSubcategorySeqno is not null) as corpusSubcategory_nt)
				 from tmp_corpusobject b
				 where b.ISFASSIGNEDCORPUSIDNO = c.ISFASSIGNEDCORPUSIDNO 
				and b.CorpusCategorySeqno is not null) as Corpuscategory_nt)
	from tmp_corpusobject c 
	where c.isfassignedcorpusidno = i.isfassignedcorpusidno
	group by c.isfassignedcorpusidno
		 ,c.corpusname
		 ,c.corpusdesc ;
     Exception
	when others then
		MyErrorCode := SQLCODE ;
		MyErrorMessage := SQLERRM ;
		insert into err_CorpusObject
			values( i.isfassignedcorpusidno
				,sysdate()
				,mYErrorCode
				,'Insert Failed'
				,MyErrorMessage) ;
		null ;
     end ;
     commit ;	
   end loop ;
end CorpusObjectInsert ;
/
