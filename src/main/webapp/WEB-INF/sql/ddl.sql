drop sequence seq_board_image;
drop sequence seq_reply;
drop sequence seq_board;
drop sequence seq_member;

drop table boardImage;
drop table memberAuth;
drop table reply;
drop table board;
drop table member;
create table board(
  idx number(10, 0) not null,
  title varchar2(200) not null,
  content varchar2(2000) not null,
  writer varchar2(50) not null,
  regdate date default sysdate not null,
  updatedate date default sysdate not null,
  replyCount number(5,0) default 0 not null,
  imageCount number(5,0) default 0 not null
  constraint pk_board_idx primary key (idx)
);
create sequence seq_board;
insert into board (idx, title, content, writer)
values (seq_board.nextval, '101 Eastbound','Bass: Nathan east','Fourplay');
insert into board (idx, title, content, writer)
values (seq_board.nextval, 'Bali run','Bass: Nathan east','Fourplay');
insert into board (idx, title, content, writer)
values (seq_board.nextval, 'Aprieta','Bass: Vincen Garcia','Vincen Garcia');
insert into board (idx, title, content, writer)
values (seq_board.nextval, 'Nap That Chap!','Bass: Sutoh Mitsuru','T-Square');

insert into board (idx, title, content, writer)
select seq_board.nextval, title, content, writer from board;


create index idx_board_title on board(lower(title));
create index idx_board_writer on board(lower(writer));


create table reply (
  idx number(10,0) not null,
  writer  varchar2(50) not null,
  content varchar2(250) not null,
  regdate date default sysdate not null,
  boardIdx number(10,0) not null,
  constraint pk_reply primary key (idx),
  constraint fk_reply_board foreign key (boardIdx)
    REFERENCES board(idx) on delete cascade
);

create index idx_reply on reply(boardIdx);

create sequence seq_reply;

insert into reply (idx, writer, content, boardIdx)
values (seq_reply.nextval, 'Vincen Garcia', 'I''ve been inspired', 1);
insert into reply (idx, writer, content, boardIdx)
values (seq_reply.nextval, 'Vincen Garcia', 'I''ve been inspired', 2);
insert into reply (idx, writer, content, boardIdx)
values (seq_reply.nextval, 'Vincen Garcia', 'I''ve been inspired', 4);
update board set
replyCount = 1
where idx = 1 or idx = 2 or idx = 4;


create sequence seq_member;
create table member(
 idx number(10,0) not null,
 username varchar2(50) unique,
 password varchar2(60) not null,
 regdate date default sysdate not null,
 enabled char(1) default '1' not null,
 constraint pk_member primary key (idx)
);

insert into member (idx, username, password)
values (seq_member.nextval, 'Fourplay',
  'NathanEast');
  -- NathanEast
insert into member (idx, username, password)
values (seq_member.nextval, 'TSQ',
  '3910e27da414e42aca57df9f6390d5c7958a4bf98bbf48d387e02e2bfcbd3ffb');
  -- SutohMitsuru
insert into member (idx, username, password)
values (seq_member.nextval, 'Casiopea',
  'NaruseYoshihiro');
  -- NaruseYoshihiro
insert into member (idx, username, password)
values (seq_member.nextval, 'Vincen Garcia',
  'VincenGarcia');
  -- VincenGarcia

create table memberAuth (
  username varchar2(50) not null,
  auth varchar2(50) not null,
  primary key (username, auth),
  constraint fk_member_auth foreign key(username)
    references member(username)
);


create table persistent_logins (
  username varchar2(64) not null,
  series varchar2(64) not null,
  token varchar2(64) not null,
  last_used timestamp not null
);


create table boardImage(
  idx number(10,0) primary key,
  boardIdx number(10,0),
  originalFileName varchar2(64) not null,
  realFileName varchar2(40) not null,
  filePath varchar2(40) not null,
  regDate date default sysdate not null,
  constraint fk_board_image foreign key(boardIdx)
    references board(idx) on delete set null
);

create index idx_board_image on boardImage(boardIdx);
create sequence seq_board_image;

insert into boardImage (idx, boardIdx, originalFileName, realFileName, filePath)
values (seq_board_image.nextval, 8, 'testfile', 'tteessttffiillee', '/t/e/s/t');

commit;

select * from board;