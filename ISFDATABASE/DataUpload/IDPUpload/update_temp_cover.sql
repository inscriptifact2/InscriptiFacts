create or replace procedure update_temp_cover is
cursor timeCur  is select ISFASSIGNEDTEXTNO, NAMEDTIMEPERIOD, RELEVANTTIMELINE from textobject ;
ctr integer;
begin 
	for ctr in timeCur loop
		begin
		update textobject set TEMPORALCOVERAGERANGE = temp_cov1(ctr.NAMEDTIMEPERIOD,ctr.RELEVANTTIMELINE) where ISFASSIGNEDTEXTNO = ctr.ISFASSIGNEDTEXTNO;
		commit;
		end;
	end loop;
end update_temp_cover;

        