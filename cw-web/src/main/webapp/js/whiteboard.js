var whiteboardService = 'whiteboard';
var websocketWhiteboard = {};

window.onload = function () {
    websocketWhiteboard = connect(whiteboardService);
    initWhiteboardRegisters(websocketWhiteboard);
};

function initWhiteboardRegisters(websocketWhiteboard) {
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

function whiteboardWriter(evt) {
    var whiteboard = $('#whiteboard');
    var whiteboardJson = JSON.parse(evt.data);
    var stages = whiteboardJson['stages'];
    var update_date_area = $('#whiteboard_update');

    whiteboard.empty();

    if (init_whiteboard(stages)) {
        var stages_area = $(document.createElement('div'));
        stages_area.attr('class','stages');
        whiteboard.append(stages_area);

        write_last_upadate(update_date_area, whiteboardJson.updateDate);
        write_all_stages(stages_area, stages);
        write_tasks(whiteboardJson);
        add_motion_whiteboard(stages_area);
    } else {
        write_whiteboard_message(whiteboard, 'Quadro não inicializado!');
    }
}

function init_whiteboard(stages) {
    if (stages.length == 0) {
        return false;
    } else {
        return true;
    }
}

function add_motion_whiteboard(elemente) {
    elemente.draggable({
        axis: "x",
        delay: 300,
        scroll: true
    });
}

function remove_motion(element){
    element.draggable( "disable" );
}

function center_whiteboard() {
    center_element($('#whiteboard'));

    $('#whiteboard').dblclick(function(){
        center_element($('#whiteboard'));
    });
}

function center_element(element) {
    var container = $(window);
    element.css("left", (container.width() - element.width()) / 2);
}

function action_task(action, element_event){
    var task_code = $(element_event).parent().siblings('.task_code').children().text();

    if(action == 'PLAY'){
        task_play([{name:'task_code', value:task_code}]);

    }else if(action == 'STOP'){
        task_stop([{name:'task_code', value:task_code}])

    }else if(action == 'NEXT'){
        task_next([{name:'task_code', value:task_code}]);

    }else if(action == 'PREV'){
        task_prev([{name:'task_code', value:task_code}])
    }
}


