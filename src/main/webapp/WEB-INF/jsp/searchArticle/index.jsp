<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script>
	$().ready(function() {
		$('#searchForm').ajaxForm({
            target: '#articles'
        });
		
		$('#faqForm').ajaxForm({
            target: '#faqArticleBlock'
        });
		$('#latestForm').ajaxForm({
            target: '#latestArticleBlock'
        });
		$('#exSeries').selectChain({
		    target: $('#exModel'),
		    type:'POST',
		    data:{series : $('#exSeries').val()},
		    url: '${ctx}/ajax!ajaxModels.action'
		}).trigger('change');
		/*
		$('#exSeries').multiselect({position: {
			      my: 'left bottom',
			      at: 'left top'
			   }, multiple:false}).multiselectfilter();
		
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
			<tr>
				<td><h3>Knowledge Base</h3></td>
				<td align="right" colspan="2">Country:<select><option>----</option></select>　　Language:<s:select list="languages" listKey="oid" listValue="name" name="example.language.oid" value="EN" /></td>
			</tr>
			<tr>
				<td colspan="3" style="line-height: 12px">Knowledge Base(KB) is designed to collect and publish users' valuable problem-solving experience for reference and reuse. 
				An web-base service providing dynamic self-service information for online support requests. The Knowledge Base is where we post product information,
				SB and SMB application, FAQs, and articles that might help solving user issues or problems. Please search your questions from Knowledge Base before submitting a request to the Support Feedback service.
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>Product Series:<s:select id="exSeries" name="series" list="productSeries" listKey="id" listValue="name" headerKey="" headerValue="----" /></td>
				<td rowspan="2"><s:submit value="Search" /></td>
			</tr>
			<tr>
				<td>Keyword:<s:textfield name="example.keywords" maxlength="40" size="40" /></td>
				<td>Product Model:<select id="exModel" name="example.product"><option value="">----</option></select></td>
			</tr>
			</s:form>
		</table>
</div>
<div class="clear"></div>
<div id="articles">
</div>
