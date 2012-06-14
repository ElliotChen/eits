<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>

<script type="text/javascript">
<!--
	$().ready(function() {
		$('#editForm').ajaxForm({
			target: '#main',
			  beforeSubmit: function() {
			    $('#editForm').validate({ rules : {
					rejectReason : {required:function(element) {
				        return $("#statusAction").val() == '';
				      }}
				} });
			    return $('#editForm').valid();
			  }
			});
		delete CKEDITOR.instances['question'];
		delete CKEDITOR.instances['answer'];
		delete CKEDITOR.instances['scenario'];
		delete CKEDITOR.instances['step'];
		delete CKEDITOR.instances['verification'];
		delete CKEDITOR.instances['problem'];
		delete CKEDITOR.instances['solution'];
		delete CKEDITOR.instances['procedure'];
		
		$('#question').ckeditor();
		$('#answer').ckeditor();
		$('#scenario').ckeditor();
		$('#step').ckeditor();
		$('#verification').ckeditor();
		$('#problem').ckeditor();
		$('#solution').ckeditor();
		$('#procedure').ckeditor();
		switchType();
	});
	function previewSave() {
		cloneForm('#editForm', '#editPreviewForm');
		$("#editPreviewForm").submit();
	}

	function cancelSave() {
		$('#editForm').val('action', '${ctx}/edit!list.action');
		$('#editForm').submit();
	}
	
	function switchType() {
		$('.ArticleType').hide();
		$('.'+$('#type').val()).show();
	}
//-->
</script>
<input type="button" value="View Log" onclick="viewArticleLog('${oid}')"/>
<div class="condition">
<s:form id="editForm" namespace="/" action="edit!updateStatus" theme="simple" method="POST" enctype ="multipart/form-data">
	<s:hidden name="oid" />
	<s:hidden name="keywords" />
	<table class="conditionborder">
		<tr>
			<td>ARTICLE ID:</td>
			<td><s:textfield name="articleId.oid" readonly="true" maxlength="6" size="6"/> (${article.status} - ${article.agentType})<input type="button" value="View Reason" onclick="viewRejectLogs('${oid}')"/></td>
		</tr>
		<tr>
			<td>LANGUAGE:</td>
			<td><s:select list="languages" listKey="oid" listValue="name" name="language.oid" disabled="true" /></td>
		</tr>
		<s:if test="user.l3">
		<tr>
			<td>SOURCE:</td>
			<td><s:radio name="source" disabled="true" list="@tw.com.dsc.domain.Source@values()" onchange="switchSource()" />
				<s:select id="projectCode" name="projectCode" list="projects" disabled="true" listKey="oid" listValue="projectCode"></s:select>
			</td>
		</tr>
		</s:if>
		
		<s:if test="user.l3">
		<tr>
			<td>ZyTech News:</td>
			<td><s:radio name="news" disabled="true" list="#{'true':'Yes','false':'No'}" />
			</td>
		</tr>
		</s:if>
		
		<tr>
			<td>TYPE:</td>
			<td><s:select id="type" name="type" list="@tw.com.dsc.domain.ArticleType@values()" listValue="%{getText('enum.ArticleType.'+toString())}" disabled="true" /></td>
		</tr>
		<tr>
			<td>SUMMARY:</td>
			<td><s:textfield name="summary" size="40" maxlength="50" disabled="true"/></td>
		</tr>
		<tr>
			<td>Expire After:</td>
			<td><s:select list="@tw.com.dsc.domain.ExpireType@values()" listValue="%{getText('enum.ExpireType.'+toString())}" name="expireType" disabled="true"/></td>
		</tr>
		<tr>
			<td colspan="2">ENTRY DATE:<s:date name="entryDate" format="yyyy/MM/dd HH:mm:ss" />　　　　LAST UPDATE:<s:date name="updateDate" format="yyyy/MM/dd HH:mm:ss" />　　　　PUBLISH DATE:<s:date name="publishDate" format="yyyy/MM/dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td>ENTRY USER:</td>
			<td>${entryUser}</td>
		</tr>
		<%--
		<tr>
			<td>KEYWORDS</td>
			<td><s:textfield name="keywords" size="40" maxlength="50" disabled="true"/></td>
		</tr>
		 --%>
		<tr class="ArticleType SpecInfo">
			<td>eITS TICKET ID:</td>
			<td><s:textfield name="ticketId" size="40" disabled="true"/></td>
		</tr>
		<tr class="ArticleType GeneralInfo SpecInfo">
			<td>QUESTION:</td>
			<td><s:textarea id="question" name="question" cols="40" rows="4" disabled="true" /></td>
		</tr>
		<tr class="ArticleType GeneralInfo SpecInfo">
			<td>ANSWER:</td>
			<td><s:textarea id="answer" name="answer" cols="40" rows="8" disabled="true"/></td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<td>SCENARIO DESCRIPTION:</td>
			<td><s:textarea id="scenario" name="scenario" cols="40" rows="4" disabled="true"/></td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<td>SETUP/STEP BY STEP PROCEDURE:</td>
			<td><s:textarea id="step" name="step" cols="40" rows="4" disabled="true"/></td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<td>VERIFICATION:</td>
			<td><s:textarea id="verification" name="verification" cols="40" rows="4" disabled="true"/></td>
		</tr>
		
		<tr class="ArticleType Issue">
			<td>PROBLEM DESCRIPTION:</td>
			<td><s:textarea id="problem" name="problem" cols="40" rows="4" disabled="true"/></td>
		</tr>
		<tr class="ArticleType Issue">
			<td>SOLUTION:</td>
			<td><s:textarea id="solution" name="solution" cols="40" rows="4" disabled="true"/></td>
		</tr>
		<tr class="ArticleType Issue">
			<td>CONDITION/REPRODUCE PROCEDURE:</td>
			<td><s:textarea id="procedure" name="procedure" cols="40" rows="4" disabled="true"/></td>
		</tr>
		<tr>
			<td>VIEW LEVEL:</td>
			<td><s:select id="level" name="level" list="@tw.com.dsc.domain.Level@values()" listValue="%{getText('enum.Level.'+toString())}" disabled="true"/></td>
		</tr>
		<tr>
			<td>TECHNOLOGY:</td>
			<td><s:textarea id="technology" name="technology" cols="40" rows="4" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td>PRODUCT:</td>
			<td><s:textarea id="product" name="product" cols="40" rows="4" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td>FIRMWARE:</td>
			<td>
				<s:textfield name="firmware" size="40" maxlength="10" disabled="true"/>
			</td>
		</tr>

		<tr>
			<td>STATUS ACTION:</td>
			<td>
				<s:select id="statusAction" name="statusAction" list="availableStatus" listValue="%{getText('edit.statusAction.'+toString())}" headerKey="" headerValue="-----" />
			</td>
		</tr>
		<tr>
			<td colspan="2" align="right"><s:submit value="Update" cssClass="save" /> <s:submit value="Delete" cssClass="delete" action="edit" method="disable"/><input type="button" value="Do Nothing" onclick="switchMenu('m3', 'edit!list.action');" />
			</td>
		</tr>
	</table>
</s:form>
</div>