<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<display:table name="${draftArticles.result}" partialList="true" pagesize="${draftArticles.pageSize}" size="${draftArticles.totalCount}" form="draftForm" sort="external" id="article" requestURI="edit!searchDraft.action">
	<display:caption>Draft Articles</display:caption>
	<display:column property="articleId.oid" title="ID" style="width:50px" headerClass="tablehead"/>
	<display:column property="summary" title="Summary" style="width:300px" headerClass="tablehead"/>
	<display:column property="entryUser" title="Agent" style="width:100px" headerClass="tablehead"/>
	<display:column title="Entry Date" style="width:90px" headerClass="tablehead">
		<fmt:formatDate value="${article.entryDate}" pattern="yyyy/MM/dd"/>
	</display:column>
	<display:column property="product" title="Prod. Series" style="width:200px" headerClass="tablehead"/>
	<display:column title="Function" headerClass="tablehead">
      <a href="javascript:previewArticle('${article.oid}');">Preview</a>
      <a href="javascript:editArticle('${article.oid}');">Edit</a>
    </display:column>
</display:table>