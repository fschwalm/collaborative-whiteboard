function connect(service) {
    return new WebSocket('ws://'+ document.location.host + '/cw/' + service);

}

function sendMessage(message, websocket) {
    websocket.send(message);
}

function isConnect(websocket) {
    if (websocket != null && websocket != undefined && websocket.readyState == WebSocket.OPEN) {
        return true;
    } else {
        return false;
    }
}

