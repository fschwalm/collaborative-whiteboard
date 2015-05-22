var whiteboardService = 'whiteboard';
var websocketWhiteboard = {};

window.onload = function() {
    websocketWhiteboard = connect(whiteboardService);
    initWhiteboardRegisters(websocketWhiteboard);
    whiteboardRemoteConnection();
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
    var messageData = JSON.parse(evt.data);
    var jsonStages = messageData['jSonStages'];
    stageWriter(jsonStages);
}

function stageWriter(jsonStages){
    var whiteboard = $('#whiteboard');
    whiteboard.empty();

    if(jsonStages.length != 0){
        createStages(whiteboard, jsonStages);
    }else{
        var textForMessage = 'Quadro não inicializado. Crie novas etapas para inicializa-lo!';
        createMessageEmptyStages(whiteboard, textForMessage);
    }
}

function createMessageEmptyStages(whiteboard, messageText){
    var messageDiv = document.createElement('div');
    var message = document.createElement('span');
    message.textContent = messageText;

    messageDiv.setAttribute('class','emptyMessage');

    $(messageDiv).append(message);
    $(whiteboard).append(messageDiv);
}

function createStages(whiteboard, jsonStages){
    jsonStages.sort(sortByProperty('position'));

    whiteboard.empty();

    var whiteboardSize = whiteboard.width();
    var numberOfAreas = jsonStages.length;
    var margin = 14;
    var sumMargin = numberOfAreas * margin;
    var sizeForStage = ((whiteboardSize - sumMargin) / numberOfAreas);

    $.each(jsonStages, function (key, value){
        var stage = document.createElement('div');
        stage.setAttribute('class','stage');
        stage.setAttribute('id', value.name);
        $(stage).css("min-width", sizeForStage);

        createStageHeader(value.name, stage);
        $(whiteboard).append(stage)
    });
}

function createStageHeader(label, stage){
    var header = document.createElement('div');
    var headerLabel = document.createElement('span');

    headerLabel.textContent = label;
    header.setAttribute('class','stage_header');

    $(header).append(headerLabel);
    $(stage).append(header);
}

function sortByProperty(property) {
    'use strict';
    return function (a, b) {
        var sortStatus = 0;
        if (a[property] < b[property]) {
            sortStatus = -1;
        } else if (a[property] > b[property]) {
            sortStatus = 1;
        }

        return sortStatus;
    };
}


