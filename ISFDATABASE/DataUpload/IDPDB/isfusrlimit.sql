create  table isfusrlimit
(NO_MAX_USERS  number(3),
sessiontime number(3) default 30)
/
insert into isfusrlimit
values(20,30)
/
COMMIT;
