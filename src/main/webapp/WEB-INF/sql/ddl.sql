drop sequence seq_reply;
drop sequence seq_board;
drop sequence seq_member;
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
 password char(64) not null,
 regdate date default sysdate not null,
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
  
commit;