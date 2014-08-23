<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/includes/init.jsp" %>
<s:url id="url" action="loginAction" namespace="/login" includeContext="false" includeParams="get"/>
<c:redirect url="${requestScope.url}"/>
