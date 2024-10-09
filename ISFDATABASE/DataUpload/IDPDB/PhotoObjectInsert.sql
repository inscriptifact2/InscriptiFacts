CREATE OR REPLACE
procedure Photoobjectinsert is                                                  
                                                                                
cursor Curphtoobjidentifier is                                                  
       select distinct ISFDIGITALOBJECTIDENTIFIER from tmp_photoobject;         
cursor CurPhotoObjinsert is                  
		select *                                                                      
		from 	tmp_photoobject ;                                                        
    --where ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier;            
    --  where ISFDIGITALOBJECTIDENTIFIER = 'ISF_DO_00724';                      
                                                                                
Cursor CurAncientTextRepresented(MyISFDigitalObjectIdentifier Char) is          
		select 	distinct AncientTextRepresented                                       
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and AncientTextRepresented is not null ;                                     
--			and rownum < 31 ;                                                          
                                                                                
Cursor CurISFAssignedTextNo(MyISFDigitalObjectIdentifier Char) is               
		select 	distinct ISFAssignedTextNo                                            
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and ISFAssignedTextNo is not null ;                                          
--			and rownum < 31 ;                                                          
                                                                                
--Cursor CurMuseumAccessionNo(MyISFDigitalObjectIdentifier Char) is             
--		select 	distinct MuseumAccessionNo                                          
--		from 	tmp_photoobject                                                       
--		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier            
--			and MuseumAccessionNo is not null ;                                        
---			and rownum < 16 ;                                                         
                                                                                
Cursor CurPhotoTextOrPublcnNoNote(MyISFDigitalObjectIdentifier Char) is         
		select 	distinct PhotoTextOrPublcnNoNote                                      
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and PhotoTextOrPublcnNoNote is not null ;                                    
--			and rownum < 31 ;                                                          
                                                                                
                                                                                
Cursor CurRightsPhysicalObject(MyISFDigitalObjectIdentifier Char) is            
select 	distinct RightsPhysicalObject,RightsPhysicalObject1,RightsPhysicalObject2,RightsPhysicalObject3,RightsPhysicalObject4  
from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and RightsPhysicalObject is not null ;                                       
---             	and rownum < 6 ;                                               
                                                                                
Cursor CurScript(MyISFDigitalObjectIdentifier Char) is                          
		select 	distinct Script,Script1,Script2,Script3                               
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and Script is not null ;                                                     
---  		        and rownum < 5 ;                                                 
                                                                                
                                                                                
Cursor CurScriptSubLevel(MyISFDigitalObjectIdentifier Char) is                  
		select 	distinct ScriptSubLevel,Script1SubLevel,Script2SubLevel,substr(Script3SubLevel,1,30) Script3SubLevel                                                  
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and ScriptSubLevel is not null;                                              
-----			and rownum < 5 ;                                                        
                                                                                
Cursor CurTextDivision(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct TextDivision                                                 
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and TextDivision is not null ;                                               
--			and rownum < 101 ;                                                         
                                                                                
                                                                                
Cursor CurTextDivisionSubDivision(MyISFDigitalObjectIdentifier Char) is         
		select 	distinct TextDivisionSubDivision                                      
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and TextDivisionSubDivision is not null ;                                    
---			and rownum < 101 ;                                                        
                                                                                
Cursor CurTextVIew(MyISFDigitalObjectIdentifier Char) is                        
 select  distinct TextVIew,TextVIew1,TextVIew2,TextVIew3,TextVIew4,TextVIew5,TextVIew6,                                                                         
                                                                                
         TextVIew7,TextVIew8,TextVIew9                                          
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and TextVIew is not null ;                                                   
---			and rownum < 76 ;                                                         
                                                                                
                                                                                
Cursor CurLanguage(MyISFDigitalObjectIdentifier Char) is                        
		select 	distinct Language,Language1,Language2,Language3                       
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and Language is not null ;                                                   
---			and rownum < 5 ;                                                          
                                                                                
Cursor CurMyPhotoMuseumAccessionNo(MyISFDigitalObjectIdentifier Char) is        
                                                                                
 select distinct PHOTOMUSEUMACCESSIONNO,PHOTOMUSEUMACCESSIONNO1,PhotoMuseumAccessionNo2,PhotoMuseumAccessionNo3,PhotoMuseumAccessionNo4,PhotoMuseumAccessionNo5,PhotoMuseumAccessionNo6,PhotoMuseumAccessionNo7,PhotoMuseumAccessionNo8,PhotoMuseumAccessionNo9,PhotoMuseumAccessionNo10,                                                                         
                     PhotoMuseumAccessionNo11,PhotoMuseumAccessionNo12,PhotoMuseumAccessionNo13,PhotoMuseumAccessionNo14,PhotoMuseumAccessionNo15,PhotoMuseumAccessionNo16,                                                                     
                     PhotoMuseumAccessionNo17,PhotoMuseumAccessionNo18,PhotoMuseumAccessionNo19,PhotoMuseumAccessionNo20,PhotoMuseumAccessionNo21,PhotoMuseumAccessionNo22,                                                                     
                     PhotoMuseumAccessionNo23,substr(PhotoMuseumAccessionNo24,1,50) PhotoMuseumAccessionNo24                                                    
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and PHOTOMUSEUMACCESSIONNO is not null ;      

/****      to pick Single Occurance values   ***********/                      
                                                                                
Cursor CurARCHIVALFILERESOLUTION(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct ARCHIVALFILERESOLUTION                                                 
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and ARCHIVALFILERESOLUTION is not null ;                                               

Cursor CurARCHIVALFILESIZE(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct ARCHIVALFILESIZE                                                        
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and ARCHIVALFILESIZE    is not null ;                                               

Cursor CurCOMPRESSEDFORMATEXTENSION(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct COMPRESSEDFORMATEXTENSION                                                        
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and COMPRESSEDFORMATEXTENSION    is not null ;                                               

Cursor CurCOMPRESSIONRATIO(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct COMPRESSIONRATIO                                                        
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and COMPRESSIONRATIO    is not null ;                                               
                                                                                
Cursor CurCOMPRESSIONROUTINE(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct COMPRESSIONROUTINE                                                        
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and COMPRESSIONROUTINE    is not null ;     
                                          

Cursor CurDATEOFDIGITALCONVERSION(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct DATEOFDIGITALCONVERSION                                                                
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and DATEOFDIGITALCONVERSION    is not null ;     

Cursor CurDATEOFPHOTOGRAPH(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct DATEOFPHOTOGRAPH                                                                
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and DATEOFPHOTOGRAPH   is not null ;  
   
Cursor CurDIGITALOBJECTFORMAT(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct DIGITALOBJECTFORMAT                                                              
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and DIGITALOBJECTFORMAT  is not null ;    
 
Cursor CurDIGITALOBJECTTYPENOTE(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct DIGITALOBJECTTYPENOTE                                                                
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and DIGITALOBJECTTYPENOTE    is not null ;     

Cursor CurDIGITIZATIONEQUIPMENTSPECS(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct  DIGITIZATIONEQUIPMENTSPECS                                                                     
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and  DIGITIZATIONEQUIPMENTSPECS is not null ;     

Cursor CurFILMTYPECODE(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct  FILMTYPECODE                                                                        
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and  FILMTYPECODE  is not null ;     

Cursor CurISPARTOFWSRPROJECT(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct  ISPARTOFWSRPROJECT                                                                             
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and  ISPARTOFWSRPROJECT is not null ;    

Cursor CurISFFINDSITE(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct  ISFFINDSITE                                                                             
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and  ISFFINDSITE is not null ;  
  
Cursor CurMAGNIFICATIONCODE(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct MAGNIFICATIONCODE                                                                             
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and  MAGNIFICATIONCODE is not null ;  

Cursor CurPHOTODESCRIPTIVETITLE(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct PHOTODESCRIPTIVETITLE                                                                               
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and  PHOTODESCRIPTIVETITLE   is not null ;  

Cursor CurPHOTODIMENSIONS(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct PHOTODIMENSIONS                                                                               
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and  PHOTODIMENSIONS   is not null ;  

Cursor CurPHOTOGRAPHDESCRIPTION(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct PHOTOGRAPHDESCRIPTION                                                                                 
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and  PHOTOGRAPHDESCRIPTION     is not null ;  

Cursor CurPHOTOGRAPHIDENTIFICATIONNO(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct  PHOTOGRAPHIDENTIFICATIONNO                                                                                
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and  PHOTOGRAPHIDENTIFICATIONNO  is not null ;  

Cursor CurRELEVANTTIMELINE(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct  RELEVANTTIMELINE                                                                                
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and  RELEVANTTIMELINE  is not null ;  

Cursor CurRIGHTSDIGITALOBJECT(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct  RIGHTSDIGITALOBJECT                                                                                    
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and  RIGHTSDIGITALOBJECT   is not null ;  
Cursor CurRIGHSPHOTOGRAPH(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct  RIGHSPHOTOGRAPH                                                                                    
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and  RIGHSPHOTOGRAPH   is not null ;  

Cursor CurTHUMBNAILHEADER(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct  THUMBNAILHEADER                                                                                           
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and  THUMBNAILHEADER    is not null ;  

Cursor CurTYPEOFITEMCATELOGED(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct  TYPEOFITEMCATELOGED                                                                                           
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and  TYPEOFITEMCATELOGED    is not null ;  

Cursor CurMEDIUM (MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct   MEDIUM                                                                                            
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and   MEDIUM     is not null ;  

Cursor CurLANGUAGENOTE(MyISFDigitalObjectIdentifier Char) is                    
		select 	max(LANGUAGENOTE) LANGUAGENOTE                                                                                         
		from 	tmp_photoobject
		group by ISFDigitalObjectIdentifier
		having 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier;   
------			and LANGUAGENOTE     is not null ;  


cursor CurSCRIPTNOTE(MyISFDigitalObjectIdentifier Char) is                    
		select 	max(SCRIPTNOTE)   SCRIPTNOTE                                                                                      
		from 	tmp_photoobject    
		group by ISFDigitalObjectIdentifier
	        having ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier ;             
----			 and SCRIPTNOTE    is not null ;  

cursor CurPHOTOMUSEUMACCESSIONNONOTE(MyISFDigitalObjectIdentifier Char) is                    
		select 	distinct  PHOTOMUSEUMACCESSIONNONOTE                                                                                         
		from 	tmp_photoobject                                                         
		where 	ISFDigitalObjectIdentifier = MyISFDigitalObjectIdentifier              
			and PHOTOMUSEUMACCESSIONNONOTE    is not null ;  
--******End of Single Occurance 

                                                                                
MyAncientTextRepresented	AncientTextRepresented_va  :=  AncientTextRepresented_va() ;                                                                           
                                                                                
MyISFAssignedTextNo		ISFAssignedTextNo_va := ISFAssignedTextNo_va() ;           
MyMuseumAccessionNo		MuseumAccessionNo_va := MuseumAccessionNo_va() ;           
MyPhotoTextOrPublcnNoNote	PhotoTextOrPublcnNoNote_va := PhotoTextOrPublcnNoNote_va() ;                                                                          
                                                                                
MyRightsPhysicalObject		RightsPhysicalObject_va := RightsPhysicalObject_va() ;  
MyScript			Script_va := Script_va() ;                                           
MyScriptSubLevel		ScriptSublevel_va := ScriptSublevel_va() ;                    
MyTextDivision			TextDivision_va := TextDivision_va() ;                         
MyTextDivisionSubDivision	TextDivisionSubDivision_va := TextDivisionSubDivision_va() ;                                                                          
                                                                                
MyTextVIew			textview_va := textview_va() ;                                     
MyLanguage			Language_va := language_va() ;                                     
MyPhotoMuseumAccessionNo        PhotoMuseumAccessionNo_va  := PhotoMuseumAccessionNo_va();                                                                      
                                                                                
                                                                                
MyCtr				integer ;                                                              
                                                                                
Mycount				integer ;                                                            
                                                                                
MyErrorCode			number ;                                                          
MyErrorMessage			varchar2(200) ;                                                
chkISFDigitalObjectIdentifier   char(13);   

-- **  Single Occurance Mem Var
MyARCHIVALFILERESOLUTION       char(9);
MyARCHIVALFILESIZE             char(8);
MyCOMPRESSEDFORMATEXTENSION    char(4);
MyCOMPRESSIONRATIO             char(8);
MyCOMPRESSIONROUTINE           char(8);
MyDATEOFDIGITALCONVERSION      char(10);
MyDATEOFPHOTOGRAPH             char(10);
MyDIGITALOBJECTFORMAT          char(20);
MyDIGITALOBJECTTYPENOTE          char(20);    
MyDIGITIZATIONEQUIPMENTSPECS     char(30);
MyFILMTYPECODE                   char(2);
MyISPARTOFWSRPROJECT             char(40);
MyISFFINDSITE                    char(40);
MyMAGNIFICATIONCODE              char(1);
MyPHOTODESCRIPTIVETITLE          char(50);
MyPHOTODIMENSIONS                char(25);
MyPHOTOGRAPHDESCRIPTION          CHAR(200);
MyPHOTOGRAPHIDENTIFICATIONNO     CHAR(20);
MyRELEVANTTIMELINE               CHAR(20);	
MyRIGHTSDIGITALOBJECT            CHAR(60);
MyRIGHSPHOTOGRAPH                CHAR(60);
MyTHUMBNAILHEADER                CHAR(50);
MyTYPEOFITEMCATELOGED            CHAR(20);
MyMEDIUM                         CHAR(20);
MyLANGUAGENOTE                   CHAR(50);
MySCRIPTNOTE                     CHAR(50);
MyPHOTOMUSEUMACCESSIONNONOTE     CHAR(75);                                                                            
begin                                                                           
for i in CurPhotoObjinsert loop
	begin                                                                          
		dbms_output.put_line(i.ISFDigitalObjectIdentifier) ;                          
                                                                                
		MyAncientTextRepresented := AncientTextRepresented_va() ;                     
		MyISFAssignedTextNo := ISFAssignedTextNo_va() ;                               
		MyMuseumAccessionNo := MuseumAccessionNo_va() ;                               
                MyPhotoMuseumAccessionNo := PhotoMuseumAccessionNo_va();        
		MyPhotoTextOrPublcnNoNote	:= PhotoTextOrPublcnNoNote_va() ;                   
		MyRightsPhysicalObject := RightsPhysicalObject_va() ;                         
		MyScript := Script_va() ;                                                     
		MyScriptSubLevel := ScriptSublevel_va() ;                                     
		MyTextDivision := TextDivision_va() ;                                         
		MyTextDivisionSubDivision := TextDivisionSubDivision_va() ;                   
		MyTextVIew := textview_va() ;                                                 
		MyLanguage := Language_va() ;                                                 
		Mycount := 0 ;                                                                
		MyCtr := 1 ;                                                                  
		for j in CurAncientTextRepresented(i.ISFDigitalObjectIdentifier) loop         
			MyAncientTextRepresented.extend ;                                            
			MyAncientTextRepresented(MyCtr) := j.AncientTextRepresented ;                
			MyCtr := MyCtr + 1 ;                                                         
		end loop ;                                                                    
                                                                                
		MyCtr := 1 ;                                                                  
		for j in CurISFAssignedTextNo(i.ISFDigitalObjectIdentifier) loop              
			MyISFAssignedTextNo.extend ;                                                 
			MyISFAssignedTextNo(MyCtr) := j.ISFAssignedTextNo ;                          
			MyCtr := MyCtr + 1 ;                                                         
		end loop ;                                                                    
                                                                                
                                                                                
--		MyCtr := 1 ;                                                                
---		for j in CurMuseumAccessionNo(i.ISFDigitalObjectIdentifier) loop           
--			MyMuseumAccessionNo.extend ;                                               
--			MyMuseumAccessionNo(MyCtr) := substr(j.MuseumAccessionNo,1,30) ;           
--			MyCtr := MyCtr + 1 ;                                                       
--		end loop ;                                                                  
                                                                                
--		MyCtr := 1 ;                                                                
		for j in CurMyPhotoMuseumAccessionNo(i.ISFDigitalObjectIdentifier) loop       
	        	MyPhotoMuseumAccessionNo.extend(25);                                  
--                     MyPhotoMuseumAccessionNo(MyCtr) :=substr(j.PhotoMuseumAccessionNo,1,30);                                                                 
                                                                                
--			MyCtr := MyCtr + 1 ;                                                       
MyPhotoMuseumAccessionNo := PhotoMuseumAccessionNo_va(j.PhotoMuseumAccessionNo,j
.PhotoMuseumAccessionNo1,j.PhotoMuseumAccessionNo2,j.PhotoMuseumAccessionNo3,j.PhotoMuseumAccessionNo4,                                                         
                                                                                
                      j.PhotoMuseumAccessionNo5,j.PhotoMuseumAccessionNo6,j.PhotoMuseumAccessionNo7,j.PhotoMuseumAccessionNo8,j.PhotoMuseumAccessionNo9,j.PhotoMuseumAccessionNo10,                                                             
                                                                                
                     j.PhotoMuseumAccessionNo11,j.PhotoMuseumAccessionNo12,j.PhotoMuseumAccessionNo13,j.PhotoMuseumAccessionNo14,j.PhotoMuseumAccessionNo15,j.PhotoMuseumAccessionNo16,                                                         
                                                                                
                     j.PhotoMuseumAccessionNo17,j.PhotoMuseumAccessionNo18,j.PhotoMuseumAccessionNo19,j.PhotoMuseumAccessionNo20,j.PhotoMuseumAccessionNo21,j.PhotoMuseumAccessionNo22,                                                         
                                                                                
                     j.PhotoMuseumAccessionNo23,j.PhotoMuseumAccessionNo24);    
                   exit;                                                        
		end loop ;                                                                    
                                                                                
		MyCtr := 1 ;                                                                  
		for j in CurPhotoTextOrPublcnNoNote(i.ISFDigitalObjectIdentifier) loop        
			MyPhotoTextOrPublcnNoNote.extend ;                                           
			MyPhotoTextOrPublcnNoNote(MyCtr) := substr(j.PhotoTextOrPublcnNoNote,1,75) ; 
			MyCtr := MyCtr + 1 ;                                                         
		end loop ;                                                                    
                                                                                
--		MyCtr := 1 ;                                                                
		for j in CurRightsPhysicalObject(i.ISFDigitalObjectIdentifier) loop           
			MyRightsPhysicalObject.extend(5) ;                                           
MyRightsPhysicalObject :=RightsPhysicalObject_va(j.RightsPhysicalObject,j.RightsPhysicalObject1,j.RightsPhysicalObject2,j.RightsPhysicalObject3,j.RightsPhysicalObject4);                                                                       
                                                                                
--			MyCtr := MyCtr + 1 ;                                                       
                    exit;                                                       
		end loop ;                                                                    
                                                                                
--		MyCtr := 1 ;                                                                
		for j in CurScript(i.ISFDigitalObjectIdentifier) loop                         
			MyScript.extend(4) ;                                                         
--			MyScript(MyCtr) := j.Script ;                                              
                MyScript := Script_va(j.Script,j.Script1,j.Script2,j.Script3);  
--			MyCtr := MyCtr + 1 ;                                                       
                 exit;                                                          
		end loop ;                                                                    
                                                                                
---		MyCtr := 1 ;                                                               
		for j in CurScriptSubLevel(i.ISFDigitalObjectIdentifier) loop                 
			MyScriptSubLevel.extend(4) ;                                                 
	MyScriptSubLevel := ScriptSublevel_va(j.ScriptSubLevel,j.Script1SubLevel,j.Script2SubLevel,j.Script3SubLevel);                                                 
                                                                                
--			MyCtr := MyCtr + 1 ;                                                       
                   exit;                                                        
		end loop ;                                                                    
		MyCtr := 1 ;                                                                  
		for j in CurTextDivision(i.ISFDigitalObjectIdentifier) loop                   
			MyTextDivision.extend ;                                                      
			MyTextDivision(MyCtr) := trim(substr(j.TextDivision,1,40)) ;            
			MyCtr := MyCtr + 1 ;                                                         
		end loop ;                                                                    
                                                                                
		MyCtr := 1 ;                                                                  
		for j in CurTextDivisionSubDivision(i.ISFDigitalObjectIdentifier) loop        
			MyTextDivisionSubDivision.extend;                                            
			MyTextDivisionSubDivision(MyCtr) := j.TextDivisionSubDivision ;              
			MyCtr := MyCtr + 1 ;                                                         
		end loop ;                                                                    
                                                                                
--		MyCtr := 1 ;                                                                
		for j in CurTextVIew(i.ISFDigitalObjectIdentifier) loop                       
			MyTextVIew.extend(10) ;                                                      
			MyTextVIew := textview_va(j.TextVIew,j.TextVIew1,j.TextVIew2,j.TextVIew3,j.TextVIew4,                                                                        
                                                                                
			                j.TextVIew5,j.TextVIew6,j.TextVIew7,j.TextVIew8,j.TextVIew9);
                                                                                
                                                                                
--			MyCtr := MyCtr + 1 ;                                                       
                   exit;                                                        
		end loop ;                                                                    
                                                                                
---		MyCtr := 1 ;                                                               
		for j in CurLanguage(i.ISFDigitalObjectIdentifier) loop                       
			MyLanguage.extend(4) ;                                                       
			MyLanguage := Language_va(j.Language,j.Language1,j.Language2,j.Language3);   
----			MyCtr := MyCtr + 1 ;                                                     
                        exit;                                                   
		end loop ;              


        	for j in CurARCHIVALFILERESOLUTION(i.ISFDigitalObjectIdentifier) loop                       
 		 MyARCHIVALFILERESOLUTION := j.ARCHIVALFILERESOLUTION;   
		end loop ;              

        	for j in CurARCHIVALFILESIZE(i.ISFDigitalObjectIdentifier) loop                       
                  MyARCHIVALFILESIZE  := j.ARCHIVALFILESIZE;
		end loop ;              

        	for j in CurCOMPRESSEDFORMATEXTENSION(i.ISFDigitalObjectIdentifier) loop                       
	             MyCOMPRESSEDFORMATEXTENSION   := j.COMPRESSEDFORMATEXTENSION;
		end loop ;              

        	for j in CurCOMPRESSIONRATIO(i.ISFDigitalObjectIdentifier) loop                       
	             MyCOMPRESSIONRATIO := j.COMPRESSIONRATIO;
	       	end loop ;              

        	for j in CurCOMPRESSIONROUTINE(i.ISFDigitalObjectIdentifier) loop                       
                     MyCOMPRESSIONROUTINE := j.COMPRESSIONROUTINE;
	       		end loop ;              

        	for j in CurDATEOFDIGITALCONVERSION(i.ISFDigitalObjectIdentifier) loop                       
                   MyDATEOFDIGITALCONVERSION  :=   j.DATEOFDIGITALCONVERSION;
	       		end loop ;              

        	for j in CurDATEOFPHOTOGRAPH(i.ISFDigitalObjectIdentifier) loop                       
                   MyDATEOFPHOTOGRAPH  := j.DATEOFPHOTOGRAPH;
	       		end loop ;              

        	for j in CurDIGITALOBJECTFORMAT(i.ISFDigitalObjectIdentifier) loop                       
                      MyDIGITALOBJECTFORMAT  := j.DIGITALOBJECTFORMAT;
	       	end loop ;              

        	for j in CurDIGITALOBJECTTYPENOTE(i.ISFDigitalObjectIdentifier) loop                       
          	       MyDIGITALOBJECTTYPENOTE  := j.DIGITALOBJECTTYPENOTE;
	       		end loop ;              

        	for j in CurDIGITIZATIONEQUIPMENTSPECS(i.ISFDigitalObjectIdentifier) loop                       
               	       MyDIGITIZATIONEQUIPMENTSPECS  := j.DIGITIZATIONEQUIPMENTSPECS ;
	       	end loop ;              

        	for j in CurFILMTYPECODE(i.ISFDigitalObjectIdentifier) loop                       
                      MyFILMTYPECODE :=   j.FILMTYPECODE;
	       	end loop ;              

        	for j in CurISPARTOFWSRPROJECT(i.ISFDigitalObjectIdentifier) loop                       
                     MyISPARTOFWSRPROJECT :=j.ISPARTOFWSRPROJECT;
	       	end loop ;              

        	for j in CurISFFINDSITE(i.ISFDigitalObjectIdentifier) loop                       
        	       MyISFFINDSITE     := j.ISFFINDSITE;
	       	end loop ;              

        	for j in CurMAGNIFICATIONCODE(i.ISFDigitalObjectIdentifier) loop                       
                    MyMAGNIFICATIONCODE  := j.MAGNIFICATIONCODE;
       		end loop ;              

        	for j in CurPHOTODESCRIPTIVETITLE(i.ISFDigitalObjectIdentifier) loop                       
         	   MyPHOTODESCRIPTIVETITLE := j.PHOTODESCRIPTIVETITLE;
	       	end loop ;              

        	for j in CurPHOTODIMENSIONS(i.ISFDigitalObjectIdentifier) loop                       
	              MyPHOTODIMENSIONS  :=j.PHOTODIMENSIONS ;           
       		end loop ;              

        	for j in CurPHOTOGRAPHDESCRIPTION(i.ISFDigitalObjectIdentifier) loop                       
                   MyPHOTOGRAPHDESCRIPTION  := j.PHOTOGRAPHDESCRIPTION;
       		end loop ;              

        	for j in CurPHOTOGRAPHIDENTIFICATIONNO(i.ISFDigitalObjectIdentifier) loop                       
               	       MyPHOTOGRAPHIDENTIFICATIONNO  := j.PHOTOGRAPHIDENTIFICATIONNO;
	       	end loop ;              

        	for j in CurRELEVANTTIMELINE(i.ISFDigitalObjectIdentifier) loop                       
              	       MyRELEVANTTIMELINE  := j.RELEVANTTIMELINE;
	       		end loop ;              

        	for j in CurRIGHTSDIGITALOBJECT(i.ISFDigitalObjectIdentifier) loop                       
                      MyRIGHTSDIGITALOBJECT  := j.RIGHTSDIGITALOBJECT;
       		end loop ;              

        	for j in CurRIGHSPHOTOGRAPH(i.ISFDigitalObjectIdentifier) loop                       
              	       MyRIGHSPHOTOGRAPH  := j.RIGHSPHOTOGRAPH;           
       		end loop ;              

        	for j in CurTHUMBNAILHEADER(i.ISFDigitalObjectIdentifier) loop                       
            	       MyTHUMBNAILHEADER  := j.THUMBNAILHEADER;            
		end loop ;              

        	for j in CurTYPEOFITEMCATELOGED(i.ISFDigitalObjectIdentifier) loop                       
	            MyTYPEOFITEMCATELOGED  := j.TYPEOFITEMCATELOGED;
	      	end loop ;              

        	for j in CurMEDIUM(i.ISFDigitalObjectIdentifier) loop                       
                    MyMEDIUM :=j.MEDIUM;                      
       		end loop ;              

        	for j in CurLANGUAGENOTE(i.ISFDigitalObjectIdentifier) loop                       
          	       MyLANGUAGENOTE  :=  j.LANGUAGENOTE;              
       		end loop ;              

        	for j in CurSCRIPTNOTE (i.ISFDigitalObjectIdentifier) loop                       
                    MySCRIPTNOTE  :=  j.SCRIPTNOTE ;             
       		end loop ;              

        	for j in CurPHOTOMUSEUMACCESSIONNONOTE(i.ISFDigitalObjectIdentifier) loop                       
        	       MyPHOTOMUSEUMACCESSIONNONOTE  := j.PHOTOMUSEUMACCESSIONNONOTE;
		end loop ;              

                                                                                
--if chkISFDigitalObjectIdentifier <> i.ISFDigitalObjectIdentifier  then          
     begin                                                                      
		insert into photoobject( ISFDIGITALOBJECTIDENTIFIER                           
					,ANCIENTTEXTREPRESENTED                                                    
					,ARCHIVALFILERESOLUTION                                                    
					,ARCHIVALFILESIZE                                                          
					,COLABORATOR                                                               
					,COMPRESSEDFORMATEXTENSION                                                 
					,COMPRESSIONRATIO                                                          
					,COMPRESSIONROUTINE                                                        
					,DATEOFDIGITALCONVERSION                                                 
					,DATEOFPHOTOGRAPH                                                          
					,DIGITALOBJECTFORMAT                                                       
					,DIGITALOBJECTTYPENOTE                                                     
					,DIGITIZATIONEQUIPMENTSPECS                                                
					,FILMTYPECODE                                                              
					,ISPARTOFWSRPROJECT                                                        
					,ISFASSIGNEDTEXTNO                                                         
					,ISFFINDSITE                                                               
					,LANGUAGE                                                                  
					,MAGNIFICATIONCODE                                                         
					,NAMEDTIMEPERIOD                                                           
					,PHOTODESCRIPTIVETITLE                                                     
					,PHOTODIMENSIONS                                                           
					,PHOTOTEXTORPUBLCNNONOTE                                                   
					,PHOTOGRAPHDESCRIPTION                                                     
					,PHOTOGRAPHIDENTIFICATIONNO                                                
					,PHOTOGRAPHER                                                              
					,RELEVANTTIMELINE                                                          
					,RIGHTSDIGITALOBJECT                                                       
					,RIGHSPHOTOGRAPH                                                           
					,RIGHTSPHYSICALOBJECT                                                      
					,SCRIPT                                                                    
					,SCRIPTSUBLEVEL                                                            
					,TEXTDIVISION                                                              
					,TEXTDIVISIONSUBDIVISION                                                   
					,TEXTVIEW                                                                  
					,THUMBNAILHEADER                                                           
					,TYPEOFITEMCATELOGED                                                       
                                        ,MEDIUM                                 
					,LANGUAGENOTE                                                              
                                        ,SCRIPTNOTE                             
                                        ,PHOTOMUSEUMACCESSIONNO                 
                                        ,PHOTOMUSEUMACCESSIONNONOTE             
                                        )                                       
				Values ( i.ISFDIGITALOBJECTIDENTIFIER                                       
					,MyANCIENTTEXTREPRESENTED                                                  
					,MyARCHIVALFILERESOLUTION                                                  
					,MyARCHIVALFILESIZE                                                        
					,COLABORATOR_va(i.colaborator1,i.colaborator2,i.colaborator3,i.colaborator4,i.colaborator5)                                                                
                                                                                
					,substr(MyCOMPRESSEDFORMATEXTENSION,1,4)                                   
					,MyCOMPRESSIONRATIO                                                        
					,substr(MyCOMPRESSIONROUTINE,1,8)                                          
			,to_char(to_date(Mydateofdigitalconversion,'dd-mm-yyyy'),'mm/yyyy')      
				,to_char(to_date(Mydateofphotograph,'dd-mm-yyyy'),'yyyy')  
--,substr(Mydateofdigitalconversion,0,7)
--,substr(Mydateofphotograph,length(Mydateofphotograph)-3,4)  
					,MyDIGITALOBJECTFORMAT                                                     
					,substr(MyDIGITALOBJECTTYPENOTE,1,20)                                      
					,substr(MyDIGITIZATIONEQUIPMENTSPECS,1,30)                                 
					,substr(MyFILMTYPECODE,1,2)                                                
					,MyISPARTOFWSRPROJECT                                                      
					,MyISFASSIGNEDTEXTNO                                                       
					,MyISFFINDSITE                                                             
					,MyLANGUAGE                                                                
					,MyMAGNIFICATIONCODE                                                       
                                        ,NAMEDTIMEPERIOD_VA(i.NAMEDTIMEPERIOD,i.NAMEDTIMEPERIOD1)                                                               
                                                                                
					,MyPHOTODESCRIPTIVETITLE    
					,MyPHOTODIMENSIONS                                                         
					,MyPHOTOTEXTORPUBLCNNONOTE                                                 
				 	,MyPHOTOGRAPHDESCRIPTION                                                  
					,MyPHOTOGRAPHIDENTIFICATIONNO                                              
					,PHOTOGRAPHER_va(i.photographer1,i.photographer2,i.photographer3,i.photographer4,i.photographer5)                                                          
                                                                                
					,substr(i.RELEVANTTIMELINE,1,20)                                           
					,MyRIGHTSDIGITALOBJECT                                                     
					,MyRIGHSPHOTOGRAPH                                                         
					,MyRIGHTSPHYSICALOBJECT                                                    
					,MySCRIPT                                                                  
					,MySCRIPTSUBLEVEL                                                          
					,MyTEXTDIVISION                                                            
					,MyTEXTDIVISIONSUBDIVISION                                                 
					,MYTEXTVIEW                                                                
--					,TEXTVIEW_VA(i.TEXTVIEW,i.TEXTVIEW1,i.TEXTVIEW2,i.TEXTVIEW3,i.TEXTVIEW4,i.TEXTVIEW5,i.TEXTVIEW6,i.TEXTVIEW7,i.TEXTVIEW8,i.TEXTVIEW9)                     
                                                                                
					,MyTHUMBNAILHEADER                                                         
					,MyTYPEOFITEMCATELOGED                                                     
                                        ,MyMEDIUM                               
 					,MyLANGUAGENOTE                                                           
					,MySCRIPTNOTE                                                              
					,MYPHOTOMUSEUMACCESSIONNO                                                  
                                        ,MyPHOTOMUSEUMACCESSIONNONOTE) ;        
			commit ;                                                                     
		exception                                                                     
			When others then                                                             
				MyErrorCode := SQLCODE ;                                                    
				MyErrorMessage := SQLERRM ;                                                 
				dbms_output.put_line('Error ' || i.ISFDigitalObjectIdentifier) ;            
				insert into err_photoobject                                                 
					values(  i.ISFDigitalObjectIdentifier                                      
						,sysdate()                                                                

						,MyErrorCode                                                              
						,'Insert Failed'                                                          
						,MyErrorMessage) ;                                                        
				null ;                                                                      
		end ;                                                                         
--  end if;                                                                       
		exception                                                                     
			when others then                                                             
				MyErrorCode := SQLCODE ;                                                    
				MyErrorMessage := SQLERRM ;                                                 
				insert into err_photoobject                                                 
					values(  i.ISFDigitalObjectIdentifier                                      
						,sysdate()                                                                
						,mYErrorCode                                                              
						,'Data Errror'                                                            
						,MyErrorMessage) ;                                                        
				null ;                                                                      
                                                                                
		end ;                                                                         
		End loop ;                                                                    
end;
--End Photoobjectinsert;                                                          
/
