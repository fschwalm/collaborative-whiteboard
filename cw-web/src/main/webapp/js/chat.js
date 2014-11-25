$(document).ready(function () {
    connect();
    ws.onmessage = function (evt) {
        printReceivedMessages(JSON.parse(evt.data));
    }
});

function printReceivedMessages(jsonObj) {
    var $outputList = $('#messagePanel')

    var date = jsonObj['date'];
    var messageValue = jsonObj['messageValue'];
    var user = jsonObj['user'];
    var formattedDate = moment(date).locale("pt-br").format('DD/MM/YYYY HH:mm');

    $outputList.append(
        '<span class="receivedMessages"><b> ' + formattedDate + ' </b></span>' +
        '<span class="receivedMessages">' + user + '</span><br>' +
        '<span class="receivedMessages">' + messageValue + '</span><br><br>');
}

function printMyMessages(jsonObj) {
    var $outputList = $('#messagePanel');

    var date = jsonObj['date'];
    var messageValue = jsonObj['messageValue'];
    var user = jsonObj['user'];
    var formattedDate = moment(date).locale("pt-br").format('DD/MM/YYYY HH:mm');

    $outputList.append(
            '<span class="myMessages"><b> ' + formattedDate + ' </b></span>' +
            '<span class="myMessages">Eu</span><br>' +
            '<span class="myMessages">' + messageValue + '</span><br><br>');
}


function commit(inputTextId) {
    if (isValidMessage(inputTextId)) {
        var $messageData = createMessage(inputTextId)
        var jsonObject = JSON.stringify($messageData)

        sendMessage(jsonObject);
        printMyMessages(JSON.parse(jsonObject));
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


