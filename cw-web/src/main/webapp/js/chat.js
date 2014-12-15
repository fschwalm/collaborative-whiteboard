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
            registerCloseConnection();
            registerReceiptsMessages();
            registerErrorHandling();
            registerRemovalReceivingFlag();
            setStatus();
        }

    }catch (exception){
        setStatus();
    }
}

function setStatus(){
    if(isConnect()){
        $('#chatHeader').text('Online');
        $('#chatHeader').css("color", "green");
        $('#chatHeader').css("background-color", "blue");
    }else{
        $('#chatHeader').text('Offline');
        $('#chatHeader').css("color", "white");
        $('#chatHeader').css("background-color", "gray");
    }
}

function registerCloseConnection(){
    ws.onclose = function(evt){
        setStatus();
    }
}

function registerErrorHandling(){
    ws.onerror = function(evt){
        alert("Erro ao comunicar com o servidor");
    }
}

function registerReceiptsMessages(){
    ws.onmessage = function (evt) {
        printReceivedMessages(JSON.parse(evt.data));
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

function printReceivedMessages(jsonObj) {
    var $outputList = $('#messagePanel')

    var date = jsonObj['date'];
    var messageValue = jsonObj['messageValue'];
    var user = jsonObj['user'];
    var formattedDate = moment(date).locale("pt-br").format('HH:mm - DD/MM/YYYY');

    $outputList.append(
        '<div class="receivedMessages"><br><span class="hour"> ' + formattedDate + ' </span><br>' +
        '<span class="user">' + user + '</span><br>' +
        '<span class="message">' + messageValue + '</span><br><br></div>');

}

function printMyMessages(jsonObj) {
    var $outputList = $('#messagePanel');

    var date = jsonObj['date'];
    var messageValue = jsonObj['messageValue'];
    var user = jsonObj['user'];
    var formattedDate = moment(date).locale("pt-br").format('HH:mm - DD/MM/YYYY');

    $outputList.append(
            '<div class="myMessages"><br><span class="hour"> ' + formattedDate + ' </span><br>' +
            '<span class="user">Eu</span><br>' +
            '<span class="message">' + messageValue + '</span><br><br></div>');
}


function commit(inputTextId) {
    if (isValidMessage(inputTextId)) {
        var $messageData = createMessage(inputTextId)
        var jsonObject = JSON.stringify($messageData)

        if(isConnect()){
            sendMessage(jsonObject);
            printMyMessages(JSON.parse(jsonObject));
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

