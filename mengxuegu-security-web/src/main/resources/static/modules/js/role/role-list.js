var $table = $('#table');
$(function() {
    var options = {
        url: contextPath + 'role/page',  // 请求后台URL(*)
        pageNumber: 1, // 当前页码，默认第1页
        pageSize: 3, // 每页显示记录数
        columns: [
            {title: '序号', width: 20, formatter: function (value,row,index) { return index + 1 }},
            {field: 'id', visible: false},
            {field: 'name', title: '角色名称'},
            {field: 'remark', title: '角色描述'},
            {field: 'action', visible: false, title: '操作', width: 50, align: 'center', formatter: $.operationFormatter}
        ],
    };
    //初始化数据渲染, 是调用common.js中的 pageTable 方法
    $.pageTable(options);
});

function searchForm() {
    var query = {
        size: 3, // 每页显示条数
        current: 1, // 当前页码
        name: $("#name").val().trim()
    };
    $table.bootstrapTable('refresh', {query: query})
}