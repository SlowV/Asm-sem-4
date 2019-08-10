function get_list_article(status, limit, action) {
    // GET articles
    $.ajax({
        url: URL_GET_LIST_ARTICLE + "?limit=" + limit + '&status=' + status,
        method: "GET",
        type: "application/json",
        success: function (data) {
            if (data.status === 200) {
                var content = "";
                for (let i = 0; i < data.obj.length; i++) {
                    content += '<tr>' +
                        '<td class="text-center text-muted">' +
                        '    <input type="checkbox" style="width: 15px; height: 15px;">' +
                        '</td>' +
                        '<td>' +
                        '    <div class="widget-content p-0">' +
                        '        <div class="widget-content-wrapper">' +
                        '            <div class="widget-content-left mr-3">' +
                        '                <div class="widget-content-left">' +
                        '                    <img class="rounded-circle"' +
                        '                         src="' + data.obj[i].image + '" alt="" style="width: 40px; height: 40px; object-fit: cover">' +
                        '                </div>' +
                        '            </div>' +
                        '            <a href="javascript:void(0)" onclick="get_article_detail(\'' + data.obj[i].url + '\')" style="color: #4c5e8c;">' +
                        '                <div class="widget-content-left flex2">' +
                        '                    <div class="widget-heading text-dark">' + data.obj[i].title +
                        '                    </div>' +
                        '                    <div class="widget-subheading opacity-7">' + data.obj[i].description +
                        '                    </div>' +
                        '                </div>' +
                        '            </a>' +
                        '        </div>' +
                        '    </div>' +
                        '</td>' +
                        '<td class="text-center">' + data.obj[i].categoryName + '</td>' +
                        '<td class="text-center">' +
                        '    <div class="badge ' + generateClassBadgeFromIntStatus(data.obj[i].status) + '">' + convertStatusIntToString(data.obj[i].status) + '</div>' +
                        '</td>';
                    if (action === ACTION_ACTIVE) {
                        content += '<td class="text-center">' +
                            '    <button type="button" ' + disabledButtonByStatus(data.obj[i].status) + '  id="' + data.obj[i].url + '"  class="btn btn-primary btn-sm btn_duyet_nhanh">' +
                            '        Duyệt' +
                            '    </button>' +
                            '</td>' +
                            '</tr>';
                    } else if (action === ACTION_CONTROL) {
                        content += '<td class="text-center">' +
                            '    <button type="button" id="' + data.obj[i].url + '" title="Xóa"  class="btn btn-danger btn-sm btn_delete">' +
                            '<i class="fa fa-trash"></i>' +
                            '</button>' +
                            ' <button type="button" onclick="get_article_detail(\'' + data.obj[i].url + '\')" title="Sửa"  class="btn btn-warning btn-sm btn_edit">' +
                            '<i class="fa fa-pencil-alt"></i>' +
                            '</button>'+
                            '</td>' +
                            '</tr>';
                    }
                }
                $('#tbody_data_articles').html(content);
                // console.log(content);
            }
        },
        complete: function (xhr, textStatus) {
            if (xhr.status === 200) {
                console.log("completed: " + xhr.status);
            }

        },
        errors: function (msg) {
            console.log("Errors: " + msg);
        }
    });
    // End GET articles

}
