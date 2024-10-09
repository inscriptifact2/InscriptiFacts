LOAD DATA
INFILE *
INTO TABLE tmp_textaliases
APPEND
FIELDS TERMINATED BY "|" OPTIONALLY ENCLOSED BY '"'
TRAILING NULLCOLS
(Alttextorpublcnno,
 isfassignedtextno,
 maintextorpublcnno,
 textdescrptivetitle,
 Alttextorpublcnnoprefix,
 Alttextorpublcnnosuffix)

