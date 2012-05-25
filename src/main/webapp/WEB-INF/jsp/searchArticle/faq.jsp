<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<display:table name="${faqArticles.result}" partialList="true" pagesize="${faqArticles.pageSize}" size="${faqArticles.totalCount}" form="faqForm" sort="external" id="article" class="displayTagGrid">
	<display:column property="articleId.oid" title="ID" headerClass="tablehead"/>
	<display:column property="summary" title="Summary" headerClass="tablehead"/>
	<display:column title="Publish Date" headerClass="tablehead">
		<fmt:formatDate value="${article.publishDate}" pattern="yyyy/MM/dd"/>
	</display:column>
	<display:column property="hitCount" title="Hit Count" headerClass="tablehead"/>
	<display:column title="Function" headerClass="tablehead">
     <input type="button" value="View" onclick="viewArticle('${article.oid}');" />
    </display:column>
</display:table>
