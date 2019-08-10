// BASE URL
// const BASE_URL = "https://crawler-dot-crawldata-248604.appspot.com/";
const BASE_URL = "http://localhost:8080/";

// Article api
const URL_GET_LIST_ARTICLE = BASE_URL + "api/v1/articles";
const URL_GET_ARTICLE_DETAIL = BASE_URL + "api/v1/article/detail";
const URL_GET_ACTIVE_ARTICLE = BASE_URL + "article/active";
const URL_GET_CATEGORIES = BASE_URL + "api/v1/categories";
const URL_POST_ARTICLE = BASE_URL + "api/v1/article/create";
const URL_POST_SPECIAL_ARTICLE = BASE_URL + "api/v1/article/special/save";
const URL_DELETE_DELETE_ARTICLE = BASE_URL + "api/v1/article/delete";

//Category api
const URL_GET_LIST_CATEGORY = BASE_URL + "api/v1/categories";

// Language api
const URL_GET_LANGUAGE = BASE_URL + "api/v1/language";

//============================== * ================================

// VAR
const ACTION_ACTIVE = "ACTIVE";
const ACTION_CONTROL = "CONTROL";
let divRootModal = $('div[name-layout="modal"]');
let ARR_LANG = {};

//============================== * ================================

// FUNCTION
function fadeOutElement(el) {
    el.fadeOut(1 * 1000, function () {
        parent.remove();
    });
}

function convertStatusIntToString(status) {
    if (status === 0) {
        return "Đang chờ";
    } else if (status === 1) {
        return "Hoạt động"
    } else {
        return "Đã xóa";
    }
}

function generateClassBadgeFromIntStatus(status) {
    if (status === 0) {
        return "badge-warning";
    } else if (status === 1) {
        return "badge-success"
    } else {
        return "badge-danger";
    }
}

function disabledButtonByStatus(status) {
    if (status === 0) {
        return "";
    } else if (status === 1) {
        return "disabled"
    } else {
        return "disabled";
    }
}