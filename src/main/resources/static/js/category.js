function listCategory() {
    $.ajax({
        url: "/categories",
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.code == 1) {
                $("#categoryBody").html(null)
                $.each(result.data, function (index, category) {
                    const it = '<a href="/article-category/' + category.id + '" type="button" target="_blank" class="btn btn-outline-primary m-2">' + category.name + "</a>";
                    $("#categoryBody").append(it)
                })
            }
        }
    })
}

function categoryStatistics() {
    const a = document.getElementById("main"), b = echarts.init(a);
    $.ajax({
        url: "/categories/statistics",
        type: "get",
        dataType: "json",
        success: function (result) {
            let option;
            if (result.code == 1) {
                option = {
                    tooltip: {trigger: "item"},
                    legend: {bottom: "5%", left: "center"},
                    series: [{
                        name: "文章分类",
                        type: "pie",
                        radius: ["40%", "70%"],
                        avoidLabelOverlap: false,
                        itemStyle: {borderRadius: 10, borderColor: "#fff", borderWidth: 2},
                        label: {show: !1, position: "center"},
                        emphasis: {label: {show: !0, fontSize: "40", fontWeight: "bold"}},
                        labelLine: {show: false},
                        data: []
                    }]
                }
                option.series[0].data = result.data
                b.setOption(option)
            }
        }
    })
}

$(function () {
    listCategory()
    categoryStatistics()
});