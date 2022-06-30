function listArticle(status) {
    $.ajax({
        url: "/articles",
        type: "get",
        data: {
            status: status
        },
        dataType: "json",
        success: function (result) {
            oTimeAxiosFun = null;
            const b = {
                data: [],
                id: "cxTime",
                index: 0,
                sort: "back",
                sortKey: "createTime",
                activeColor: "#26a69a",
                props: ["title", "createTime"],
                then: function (result) {
                    window.location.href = "/p/" + result.id
                }
            };
            b.data = result.data.list
            oTimeAxiosFun = new oTimeAxios(b)
        }
    })
}

$(function () {
    listArticle(1);
})