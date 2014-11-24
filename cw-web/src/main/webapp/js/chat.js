$(document).ready(function () {
    connect();
    ws.onmessage = function(evt){
        printMessages(evt);
    }
});

function printMessages(evt){
    var $outputList = $('#messagePanelTable').find('tbody')
    var jsonObj = JSON.parse(evt.data);

    var date = jsonObj['date'];
    var messageValue = jsonObj['messageValue'];

    $outputList.append('<tr>')
        .append('<td>')
            .append('<span>'+date+'</span>')
            .append('<span>'+messageValue+'</span>');
}

function commit(inputTextId) {
    if (isValidMessage(inputTextId)) {
        var $messageData = createMessage(inputTextId)
        sendMessage(JSON.stringify($messageData));
    }
};

function createMessage(inputTextId) {
    var $inputText = jQuery('#'+inputTextId);

    var $messageData = {
        'messageValue' : $inputText.val(),
        'date' : new Date()
    };

    clearMessage(inputTextId);

    return $messageData;

};

function isValidMessage(inputTextId) {
    var $inputText = jQuery('#'+inputTextId);
    if($inputText.val().trim().length > 0) {
        return true;
    }else{
        return false
    };
};

function clearMessage(inputTextId) {
    var $inputText = jQuery('#'+inputTextId);
    $inputText.val('');
};


