function cellStyle(cell) {
    cell.style.border = '1px solid #008080';
    cell.style.padding = '5px';
    cell.style.textAlign = 'center';
    cell.style.verticalAlign = 'middle';
}

function showHide() {
    debugger;
    var oldTable = document.querySelector('table');
    if (oldTable !== null) {
        oldTable.remove();
    }
    var elements = document.getElementsByClassName('ClientCRUD');
    var find = document.getElementsByClassName('ClientFind');
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


    $.getJSON('http://localhost:8080/PrintClients', function (data) {
        debugger;
        var table = document.createElement("TABLE");
        table.style.width = '20%';
        table.style.margin = 'auto';
        table.style.border = '4px double #008080';
        table.style.borderCollapse = 'collapse';
        table.style.marginTop = '30px';
        table.border = "1";
        var columnTH = new Array();
        ;
        columnTH.push("ID");
        columnTH.push("NAME");
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

        for (var j = 0; j < data.length; j++) {
            row = table.insertRow(-1);
            var cellId = row.insertCell(-1);
            cellStyle(cellId);
            cellId.innerHTML = data[j].id;
            var cellName = row.insertCell(-1);
            cellName.innerHTML = data[j].name;
            cellStyle(cellName);
        }
        debugger;
        document.body.appendChild(table);
    });
}