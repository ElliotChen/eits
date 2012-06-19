alter table T_EITS_ARTICLE add AVG_RATE NUMBER(8,2);
update T_EITS_ARTICLE set AVG_RATE = 0;
update T_EITS_ARTICLE set AVG_RATE = ((RATE_1 + RATE_2*2 + RATE_3*3 + RATE_4*4 + RATE_5*5) / (RATE_1 + RATE_2 + RATE_3 + RATE_4 + RATE_5)) WHERE (RATE_1 + RATE_2 + RATE_3 + RATE_4 + RATE_5) > 0;
alter table T_EITS_EXPORT_PACKAGE add OID_LIST VARCHAR2(500CHAR);
alter table T_EITS_EXPORT_PACKAGE add NEWS_ID_LIST VARCHAR2(500CHAR);