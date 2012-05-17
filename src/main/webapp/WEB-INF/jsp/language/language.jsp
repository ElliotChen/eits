<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<div class="viewmycart">
<s:form id="createForm" namespace="/" action="language!create" theme="simple">
<display:table name="page.result" partialList="true" pagesize="${page.pageSize}" size="${page.totalCount}" form="displayForm" sort="external" id="language" export="true" requestURI="language!search.action" class="displayTagGrid">
	<display:caption>Language</display:caption>
	<display:column title="" headerClass="tablehead">${language_rowNum}</display:column>
	<display:column property="oid" title="Language ID" headerClass="tablehead"/>
	<display:column property="name" title="Language" headerClass="tablehead"/>
	<display:column title="Function" headerClass="tablehead">
		<a href="javascript:editLanguage('${language.oid}','${language.name}');">Edit</a>
		<c:if test="${!language.system}">
			<a href="javascript:deleteLanguage('${language.oid}');">Delete</a>
		</c:if>
	</display:column>
	<display:footer>
		<tr>
			<td>&nbsp;</td>
			<td><input type="text" id="cmoid" name="oid" /></td>
			<td><input type="text" id="cmname" name="name" /></td>
			<td><input type="submit" value="Create"/> </td>
		</tr>
	</display:footer>
</display:table>
</s:form>
</div>