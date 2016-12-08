function hide() {
    var oldTable = document.querySelector('table');
    if (oldTable !== null) {
        oldTable.remove();
    }
    var elements = document.getElementsByClassName('DocumentCRUD');
    var find = document.getElementsByClassName('DocumentFind');
    for (var p = 0; p < elements.length; p++) {
        var childrenElements = elements[p].children;
        for (var d = 0; d < childrenElements.length; d++) {
            childrenElements[d].style.display = 'none';
        }
    }
    for (var r = 0; r < find.length; r++) {
        console.log(find[r]);
        find[r].style.display = 'none';
    }
}

function cellStyle(cell) {
    cell.style.border = '1px solid #008080';
    cell.style.padding = '5px';
    cell.style.textAlign = 'center';
    cell.style.verticalAlign = 'middle';
}

function cellStyleRed(cell) {
    cell.style.border = '1px solid #008080';
    cell.style.padding = '5px';
    cell.style.textAlign = 'center';
    cell.style.verticalAlign = 'middle';
    cell.style.color = 'red';
}

function findClient(clientName) {
    hide();
    $.getJSON('http://localhost:8080/PrintDocuments', function (data) {

        var table = document.createElement("TABLE");
        table.style.width = '90%';
        table.style.margin = 'auto';
        table.style.border = '4px double #008080';
        table.style.borderCollapse = 'collapse';
        table.style.marginTop = '30px';
        table.border = "1";
        var columnTH = new Array();
        columnTH.push("ID DOCUMENT");
        columnTH.push("ACCOUNT NUMBER CT");
        columnTH.push("CLIENT NAME CT");
        columnTH.push("ACCOUNT NUMBER DT");
        columnTH.push("CLIENT NAME DT");
        columnTH.push("PURPOSE");
        columnTH.push("SUMMA");
        columnTH.push("DOC DATE");
        columnTH.push("STORNO");
        var row = table.insertRow(-1);
        debugger;
        for (var i = 0; i < columnTH.length; i++) {
            var headerCell = document.createElement("TH");
            headerCell.innerHTML = columnTH[i];
            headerCell.style.textAlign = 'center';
            headerCell.style.verticalAlign = 'center';
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
            if ((data[j].accountCT.client.name === clientName.value) || (data[j].accountDT.client.name === clientName.value)) {
                if (data[j].storno) {
                    var cellId = row.insertCell(-1);
                    cellStyleRed(cellId);
                    cellId.innerHTML = data[j].id;
                    var cellNumberCT = row.insertCell(-1);
                    cellNumberCT.innerHTML = data[j].accountCT.accNum;
                    cellStyleRed(cellNumberCT);
                    var cellClientNameCT = row.insertCell(-1);
                    cellClientNameCT.innerHTML = data[j].accountCT.client.name;
                    cellStyleRed(cellClientNameCT);
                    var cellNumberDT = row.insertCell(-1);
                    cellNumberDT.innerHTML = data[j].accountDT.accNum;
                    cellStyleRed(cellNumberDT);

                    var cellClientNameDT = row.insertCell(-1);
                    cellClientNameDT.innerHTML = data[j].accountDT.client.name;
                    cellStyleRed(cellClientNameDT);
                    var cellPurpose = row.insertCell(-1);
                    cellPurpose.innerHTML = data[j].purpose;
                    cellStyleRed(cellPurpose);
                    var cellSumma = row.insertCell(-1);
                    cellSumma.innerHTML = data[j].summa;
                    cellStyleRed(cellSumma);
                    var cellDocDate = row.insertCell(-1);
                    var d = new Date(data[j].docDate);
                    var formattedDate = d.getDate() + "-" + (d.getMonth() + 1) + "-" + d.getFullYear();
                    var hours = (d.getHours() < 10) ? "0" + d.getHours() : d.getHours();
                    var minutes = (d.getMinutes() < 10) ? "0" + d.getMinutes() : d.getMinutes();
                    var formattedTime = hours + ":" + minutes;
                    formattedDate = formattedDate + " " + formattedTime;
                    cellDocDate.innerHTML = formattedDate;
                    cellStyleRed(cellDocDate);
                    var cellStorno = row.insertCell(-1);
                    cellStorno.innerHTML = data[j].storno;
                    cellStyleRed(cellStorno);
                } else {
                    var cellId = row.insertCell(-1);
                    cellStyle(cellId);
                    cellId.innerHTML = data[j].id;
                    var cellNumberCT = row.insertCell(-1);
                    cellNumberCT.innerHTML = data[j].accountCT.accNum;
                    cellStyle(cellNumberCT);
                    var cellClientNameCT = row.insertCell(-1);
                    cellClientNameCT.innerHTML = data[j].accountCT.client.name;
                    cellStyle(cellClientNameCT);
                    var cellNumberDT = row.insertCell(-1);
                    cellNumberDT.innerHTML = data[j].accountDT.accNum;
                    cellStyle(cellNumberDT);
                    var cellClientNameDT = row.insertCell(-1);
                    cellClientNameDT.innerHTML = data[j].accountDT.client.name;
                    cellStyle(cellClientNameDT);
                    var cellPurpose = row.insertCell(-1);
                    cellPurpose.innerHTML = data[j].purpose;
                    cellStyle(cellPurpose);
                    var cellSumma = row.insertCell(-1);
                    cellSumma.innerHTML = data[j].summa;
                    cellStyle(cellSumma);
                    var cellDocDate = row.insertCell(-1);
                    var d = new Date(data[j].docDate);
                    var formattedDate = d.getDate() + "-" + (d.getMonth() + 1) + "-" + d.getFullYear();
                    var hours = (d.getHours() < 10) ? "0" + d.getHours() : d.getHours();
                    var minutes = (d.getMinutes() < 10) ? "0" + d.getMinutes() : d.getMinutes();
                    var formattedTime = hours + ":" + minutes;
                    formattedDate = formattedDate + " " + formattedTime;
                    cellDocDate.innerHTML = formattedDate;
                    cellStyle(cellDocDate);
                    var cellStorno = row.insertCell(-1);
                    cellStorno.innerHTML = data[j].storno;
                    cellStyle(cellStorno);
                }
            }
        };
        document.body.appendChild(table);
        alert(data);
    });
}

function showHide() {

    hide();

    $.getJSON('http://localhost:8080/PrintDocuments', function (data) {

        var table = document.createElement("TABLE");
        table.style.width = '90%';
        table.style.margin = 'auto';
        table.style.border = '4px double #008080';
        table.style.borderCollapse = 'collapse';
        table.style.marginTop = '30px';
        table.border = "1";
        var columnTH = new Array();
        columnTH.push("ID DOCUMENT");
        columnTH.push("ACCOUNT NUMBER CT");
        columnTH.push("CLIENT NAME CT");
        columnTH.push("ACCOUNT NUMBER DT");
        columnTH.push("CLIENT NAME DT");
        columnTH.push("PURPOSE");
        columnTH.push("SUMMA");
        columnTH.push("DOC DATE");
        columnTH.push("STORNO");
        var row = table.insertRow(-1);
        debugger;
        for (var i = 0; i < columnTH.length; i++) {
            var headerCell = document.createElement("TH");
            headerCell.innerHTML = columnTH[i];
            headerCell.style.textAlign = 'center';
            headerCell.style.verticalAlign = 'center';
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
            if (data[j].storno) {
                var cellId = row.insertCell(-1);
                cellStyleRed(cellId);
                cellId.innerHTML = data[j].id;
                var cellNumberCT = row.insertCell(-1);
                cellNumberCT.innerHTML = data[j].accountCT.accNum;
                cellStyleRed(cellNumberCT);
                var cellClientNameCT = row.insertCell(-1);
                cellClientNameCT.innerHTML = data[j].accountCT.client.name;
                cellStyleRed(cellClientNameCT);
                var cellNumberDT = row.insertCell(-1);
                cellNumberDT.innerHTML = data[j].accountDT.accNum;
                cellStyleRed(cellNumberDT);
                var cellClientNameDT = row.insertCell(-1);
                cellClientNameDT.innerHTML = data[j].accountDT.client.name;
                cellStyleRed(cellClientNameDT);
                var cellPurpose = row.insertCell(-1);
                cellPurpose.innerHTML = data[j].purpose;
                cellStyleRed(cellPurpose);
                var cellSumma = row.insertCell(-1);
                cellSumma.innerHTML = data[j].summa;
                cellStyleRed(cellSumma);
                var cellDocDate = row.insertCell(-1);
                var d = new Date(data[j].docDate);
                var formattedDate = d.getDate() + "-" + (d.getMonth() + 1) + "-" + d.getFullYear();
                var hours = (d.getHours() < 10) ? "0" + d.getHours() : d.getHours();
                var minutes = (d.getMinutes() < 10) ? "0" + d.getMinutes() : d.getMinutes();
                var formattedTime = hours + ":" + minutes;
                formattedDate = formattedDate + " " + formattedTime;
                cellDocDate.innerHTML = formattedDate;
                cellStyleRed(cellDocDate);
                var cellStorno = row.insertCell(-1);
                cellStorno.innerHTML = data[j].storno;
                cellStyleRed(cellStorno);
            } else {
                var cellId = row.insertCell(-1);
                cellStyle(cellId);
                cellId.innerHTML = data[j].id;
                var cellNumberCT = row.insertCell(-1);
                cellNumberCT.innerHTML = data[j].accountCT.accNum;
                cellStyle(cellNumberCT);
                var cellClientNameCT = row.insertCell(-1);
                cellClientNameCT.innerHTML = data[j].accountCT.client.name;
                cellStyle(cellClientNameCT);
                var cellNumberDT = row.insertCell(-1);
                cellNumberDT.innerHTML = data[j].accountDT.accNum;
                cellStyle(cellNumberDT);
                var cellClientNameDT = row.insertCell(-1);
                cellClientNameDT.innerHTML = data[j].accountDT.client.name;
                cellStyle(cellClientNameDT);
                var cellPurpose = row.insertCell(-1);
                cellPurpose.innerHTML = data[j].purpose;
                cellStyle(cellPurpose);
                var cellSumma = row.insertCell(-1);
                cellSumma.innerHTML = data[j].summa;
                cellStyle(cellSumma);
                var cellDocDate = row.insertCell(-1);
                var d = new Date(data[j].docDate);
                var formattedDate = d.getDate() + "-" + (d.getMonth() + 1) + "-" + d.getFullYear();
                var hours = (d.getHours() < 10) ? "0" + d.getHours() : d.getHours();
                var minutes = (d.getMinutes() < 10) ? "0" + d.getMinutes() : d.getMinutes();
                var formattedTime = hours + ":" + minutes;
                formattedDate = formattedDate + " " + formattedTime;
                cellDocDate.innerHTML = formattedDate;
                cellStyle(cellDocDate);
                var cellStorno = row.insertCell(-1);
                cellStorno.innerHTML = data[j].storno;
                cellStyle(cellStorno);
            }
        }
        document.body.appendChild(table);
    });
}