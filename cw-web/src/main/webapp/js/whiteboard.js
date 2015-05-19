//var url = 'ccem.ufrgs.br';
var url = 'localhost';
var port = '8080';
var whiteboardService = 'whiteboard';

$(document).ready(function () {
    connect(url, port, whiteboardService);
});

