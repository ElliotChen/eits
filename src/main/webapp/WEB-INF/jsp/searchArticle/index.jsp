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
		    url: '${ctx}/ajax!ajaxModels.action',
		    afterChange : resetModel
		}).trigger('change');
		
		$('#exSeries').ufd();
		$('#exModel').ufd();
		/*$('#exModel').ufd({useUiCss:true});
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
	function resetModel() {
		$('#exModel').ufd("changeOptions");
	}
</script>

<br />
<div id="search" class="condition">
	<s:form id="searchForm" namespace="/" action="searchArticle!search" theme="simple">
		<table class="conditionborder">
			<tr>
				<td><h3>Knowledge Base</h3></td>
				<td align="right" colspan="4">Language:<s:select list="languages" listKey="oid" listValue="name" name="example.language.oid" value="user.defaultLanguageOid" onchange="switchLanguage(this);" /></td>
			</tr>
			<tr>
				<td colspan="5" style="line-height: 18px; font-size: 18px">The Knowledge Base is where we post product information, SB and SMB application, FAQs, and articles that might help solving user issues or problems. We strongly recommend to search your questions from Knowledge Base before submitting a request to our local support.
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td nowrap="nowrap">Product Series:</td><td><s:select id="exSeries" name="exSeries" list="productSeries" listKey="id" listValue="name" headerKey="" headerValue="----"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td nowrap="nowrap">Keyword:<s:textfield name="example.keywords" maxlength="40" size="40" /></td>
				<td nowrap="nowrap">Product Model:</td><td><s:select id="exModel" name="exModel" list="productModels" listKey="name" listValue="name" headerKey="" headerValue="----"/></td>
				<td><s:submit value="Search" /></td>
			</tr>
		</table>
	</s:form>
</div>
<div id="articles">
	<jsp:include page="list.jsp"></jsp:include>
</div>
<div id="detail">
</div>