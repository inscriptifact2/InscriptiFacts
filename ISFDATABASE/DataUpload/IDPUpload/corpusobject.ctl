LOAD DATA
INFILE *
INTO TABLE tmp_corpusobject
APPEND
FIELDS TERMINATED BY "|" OPTIONALLY ENCLOSED BY '"'
TRAILING NULLCOLS
(ISFAssignedCORPUSIDNO
 ,Corpusname
 ,CorpusDesc
 ,CorpusCATEGORY 
 ,Corpuscategoryseqno
 ,CorpusSUBCATEGORY
 ,Corpussubcategoryseqno)
