<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<display:table name="${expiredArticles.result}" partialList="true" pagesize="${expiredArticles.pageSize}" size="${expiredArticles.totalCount}" form="expiredForm" sort="external" id="article" requestURI="edit!searchExpired.action" style="width:100%">
	<display:column property="articleId.oid" style="width:50px" headerClass="tablehead"/>
	<display:column property="summary" title="Summary" style="width:300px" headerClass="tablehead" />
	<display:column property="entryUser" title="Agent" style="width:50px" headerClass="tablehead"/>
	<display:column title="Entry Date" style="width:90px" headerClass="tablehead">
		<fmt:formatDate value="${article.entryDate}" pattern="yyyy/MM/dd"/>
	</display:column>
	<display:column property="formattedSeries" title="Prod. Series" style="width:200px" headerClass="tablehead" />
	<display:column title="Function" headerClass="tablehead">
      <input type="button" onclick="javascript:previewArticle('${article.oid}');" value="Preview" />
      <input type="button" onclick="javascript:editArticle('${article.oid}');" value="Edit"/>
    </display:column>
</display:table>