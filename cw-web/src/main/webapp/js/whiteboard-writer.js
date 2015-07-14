function write_last_upadate(target, date) {
    var div_date = $(document.createElement('div'));
    var span_date = $(document.createElement('span'));

    span_date.text(date);

    div_date.append(span_date);
    target.append(div_date);
}

function write_all_stages(target, stages) {
    stages.sort(sortByProperty('position'));

    if (stages.length != 0) {
        $.each(stages, function (key, value) {
            write_stage(target, value);
        });
    } else {
        write_whiteboard_message(target, 'Quadro não inicializado. Crie novas etapas para inicializa-lo!');
    }
}

function write_stage(target, stage) {
    var stage_name = stage.name;

    var collumn = $(document.createElement('column'));
    var div_stage = $(document.createElement('div'));
    div_stage.attr('id', stage_name);
    div_stage.attr('class', 'stage');

    write_stage_header(stage_name, div_stage);
    collumn.append(div_stage);
    target.append(collumn);
}

function write_stage_header(label, stage) {
    var header = document.createElement('div');
    var headerLabel = document.createElement('span');

    headerLabel.textContent = label;
    header.setAttribute('class', 'stage_header');

    $(header).append(headerLabel);
    stage.append(header);
}

function write_tasks(whiteboardJson) {
    var stages = whiteboardJson['stages'];

    if (stages.length != 0) {
        $.each(stages, function (key, stage) {
            var stories = stage.stories;

            if (stories.length != 0) {
                $.each(stories, function (key, story) {
                    var tasks = story.tasks;

                    if (tasks.length != 0) {
                        $.each(tasks, function (key, task) {
                            write_task(stage, task)
                        });
                    }
                });
            }
        });
    }
}

function write_task(stage_target, taskJson) {
    var task = $(document.createElement('div'));
    task.attr('id', taskJson.code);
    task.attr('class', 'ticket');

    var taks_code = $(document.createElement('span'));
    taks_code.attr('class', 'task_code');
    taks_code.text(taskJson.code);

    var estimated_time = $(document.createElement('span'));
    estimated_time.attr('class', 'task_estimated');
    estimated_time.text(taskJson.estimatedTime);

    var subject = $(document.createElement('span'));
    subject.attr('class', 'task_subject');
    subject.text(taskJson.subject);

    task.append(taks_code);
    task.append(estimated_time);
    task.append(subject);

    $('#'+stage_target.name).append(task);
}

function write_whiteboard_message(whiteboard, messageText) {
    var messageDiv = $(document.createElement('div'));
    var message = document.createElement('span');

    message.textContent = messageText;

    messageDiv.attr('class', 'emptyMessage');

    $(messageDiv).append(message);
    $(whiteboard).append(messageDiv);
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
