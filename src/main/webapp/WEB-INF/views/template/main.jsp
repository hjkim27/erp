<%--
  Created by IntelliJ IDEA.
  User: hjkim27
  Date: 2025-07-16
  Time: 오후 11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Title</title>
    <%@include file="/WEB-INF/views/layout/header/common-header.jsp" %>
</head>
<body>
<div class="container-layout">
    <jsp:include page="../layout/menu.jsp"/>

    <div class="container main-container"></div>
</div>

</body>
<script>
    $.ajax({
        type: "get",
        url: '${contextPath}/test/path/info',
        success: function (data) {
            if (data) {
                $('.main-container').html(data);
            } else {
                $('.main-container').html('<p>정보를 불러오는 데 실패했습니다.</p>');
            }
        },
        error: function (e) {
            console.error(e);
        }
    })
</script>

</html>
