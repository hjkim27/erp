<%--
  Created by IntelliJ IDEA.
  User: hjkim27
  Date: 2025-07-16
  Time: 오후 11:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="variables.jsp"/>

<div class="menu-container">
    <div class="flex-item left-item">
        <div class="logo">
            logo
            <%--logo--%>
        </div>
        <div class="menu">
            <div class="menu-item">menu1</div>
            <div class="menu-item">menu2</div>
            <div class="menu-item">menu3</div>
        </div>
    </div>
    <div class="flex-item flex-item">
        <div class="search">
            search
        </div>
        <div class="menu">
            icon
            <%-- 마우수 hover 시 열리는 메뉴--%>
        </div>
    </div>
</div>