<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<display:table name="draftArticles" partialList="true" pagesize="5" size="20" form="draftForm" sort="external" id="article">
	<display:caption>Draft Articles</display:caption>
	<display:column property="id" title="ID" sortable="true" sortName="id" sortProperty="id" />
	<display:column property="summary" title="Summary" sortable="true" sortName="summary" sortProperty="summary"/>
	<display:column title="Function" >
      <a href="javascript:editArticle('${article.oid}');">Edit Article</a>
    </display:column>
</display:table>