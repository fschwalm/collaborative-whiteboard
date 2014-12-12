function handleDrop(objectToDrag, receptor) {
	var dataToSend = createData(objectToDrag, receptor);
	
	if (isConnect()) {
		sendData(dataToSend);
	} else {
		console.log('Sem conexão.')
	}
    closeSocket();
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

function log(object) {
    console.log(object);
}