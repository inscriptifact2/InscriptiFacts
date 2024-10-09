create or replace procedure CleanTmp_CorpusObject is
begin
   Delete from Tmp_CorpusObject ;
   commit ;
end CleanTmp_CorpusObject ;
/