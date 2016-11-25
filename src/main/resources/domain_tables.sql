create table municipality_v_%s (
	municipality_id nvarchar(32) not null,
	municipality_name varchar(200)
)
create table street_name_v_%s (
	street_name_id nvarchar(32) not null,
	street_name varchar(200)
)

insert into municipality_v_%s SELECT DISTINCT CONVERT(NVARCHAR(32),HASHBYTES('SHA1', municipality),2) , municipality from v_address

insert into street_name_v_%s SELECT DISTINCT CONVERT(NVARCHAR(32), HASHBYTES('SHA1', street_name), 2) as street_name_id, street_name from v_address





