var whiteboardService = 'whiteboard';
var websocketWhiteboard = {};

window.onload = function() {
    websocketWhiteboard = connect(whiteboardService);
    initWhiteboardRegisters(websocketWhiteboard);
    whiteboardRemoteConnection();
};

function initWhiteboardRegisters(websocketWhiteboard){
    websocketWhiteboard.onclose = function (evt) {
        alert("CLOSE")
    };
    websocketWhiteboard.onerror = function (evt) {
        alert("ERRO")
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
        createMessageEmptyStages(whiteboard, 'TA VAZIO O BAGULHO');
    }
}

function createMessageEmptyStages(whiteboard, messageText){
    var messageDiv = document.createElement('div');
    var message = document.createElement('span');
    message.textContent = messageText;

    $(messageDiv).append(message);
    $(whiteboard).append(messageDiv);
}



function createStages(whiteboard, jsonStages){
    jsonStages.sort(sortByProperty('position'));

    whiteboard.empty();

    var whiteboardSize = whiteboard.width();
    var numberOfAreas = jsonStages.length;
    var margin = 20;
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


