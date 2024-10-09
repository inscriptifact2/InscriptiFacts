create or replace procedure CleanTmp_IndexMap is
begin
   Delete from Tmp_IndexMap ;
   commit ;
end CleanTmp_IndexMap ;
/
