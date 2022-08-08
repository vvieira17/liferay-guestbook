create index IX_798C30B6 on Entry (guestBookId, groupId);
create index IX_756A1B8C on Entry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_A59C150E on Entry (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_A395F463 on Guestbook (groupId);
create index IX_299BA25B on Guestbook (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_DACF039D on Guestbook (uuid_[$COLUMN_LENGTH:75$], groupId);