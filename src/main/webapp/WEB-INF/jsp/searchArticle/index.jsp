<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script>
	$().ready(function() {
		$('#searchForm').ajaxForm({
            target: '#articles'
        });
	});
</script>
Knowledge Base Language:
<select name="language"><option value="en">EN</option></select>
<br />
<div id="search">
	<s:form id="searchForm" namespace="/" action="searchArticle!search" theme="simple">
Keyword:<input type="text" maxlength="40" />
Product Series:<select name="series"><option value="pa">PS1</option>
			<option value="pb">PS2</option></select>
Product Model:<select name="model"><option value="">----</option></select>
		<s:submit value="Search" />
	</s:form>
</div>
<div id="articles"></div>
