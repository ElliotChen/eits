<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<display:table name="${unpublishedArticles.result}" partialList="true" pagesize="${unpublishedArticles.pageSize}" size="${unpublishedArticles.totalCount}" form="unpublishedForm" sort="external" id="article" export="true" requestURI="edit!searchUnpublished.action">
	<display:caption>Unpublished Articles</display:caption>
	<display:column property="articleId.oid" title="ID"/>
	<display:column property="summary" title="Summary" />
	<display:column property="entryUser" title="Agent"/>
	<display:column title="Entry Date">
		<fmt:formatDate value="${article.entryDate}" pattern="yyyy/MM/dd"/>
	</display:column>
	<display:column property="product" title="Prod. Series" />
	<display:column property="status" title="Status" />
	<display:column title="Function">
      <a href="javascript:previewArticle('${article.oid}');">Preview</a>
      <a href="javascript:editArticle('${article.oid}');">Edit</a>
    </display:column>
</display:table>