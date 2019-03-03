<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User</title>
</head>
<body>
    <ul>
        <li>id: <c:out value="${user.id}"/></li>
        <li>first name: <c:out value="${user.firstName}"/></li>
        <li>last name: <c:out value="${user.lastName}"/></li>
        <li>jobTitle: <c:out value="${user.jobTitle}"/></li>
        <li>bio: <c:out value="${user.bio}"/></li>
        <c:if test="${fn:length(user.skills) gt 0}">
            <li>
                skills:
                <ul>
                    <c:forEach var="skill" items="${user.skills}">
                        <li>
                            <c:out value="${skill.value.name}"/>: <c:out value="${skill.value.point}"/>
                            <form action="/skill" method="GET">
                                <input type="hidden" name="userId" value="${user.id}">
                                <input type="hidden" name="skillName" value="${skill.value.name}">
                                <button>Delete</button>
                            </form>
                        </li>
                    </c:forEach>
                </ul>
            </li>
        </c:if>
    </ul>
    <c:if test="${fn:length(abilities) gt 0}">
        Add Skill:
        <form action="/skill" method="POST">
            <select name="skill">
                <c:forEach var="ability" items="${abilities}">
                    <option value="${ability.name}"><c:out value="${ability.name}"/></option>
                </c:forEach>
            </select>
            <input type="hidden" name="userId" value="${user.id}">
            <button>Add</button>
        </form>
    </c:if>
</body>
</html>