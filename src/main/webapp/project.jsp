<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Project</title>
</head>
<body>
<ul>
    <li>id: <c:out value="${project.id}"/></li>
    <li>title: <c:out value="${project.title}"/></li>
    <li>description: <c:out value="${project.description}"/></li>
    <li>imageUrl: <img src="${project.imageUrl}" style="width: 50px; height: 50px;"></li>
    <li>budget: <c:out value="${project.budget}"/></li>
</ul>
<!-- display form if user has not bidded before -->
<c:if test = "${hasBid == false}">
    <form action="/JobOonJa_war/bid" method="POST">
        <label for="bidAmount">Bid Amount:</label>
        <input type="number" name="bidAmount">
        <input type="hidden" name="userId" value="1">
        <input type="hidden" name="projectId" value="${project.id}">
        <button>Submit</button>
    </form>
</c:if>
</body>
</html>