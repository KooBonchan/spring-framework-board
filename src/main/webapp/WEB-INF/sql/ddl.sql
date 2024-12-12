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
  '0a6518cefc973b97cee5fe934d8e47ec40bb24702b3b4122d429017db2531be3');
  -- NathanEast
insert into member (idx, username, password)
values (seq_member.nextval, 'TSQ',
  'd5117f2804ff59d68646c7af0597148b594844ba353a1eb0ac87c1eca5b9bd36');
  -- SutohMitsuru
insert into member (idx, username, password)
values (seq_member.nextval, 'Casiopea',
  '28a771b935b814b736de61426e1702aca8823519fc5bd33dd81c33ac9f227ace');
  -- NaruseYoshihiro
insert into member (idx, username, password)
values (seq_member.nextval, 'Vincen Garcia',
  '3767a0a7dad6216feb3ec0db008131f4264e15f544bc3d054e30672e136b3fa6');
  -- VincenGarcia

commit;