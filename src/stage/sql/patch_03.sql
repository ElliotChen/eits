alter table T_EITS_ARTICLE drop column FIRMWARE_OID;
alter table T_EITS_ARTICLE add FIRMWARE varchar2(50CHAR);

alter table T_EITS_ARTICLE modify PRODUCT VARCHAR2(1000CHAR);

alter table T_EITS_ARTICLE drop ( 
	SCENARIO,
	STEP,
	VERIFICATION,
	PROBLEM,
	SOLUTION,
	PROCEDURE_DATA);

alter table T_EITS_ARTICLE add (
	SCENARIO CLOB,
	STEP CLOB,
	VERIFICATION CLOB,
	PROBLEM CLOB,
	SOLUTION CLOB,
	PROCEDURE_DATA CLOB
);