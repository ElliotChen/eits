<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<s:if test="hasActionMessages()">
   <div>
      <s:actionmessage/>
   </div>
</s:if>

<div id="faqArticleBlock">
<display:table name="faqArticles" id="article">
	<display:caption>FAQ</display:caption>
	<display:column property="id" title="ID" />
	<display:column property="summary" title="Summary" />
	<display:column title="Publish Date">
		<fmt:formatDate value="${article.publishDate}" pattern="yyyy/MM/dd"/>
	</display:column>
	<display:column property="hitCount" title="Hit Count" />
	<display:column title="Function" >
      <a href="javascript:viewArticle('${article.oid}');">View Article</a>
    </display:column>
</display:table>
</div>

<div id="latestArticleBlock">
<display:table name="latestArticles" partialList="true" pagesize="5" size="20" form="displaytagform" sort="external" id="article">
	<display:caption>Latest Articles</display:caption>
	<display:column property="id" title="ID"/>
	<display:column property="summary" title="Summary"/>
	<display:column title="Publish Date">
		<fmt:formatDate value="${article.publishDate}" pattern="yyyy/MM/dd"/>
	</display:column>
	<display:column property="hitCount" title="Hit Count" />
	<display:column title="Function" >
      <a href="javascript:viewArticle('${article.oid}');">View Article</a>
    </display:column>
</display:table>
</div>