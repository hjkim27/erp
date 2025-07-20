<%--
  Created by IntelliJ IDEA.
  User: hjkim27
  Date: 2025-07-16
  Time: 오후 11:18
  - 모든 페이지에 적용하기 위한 js, css 를 관리하는 공통 header
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<%-- library --%>
<script type="text/javascript" src="${contextPath}/static/js/jquery/jquery-3.7.1.min.js"></script>

<%-- fontawesome --%>
<script type="text/javascript" src="${contextPath}/static/fontawesome/fontawesome-6.7.2/all.min.js"></script>
<link type="text/css" href="${contextPath}/static/fontawesome/fontawesome-6.7.2/all.min.css"/>

<%-- default --%>
<script type="text/javascript" src="${contextPath}/static/js/default/common.js"></script>
<script type="text/javascript" src="${contextPath}/static/js/default/param-checker.js"></script>
<script type="text/javascript" src="${contextPath}/static/js/default/valid-utils.js"></script>
<link type="text/css" href="${contextPath}/static/css/default/common.css"/>

<%-- custom component --%>
<script type="text/javascript" src="${contextPath}/static/custom-lib/select-box/select-box.js"></script>
<link type="text/css" href="${contextPath}/static/custom-lib/select-box/select-box.css"/>