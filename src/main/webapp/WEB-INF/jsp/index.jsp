<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <spring:url value="/resources/front/css/reset.css" var="resetCss" />
    <link href="${resetCss}" rel="stylesheet" />
    <spring:url value="/resources/front/css/index.css" var="indexCss" />
    <link href="${indexCss}" rel="stylesheet" />
    <meta charset="UTF-8">
    <title>Bank Application</title>
</head>
<body>
<div class = "menu1">
    <ul class="main-menu">
        <li class="opened">
            <a href="/Client">Client</a>
            <!--<ul class="sub-menu sub-menu-1">
                <li><a href="/Client">Create client</a></li>
                <li><a href="/Client">Update client</a></li>
                <li><a href="/Client">Delete client</a></li>
                <li><a href="/Client">Find client</a></li>
                <li><a href="/Client">Print all client</a></li>
            </ul>-->
        </li>
        <li class="opened">
            <a href="/Account">Account</a>
            <!--<ul class="sub-menu sub-menu-2">
                <li><a href="/Account">Create account</a></li>
                <li><a href="/Account">Create account and client</a></li>
                <li><a href="/Account">Update account</a></li>
                <li><a href="/Account">Delete account</a></li>
                <li><a href="/Account">Find account</a></li>
                <li><a href="/Account">Find all account</a></li>
            </ul>-->
        </li>
        <li class="opened">
            <a href="/Document">Document</a>
            <!--<ul class="sub-menu sub-menu-3">
                <li><a href="/Document">Create document</a></li>
                <li><a href="/Document">Create document, account and client</a></li>
                <li><a href="/Document">Delete document</a></li>
                <li><a href="/Document">Find document</a></li>
                <li><a href="/Document">Find all document</a></li>
            </ul>-->
        </li>

    </ul>
</div>
<div class = "DB">
    <p><a href="/CreateDB">Create Data Base</a></p>
    <input name="messageFromServerDB" maxlength="100" size="45" value="${messageDB}">
</div>
</body>
</html>