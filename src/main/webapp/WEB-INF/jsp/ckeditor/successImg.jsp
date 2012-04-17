<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="cache-control" content="no-cache">
<script type="text/javascript">
window.parent.CKEDITOR.tools.callFunction('${CKEditorFuncNum}','${fileUrl}','');
</script>
</head>
<body>
</body>
</html>