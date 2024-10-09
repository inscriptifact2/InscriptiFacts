LOAD DATA
INFILE *
APPEND 
INTO TABLE TMP_PHOTOOBJECT
FIELDS TERMINATED BY "|" OPTIONALLY ENCLOSED BY '"'
TRAILING NULLCOLS
(
 ISFDigitalObjectIdentifier	
,PhotographIDentificationNo	
,ThumbnailHeader		
,PhotoDescriptiveTitle		
,PhotographDescription		
,AncientTextRepresented	
,ISFAssignedTextNo		
,TextDivision			
,TextDivisionSubDivision	
,TextVIew			
,TextVIew1			
,TextVIew2			
,TextVIew3			
,TextVIew4			
,TextVIew5			
,TextVIew6			
,TextVIew7			
,TextVIew8			
,TextVIew9			
,TypeOfItemCateloged		
,PhotoTextOrPublcnNoNote	
,DateOfPhotograph		
,Photographer1			
,Photographer2			
,Photographer3			
,Photographer4			
,Photographer5			
,RightsPhysicalObject		
,RightsPhysicalObject1		
,RightsPhysicalObject2		
,RightsPhysicalObject3		
,RightsPhysicalObject4		
,IsPartOfWSRProject		
,PhotoMuseumAccessionNo        
,PhotoMuseumAccessionNo1       
,PhotoMuseumAccessionNo2       
,PhotoMuseumAccessionNo3       
,PhotoMuseumAccessionNo4       
,PhotoMuseumAccessionNo5       
,PhotoMuseumAccessionNo6       
,PhotoMuseumAccessionNo7       
,PhotoMuseumAccessionNo8       
,PhotoMuseumAccessionNo9       
,PhotoMuseumAccessionNo10      
,PhotoMuseumAccessionNo11      
,PhotoMuseumAccessionNo12      
,PhotoMuseumAccessionNo13      
,PhotoMuseumAccessionNo14      
,PhotoMuseumAccessionNo15      
,PhotoMuseumAccessionNo16      
,PhotoMuseumAccessionNo17      
,PhotoMuseumAccessionNo18      
,PhotoMuseumAccessionNo19      
,PhotoMuseumAccessionNo20      
,PhotoMuseumAccessionNo21      
,PhotoMuseumAccessionNo22      
,PhotoMuseumAccessionNo23      
,PhotoMuseumAccessionNo24      
,PhotoMuseumAccessionNoNote    
,Colaborator1			
,Colaborator2			
,Colaborator3			
,Colaborator4			
,Colaborator5			
,PhotoDimensions		
,MagnificationCode		
,FilmTypeCode
,DateOfDigitalConversion	
,DigitalObjectFormat		
,ArchivalFileSize		
,ArchivalFileResolution	
,CompressedFormatExtension	
,CompressionRatio		
,CompressionRoutine		
,DigitalObjectTypeNote		
,DigitizationEquipmentSpecs	
,RightsDigitalObject		
,RighsPhotograph		
,SCRIPTNOTE                    
,Script			
,ScriptSubLevel		
,Script1			
,Script1SubLevel		
,Script2			
,Script2SubLevel		
,Script3			
,Script3SubLevel		
,LANGUAGENOTE                  
,Language			
,Language1			
,Language2			
,Language3			
,ISFFindSite			
,RelevantTimeLine		
,NamedTimeperiod		
,NamedTimeperiod1		
,MEDIUM                        
)