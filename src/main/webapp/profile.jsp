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
                        <c:out value="${skill.value.name}"/>: <c:out value="${skill.value.point}"/>
                        <c:set var="contains" value="false" />
                        <c:forEach var="item" items="${skill.value.endorsers}">
                            <c:if test="${item == '1' }">
                                <c:set var="contains" value="true" />
                            </c:if>
                        </c:forEach>
                            <li>
                                <c:if test="${not contains}">
                                <form action="/endorse" method="POST">
                                    <input type="hidden" name="skillName" value="${skill.value.name}">
                                    <input type="hidden" name="userId" value="${user.id}">
                                    <button>Endorse</button>
                                </form>
                                </c:if>
                            </li>

                    </c:forEach>
                </ul>
            </li>
        </c:if>
    </ul>
</body>
</html>