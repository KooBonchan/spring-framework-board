create sequence seq_board;
drop sequence seq_board;
drop table board;
create table board(
  idx number(10, 0) not null,
  title varchar2(200) not null,
  content varchar2(2000) not null,
  writer varchar2(50) not null,
  regdate date default sysdate,
  updatedate date default sysdate,
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

commit;

create index idx_board_title on board(lower(title));
create index idx_board_writer on board(lower(writer));