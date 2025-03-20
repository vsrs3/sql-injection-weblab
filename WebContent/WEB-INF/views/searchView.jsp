<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
 <head>
    <meta charset="UTF-8">
    <title>Product List</title>
 </head>
 <body>
 
    <jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
 
    <h3>Product List</h3>
 	<form method="GET" action="${pageContext.request.contextPath}/search">
 	Code: <input type="text" name="code" />
 	<input type="submit" value= "Search" />
 	</form>
 	
    <p style="color: red;">${errorString}</p>
	
	<c:choose>
	<c:when test="${not empty searchResult}">
		Result for <i> ${searchResult.code} </i><br>	
		Code:  ${searchResult.code}<br>
		Name:  ${searchResult.name}<br>
		Price:  ${searchResult.price}<br>
	</c:when>
	<c:otherwise>
	<br>Not found <i> ${param.code} </i><br>
	</c:otherwise>
	</c:choose>
    <jsp:include page="_footer.jsp"></jsp:include>
 
 </body>
</html>