function topSearch() {
    $.ajax({
        url: "/articles/ss",
        type: "get",
        data: {
            text: $(".top-search-input").val()
        },
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                $(".top-search-body").html(null);
                $.each(result.data, function (index, article) {
                    const it = '<div class="d-flex justify-content-between p-2 my-2 bg-white shadow rounded">' +
                        '           <a href="/p/' + article.id + '" class="fs-6 fw-bold my-hover-bg text-decoration-none text-dark my-letter-spacing">' + article.title + '</a>' +
                        '           <div class="fs-5">' + article.createTime + "</div>" +
                        "       </div>";
                    $(".top-search-body").append(it)
                })
            }else{
                $(".top-search-body").html('暂无内容');
            }
        }
    });
    $(".top-search-result").click()
}

$(".top-search-btn").click(function () {
    topSearch()
});
$(".top-search-input").keydown(function (event) {
    if (event.keyCode == 13) {
        topSearch()
    }
});
