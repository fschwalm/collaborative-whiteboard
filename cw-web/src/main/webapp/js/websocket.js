var ws;

function connect() {
    ws = new WebSocket("ws://localhost:8080/cw/chat");
}

function disconect() {
    ws.close();
}

function sendMessage(message) {
    ws.send(message);
}

function isConnect() {
    if (ws != null && ws != undefined && ws.readyState == WebSocket.OPEN) {
        return true;
    } else {
        return false;
    }
}

