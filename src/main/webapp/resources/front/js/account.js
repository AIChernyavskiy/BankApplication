function findClient(clientName) {
    debugger
    hide();

    $.getJSON('http://localhost:8080/PrintAccounts', function (data) {
        debugger;
        var table = document.createElement("TABLE");
        table.style.width = '60%';
        table.style.margin = 'auto';
        table.style.border = '4px double #008080';
        table.style.borderCollapse = 'collapse';
        table.style.marginTop = '30px';
        table.border = "1";
        var columnTH = new Array();
        columnTH.push("ID");
        columnTH.push("ACCOUNT NUMBER");
        columnTH.push("SALDO");
        columnTH.push("CLIENT NAME");
        columnTH.push("CLIENT ID");
        var row = table.insertRow(-1);

        for (var i = 0; i < columnTH.length; i++) {
            var headerCell = document.createElement("TH");
            headerCell.innerHTML = columnTH[i];
            headerCell.style.textAlign = 'center';
            headerCell.style.background = '#ccc';
            headerCell.style.padding = '5px';
            headerCell.style.border = '1px solid #008080';
            headerCell.style.backgroundColor = '#008080';
            headerCell.style.color = 'white';
            row.appendChild(headerCell);
        }
        debugger;
        for (var j = 0; j < data.length; j++) {
            if (data[j].client.name === clientName.value) {
                row = table.insertRow(-1);
                var cellId = row.insertCell(-1);
                cellStyle(cellId);
                cellId.innerHTML = data[j].id;
                var cellNumber = row.insertCell(-1);
                cellNumber.innerHTML = data[j].accNum;
                cellStyle(cellNumber);
                var cellSaldo = row.insertCell(-1);
                cellStyle(cellSaldo);
                cellSaldo.innerHTML = data[j].saldo;
                var cellClientName = row.insertCell(-1);
                cellClientName.innerHTML = data[j].client.name;
                cellStyle(cellClientName);
                var cellClientId = row.insertCell(-1);
                cellClientId.innerHTML = data[j].client.id;
                cellStyle(cellClientId);
            }

        }
        document.body.appendChild(table);
    });
}

function hide() {
    var oldTable = document.querySelector('table');
    if (oldTable !== null) {
        oldTable.remove();
    }
    var elements = document.getElementsByClassName('AccountCRUD');
    var find = document.getElementsByClassName('AccountFind');
    for (var p = 0; p < elements.length; p++) {
        var childrenElements = elements[p].children;
        for (var d = 0; d < childrenElements.length; d++) {
            childrenElements[d].style.display = 'none';
        }
    }
    for (var r = 0; r < find.length; r++) {
        find[r].style.display = 'none';
    }
}

function cellStyle(cell) {
    cell.style.border = '1px solid #008080';
    cell.style.padding = '5px';
    cell.style.textAlign = 'center';
    cell.style.verticalAlign = 'middle';
}

function showHide() {

    hide();

    $.getJSON('http://localhost:8080/PrintAccounts', function (data) {
        var table = document.createElement("TABLE");
        table.style.width = '60%';
        table.style.margin = 'auto';
        table.style.border = '4px double #008080';
        table.style.borderCollapse = 'collapse';
        table.style.marginTop = '30px';
        table.border = "1";
        var columnTH = new Array();
        columnTH.push("ID");
        columnTH.push("ACCOUNT NUMBER");
        columnTH.push("SALDO");
        columnTH.push("CLIENT NAME");
        columnTH.push("CLIENT ID");
        var row = table.insertRow(-1);

        for (var i = 0; i < columnTH.length; i++) {
            var headerCell = document.createElement("TH");
            headerCell.innerHTML = columnTH[i];
            headerCell.style.textAlign = 'center';
            headerCell.style.background = '#ccc';
            headerCell.style.padding = '5px';
            headerCell.style.border = '1px solid #008080';
            headerCell.style.backgroundColor = '#008080';
            headerCell.style.color = 'white';
            row.appendChild(headerCell);
        }
        debugger;
        for (var j = 0; j < data.length; j++) {
            row = table.insertRow(-1);
            var cellId = row.insertCell(-1);
            cellStyle(cellId);
            cellId.innerHTML = data[j].id;
            var cellNumber = row.insertCell(-1);
            cellNumber.innerHTML = data[j].accNum;
            cellStyle(cellNumber);
            var cellSaldo = row.insertCell(-1);
            cellStyle(cellSaldo);
            cellSaldo.innerHTML = data[j].saldo;
            var cellClientName = row.insertCell(-1);
            cellClientName.innerHTML = data[j].client.name;
            cellStyle(cellClientName);
            var cellClientId = row.insertCell(-1);
            cellClientId.innerHTML = data[j].client.id;
            cellStyle(cellClientId);
        }
        document.body.appendChild(table);
    });
}