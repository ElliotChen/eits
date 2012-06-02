update T_EITS_LANGUAGE set IS_SYSTEM = 'N' WHERE IS_SYSTEM is null;
alter table T_EITS_ARTICLE modify PROJECT_CODE varchar2(100);
alter table T_EITS_ATTACHMENT modify CONTENT_TYPE varchar2(50);