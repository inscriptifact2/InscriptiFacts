create or replace view language_ov of language_ov_ty with object oid(language) as
select distinct b.* from textobject a, table(language) b
/

create or replace view locnoforigin_ov of LocationOfOriginal_OV_TY with object oid(LocationOfOriginal) as
select distinct b.* from textobject a, table(LocationOfOriginal) b
/

create or replace view isffindsite_ov of isffindsite_ov_ty with object oid(isffindsite) as
select distinct a.isffindsite from textobject a
/

create or replace view SCRIPT_OV of SCRIPT_ov_TY with object OID (Script)  as
select distinct b.script, b.scriptsublevel
 from textobject a , table(a.script) b
/

Create or replace view  MainorAlttextorPublcn_OV of MainorAlttextorPublcn_OV_ty
      with object OID (MainTextOrPublcnNoPrefix) as
 select  a.MainTextOrPublcnNoPrefix, b.AltTextOrPublcnNoPrefix
 from  TEXTOBJECT a, table(a.TEXTALIASES) (+) b
/


create or replace view KeywordsOrPhrases_ov of KeywordsOrPhrases_ov_ty with object oid(KeywordsOrPhrases) as
select distinct b.* from textobject a, table(a.keywordsOrPhrases) b
/


create or replace view CorpusObject_ov of CorpusObject_ov_ty with object oid(CorpusName) as
select distinct a.corpusName
	,b.CorpusCategorySeqNo
	,b.CorpusCategory
	,c.CorpusSubCategorySeqno
	,c.CorpusSubCategory
from 	corpusobject a
	,table(a.corpuscategory) (+) b
	,table(b.corpusSubcategory) (+) c
order by CorpusName
	,b.CorpusCategorySeqNo
	,c.CorpusSubCategorySeqNo
/

create or replace view textdivision_ov of textdivision_ov_ty 
with object OID (ISFAssignedTextNo) as
select    ISFAssignedTextNo
	 ,a.MainTextOrPublcnNo
	 ,b.INDEXMAPFILENAME                 
	 ,b.SPATIALSEARCHCODE               
	 ,b.TEXTDESCRIPTIVETITLE            
	 ,b.TEXTDIVISION                    
	 ,b.TEXTDIVISIONSEQNO               
	 ,b.TEXTDIVISIONDESCRIPTION         
	 ,b.TEXTDIVISIONMUSEUMNO            
	 ,b.TEXTVIEW                        
	 ,c.TEXTDIVISIONSUBDIVISIONSEQNO    
	 ,c.TEXTDIVISIONSUBDIVISION         
from 	textobject a
	, table(textdivision) (+) b
	, table(textdivisionSubDivision) (+) c
/

create or replace view TEXTOBJECT_OV of TEXTOBJECT_OV_TY
with object OID (ISFAssignedTextNo) as
select ISFAssignedTextNo
 ,a.MainTextOrPublcnNo 
 ,d.*
 ,e.*
 ,f.*
 ,a.ISFFIndsite
 ,g.*
 ,h.*
 ,i.*
 ,a.MainTextOrPublcnNoPrefix
 ,A.medium
 ,p.*
 ,a.RelevantTimeLine
 ,m.Script
 ,m.ScriptSubLevel
 ,a.TextDescription
 ,n.AltTextOrPublcnNoPrefix
 ,a.MainTextOrPublcnNoSuffix
 ,n.AltTextOrPublcnNoSuffix
 ,trim(TIMEPERIODBEGINDATEYEAR) || ' ' || trim(TIMEPERIODBEGINDATEBCE_CE) || ' - ' ||   trim(TIMEPERIODENDDATEYEAR) || ' ' || trim(TIMEPERIODENDDATEBCE_CE) 
 ,a.LANGUAGENOTE                 
 ,a.SCRIPTNOTE    
from textobject a
 ,table(IsPartOfCorpus) d
 ,table(ispartofCorpusCategory) e
 ,table(isPartOfCorpusSubCategory) f
 ,table(keywordsOrPhrases) g
 ,table(Language) h
 ,table(LocationOfOriginal) i
 ,table(script) (+) m
 ,table(textaliases) (+) n
 ,TimeperiodObject o
 ,table(a.namedtimeperiod) (+) p
where 	a.RELEVANTTIMELINE = o.TIMELINENAME (+)
	and o.namedtimeperiod = p.column_value
/

create or replace view phototexttextdivision_OV of phototexttextdivision_OV_TY with object oid(ISFDIGITALOBJECTIDENTIFIER) as
select  c.*
 ,a.ISFDIGITALOBJECTIDENTIFIER
 ,b.MAINTEXTORPUBLCNNO
 ,a.PHOTOGRAPHDESCRIPTION
 ,a.PHOTOGRAPHIDENTIFICATIONNO
 ,d.*
 ,e.*
from  photoobject a
 ,textobject b
 ,table(a.isfassignedtextno) (+) c
 ,table(a.textdivision) (+) d
 ,table(a.textdivisionsubdivision) (+) e
where   b.isfassignedtextno = c.column_value
/
create type TEMP_COVER_VA as varray(2) of  CHAR(29)
/
create or replace function temp_cov1 (ntp1 in NAMEDTIMEPERIOD_VA,rtl in char )
return TEMP_COVER_VA
is tmpcov TEMP_COVER_VA ;
MyCtr  integer ;
Mycount  integer ;
Myntp   TEMP_COVER_VA  := TEMP_COVER_VA() ;
TEMPORALCOVERAGERANGE     CHAR(29);
tmpstr1         char(50);
Begin
 Myntp := TEMP_COVER_VA();
 Mycount := 0 ;
 MyCtr := 1 ;
 for j in 1..2 loop
  Myntp.extend ;
  tmpstr1 := ntp1(j);
  if tmpstr1 is not null then   
         select
          '( ' || trim(timeperiodbegindateyear) || ' ' || trim(timeperiodbegindatebce_ce) || ' - ' ||trim(timeperiodenddateyear) || ' ' || trim(timeperiodenddatebce_ce) || ' )'
          into TEMPORALCOVERAGERANGE
          from TIMEPERIODOBJECT
          where namedtimeperiod=tmpstr1 and timelinename=rtl;
          Myntp(MyCtr) := TEMPORALCOVERAGERANGE;
  end if;
 MyCtr := MyCtr + 1 ;
 end loop ;
 tmpcov := Myntp;
 return(tmpcov);
End;
/
create or replace function temp_cov2 (ntp1 in NAMEDTIMEPERIOD_VA,rtl in char )
return TEMP_COVER_VA
is tmpcov TEMP_COVER_VA ;
MyCtr  integer ;
Mycount  integer ;
Myntp   TEMP_COVER_VA  := TEMP_COVER_VA() ;
TEMPORALCOVERAGERANGE     CHAR(29);
tmpstr1         char(50);
Begin
 Myntp := TEMP_COVER_VA();
 Mycount := 0 ;
 MyCtr := 1 ;
 for j in 1..2 loop
  Myntp.extend ;
  tmpstr1 := ntp1(j);
  if tmpstr1 is not null then   
         select
          '( ' || trim(timeperiodbegindateyear) || ' ' || trim(timeperiodbegindatebce_ce) || ' - ' ||trim(timeperiodenddateyear) || ' ' || trim(timeperiodenddatebce_ce) || ' )'
          into TEMPORALCOVERAGERANGE
          from TIMEPERIODOBJECT
          where namedtimeperiod=tmpstr1 and timelinename=rtl;
          Myntp(MyCtr) := TEMPORALCOVERAGERANGE;
  end if;
 MyCtr := MyCtr + 1 ;
 end loop ;
 tmpcov := Myntp;
 return(tmpcov);
End;
/

create or replace view TEXTOBJECT_OV1 of TEXTOBJECT_OV_TY1
with object OID (ISFAssignedTextNo) as
select ISFAssignedTextNo
 ,a.MainTextOrPublcnNo 
 ,d.*
 ,e.*
 ,f.*
 ,a.ISFFIndsite
 ,g.*
 ,h.*
 ,i.*
 ,a.MainTextOrPublcnNoPrefix
 ,A.medium
 ,p.*
 ,a.RelevantTimeLine
 ,m.Script
 ,m.ScriptSubLevel
 ,a.TextDescription
 ,n.AltTextOrPublcnNoPrefix
 ,a.MainTextOrPublcnNoSuffix
 ,n.AltTextOrPublcnNoSuffix
 ,a.LANGUAGENOTE                 
 ,a.SCRIPTNOTE    
from textobject a
 ,table(IsPartOfCorpus) d
 ,table(ispartofCorpusCategory) e
 ,table(isPartOfCorpusSubCategory) f
 ,table(keywordsOrPhrases) g
 ,table(Language) h
 ,table(LocationOfOriginal) i
 ,table(script) (+) m
 ,table(textaliases) (+) n
 ,table(a.namedtimeperiod)  p
/
