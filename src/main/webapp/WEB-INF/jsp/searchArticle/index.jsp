<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script>
	$().ready(function() {
		$('#searchForm').ajaxForm({
            target: '#articles'
        });
		$('#displaytagform').ajaxForm({
            target: '#articles'
        });
		
		$('#exSeries').selectChain({
		    target: $('#exModel'),
		    type:'POST',
		    data:$('#exSeries').val(),
		    url: '${ctx}/ajaxFunction!ajaxModels.action'
		}).trigger('change');
		
	});

</script>
Knowledge Base Language:<s:select list="languages" listKey="oid" listValue="name" name="example.language.oid"/>
<br />
<div id="search">
	<s:form id="searchForm" namespace="/" action="searchArticle!search" theme="simple">
Keyword:<input type="text" name="keyword" maxlength="40" />
Product Series:<select id="exSeries" name="series">
			<option value="" selected="selected">----</option>
			<option value="pa">PS1</option>
			<option value="pb">PS2</option></select>
Product Model:<select id="exModel" name="model"><option value="">----</option></select>
		<s:submit value="Search" />
	</s:form>
	
	<s:form id="displaytagform" namespace="/" action="searchArticle!search" theme="simple">
		<div id="distagArea" class="distagArea">
		</div>
	</s:form>
</div>

<div id="articles">
</div>
