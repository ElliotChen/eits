<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<display:table name="${expiredArticles.result}" partialList="true" pagesize="${draftArticles.pageSize}" size="${draftArticles.totalCount}" form="expiredForm" sort="external" id="article" requestURI="edit!searchExpired.action">
	<display:caption>Expired Articles</display:caption>
	<display:column property="articleId.oid" style="width:50px" headerClass="tablehead"/>
	<display:column property="summary" title="Summary" style="width:50px" headerClass="tablehead" />
	<display:column property="entryUser" title="Agent" headerClass="tablehead"/>
	<display:column title="Entry Date" headerClass="tablehead">
		<fmt:formatDate value="${article.entryDate}" pattern="yyyy/MM/dd"/>
	</display:column>
	<display:column property="product" title="Prod. Series" headerClass="tablehead" />
	<display:column title="Function" headerClass="tablehead">
      <a href="javascript:previewArticle('${article.oid}');">Preview</a>
      <a href="javascript:editArticle('${article.oid}');">Edit</a>
    </display:column>
</display:table>