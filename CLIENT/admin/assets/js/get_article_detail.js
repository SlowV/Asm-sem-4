function get_article_detail(id) {
    $.ajax({
        url: URL_GET_ARTICLE_DETAIL + "?id=" + id,
        method: "GET",
        type: "application/json",
        success: function (data) {
            if (data.status === 200) {
                let title = data.obj.title;
                let description = data.obj.description;
                let content = data.obj.content;
                let author = data.obj.author;
                let img = data.obj.thumbnail;
                let cateId = data.obj.categoryId;
                let urlPre = data.obj.url;
                let categoryName = data.obj.categoryName;
                setValuePreviewArticle(title, description, content, author, img, cateId, categoryName, urlPre);
            }
        },
        complete: function (xhr, textStatus) {
            if (xhr.status === 200) {
                console.log("completed: " + xhr.status);
                $('#modalPreviewArticle').modal({
                    backdrop: false
                });
            }

        },
        errors: function (msg) {
            console.log("Errors: " + msg);
        }
    });
}