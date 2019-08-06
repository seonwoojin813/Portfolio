<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 에러 페이지를 만들기 위한 설정 -->
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta name="dummy" content="의미없는 메시지" />
<meta name="dummy" content="의미없는 메시지" />
<meta name="dummy" content="의미없는 메시지" />
<meta name="dummy" content="의미없는 메시지" />
<meta charset="UTF-8">
<title>예외 발생</title>
</head>
<body>
	<h3>${exception.getMessage()}</h3>
</body>
</html>