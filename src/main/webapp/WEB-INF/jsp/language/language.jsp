<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<div class="viewmycart">

<display:table name="page.result" partialList="true" pagesize="${page.pageSize}" size="${page.totalCount}" form="displayForm" sort="external" id="language" export="true" requestURI="language!search.action" class="displayTagGrid">
	<display:caption>Language</display:caption>
	<display:column title="" headerClass="tablehead">${language_rowNum}</display:column>
	<display:column property="oid" title="Language ID" headerClass="tablehead"/>
	<display:column property="name" title="Language" headerClass="tablehead"/>
	<display:column title="Function" headerClass="tablehead">
		<input type="button" onclick="editLanguage('${language.oid}','${language.name}');" value="Edit" />
		<c:if test="${!language.system}">
			<input type="button" onclick="deleteLanguage('${language.oid}')" value="Delete" />
		</c:if>
	</display:column>
</display:table>
</div>