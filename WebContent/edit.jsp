<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>簡易Twitter</title>
		<link href="./css/style.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="main-contents">
			<div class="header">
				<c:if test="${ not empty loginUser }">
					<a href="./">ホーム</a>
					<a href="setting">設定</a>
					<a href="logout">ログアウト</a>
				</c:if>
			</div>

            <c:if test="${ not empty errorMessages }">
                <div class="errorMessages">
                    <ul>
                        <c:forEach items="${errorMessages}" var="errorMessage">
                            <li><c:out value="${errorMessage}" />
                        </c:forEach>
                    </ul>
                    </div>
            </c:if>

			<!-- つぶやきの編集テキストエリア -->
			<form action="edit" method="post">
				<input name="id" value="${editMessage.id}" id="id" type="hidden"/>
				つぶやき<br />
				<textarea name="text" rows="5" cols="100" class="tweet-box"><c:out value="${editMessage.text}"></c:out></textarea><br />
				<input type="submit" value="更新" /> <br />
				<a href="./">戻る</a>
			</form>

		</div>
	</body>
</html>