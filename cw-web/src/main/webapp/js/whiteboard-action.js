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

    }else if(action == 'FINALIZE'){
        task_finalize([{name:'task_code', value:task_code}])
    }
}