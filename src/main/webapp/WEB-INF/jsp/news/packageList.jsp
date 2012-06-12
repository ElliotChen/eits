<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<display:table name="${exportPackages.result}" partialList="true" pagesize="${exportPackages.pageSize}" size="${exportPackages.totalCount}" form="exportPackageForm" sort="external" id="article" requestURI="news!searchExportPackage.action" style="width:100%">
	<display:column title="SELECT" headerClass="tablehead">
		<input type="checkbox" name="packageOids" />
	</display:column>
	<display:column property="oid" title="ID" headerClass="tablehead"/>
	<display:column property="news" title="ZyTech News" headerClass="tablehead"/>
	<display:column title="Export Date" headerClass="tablehead">
		<fmt:formatDate value="${article.exportDate}" pattern="yyyy/MM/dd"/>
	</display:column>
	<display:column title="Duration" headerClass="tablehead">
		<fmt:formatDate value="${article.beginDate}" pattern="yyyy/MM/dd"/>~<fmt:formatDate value="${article.endDate}" pattern="yyyy/MM/dd"/>
	</display:column>
	<display:column property="articleIdList" title="Article ID" headerClass="tablehead"/>
</display:table>