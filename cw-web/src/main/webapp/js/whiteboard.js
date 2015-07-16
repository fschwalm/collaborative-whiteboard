var whiteboardService = 'whiteboard';
var websocketWhiteboard = {};

window.onload = function() {
    websocketWhiteboard = connect(whiteboardService);
    initWhiteboardRegisters(websocketWhiteboard);
    center_element($('#center_arrow'));

    $('#whiteboard').draggable({
        axis: "x",
        distance: 50
    });
};

function initWhiteboardRegisters(websocketWhiteboard){
    websocketWhiteboard.onclose = function (evt) {
        var whiteboard = $('#whiteboard');
        whiteboard.empty();

        var textForMessage = 'Sem conexão com o Quadro.';
        write_whiteboard_message(whiteboard, textForMessage);
    };
    websocketWhiteboard.onerror = function (evt) {
        var whiteboard = $('#whiteboard');
        whiteboard.empty();

        var textForMessage = 'OMG!! Erro ao conectar com o Servidor.';
        write_whiteboard_message(whiteboard, textForMessage);
    };
    websocketWhiteboard.onmessage = function (evt) {
        whiteboardWriter(evt);
    };
}

function whiteboardWriter(evt){
    var whiteboard = $('#whiteboard');
    var whiteboardJson = JSON.parse(evt.data);
    var stages = whiteboardJson['stages'];
    var update_date_area = $('#whiteboard_update');

    whiteboard.empty();
    write_last_upadate(update_date_area, whiteboardJson.updateDate);
    write_all_stages(whiteboard, stages);
    write_tasks(whiteboardJson);
    center_whiteboard();
}

function center_whiteboard(){
    center_element($('#whiteboard'));
}

function center_element(element){
    var container = $(window);
    element.css("left", (container.width()-element.width())/2);
}


