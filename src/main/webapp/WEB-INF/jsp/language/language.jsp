<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<display:table name="languages" partialList="true" pagesize="5" size="20" form="displaytagform" sort="external" id="language" export="true" requestURI="language!search.action">
	<display:caption>Language</display:caption>
	<display:column title="">${language_rowNum}</display:column>
	<display:column property="id" title="Language ID" />
	<display:column property="name" title="Language" />
	<display:column title="Function">
		<a href="javascript:editLanguage('${language.oid}','${language.id}','${language.name}');">Edit</a>
		<a href="javascript:deleteLanguage('${language.oid}');">Delete</a>
	</display:column>
</display:table>