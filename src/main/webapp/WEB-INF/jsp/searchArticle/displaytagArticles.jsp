<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<s:if test="hasActionMessages()">
   <div>
      <s:actionmessage/>
   </div>
</s:if>

<div id="faqArticleBlock">
<display:table name="faqArticles">
	<display:column property="articleId.oid" title="ID" />
	<display:column property="summary" title="Summary" />
</display:table>
</div>

<div id="latestArticleBlock">
<display:table name="latestArticles" partialList="true" pagesize="5" size="20" form="displaytagform" sort="external" id="article">
	<display:setProperty name="pagination.pagenumber.param" value="pageNo" />
	<display:column property="articleId.oid" title="ID" sortable="true" sortName="id" sortProperty="id" />
	<display:column property="summary" title="Summary" sortable="true" sortName="summary" sortProperty="summary"/>
	<display:column title="Function" >
      <a href="javascript:viewArticle('${article.oid}');">View Article</a>
    </display:column>
</display:table>
</div>