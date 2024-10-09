create or replace procedure CleanTmp_textaliases is
begin
   Delete from Tmp_textaliases ;
   commit ;
end CleanTmp_textaliases ;
/
