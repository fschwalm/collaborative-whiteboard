var projectsIsOpen;

$(document).ready(function () {
    registerClickProjectsButton();
});

function registerClickProjectsButton() {
    $("#projectsBtn").click(function () {
        if (!projectsIsOpen) {
            openProjects();
        }else{
            closeProjects();
        }
    });
}

function openProjects() {
    $('#exitBtn').animate({'margin-right': "10%"});
    $("#myProjects").slideDown('fast');
    $("#createProjectShortcut").focus();
    projectsIsOpen = true;
}

function closeProjects() {
    $('#exitBtn').animate({'margin-right': "0%"});
    $("#myProjects").slideUp('fast');
    projectsIsOpen = false;
}
