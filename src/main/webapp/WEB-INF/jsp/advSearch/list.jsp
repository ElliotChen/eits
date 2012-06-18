<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script>
	$().ready(function() {
		$('#advExportForm').ajaxForm({
            target: '#articleBlock'
        });
		$('#advEditArticleForm').ajaxForm({
            target: '#main',
            success : $.unblockUI
        });
	});

	function advEditArticle(oid) {
		$('#advEditOid').val(oid);
		
		$('#advEditArticleForm').submit();
	}
</script>

<s:form id="advExportForm" namespace="/" action="advSearchArticle!search" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>
<div id="articleBlock" class="datagrid">
<h5>KBs</h5>
<display:table name="${articles}" export="true" form="advExportForm" id="article" requestURI="advSearchArticle!search.action">
	<display:column property="articleId.oid" title="ID" headerClass="tablehead"/>
	<display:column property="summary" title="Summary" headerClass="tablehead"/>
	<display:column property="formattedSeries" title="Prod. Series" headerClass="tablehead"/>
	<display:column title="Date" headerClass="tablehead">
		<fmt:formatDate value="${article.entryDate}" pattern="yyyy/MM/dd"/>
	</display:column>
	<display:column property="entryUser" title="Agent" headerClass="tablehead"/>
	<display:column property="i18nStatus" title="Status" headerClass="tablehead"/>
	<display:column property="type" title="Type" headerClass="tablehead"/>
	<display:column property="level" title="View Level" headerClass="tablehead"/>
	<display:column property="hitCount" title="Views" headerClass="tablehead"/>
	<display:column property="ratingInfo" title="Rating" headerClass="tablehead"/>
	<display:column title="Function" headerClass="tablehead">
      <input type="button" onclick="javascript:previewArticle('${article.oid}');" value="Preview" />
      <input type="button" onclick="javascript:advEditArticle('${article.oid}');" value="Edit"/>
    </display:column>
</display:table>
</div>
<!-- 
private String ;
	private Source ;
	private String ;
	private Boolean ;
	private ArticleType ;
	private String ; //entryDate, lastUpdate, publishDate
	private Date ;
	private Date ;
	private String ; //publishedType, statusType
	private Boolean ;
	private Status ;
	private String ; //group, agent, self
	private String ;
	private String ;
	private Level[] ;
	private String ;
	private String ;
	private String ;
	private OperationEnum ;
	private Integer ;
	private OperationEnum ;
	private Float ; -->
<s:form id="advEditArticleForm" namespace="/" action="edit!load" theme="simple">
		<input id="comefrom" type="hidden" name="comefrom" value="m8"/>
		<input id="advEditOid" type="hidden" name="oid" />
		<s:hidden name="advLanguageOid" />
		<s:hidden name="advSourceType" />
		<s:hidden name="advProjectCode" />
		<s:hidden name="advNews" />
		<s:hidden name="advType" />
		<s:hidden name="advDateType" />
		<s:hidden name="advBeginDate" />
		<s:hidden name="advEndDate" />
		<s:hidden name="advApType" />
		<s:hidden name="advPublished" />
		<s:hidden name="advStatus" />
		<s:hidden name="advAgentSearchType" />
		<s:hidden name="advGroup" />
		<s:hidden name="advAccount" />
		<s:hidden name="advTechnology" />
		<s:hidden name="advProduct" />
		<s:hidden name="advFirmware" />
		<s:hidden name="advViewsType" />
		<s:hidden name="advHitCount" />
		<s:hidden name="advRatingType" />
		<s:hidden name="advAvgRate" />
		<s:iterator value="advLevels" var="lev">
			<input type="hidden" name="advLevels" value="${lev}" />
		</s:iterator>
	</s:form>
