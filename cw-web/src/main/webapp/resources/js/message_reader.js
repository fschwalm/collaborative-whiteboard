var USER_MESSAGE = 'USER_MESSAGE';
var STATUS_MESSAGE = 'STATUS_MESSAGE';
var SERVER_MESSAGE = 'SERVER_MESSAGE';

function messageWriter(jsonData) {
    var outputList = $('#chat_panel_content');
    var typeMessage = jsonData['TYPE_MESSAGE'];

    switch (typeMessage) {

        case USER_MESSAGE:
            printUserMessage(jsonData, outputList);
            break;
        case STATUS_MESSAGE:
            printStatusMessage(jsonData, outputList);
            break;
        case SERVER_MESSAGE:
            printServerMessage(jsonData);
            break;
    }

    scroll_chat();
}

function printUserMessage(jsonObject, outputList) {
    var date = jsonObject['DATE'];
    var message_value = jsonObject['MESSAGE'];
    var user_value = jsonObject['USERNAME'];
    var formatted_date = moment(date).locale("pt-br").format('HH:mm - DD/MM/YYYY');

    outputList.prepend(mount_message(user_value, formatted_date, message_value));
}

function printStatusMessage(jsonObject, outputList) {
    var jsonLastMessages = jsonObject['LAST_MESSAGES'];

    $.each(jsonLastMessages, function (index, value) {
        printUserMessage(value, outputList);
    });
}

function printServerMessage(jsonObject, outputList) {
    var date = jsonObject['date'];
    var message_value = jsonObject['MESSAGE'];
    var user_value = jsonObject['USERNAME'];
    var formatted_date = moment(date).locale("pt-br").format('HH:mm - DD/MM/YYYY');

    outputList.prepend(mount_message(user_value, formatted_date, message_value));
}

function mount_message(user_value, date_value, message_value){
    var message = $(document.createElement('div'));
    message.addClass('chat_message');

    var header = $(document.createElement('div'));
    header.addClass('header_message');

    var user = $(document.createElement('div'));
    user.addClass('chat_user');
    user.addClass('standart_font');
    user.text(user_value);
    header.append(user);

    var hour = $(document.createElement('div'));
    hour.addClass('date');
    hour.addClass('standart_font');
    hour.text(date_value);
    header.append(hour);

    var text_area = $(document.createElement('div'));
    text_area.text(message_value);
    text_area.addClass('text');
    text_area.addClass('highlight_font');

    message.append(header);
    message.append(text_area);

    return message;
}
