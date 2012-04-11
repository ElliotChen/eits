<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<display:table name="faqArticles" pagesize="1">
	<display:column property="id" title="ID" />
	<display:column property="summary" title="Summary" />
</display:table>

<display:table name="latestArticles" partialList="true" pagesize="5" size="20" form="searchForm" sort="external" id="row">
	<display:column property="id" title="ID" sortable="true" sortName="id" sortProperty="id">
		<a href="javascript:detail('${row.id}');">${row.id}</a>
	</display:column>
	<display:column property="summary" title="Summary" />
	<display:column title="test" >
      <a href="javascript:detail('${row.id}');">${row.id}</a>
    </display:column>
</display:table>