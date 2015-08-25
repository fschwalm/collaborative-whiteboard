var chatService = 'chat';
var websocket;


$(document).ready(function () {
    initChat();
});

function initChat() {
    try {
        if (isConnect(websocket) == false) {
            websocket = connect(chatService);
            registerCloseConnection();
            registerReceiptsMessages();
            registerErrorHandling();
        }

    } catch (exception) {
    }
}

function registerCloseConnection() {
    websocket.onclose = function (evt) {
    }
}

function registerErrorHandling() {
    websocket.onerror = function (evt) {
    }
}

function registerReceiptsMessages() {
    websocket.onmessage = function (evt) {
        messageWriter(JSON.parse(evt.data));
    }
}

function commit(input_element) {
    var input_element = $(input_element);

    if (isValidMessage(input_element)) {
        var messageData = createMessage(input_element)
        var jsonObject = JSON.stringify(messageData)

        if (isConnect(websocket)) {
            sendMessage(jsonObject, websocket);
        } else {
        }
    }
}

function createMessage(input_element) {
    var messageData = {
        'messageValue': input_element.val(),
        'date': new Date()
    };

    clearMessage(input_element);

    return messageData;
}

function isValidMessage(input_element) {
    if (input_element.val().trim().length > 0) {
        return true;
    } else {
        return false
    };
}

function clearMessage(input_element) {
    input_element.val('');
}

function scroll_chat() {
    $('.chat_panel').scrollTop(0);
}

function disconect() {
    websocket.close();
}
