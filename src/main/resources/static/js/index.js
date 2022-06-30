$(function () {
    listArticle(1, 1);
    articleCount(1);
    categoryCount();
    tagCount();
    listTag()
});

function articleCount(status) {
    $.ajax({
        url: "/articles/count/" + status,
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                $(".article-count").text(result.data)
            }
        }
    })
}

function categoryCount() {
    $.ajax({
        url: "/categories/count",
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                $(".category-count").text(result.data)
            }
        }
    })
}

function tagCount() {
    $.ajax({
        url: "/tags/count",
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                $(".tag-count").text(result.data)
            }
        }
    })
}

function randomTagColor() {
    const color = ['bg-info', 'bg-warning', 'bg-primary', 'bg-success', 'bg-danger']
    const t = Math.round(4 * Math.random());
    return color[t]
}

function listArticle(pageNum, status) {
    $.ajax({
        url: "/articles",
        type: "get",
        data: {
            page: pageNum,
            pageSize: 5,
            status: status
        },
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                $(".articleBody").html(null);
                $.each(result.data.list, function (index, article) {
                    let e = '<div class="d-flex flex-column mb-3 jello-horizontal">' +
                        '       <div class="row py-3 align-items-center bg-white shadow rounded">' +
                        '           <div class="col-lg-4 mb-2 animate__animated animate__flipInX">' +
                        '               <a href="/p/' + article.id + '"><img src="' + article.coverImg + '" class="img-fluid rounded" alt="cover-image"> </a>' +
                        '           </div>' +
                        '       <div class="col-lg-8">' +
                        '           <div class="row">' +
                        '               <div class="text-center mb-2">' +
                        '                   <a href="/p/' + article.id + '" class="h4 fw-bold text-decoration-none my-hover-bg text-dark my-letter-spacing">' + article.title + '</a>' +
                        '               </div>' +
                        '               <p class="my-text-light text-black-50 my-letter-spacing my-indent">' + article.summary + '</p>' +
                        '               <div class="d-flex justify-content-start">' +
                        '                   <label class="btn text-dark disabled"><i class="fas fa-clock"></i>' + article.createTime + '</label>' +
                        '               </div> ' +
                        '               <div class="d-flex justify-content-between mt-2">' +
                        '                   <div class="articleTags"></div>' +
                        '                   <div class="justify-content-lg-center">' +
                        '                       <button class="btn text-dark disabled"> <i class="fas fa-bookmark"></i> ' + article.category.name + " </button>" +
                        "                   </div>" +
                        "               </div>" +
                        "           </div>" +
                        "       </div>" +
                        "   </div>";
                    $(".articleBody").append(e);
                    $(".articleTags").eq(index).html(null);
                    if (article.tags != null && article.tags.length > 0) {
                        $.each(article.tags, function (i, tag) {
                            const it = '<label class="mx-1 btn badge rounded-pill ' + randomTagColor() + ' disabled">' + tag.name + "</label>";
                            $(".articleTags").eq(index).append(it)
                        })
                    } else {
                        const it = '<label class="mx-1 btn badge rounded-pill bg-black disabled">暂无标签</label>';
                        $(".articleTags").eq(index).html(it)
                    }
                    $("#currentPage").html(result.data.pageNum);
                    $("#totalPage").html(result.data.pages);
                    pnDisabled()
                })
            }
        }
    })
}

function listTag() {
    $.ajax({
        url: "/tags",
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                $("#tagBody").html(null);
                $.each(result.data, function (index, tag) {
                    const it = '<a href="/article-tag/' + tag.id + '" target="_blank" class="d-inline-block fs-5 fw-bold m-1 py-1 px-2 rounded text-decoration-none text-dark my-hover-bg">' + tag.name + '</a>';
                    $("#tagBody").append(it)
                })
            }
        }
    })
}

function pnDisabled() {
    const current = $("#currentPage").text()
    if (current > 1) {
        $(".prePage").removeClass("disabled")
    } else {
        $(".prePage").addClass("disabled")
    }
    const total = $("#totalPage").text()
    if (total > current) {
        $(".nextPage").removeClass("disabled")
    } else {
        $(".nextPage").addClass("disabled")
    }
}

$(".prePage").click(function () {
    const current = $("#currentPage").text();
    if (current > 1) {
        listArticle(Number(current) - 1, 1)
    }
});
$(".nextPage").click(function () {
    const current = $("#currentPage").text();
    const total = $("#totalPage").text();
    if (current < total) {
        listArticle(Number(current) + 1, 1)
    }
});
