function write_last_upadate(target, date) {
    target.empty();
    var span_date = $(document.createElement('span'));
    span_date.text(date);
    target.append(span_date);
}

function write_all_stages(target, stages) {
    stages.sort(sortByProperty('position'));

    $.each(stages, function (key, value) {
        write_stage(target, value);
    });
}

function write_stage(target, stage) {
    var stage_name = stage.name;

    var div_stage = $(document.createElement('div'));
    div_stage.attr('id', stage_name);
    div_stage.attr('class', 'stage');

    write_stage_header(stage_name, div_stage);
    target.append(div_stage);
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

    task.append(write_task_code(taskJson.code));
    task.append(write_task_estimated(taskJson.estimatedTime));
    task.append(write_task_subject(taskJson.subject));
    task.append(write_image_user());

    $('#' + stage_target.name).append(task);
}

function write_image_user() {
    var image_div = $(document.createElement('div'));
    image_div.attr('class', 'image_user');

    return image_div;
}

function write_task_code(code) {
    var task_div = $(document.createElement('div'));
    var task_code = $(document.createElement('span'));

    task_div.attr('class', 'task_code');
    task_code.text(code);

    task_div.append(task_code);

    return task_div;
}

function write_task_estimated(estimated) {
    var task_estimated_time_div = $(document.createElement('div'));
    var estimated_time = $(document.createElement('span'));

    task_estimated_time_div.attr('class', 'task_estimated');

    var formated_estimated_time = moment(estimated).format('hh:mm');
    estimated_time.text('Estimativa : ' + formated_estimated_time);

    task_estimated_time_div.append(estimated_time);

    return task_estimated_time_div;
}

function write_task_subject(subject_value) {
    var task_subject_div = $(document.createElement('div'));
    var subject = $(document.createElement('p'));

    task_subject_div.attr('class', 'task_subject');

    subject.text(subject_value);

    task_subject_div.append(subject);

    return task_subject_div;
}

function write_whiteboard_message(whiteboard, messageText) {
    var messageDiv = $(document.createElement('div'));
    var message = document.createElement('span');

    message.textContent = messageText;

    messageDiv.attr('class', 'message');

    $(messageDiv).append(message);
    $(whiteboard).append(messageDiv);
    center_whiteboard();
    remove_motion(whiteboard);
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
