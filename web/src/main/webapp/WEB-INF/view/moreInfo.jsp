<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>${more}</title>
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/styles.css">
</head>
<body>	
	<table class="rwd-table">
		<tr>
		<td>
		${ pageContext.request.contextPath }<br>
		${ header["host"] }<br>
		${pageContext.response.contentType}<br>
		${pageContext.request.session.id}<br>
		${pageContext.request.session.creationTime}
		</td>
		</tr>
	</table>
</body>
</html>