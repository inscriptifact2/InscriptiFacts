create or replace procedure CleanTmp_textdivision is
begin
   Delete from Tmp_textdivision ;
   commit ;
end CleanTmp_textdivision ;
/
