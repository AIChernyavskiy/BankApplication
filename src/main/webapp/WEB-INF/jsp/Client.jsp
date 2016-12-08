<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <spring:url value="/resources/front/css/reset.css" var="resetCss" />
    <link href="${resetCss}" rel="stylesheet" />
    <spring:url value="/resources/front/css/client.css" var="clientCss"/>
    <link href="${clientCss}" rel="stylesheet"/>
    <spring:url value="/resources/front/js/client.js" var="clientJs"/>
    <script src="${clientJs}"></script>
    <spring:url value="/resources/front/js/jquery.js" var="jqueryJs"/>
    <script src="${jqueryJs}"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <meta charset="UTF-8">
    <title>Client</title>
</head>
<body>
<div class = "menu1">
    <ul class="main-menu">
        <li class="opened">
            <a href="/Client">Client</a>
            <ul class="sub-menu sub-menu-1">
                <li><a href="/Client">Create client</a></li>
                <li><a href="/Client">Update client</a></li>
                <li><a href="/Client">Delete client</a></li>
                <li><a href="/Client">Find client</a></li>
                <li><a onclick="showHide()">Print all client</a></li>
            </ul>
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
<div class = "ClientCRUD">
    <div class="ClientCreate">
        <form action="/Client/Create" method="get">
            <p><strong>Client NAME</strong></p>
            <p><input name="name" required maxlength="50" size="50" pattern="[A-Za-z]{2,}"></p>
            <p><input type="submit" value="Create client"></p>
        </form>
        <input name="messageFromServerCreate" maxlength="100" size="50" value="${messageCreate}">
    </div>

    <div class="ClientDelete">
        <form action="/Client/Delete" method="get">
            <p><strong>Client NAME</strong></p>
            <p><input name="name" required maxlength="50" size="50" pattern="[A-Za-z]{2,}"></p>
            <p><input type="submit" value="Delete client"></p>
        </form>
        <input name="messageFromServerDelete" maxlength="100" size="50" value="${messageDelete}">
    </div>

    <div class="ClientUpdate">
        <form action="/Client/Update" method="get">
            <p><strong>Client OLD NAME</strong></p>
            <p><input name="oldName" required maxlength="50" size="50" pattern="[A-Za-z]{2,}"></p>
            <p><strong>Client NEW NAME</strong></p>
            <p><input name="newName" required maxlength="50" size="50" pattern="[A-Za-z]{2,}"></p>
            <p><input type="submit" value="Update client"></p>
        </form>
        <input name="messageFromServerDelete" maxlength="100" size="50" value="${messageUpdate}">
    </div>
</div>
<div class="ClientFind">
    <div class="ClientFi">
        <form action="/Client/Find" method="get">
            <p><strong>Client NAME</strong></p>
            <p><input name="name" required maxlength="50" size="50" pattern="[A-Za-z]{2,}"></p>
            <p><input type="submit" value="Find client"></p>
        </form>
        <input name="messageFromServerDelete" maxlength="100" size="50" value="${messageFind}">
    </div>
</div>
</body>
</html>