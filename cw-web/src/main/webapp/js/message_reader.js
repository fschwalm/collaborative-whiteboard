var USER_MESSAGE = 'USER_MESSAGE';
var STATUS_MESSAGE = 'STATUS_MESSAGE';
var SERVER_MESSAGE = 'SERVER_MESSAGE';

function messageWriter(jsonData) {

    var typeMessage = jsonData['TYPE_MESSAGE'];

    switch (typeMessage) {

        case USER_MESSAGE:
            printUserMessage(jsonData);
            scrollChat();
            break;
        case STATUS_MESSAGE:
            printStatusMessage(jsonData);
            scrollChat();
            break;
        case SERVER_MESSAGE:
            printServerMessage(jsonData);
            scrollChat();
            break;
    }
}

function printUserMessage(jsonObject, flag) {
    var $outputList = $('#messagePanel');

    var date = jsonObject['DATE'];
    var messageValue = jsonObject['MESSAGE'];
    var user = jsonObject['USERNAME'];

    $outputList.append(
        '<div class="myMessages"><br><span class="hour"> ' + date + ' </span><br>' +
        '<span class="user">' + user + '</span><br>' +
        '<span class="message">' + messageValue + '</span><br><br></div>');

    if(flag){
        addFlagReceipt();
    }
}

function printStatusMessage(jsonObject) {
    var jsonLastMessages = jsonObject['LAST_MESSAGES'];

    $.each(jsonLastMessages, function (index, value) {
        printUserMessage(value, false);
    });
}

function printServerMessage(jsonObject) {
    var $outputList = $('#messagePanel');

    var date = jsonObject['date'];
    var messageValue = jsonObject['MESSAGE'];
    var user = jsonObject['USERNAME'];
    var formattedDate = moment(date).locale("pt-br").format('HH:mm - DD/MM/YYYY');

    $outputList.append(
        '<div class="myMessages"><br><span class="hour"> ' + formattedDate + ' </span><br>' +
        '<span class="user">Eu</span><br>' +
        '<span class="message">' + messageValue + '</span><br><br></div>');

    addFlagReceipt();
}

function addFlagReceipt() {
    var valueFlag = '⁺';

    if ($('#receivedMessageFlag').length <= 0 && (!$("#chatField").is(':visible'))) {
        $('#chatBtn').append("<span id='receivedMessageFlag'>" + valueFlag + "</span>");
    }
}

function scrollChat() {
    $('#outputMessage').scrollTo($("#outputMessage").get(0).scrollHeight);
}
