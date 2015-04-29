var chatIsOpen;

$(document).ready(function () {
    initChat();
});

function initChat() {
    try {
        if (!isConnect()) {
            connect();
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
    ws.onclose = function (evt) {
        disableChat();
    }
}

function registerErrorHandling() {
    ws.onerror = function (evt) {
        disableChat();
    }
}

function registerReceiptsMessages() {
    ws.onmessage = function (evt) {
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

        if (isConnect()) {
            sendMessage(jsonObject);
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


$.fn.scrollTo = function (target, options, callback) {
    if (typeof options == 'function' && arguments.length == 2) {
        callback = options;
        options = target;
    }
    var settings = $.extend({
        scrollTarget: target,
        offsetTop: 50,
        duration: 500,
        easing: 'swing'
    }, options);
    return this.each(function () {
        var scrollPane = $(this);
        var scrollTarget = (typeof settings.scrollTarget == "number") ? settings.scrollTarget : $(settings.scrollTarget);
        var scrollY = (typeof scrollTarget == "number") ? scrollTarget : scrollTarget.offset().top + scrollPane.scrollTop() - parseInt(settings.offsetTop);
        scrollPane.animate({scrollTop: scrollY}, parseInt(settings.duration), settings.easing, function () {
            if (typeof callback == 'function') {
                callback.call(this);
            }
        });
    });
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
    $('#exitBtn').animate({'margin-right': "8%"});
    $("#chatField").slideDown('fast');
    $("#messageInput").focus();
    scrollChat();
    chatIsOpen = true;
}

function closeChat() {
    $('#exitBtn').animate({'margin-right': "0%"});
    $("#chatField").slideUp('fast');
    chatIsOpen = false;
}
