LOAD DATA
INFILE *
INTO TABLE tmp_indexmap
APPEND
FIELDS TERMINATED BY "|" OPTIONALLY ENCLOSED BY '"'
TRAILING NULLCOLS
(Indexmapfilename,
 IsfAssignedtextno,
 Maintextorpublcnno ,
 Textdivision,
 TextSubDivision,
 Textview
)
