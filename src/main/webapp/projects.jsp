<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Projects</title>
    <style>
        table {
            text-align: center;
            margin: 0 auto;
        }
        td {
            min-width: 300px;
            margin: 5px 5px 5px 5px;
            text-align: center;
        }
    </style>
</head>
<body>
<table>
    <tr>
        <th>id</th>
        <th>title</th>
        <th>budget</th>
    </tr>
    <jsp:useBean id="projects" scope="request" type="java.util.List"/>
    <c:forEach var="project" items="${projects}">
    <tr>
        <td><c:out value="${project.id}"/></td>
        <td><c:out value="${project.title}"/></td>
        <td><c:out value="${project.budget}"/></td>
    </tr>
    </c:forEach>
</table>
</body>
</html>