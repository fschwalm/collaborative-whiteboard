function handleDrop(objectToDrag, receptor) {
	var dataToSend = createData(objectToDrag, receptor);
	
	if (isConnect()) {
		sendData(dataToSend);
	} else {
		console.log('Sem conexão.')
	}
}

function createData(objectToDrag, receptor) {
    var from = $(objectToDrag.draggable[0]).parent().parent().attr('id'),
        to = null,
        data = null;
    
    receptor.append($(objectToDrag.draggable[0]));
    to = $(objectToDrag.draggable[0]).parent().parent().attr('id');
	
    data = {
        'id'   : $(objectToDrag.helper[0]).attr('id'),
        'top'  : objectToDrag.offset.top,
        'left' : objectToDrag.offset.left,
        'from' : from,
        'to'   : to
    };

    return data;
}

function sendData(data) {
	sendMessage(JSON.stringify(data));
}

function registerOnMessageEvent() {
    ws.onmessage = function (evt) {
        var data =JSON.parse(evt.data);
        drawTask(data);
        log(data);
    }
}

function drawTask(data) {
	var receptor = $('#' + data.to),
		ticket = $('#' + data.id);
	
	ticket.css('top', data.top - ticket.height());
	ticket.css('left', data.left - 20);
	
	receptor.append(ticket);
}

function log(object) {
    console.log(object);
}