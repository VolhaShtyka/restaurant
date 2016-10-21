<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>Result Page</title>
</head>
<body>
	<p>IP: ${ip}</p>
	<p>Браузер: ${browser}</p>
	<p>Язык: ${language}</p>
	<a href="controller?command=logout">Logout</a>
</body>
</html>