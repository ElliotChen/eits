<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>

<script type="text/javascript">
<!--
	$().ready(function() {
		$('#editForm').ajaxForm({
			target: '#main',
			  beforeSubmit: function() {
			    $('#editForm').validate({ rules : {
					id : {required:true, number:true},
					summary : {required:true},
					keywords : {required:true},
					question : {required:true},
					answer : {required:true},
					product : {required:true},
					technology : {required:true},
					firmware : {required:true}
				} });
			    return $('#editForm').valid();
			  }
			});
		delete CKEDITOR.instances['question'];
		delete CKEDITOR.instances['answer'];
		$('#question').ckeditor();
		$('#answer').ckeditor();
	});
	function previewSave() {
		cloneForm('#editForm', '#previewForm');
		$("#previewForm").submit();
	}

	function cloneForm(sourceForm, targetForm) {
	    $(':input[name]', sourceForm).each(function() {
	        $('[name=\'' + $(this).attr('name') +'\']', targetForm).val($(this).val())
	    });
	}
	
	function cancelSave() {
		$('#editForm').val('action', '${ctx}/edit!list.action');
		$('#editForm').submit();
	}
//-->
</script>
<s:form id="editForm" namespace="/" action="edit!save" theme="simple">
	<table>
		<tr>
			<td>ArticleID:</td>
			<td><s:textfield name="id" /></td>
		</tr>
		<tr>
			<td>Language:</td>
			<td><s:select list="{'English', 'Chinese'}"
					value="article.language" /></td>
		</tr>
		<tr>
			<td>Source:</td>
			<td><s:radio name="source" list="#{'1':'OBM','2':'Project'}"
					onchange="" /></td>
		</tr>

		<tr>
			<td>News:</td>
			<td><s:radio name="news" list="#{'true':'Yes','false':'No'}" />
			</td>
		</tr>

		<tr>
			<td>Type:</td>
			<td><s:select list="{'General', 'FAQ'}" value="type" /></td>
		</tr>
		<tr>
			<td>Summary:</td>
			<td><s:textfield name="summary" /></td>
		</tr>
		<tr>
			<td>Expire after:</td>
			<td><s:select
					list="#{'0':'One Month','1':'One Season','2':'One Year' }"
					name="expireType" /></td>
		</tr>
		<tr>
			<td>EntryDate:</td>
			<td><s:date name="entryDate" format="yyyy/MM/dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td>Keywords:</td>
			<td><s:textfield name="keywords" maxlength="50" /></td>
		</tr>
		<tr>
			<td>Question:</td>
			<td><s:textarea id="question" name="question" cols="40" rows="4" cssClass="ckeditor"/></td>
		</tr>
		<tr>
			<td>Answer:</td>
			<td><s:textarea id="answer" name="answer" cols="40" rows="8" /></td>
		</tr>
		<tr>
			<td>View Level:</td>
			<td><s:select list="{'Sub/Partner', 'L2', 'L3'}" name="level" /></td>
		</tr>
		<tr>
			<td>Technology:</td>
			<td><s:textarea name="technology" cols="40" rows="4" /></td>
		</tr>
		<tr>
			<td>Product:</td>
			<td><s:textarea name="product" cols="40" rows="4" /></td>
		</tr>
		<tr>
			<td>Firmware:</td>
			<td><s:textfield name="firmware" /></td>
		</tr>

		<tr>
			<td>Save As:</td>
			<td><s:radio list="{'Final & Publish', 'Final', 'Draft'}"
					name="state" /></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td><s:submit action="edit!list" value="Cancel"
					cssClass="cancel" /> <input type="button" value="Preview"
				onclick="previewSave()" /> <s:submit value="Submit" cssClass="save" />
			</td>
		</tr>
	</table>
</s:form>

<s:form id="previewForm" namespace="/" action="edit!previewSave" theme="simple" target="_blank">
	<input type="hidden" name="language" />
	<input type="hidden" name="type" />
	<input type="hidden" name="summary" />
	<input type="hidden" name="question" />
	<input type="hidden" name="answer" />
	<input type="hidden" name="technology" />
	<input type="hidden" name="product" />
	<input type="hidden" name="firmware" />
	<input type="hidden" name="level" />
	<input type="hidden" name="state" />
</s:form>