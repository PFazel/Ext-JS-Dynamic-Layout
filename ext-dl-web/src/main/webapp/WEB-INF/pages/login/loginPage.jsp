    <%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/init.jsp" %>
<html>
<head>
    <title><fmt:message key="template.application.title"/></title>
    <style type="text/css">
        body {
            background-color: #666;
        }

        #loginBox {
            width: 450px;
            height: 250px;
            background-color: #CCC;
            margin-top: 100px;
            margin: 120px auto;
            border: 2px solid #FFF;
            border-radius: 5px;
            box-shadow: -2px 14px 17px rgba(0, 0, 0, 0.34);
            font-family: tahoma;
            font-size: 12px;

        }

        .field {
            display: block;
            width: 380px;
            height: 30px;
            border: 2px solid #999;
            background-color: #FFF;
            margin: 5px auto;
            border-radius: 5px;
            color: #FFF;
            padding: 5px 10px;

        }

        input[type="text"],
        input[type="password"] {
            height: 23px;
            width: 310px;
            border: none;
            color: #666;
            line-height: 23px;

        }

        input[type="text"]:focus,
        input[type="password"]:focus {
            outline: none;
        }

        [type="submit"] {
            display: block;
            width: 400px;
            height: 40px;
            background-color: #999;
            border: 2px solid #fff;
            margin: 5px auto;
            border-radius: 5px;
            color: #FFF;
            font-weight: bold;
            cursor: pointer;
        }

        label {
            color: #666;
        }

        #errorPlace {
            margin: 5px 0;
            height: 20px;
            color: #F00;
            text-align: center;
        }

        #title {
            font-family: Verdana, Geneva, sans-serif;
            font-size: 16px;
            font-weight: bold;
            color: #666;
            text-align: center;
            margin: 20px;
            text-shadow: 0px 1px 2px rgba(255, 255, 255, 1);
        }

    </style>
</head>

<body>
<s:url action="login" namespace="/login" var="loginUrl"/>
<form action="${loginUrl}" method="post" name="loginForm">
    <s:token name="loginToken"/>
    <div id="loginBox">
        <div id="title"><fmt:message key="template.application.title"/></div>
        <div id="errorPlace">
            <c:forEach var="error" items="${requestScope.actionErrors}">
                <fmt:message key="${error}"/>
            </c:forEach>
        </div>
        <div class="field"><input type="text" name="username" id="username"/><label for="username">
            <fmt:message key="login.username"/>:</label>
        </div>
        <div class="field"><input type="password" name="password" id="password"/><label for="password"><fmt:message
                key="login.password"/>:
        </label></div>
        <input type="submit" value="<fmt:message key='login.login'/>"/>
    </div>
</form>
</body>
</html>