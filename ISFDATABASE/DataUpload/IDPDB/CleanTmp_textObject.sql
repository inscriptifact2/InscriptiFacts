create or replace procedure CleanTmp_textObject is
begin
   Delete from Tmp_textObject ;
   commit ;
end CleanTmp_textObject ;
/

