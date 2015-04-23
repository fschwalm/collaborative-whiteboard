var chatIsOpen;

$(document).ready(function () {
    initChat();
});

function initChat() {
    try {
        if (!isConnect()) {
            connect();
        }
        registerClickChatButton();
        registerCloseConnection();
        registerReceiptsMessages();
        registerErrorHandling();
        registerRemovalReceivingFlag();
        enableChat();

    } catch (exception) {
        disableChat();
    }
}

function scrollChat() {
    $('#outputMessage').scrollTo($("#outputMessage").get(0).scrollHeight);
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
        flagReceipt();
    }
}

function registerRemovalReceivingFlag() {
    $('#outputMessage').bind('mouseover', function () {
        $('#chatHeader').removeClass('receivedMessageFlag');
    })

    $('#messageInput').bind('mouseover', function () {
        $('#chatHeader').removeClass('receivedMessageFlag');
    })

    $('#chatField').bind('mouseover', function () {
        $('#chatHeader').removeClass('receivedMessageFlag');
    })
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

function flagReceipt() {
    $('#chatHeader').addClass('receivedMessageFlag');
}

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
    $("#chatButton").removeClass('chatButtonEnable').addClass('chatButtonDisable');
    $("#chatButton").off();
}

function enableChat() {
    $("#chatButton").removeClass('chatButtonDisable').addClass('chatButtonEnable')
}

function registerClickChatButton() {
    $("#chatButton,#chatHeader").click(function () {
        if (!chatIsOpen) {
            openChat();
        }else{
            closeChat();
        }
    });
}

function openChat() {
    $("#chatButton").animate({right: "554px"}, 50);
    $("#chatButton").css({top: "156px"});
    $("#chatHeader").animate({width: "437px"}, 50);
    $('#chatField').slideToggle('fast');
    chatIsOpen = true;
}

function closeChat() {
    $("#chatButton").animate({right: "320px"}, 50);
    $("#chatButton").css({top: "156px"});
    $("#chatHeader").animate({width: "203px"}, 50);
    $('#chatField').slideToggle('fast');
    chatIsOpen = false;
}