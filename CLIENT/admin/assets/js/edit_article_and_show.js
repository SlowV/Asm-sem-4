
// =============================== FUNCTION ===========================

function setValuePreviewArticle(title, description, content, author, img, categoryId, categoryName, url) {
    $('#modalTitle').html(title);
    $('#titlePre').val(title);
    $('#descriptionPre').val(description);
    $('#contentPre').html(content);
    $('#thumbnailPre').attr({
        'src': img,
        'alt': title
    });

    console.log("title:" + title + " content: " + content + " description:  " + description + " img: " + img);

    $('#authorPre').val(author).attr('title', author);
    $('.categoryIdPew').html('<div id="' + categoryId + '"> Category: ' + categoryName + '</div>');
    $('input[type="hidden"]#urlPre').val(url);
}

// =============================== END FUNCTION ===========================

// ============================== EVENT CLICK ==============================

divRootModal.on('click', '#btn-edit-article-preview', function () {
    $('.slv_input_preview').attr('disabled', false);
    $('.slv_label_preview').addClass('active');
    CKEDITOR.replace('contentPre');
});

divRootModal.on('click', '#btn-save-article', function () {
    // alert($('.categoryIdPew').find('div').attr('id'));
    let dataPOST = {
        url: $('input[type="hidden"]#urlPre').val(),
        title: $('#titlePre').val(),
        description: $('#descriptionPre').val(),
        content: $('#contentPre').html(),
        author: $('#authorPre').val(),
        thumbnail: $('#thumbnailPre').attr('src'),
        categoryId: $('.categoryIdPew').find('div').attr('id')
    };
    $.ajax({
        url: URL_POST_SPECIAL_ARTICLE,
        method: 'POST',
        type: 'application/json',
        data: JSON.stringify(dataPOST),
        success: function (data) {
            console.log(data);
        },
        complete: function(xhr){
          if (xhr.status === 201){
              $('#modalPreviewArticle').modal('hide');
                alert("Save success");
          }
        },
        error: function (msg) {
            console.log(msg);
        }
    })
});

divRootModal.on('click', '#btn-preview', function () {
    $('.slv_input_preview').attr('disabled', true);
    $('.slv_label_preview').removeClass('active');
    CKEDITOR.instances["contentPre"].destroy(true);
});

// ============================== END EVENT CLICK ==============================
