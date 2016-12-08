<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <spring:url value="/resources/front/css/reset.css" var="resetCss" />
    <link href="${resetCss}" rel="stylesheet" />
    <spring:url value="/resources/front/css/document.css" var="documentCss"/>
    <link href="${documentCss}" rel="stylesheet"/>
    <spring:url value="/resources/front/js/document.js" var="documentJs"/>
    <script src="${documentJs}"></script>
    <spring:url value="/resources/front/js/jquery.js" var="jqueryJs"/>
    <script src="${jqueryJs}"></script>
    <meta charset="UTF-8">
    <title>Document</title>
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
            <ul class="sub-menu sub-menu-3">
                <li><a href="/Document">Create document</a></li>
                <li><a href="/Document">Create document, account and client</a></li>
                <li><a href="/Document">Delete document</a></li>
                <li><a href="/Document">Find document</a></li>
                <li><a onclick="showHide()">Find all document</a></li>
            </ul>
        </li>

    </ul>
</div>
<div class = "DocumentCRUD">
    <div class="DocumentCreate">
        <form action="/Document/CreateDocument" method="get">
            <p><strong>AccountDT</strong></p>
            <p><input name="accNumDT" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><strong>AccountCT</strong></p>
            <p><input name="accNumCT" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><strong>Purpose</strong></p>
            <p><input name="purpose" required maxlength="50" size="50" pattern="[A-Za-z]{4,}"></p>
            <p><strong>Summa</strong></p>
            <p><input name="summa" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><input type="submit" value="Create document"></p>
        </form>
        <input name="messageFromServerCreate" maxlength="100" size="50" value="${messageCreateDocument}">
    </div>
    <div class="DocumentCreate">
        <form action="/Document/CreateDocumentAccountClient" method="get">
            <p><strong>Account number DT</strong></p>
            <p><input name="accNumDT" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><strong>Saldo DT</strong></p>
            <p><input name="saldoDT" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><strong>Client name</strong></p>
            <p><input name="clientNameDT" required maxlength="50" size="50" pattern="[A-Za-z]{2,}"></p>
            <p><strong>Account number CT</strong></p>
            <p><input name="accNumCT" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><strong>Saldo CT</strong></p>
            <p><input name="saldoCT" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><strong>Client name CT</strong></p>
            <p><input name="clientNameCT" required maxlength="50" size="50" pattern="[A-Za-z]{2,}"></p>
            <p><strong>Purpose</strong></p>
            <p><input name="purpose" required maxlength="50" size="50" pattern="[A-Za-z]{4,}"></p>
            <p><strong>Summa</strong></p>
            <p><input name="summa" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><input type="submit" value="Create document, account and client"></p>
        </form>
        <input name="messageFromServerCreate" maxlength="100" size="50" value="${messageCreateDocumentAccountClient}">
    </div>

    <div class="DocumentDelete">
        <form action="/Document/Delete" method="get">
            <p><strong>Document ID</strong></p>
            <p><input name="idDocument" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><input type="submit" value="Delete document"></p>
        </form>
        <input name="messageFromServerDelete" maxlength="100" size="50" value="${messageDelete}">
    </div>
</div>
<div class="DocumentFind">
    <div class="DocumentFi">
        <form action="/Document/Find" method="get">
            <p><strong>Document ID</strong></p>
            <p><input name="idDocument" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><input type="submit" value="Find document"></p>
        </form>
        <input name="messageFromServerDelete" maxlength="100" size="50" value="${messageFind}">
    </div>
    <div class="ClientFind">
        <form onsubmit = "findClient(clientName);return false;">
            <p><strong>Client NAME</strong></p>
            <p><input name="clientName" required maxlength="50" size="50" pattern="[A-Za-z]{2,}"></p>
            <p><input type="submit" value="Find documents for client"></p>
        </form>
        <input name="messageFromServerDelete" maxlength="100" size="50">
    </div>
</div>
</body>
</html>
