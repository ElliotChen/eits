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
		    data:{series : $('#exSeries').val()},
		    url: '${ctx}/ajax!ajaxModels.action'
		}).trigger('change');
		$('#exSeries').multiselect({position: {
			      my: 'left bottom',
			      at: 'left top'
			   }, multiple:false}).multiselectfilter();
		/*
		$('#exModel').multiselect({position: {
		      my: 'left bottom',
		      at: 'left top'
		   }, multiple:false}).multiselectfilter();
		*/
	});

</script>

<br />
<div id="search" class="condition">
	<s:form id="searchForm" namespace="/" action="searchArticle!search" theme="simple">
		<table class="conditionborder">
<tr><td>Knowledge Base</td> <td align="right">Language:<s:select list="languages" listKey="oid" listValue="name" name="example.language.oid" value="EN"/></td></tr>
<tr><td>
Keyword:<s:textfield name="example.keywords" maxlength="60" size="60" /></td>
<td align="right">
<s:select list="{}">
					<s:optgroup list="series"></s:optgroup>
				</s:select>
Product Series:<select id="exSeries" name="series">
			<option value="" selected="selected">----</option>
			<option value="pa">PS1</option>
			<option value="pb">PS2</option></select>
Product Model:<select id="exModel" name="example.product"><option value="">----</option></select>
		<s:submit value="Search" /></td></tr>
	</s:form>
	</table>
	<s:form id="displaytagform" namespace="/" action="searchArticle!search" theme="simple">
		<div id="distagArea" class="distagArea">
		</div>
	</s:form>
</div>
<div class="clear"></div>
<div id="articles">
</div>
