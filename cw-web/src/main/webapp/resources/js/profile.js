$(document).ready(function () {
    $('#change_picture').click(function() {
        var input = $('#input_file');
        //input.attr("type", "file");
        input.trigger('click');
        return false;
    });
});
