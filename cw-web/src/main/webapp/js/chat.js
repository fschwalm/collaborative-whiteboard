$( document ).ready(function() {
    connect();
});

function commit(inputTextId) {
    var inputText = $('#'+inputTextId);

    if (inputText.val().length > 0) {
        sendMessage(inputText.val());
        inputText.val('');
    }
}

