var $table = $('#table');
$(function () {
    var options = {
        url: contextPath + 'user/page',  // 请求后台URL(*)
        pageNumber: 1, // 当前页码，默认第1页
        pageSize: 3, // 每页显示记录数
        columns: [
            {
                title: '序号', width: 20, formatter: function (value, row, index) {
                    return index + 1
                }
            },
            {field: 'id', visible: false},
            {field: 'username', title: '用户名'},
            {field: 'nickName', title: '昵称'},
            {field: 'mobile', title: '手机号'},
            // 注意下面不要属性不要 `is`
            {field: 'accountNonExpired', title: '帐号过期', formatter: statusFormatter},
            {field: 'accountNonLocked', title: '是否锁定', formatter: statusFormatter},
            {field: 'credentialsNonExpired', title: '密码过期', formatter: statusFormatter},
            {field: 'action', visible: false, title: '操作', width: 50, align: 'center', formatter: $.operationFormatter}
        ]
    };
    //初始化数据渲染, 是调用common.js中的 pageTable 方法
    $.pageTable(options);
});

// 用户状态
function statusFormatter(value, row, index) {
    return value === 1 ? "<span class='badge bg-success'>否</span>"
        : "<span class='badge bg-danger'>是</span>";
}

// 搜索
function searchForm() {
    var query = {
        size: 3,
        current: 1,
        username: $("#username").val().trim(),
        mobile: $("#mobile").val().trim()
    };
    $table.bootstrapTable('refresh', {query: query});
}