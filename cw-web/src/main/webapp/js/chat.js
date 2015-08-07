var chatIsOpen;
var chatService = 'chat';
var websocket;

$(document).ready(function () {
    initChat();
});

function initChat() {
    try {
        if (isConnect(websocket) == false) {
            websocket = connect(chatService);
            registerClickChatButton();
            registerCloseConnection();
            registerReceiptsMessages();
            registerErrorHandling();
            registerRemovalReceivingFlag();
            enableChat();
        }

    } catch (exception) {
        disableChat();
    }
}

function registerCloseConnection() {
    websocket.onclose = function (evt) {
        disableChat();
    }
}

function registerErrorHandling() {
    websocket.onerror = function (evt) {
        disableChat();
    }
}

function registerReceiptsMessages() {
    websocket.onmessage = function (evt) {
        messageWriter(JSON.parse(evt.data));
    }
}

function registerRemovalReceivingFlag() {
    $('#chatBtn').click(function () {
        $("span[id=receivedMessageFlag]").remove();
    });
}

function commit(inputTextId) {
    if (isValidMessage(inputTextId)) {
        var $messageData = createMessage(inputTextId)
        var jsonObject = JSON.stringify($messageData)

        if (isConnect(websocket)) {
            sendMessage(jsonObject, websocket);
        } else {
            disableChat();
        }
    }
};

function createMessage(inputTextId) {
    var $inputText = jQuery('#' + inputTextId);

    var $messageData = {
        'messageValue': $inputText.val(),
        'date': new Date()
    };

    clearMessage(inputTextId);

    return $messageData;

};

function isValidMessage(inputTextId) {
    var $inputText = jQuery('#' + inputTextId);
    if ($inputText.val().trim().length > 0) {
        return true;
    } else {
        return false
    }
    ;
};

function clearMessage(inputTextId) {
    var $inputText = jQuery('#' + inputTextId);
    $inputText.val('');
};

function scrollChat() {
    $('.chatPanel').scrollTop($('.chatPanel')[0].scrollHeight)
}


function disableChat() {
    $('#chatBtn').children('img').attr('src','../../images/chat_button_disable.png')
    $("#messageInput").prop('disabled', true);
}

function enableChat() {
    $('#chatBtn').children('img').attr('src','../../images/chat_button_enable.png');
    $("#messageInput").prop('disabled', false);
}

function registerClickChatButton() {
    $("#chatBtn").click(function () {
        if (!chatIsOpen) {
            openChat();
        }else{
            closeChat();
        }
    });
}

function openChat() {
    $('#exitBtn').animate({'margin-right': "10%"});
    $("#chat").slideDown('fast');
    scrollChat();
    $("#messageInput").focus();
    chatIsOpen = true;
}

function closeChat() {
    $('#exitBtn').animate({'margin-right': "0%"});
    $("#chat").slideUp('fast');
    chatIsOpen = false;
}

function disconect() {
    websocket.close();
}
