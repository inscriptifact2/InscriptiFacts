update  photoobject set 
PHOTODESCRIPTIVETITLE=replace(PHOTODESCRIPTIVETITLE,'�','-'),
THUMBNAILHEADER = replace(THUMBNAILHEADER,'�','-'),
PHOTOGRAPHDESCRIPTION = replace(PHOTOGRAPHDESCRIPTION,'�','-')
/
commit
/
exit