var whiteboardService = 'whiteboard';
var websocketWhiteboard = {};

window.onload = function() {
    websocketWhiteboard = connect(whiteboardService);
    initWhiteboardRegisters(websocketWhiteboard);
};

function initWhiteboardRegisters(websocketWhiteboard){
    websocketWhiteboard.onclose = function (evt) {
        var whiteboard = $('#whiteboard');
        whiteboard.empty();

        var textForMessage = 'Sem conexão com o Quadro.';
        createMessageEmptyStages(whiteboard, textForMessage);
    };
    websocketWhiteboard.onerror = function (evt) {
        var whiteboard = $('#whiteboard');
        whiteboard.empty();

        var textForMessage = 'OMG!! Erro ao conectar com o Servidor.';
        createMessageEmptyStages(whiteboard, textForMessage);
    };
    websocketWhiteboard.onmessage = function (evt) {
        whiteboardWriter(evt);
    };
}

function whiteboardWriter(evt){
    var target = $('#whiteboard');
    target.empty();

    var whiteboardJson = JSON.parse(evt.data);

    console.log(whiteboardJson);
    var stages = whiteboardJson['stages'];

    write_last_upadate(target, whiteboardJson.updateDate);
    write_all_stages(target, stages);
    write_tasks(whiteboardJson);
}


