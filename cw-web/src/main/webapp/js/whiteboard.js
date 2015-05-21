//var url = 'ccem.ufrgs.br';
var url = 'localhost';
var port = '8080';
var whiteboardService = 'whiteboard';
var websocketWhiteboard = {};

$(document).ready(function () {
    websocketWhiteboard = connect(url, port, whiteboardService);
    initWhiteboardRegisters(websocketWhiteboard);
});

function initWhiteboardRegisters(websocketWhiteboard){
    websocketWhiteboard.onclose = function (evt) {
        alert("CLOSE")
    }
    websocketWhiteboard.onerror = function (evt) {
        alert("ERRO")
    }
    websocketWhiteboard.onmessage = function (evt) {
        whiteboardWriter(evt);
    }
}

function whiteboardWriter(evt){
    var messageData = JSON.parse(evt.data);
    var jsonStages = messageData['jSonStages'];
    stageWriter(jsonStages);
}

function stageWriter(jsonStages){
    var whiteboard = $('#whiteboard');
    whiteboard.empty();

    $.each(jsonStages, function (key, value){
        var stage = document.createElement('div');
        stage.setAttribute('class','stage');
        stage.setAttribute('id', value.name);

        var sizeForStage = whiteboard.width() / jsonStages.length;
        var sizeForStageWithDiscount = sizeForStage - ((sizeForStage * 5.0) / 100);
        $(stage).css("min-width", sizeForStageWithDiscount);

        $('#whiteboard').append(stage)
    });
}

