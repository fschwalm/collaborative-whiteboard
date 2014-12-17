$(document).ready(function () {
    $("#chatField").hide();

    $("#chatHeader").click(function() {
        $("#chatField").slideToggle('slow');
    });

    $("#sendBtn").click(function() {
        // DESCER BARRA DE ROLAGEM
    });

    initChat();

});

function initChat(){
    try{
        if(! isConnect()){
            connect();
        }

        registerCloseConnection();
        registerReceiptsMessages();
        registerErrorHandling();
        registerRemovalReceivingFlag();

    }catch (exception){}
}

function registerCloseConnection(){
    ws.onclose = function(evt){
        stayOffline();
    }
}

function registerErrorHandling(){
    ws.onerror = function(evt){
        alert("Erro ao comunicar com o servidor");
    }
}

function registerReceiptsMessages(){
    ws.onmessage = function (evt) {
        messageWriter(JSON.parse(evt.data));
        flagReceipt();
    }
}

function registerRemovalReceivingFlag(){
    $('#outputMessage').bind('mouseover', function(){
        $('#chatHeader').removeClass('receivedMessageFlag');
    })

    $('#messageInput').bind('mouseover', function(){
        $('#chatHeader').removeClass('receivedMessageFlag');
    })
}


function commit(inputTextId) {
    if (isValidMessage(inputTextId)) {
        var $messageData = createMessage(inputTextId)
        var jsonObject = JSON.stringify($messageData)

        if(isConnect()){
            sendMessage(jsonObject);
        }else{
            alert('Sem conexão');
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

function flagReceipt(){
    $('#chatHeader').addClass('receivedMessageFlag');
}

