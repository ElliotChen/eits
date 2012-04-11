<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<display:table name="faqArticles" pagesize="5">
	<display:column property="id" title="ID" />
	<display:column property="summary" title="Summary" />
</display:table>

<display:table name="latestArticles" pagesize="1" form="searchForm">
	<display:column property="id" title="ID" />
	<display:column property="summary" title="Summary" />
</display:table>