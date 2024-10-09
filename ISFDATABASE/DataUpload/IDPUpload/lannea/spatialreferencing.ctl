LOAD DATA
INFILE *
INTO TABLE tmp_spatialreferencing
APPEND
FIELDS TERMINATED BY "|" OPTIONALLY ENCLOSED BY '"'
TRAILING NULLCOLS
(referencefile,
 detailedfile,
 detailedleft,
 detailedbottom,
 detailedwidth,
 detailedheight)
