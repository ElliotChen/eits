<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<display:table name="page.result" partialList="true" pagesize="${page.pageSize}" size="${page.totalCount}" form="languageDisForm" sort="external" id="language" export="true" requestURI="language!search.action">
	<display:caption>Language</display:caption>
	<display:column title="">${language_rowNum}</display:column>
	<display:column property="oid" title="Language ID" />
	<display:column property="name" title="Language" />
	<display:column title="Function">
		<a href="javascript:editLanguage('${language.oid}','${language.name}');">Edit</a>
		<a href="javascript:deleteLanguage('${language.oid}');">Delete</a>
	</display:column>
</display:table>