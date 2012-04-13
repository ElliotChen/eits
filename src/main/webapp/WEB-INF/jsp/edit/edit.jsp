<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script type="text/javascript">
<!--
	$().ready(function() {
		$('#copyEditForm').ajaxForm({
        	target: '#main'
    	});
	});
	function previewCopy() {
		cloneForm('#copyEditForm', '#previewForm');
		$("#previewForm").submit();
	}

	function cloneForm(sourceForm, targetForm) {
	    $(':input[name]', sourceForm).each(function() {
	        $('[name=\'' + $(this).attr('name') +'\']', targetForm).val($(this).val())
	    });
	}
//-->
</script>
<s:form id="copyEditForm" namespace="/" action="article!copy" theme="simple">
	<table>
		<tr>
			<td>ArticleID:</td>
			<td><s:textfield name="article.id"/></td>
		</tr>
		<tr>
			<td>Language:</td>
			<td><s:select list="{'English', 'Chinese'}" value="article.language" /></td>
		</tr>
		<tr>
			<td>Source:</td>
			<td><s:radio name="article.source" list="#{'1':'OBM','2':'Project'}" onchange="" /> </td>
		</tr>
		
		<tr>
			<td>News:</td>
			<td><s:radio list="#{'true':'Yes','false':'No'}" /> </td>
		</tr>
		
		<tr>
			<td>Type:</td>
			<td><s:select list="{'General', 'FAQ'}" value="article.type" /></td>
		</tr>
		<tr>
			<td>Summary:</td>
			<td><s:textfield name="article.summary" readonly="true" /></td>
		</tr>
		<tr>
			<td>Expire after:</td>
			<td><s:select list="#{'0':'One Month','1':'One Season','2':'One Year' }" value="article.expireType" /></td>
		</tr>
		<tr>
			<td>EntryDate:</td>
			<td><s:date name="article.entryDate" format="yyyy/MM/dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td>Keywords:</td>
			<td><s:textfield name="article.keywords" readonly="true" /></td>
		</tr>
		<tr>
			<td>Question:</td>
			<td><s:textarea name="article.question" readonly="true" cols="40" rows="4" /></td>
		</tr>
		<tr>
			<td>Answer:</td>
			<td><s:textarea name="article.answer" readonly="true" cols="40" rows="8" /></td>
		</tr>
		<tr>
			<td>View Level:</td>
			<td><s:select list="{'Sub/Partner', 'L2', 'L3'}" value="article.level" /></td>
			<td><input type="button" value="Copy >>" /></td>
			<td><s:select name="copy.level" list="{'Sub/Partner', 'L2', 'L3'}" headerKey="-1" headerValue="--Select--"
					emptyOption="------" /></td>
		</tr>
		<tr>
			<td>Technology:</td>
			<td><s:textarea name="article.technology" readonly="true" cols="40" rows="4" /></td>
		</tr>
		<tr>
			<td>Product:</td>
			<td><s:textarea name="article.product" readonly="true" cols="40" rows="4" /></td>
		</tr>
		<tr>
			<td>Firmware:</td>
			<td><s:textfield name="article.firmware"/></td>
		</tr>
		
		<tr>
			<td>Save As:</td>
			<td><s:radio list="{'Final & Publish', 'Final', 'Draft'}" name="copy.state" /></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td>
				<input type="button" value="Cancel" onclick="viewArticle('${article.oid}')" />
				<input type="button" value="Preview" onclick="previewSave()" /> 
				<s:submit value="Submit" />
			</td>
		</tr>
	</table>
</s:form>

<s:form id="previewForm" namespace="/" action="article!previewCopy" theme="simple" target="_blank">
	<input type="hidden" name="copy.language" />
	<input type="hidden" name="copy.type" />
	<input type="hidden" name="copy.summary" />
	<input type="hidden" name="copy.question" />
	<input type="hidden" name="copy.answer" />
	<input type="hidden" name="copy.technology" />
	<input type="hidden" name="copy.product" />
	<input type="hidden" name="copy.firmware" />
	<input type="hidden" name="copy.level" />
	<input type="hidden" name="copy.state" />
</s:form>