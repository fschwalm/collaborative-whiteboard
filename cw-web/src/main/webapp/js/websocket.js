var ws;

function connect() {
    ws = new WebSocket("ws://localhost:8080/collaborative-whiteboard/chat");
}

function sendMessage(message){
    ws.send(message);
}

function closeSocket() {
    ws.close();
}
