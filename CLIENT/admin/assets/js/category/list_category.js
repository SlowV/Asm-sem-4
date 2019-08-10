$(document).ready(window.init());

function init() {
    getListCategory('#tbody_data_category', ACTION_CONTROL);
}

function getListCategory(el, action) {
    $.ajax({
        url: URL_GET_LIST_CATEGORY,
        method: 'GET',
        type: 'application/json',
        success: function (data) {
            if (data.status === 200){
                let list = [];
                for (let i = 0; i < data.obj.length; i++) {
                    let category = {
                        id: data.obj[i].id,
                        name: data.obj[i].name,
                        description:  data.obj[i].description,
                        status: data.obj[i].status
                    };
                    list.push(category);
                    // console.log(category);
                }
                generateView(list, el, action);
            }
        },
        error: function (msg) {
            console.log(msg);
        }
    });
}

function generateView(list, selectorView, action) {
    // alert(list.length);
    let content = '';
    for (let i = 0; i < list.length; i++) {
        content += '<tr>' +
            '<td class="text-center text-muted">' +
            '    <input type="checkbox" style="width: 15px; height: 15px;">' +
            '</td>' +
            '<td class="text-center">' + list[i].name + '</td>' +
            '<td class="text-center">' +
            '    <div class="badge ' + generateClassBadgeFromIntStatus(list[i].status) + '">' + convertStatusIntToString(list[i].status) + '</div>' +
            '</td>';
        if (action === ACTION_ACTIVE) {
            content += '<td class="text-center">' +
                '    <button type="button" ' + disabledButtonByStatus(list[i].status) + '  id="' + list[i].id + '"  class="btn btn-primary btn-sm btn_duyet_nhanh">' +
                '        Duyệt' +
                '    </button>' +
                '</td>' +
                '</tr>';
        } else if (action === ACTION_CONTROL) {
            content += '<td class="text-center">' +
                '    <button type="button" id="' + list[i].id + '" title="Xóa"  class="btn btn-danger btn-sm btn_delete_category">' +
                '<i class="fa fa-trash"></i>' +
                '</button>' +
                ' <button type="button"  title="Sửa"  class="btn btn-warning btn-sm btn_edit">' +
                '<i class="fa fa-pencil-alt"></i>' +
                '</button>'+
                '</td>' +
                '</tr>';
        }
    }

    $(selectorView).html(content);
}