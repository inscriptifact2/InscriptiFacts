create or replace procedure CleanTmp_timeperiodobject is
begin
   Delete from Tmp_timeperiodobject ;
   commit ;
end CleanTmp_timeperiodobject ;
/
