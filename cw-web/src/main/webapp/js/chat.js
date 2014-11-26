$(document).ready(function () {
    connect();
    ws.onmessage = function (evt) {
        printReceivedMessages(JSON.parse(evt.data));
        flagReceipt();
    }
});

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

function flagReceipt(){
    $('chatFieldSet').addClass('.flagReceipt');
}