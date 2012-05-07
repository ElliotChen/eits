<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<display:table name="${unpublishedArticles.result}" partialList="true" pagesize="${unpublishedArticles.pageSize}" size="${unpublishedArticles.totalCount}" form="unpublishedForm" sort="external" id="article" export="true" requestURI="edit!searchUnpublished.action">
	<!--<display:caption>Unpublished Articles</display:caption>-->
	<display:column property="articleId.oid" title="ID" style="width:50px"/>
	<display:column property="summary" title="Summary" style="width:300px"/>
	<display:column property="entryUser" title="Agent" style="width:50px"/>
	<display:column title="Entry Date" style="width:90px">
		<fmt:formatDate value="${article.entryDate}" pattern="yyyy/MM/dd"/>
	</display:column>
	<display:column property="product" title="Prod. Series" style="width:200px"/>
	<display:column property="status" title="Status" style="width:100px"/>
	<display:column title="Function" style="width:100px">
      <a href="javascript:previewArticle('${article.oid}');">Preview</a>
      <a href="javascript:editArticle('${article.oid}');">Edit</a>
    </display:column>
</display:table>