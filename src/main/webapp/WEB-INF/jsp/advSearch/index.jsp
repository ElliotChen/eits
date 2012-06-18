<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script>
	$().ready(function() {
		$('#searchForm').ajaxForm({
            target: '#articles',
            beforeSubmit: function() {
            	$('#searchForm').validate({ rules : {
            		advLevels : {required:true},
            		/*
					beginDate : {required:function(element) {
						return $('input:[name="dateType"]:checked').size() > 0;
					}},
					endDate : {required:function(element) {
						return $('input:[name="dateType"]:checked').size() > 0;
					}},
					*/
					advAvgRate : {max:5, min:1} 
				},
				errorClass: "errorField"
				});
			    return $('#searchForm').valid();
            },
            success : $.unblockUI
        });
		$('#advPsForm').ajaxForm({
            target: '#advProductSelectDiv',
            success : $.unblockUI
        });
		$('#groupList').ufd();
		$('#accountList').ufd();
		var datepicker_CurrentInput;
		options = {
				dateFormat : 'yy/mm/dd',
				showButtonPanel: true,
				closeText: 'Clear',
				beforeShow: function (input, inst) { datepicker_CurrentInput = input; }
		};
		$(".ui-datepicker-close").live("click", function () {
			datepicker_CurrentInput.value = "";
		});
		$(".calendar").datepicker(options);
		$(".numeric").numeric({ decimal: false, negative: false });
		$(".float").numeric({ negative: false });
		$('#advTechSelect').multiselect({beforeclose: function(){
			$('#advTechnology').val($('#advTechSelect').val());
		   }, position: {
			      my: 'left bottom',
			      at: 'left top'
			   }}).multiselectfilter();
		$('#advProductSelect').multiselect({beforeclose: function(){
			$('#advProduct').val($('#advProductSelect').val());
		   },position: {
			      my: 'left bottom',
			      at: 'left top'
			   }}).multiselectfilter();
		
		<c:if test="${reload}">
			$('#searchForm').submit();
		</c:if>
		
	});
	function switchAdvSource() {
		if ('Project' == $('input:[name="advSourceType"]:checked').val()) {
			$('#advProjectCode').removeAttr('disabled');
			switchAdvProjectCode();
		} else {
			$('#advProjectCode').attr('disabled', 'disabled');
			showAdvAllModel();
		}
	}
	function switchAdvProjectCode() {
		$('#advPCode').val($('#advProjectCode').val());
		$('#advPsForm').submit();
	}
	function showAdvAllModel() {
		$('#advPCode').val('');
		$('#advPsForm').submit();
	}
</script>

<s:form id="advPsForm" namespace="/" action="edit!listModels" theme="simple">
	<input id="advPCode" type="hidden" name="example.projectCode" />
</s:form>
<div id="search" class="condition">
	<s:form id="searchForm" namespace="/" action="advSearchArticle!search" theme="simple">
		<table class="conditionborder">
			<tr>
				<td>LANGUAGE:</td>
				<td><s:select list="languages" listKey="oid" listValue="name" name="advLanguageOid" value="user.defaultLanguageOid"/></td>
			</tr>
			<s:if test="user.l3">
			<tr>
				<td>SOURCE:</td>
				<td>
				<s:radio name="advSourceType" list="#{'':'Any','OBM':'OBM', 'Project':'Project'}" />
				<s:select id="advProjectCode" name="advProjectCode" disabled="true" list="projects" listKey="oid" listValue="advProjectCode" onchange="switchAdvProjectCode()"/>
				</td>
			</tr>
			</s:if>
			<s:else>
			<input type="hidden" name="advSourceType" value="OBM"/>
			<input type="hidden" name="advProjectCode" value=""/>
		</s:else>
			<s:if test="user.l3">
		<tr>
			<td>ZyTech News:</td>
			<td><s:radio name="advNews" list="#{'true':'Yes','false':'No'}" />
			</td>
		</tr>
		</s:if>
		<s:else>
			<input type="hidden" name="advNews" value="false"/>
		</s:else>
		<tr>
			<td>TYPE:</td>
			<td><s:select name="advType" list="@tw.com.dsc.domain.ArticleType@values()" listValue="%{getText('enum.ArticleType.'+toString())}" headerKey="" headerValue="----" /></td>
		</tr>
			<tr>
				<td rowspan="2">DATE RANGE:</td>
				<td>
					<s:radio name="advDateType" list="#{'entryDate':'Entry Date', 'updateDate':'Last Update', 'publishDate':'Publish Date'}" />
				</td>
			</tr>
			<tr>
				<td>FROM:<s:textfield id="advBeginDate" name="advBeginDate" size="10" cssClass="calendar" readonly="true"/> TO:<input type="text" name="advEndDate" size="10" id="endDate" readonly="readonly" class="calendar"/></td>
			</tr>
			<tr>
				<td>APPROVE:</td>
				<td><input type="radio" name="advApType" value="" <c:if test="${empty advApType}">checked="checked"</c:if> />Any
					<input type="radio" name="advApType" value="publishedType" <c:if test="${'publishedType' eq advApType}">checked="checked"</c:if>/>Published <s:select name="advPublished" list="#{'true':'Yes','false':'No'}" />
					<input type="radio" name="advApType" value="statusType" <c:if test="${'statusType' eq advApType}">checked="checked"</c:if>/>Status 
					<s:select name="advStatus" list="user.advSearchStatus" listValue="%{getText('enum.Status.'+toString())}" />
				</td>
			</tr>
			<tr>
				<td rowspan="3">AGENT:</td>
				<td>
					<input type="radio" name="advAgentSearchType" value="group" <c:if test="${'group' eq advAgentSearchType}">checked="checked"</c:if>/>by Team
					<s:select id="groupList" name="advGroup" list="groups" listKey="teamName" listValue="teamName" headerKey="" headerValue="----"/>
				</td>
			</tr>
			<tr>
				<td>
					<input type="radio" name="advAgentSearchType" value="agent" <c:if test="${'agent' eq advAgentSearchType}">checked="checked"</c:if>/>by Agent
					<s:select id="accountList" name="account" list="accounts" listKey="id" listValue="name" headerKey="" headerValue="----"/>
				</td>
			</tr>
			<tr>
				<td>
					<input type="radio" name="advAgentSearchType" value="self" <c:if test="${empty advAgentSearchType || 'self' eq advAgentSearchType}">checked="checked"</c:if>/>myself
				</td>
			</tr>
			
			<tr>
				<td>VIEW LEVEL:</td>
				<td><s:checkboxlist list="user.availableLevels" listValue="%{getText('enum.Level.'+toString())}" name="advLevels" ></s:checkboxlist> </td>
			</tr>
			<tr>
			<td>TECHNOLOGY:</td>
			<td><s:textarea id="advTechnology" name="advTechnology" cols="40" rows="4" readonly="true"/>
				<div>
				<select id="advTechSelect" name="techSelect" multiple="true">
					<s:iterator value="technologies" var="tech">
						<optgroup label="<s:property value="technology" />">
							<s:iterator value="#tech.items" var="item">
								<option value="<s:property value="#tech.technology" />--<s:property value="name" />"><s:property value="name" /></option>
							</s:iterator>
						</optgroup>
					</s:iterator>
				</select>
				</div>
			</td>
		</tr>
		<tr>
			<td>PRODUCT:</td>
			<td><s:textarea id="advProduct" name="advProduct" cols="40" rows="4" readonly="true"/>
				<div id="advProductSelectDiv">
				<select id="advProductSelect" name="productSelect" multiple="true">
					<s:iterator value="products" var="product">
						<optgroup label="<s:property value="name" />">
							<s:iterator value="#product.models" var="model">
								<option value="<s:property value="#product.name" />--<s:property value="name" />"><s:property value="name" /></option>
							</s:iterator>
						</optgroup>
					</s:iterator>
				</select>
				</div>
			</td>
		</tr>
		<tr>
			<td>FIRMWARE:</td>
			<td><s:textfield name="advfirmware" size="40" maxlength="50"/></td>
		</tr>
		<tr>
			<td>VIEWS:</td>
			<td><s:select name="advViewsType" list="#{'GE':'>=','EQ':'=','LE':'<='}" /> <s:textfield name="advHitCount" size="6" cssClass="numeric" /></td>
		</tr>
		<tr>
			<td>RATING:</td>
			<td><s:select name="advRatingType" list="#{'GE':'>=','EQ':'=','LE':'<='}" /> <s:textfield name="advAvgRate" size="6" cssClass="float" /></td>
		</tr>
		 <tr>
			<td colspan="2" align="center"><s:submit value="Search" /></td>
		</tr>
		</table>
	</s:form>
</div>
<div id="articles">
	
</div>
<div id="detail">
</div>