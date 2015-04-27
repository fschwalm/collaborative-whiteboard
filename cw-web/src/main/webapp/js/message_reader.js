var USER_MESSAGE = 'USER_MESSAGE';
var STATUS_MESSAGE = 'STATUS_MESSAGE';
var SERVER_MESSAGE = 'SERVER_MESSAGE';

function messageWriter(jsonData){

    var typeMessage = jsonData['TYPE_MESSAGE'];

    switch (typeMessage){

        case USER_MESSAGE:
            printUserMessage(jsonData);
            break;
        case STATUS_MESSAGE:
            printStatusMessage(jsonData);
            break;
        case SERVER_MESSAGE:
            printServerMessage(jsonData);
            break;
    }
}

function printUserMessage(jsonObject){
    var $outputList = $('#messagePanel');

    var date = jsonObject['DATE'];
    var messageValue = jsonObject['MESSAGE'];
    var user = jsonObject['USERNAME'];

    $outputList.append(
            '<div class="myMessages"><br><span class="hour"> ' + date + ' </span><br>' +
            '<span class="user">'+ user +'</span><br>' +
            '<span class="message">' + messageValue + '</span><br><br></div>');
}

function printStatusMessage(jsonObject){
    var status = jsonObject['STATUS'];

    $('#chatHeader').append("<span id='chatStatus'>"+status+"</span>");
}

function printServerMessage(jsonObject){
    var $outputList = $('#messagePanel');

    var date = jsonObject['date'];
    var messageValue = jsonObject['MESSAGE'];
    var user = jsonObject['USERNAME'];
    var formattedDate = moment(date).locale("pt-br").format('HH:mm - DD/MM/YYYY');

    $outputList.append(
            '<div class="myMessages"><br><span class="hour"> ' + formattedDate + ' </span><br>' +
            '<span class="user">Eu</span><br>' +
            '<span class="message">' + messageValue + '</span><br><br></div>');
}