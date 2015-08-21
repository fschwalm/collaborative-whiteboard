$(document).ready(function () {
    $('#chat_button').click(function () {
        slideSideMenu($('#chat'));
    });

    $('#projects_button').click(function () {
        slideSideMenu($('#projects'));
    });
});

function slideSideMenu(target) {
    target.toggleClass('side_menu_open', 100, "easeOutSine");
}

