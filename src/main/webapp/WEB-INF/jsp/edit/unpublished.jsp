<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<display:table name="unpublishedArticles" partialList="true" pagesize="5" size="20" form="unpublishedForm" sort="external" id="article" export="true" requestURI="edit!searchUnpublished.action">
	<display:caption>Unpublished Articles</display:caption>
	<display:column property="id" title="ID"/>
	<display:column property="summary" title="Summary" />
	<display:column property="entryUser" title="Agent"/>
	<display:column title="Entry Date">
		<fmt:formatDate value="${article.entryDate}" pattern="yyyy/MM/dd"/>
	</display:column>
	<display:column property="product" title="Prod. Series" />
	<display:column property="state" title="State" />
	<display:column title="Function">
      <a href="javascript:editArticle('${article.oid}');">Edit Article</a>
    </display:column>
</display:table>