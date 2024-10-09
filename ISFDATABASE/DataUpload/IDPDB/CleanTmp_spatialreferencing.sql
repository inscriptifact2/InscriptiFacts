create or replace procedure CleanTmp_spatialreferencing is
begin
   Delete from Tmp_spatialreferencing ;
   commit ;
end CleanTmp_spatialreferencing ;
/
