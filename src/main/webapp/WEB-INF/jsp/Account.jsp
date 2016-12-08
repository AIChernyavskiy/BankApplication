<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <spring:url value="/resources/front/css/reset.css" var="resetCss" />
    <link href="${resetCss}" rel="stylesheet" />
    <spring:url value="/resources/front/css/account.css" var="accountCss"/>
    <link href="${accountCss}" rel="stylesheet"/>
    <spring:url value="/resources/front/js/account.js" var="accountJs"/>
    <script src="${accountJs}"></script>
    <spring:url value="/resources/front/js/jquery.js" var="jqueryJs"/>
    <script src="${jqueryJs}"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <meta charset="UTF-8">
    <title>Account</title>
</head>
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
            <ul class="sub-menu sub-menu-2">
                <li><a href="/Account">Create account</a></li>
                <li><a href="/Account">Create account and client</a></li>
                <li><a href="/Account">Update account</a></li>
                <li><a href="/Account">Delete account</a></li>
                <li><a href="/Account">Find account</a></li>
                <li><a onclick="showHide()">Find all account</a></li>
            </ul>
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
<div class = "AccountCRUD">
    <div class="AccountCreate">
        <form action="/Account/CreateAccount" method="get">
            <p><strong>Account number</strong></p>
            <p><input name="accNum" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><strong>Saldo</strong></p>
            <p><input name="saldo" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><strong>Client name</strong></p>
            <p><input name="clientName" required maxlength="50" size="50" pattern="[A-Za-z]{2,}"></p>
            <p><input type="submit" value="Create account"></p>
        </form>
        <input name="messageFromServerCreate" maxlength="100" size="50" value="${messageCreateAccount}">
    </div>
    <div class="AccountCreate">
        <form action="/Account/CreateAccountAndClient" method="get">
            <p><strong>Account number</strong></p>
            <p><input name="accNum" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><strong>Saldo</strong></p>
            <p><input name="saldo" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><strong>Client name</strong></p>
            <p><input name="clientName" required maxlength="50" size="50" pattern="[A-Za-z]{2,}"></p>
            <p><input type="submit" value="Create account and client"></p>
        </form>
        <input name="messageFromServerCreate" maxlength="100" size="50" value="${messageCreateAccountAndClient}">
    </div>

    <div class="AccountDelete">
        <form action="/Account/Delete" method="get">
            <p><strong>Account number</strong></p>
            <p><input name="accNum" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><input type="submit" value="Delete account"></p>
        </form>
        <input name="messageFromServerDelete" maxlength="100" size="50" value="${messageDelete}">
    </div>

    <div class="AccountUpdate">
        <form action="/Account/UpdateNumber" method="get">
            <p><strong>OLD Account Number</strong></p>
            <p><input name="oldAccNum" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><strong>NEW Account Number</strong></p>
            <p><input name="newAccNum" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><input type="submit" value="Update number"></p>
        </form>
        <input name="messageFromServerDelete" maxlength="100" size="50" value="${messageUpdateNumber}">
    </div>
    <div class="AccountUpdate">
        <form action="/Account/UpdateSaldo" method="get">
            <p><strong>Account Number</strong></p>
            <p><input name="accNum" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><strong>NEW Saldo</strong></p>
            <p><input name="newSaldo" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><input type="submit" value="Update saldo"></p>
        </form>
        <input name="messageFromServerDelete" maxlength="100" size="50" value="${messageUpdateSaldo}">
    </div>
    <div class="AccountUpdate">
        <form action="/Account/UpdateClient" method="get">
            <p><strong>Account Number</strong></p>
            <p><input name="accNum" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><strong>NEW client</strong></p>
            <p><input name="clientName" required maxlength="50" size="50" pattern="[A-Za-z]{2,}"></p>
            <p><input type="submit" value="Update client"></p>
        </form>
        <input name="messageFromServerDelete" maxlength="100" size="50" value="${messageUpdateClient}">
    </div>
</div>
<div class="AccountFind">
    <div class="AccounttFi">
        <form action="/Account/Find" method="get">
            <p><strong>Account Number</strong></p>
            <p><input name="accNum" required maxlength="50" size="50" pattern="^[ 0-9]+$"></p>
            <p><input type="submit" value="Find account"></p>
        </form>
        <input name="messageFromServerDelete" maxlength="100" size="50" value="${messageFind}">
    </div>
    <div class="ClientFind">
        <form onsubmit = "findClient(clientName);return false;">
            <p><strong>Client NAME</strong></p>
            <p><input name="clientName" required maxlength="50" size="50" pattern="[A-Za-z]{2,}"></p>
            <p><input type="submit" value="Find accounts for client"></p>
        </form>
        <input name="messageFromServerDelete" maxlength="100" size="50">
    </div>
</div>
</html>
