<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<display:table name="${expiredArticles.result}" partialList="true" pagesize="5" size="20" form="expiredForm" sort="external" id="article" export="true" requestURI="edit!searchExpired.action">
	<display:caption>Expired Articles</display:caption>
	<display:column property="articleId.oid"/>
	<display:column property="summary" title="Summary" />
	<display:column property="entryUser" title="Agent"/>
	<display:column title="Entry Date">
		<fmt:formatDate value="${article.entryDate}" pattern="yyyy/MM/dd"/>
	</display:column>
	<display:column property="product" title="Prod. Series" />
	<display:column title="Function">
      <a href="javascript:editArticle('${article.oid}');">Edit Article</a>
    </display:column>
</display:table>