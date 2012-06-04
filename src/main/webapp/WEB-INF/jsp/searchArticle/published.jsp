<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<display:table name="${publishedArticles.result}" partialList="true" pagesize="${publishedArticles.pageSize}" size="${publishedArticles.totalCount}" form="publishedForm" sort="external" id="article" class="displayTagGrid">
	<display:column property="articleId.oid" title="ID" headerClass="tablehead"/>
	<display:column property="summary" title="Summary" headerClass="tablehead"/>
	<display:column title="Publish Date" headerClass="tablehead">
		<fmt:formatDate value="${article.publishDate}" pattern="yyyy/MM/dd"/>
	</display:column>
	<display:column property="hitCount" title="View Count" headerClass="tablehead"/>
	<display:column title="Function" headerClass="tablehead">
		<input type="button" value="View" onclick="viewAjaxArticle('${article.oid}');" />
    </display:column>
</display:table>
