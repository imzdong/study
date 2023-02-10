create table userinfo(
    uid integer primary key autoincrement,
    username varchar(64) null,
    department varchar(64) null,
    created date null
);

create table userdetail(
     uid int(10) null,
     intro text null,
     profile text null,
     primary key (uid)
);

