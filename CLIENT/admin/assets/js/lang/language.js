$(document).ready(window.init());
function init() {
    getLanguageJson();
}

function getLanguageJson() {
    $.ajax({
        url: URL_GET_LANGUAGE,
        method: 'GET',
        type: 'application/json',
        success: function (data) {
            ARR_LANG = data;
            changeLanguage(ARR_LANG);
        },
        error: function (msg) {
            console.log(msg);
        }
    })
}
function changeLanguage(json) {
    $('.translate').click(function () {
        var lang = $(this).attr('id');
        $('.lang').each(function (index, element) {
            $(this).text(ARR_LANG[lang][$(this).attr('key')]);
        });
    });
}