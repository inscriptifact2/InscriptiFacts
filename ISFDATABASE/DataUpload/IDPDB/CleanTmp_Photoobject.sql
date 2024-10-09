create or replace procedure CleanTmp_Photoobject is
begin
   Delete from Tmp_Photoobject ;
   commit ;
end CleanTmp_Photoobject ;
/
