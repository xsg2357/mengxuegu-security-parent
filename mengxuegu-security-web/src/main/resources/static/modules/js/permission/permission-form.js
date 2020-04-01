/**
 * 调用方法loadPermissionTree
 */
$(function () {
    loadPermissionTree()
});

/**
 * 加载左侧权限资源树
 */
function loadPermissionTree() {
    /**
       * ztree相关配置
       * 参考: http://www.treejs.cn/v3/api.php
       */
    var menuSetting = {
        view: {
            showLine: true // 显示层级连接线
        },
        check: {
            enable: false // 不显示复选框或单选框
        },
        data: {
            simpleData: {
                enable: true, // 开启简单模块,会自动将List转Json渲染
                idKey: "id",  // 唯一标识的属性名称
                pIdKey: "parentId", // 父节点唯一标识的属性名称
                rootPId: 0 // 根节点数据
            },
            key: {
                name: "name", // 显示节点名称的属性名称
                title: "name"
            }
        },
        callback: {
            onClick: function (event, treeId, treeNode) {
                // 捕获节点被点击的事件回调函数
                // 防止点击图标时阻止跳转
                event.preventDefault();

                if (treeNode.id === $("#id").val()) {
                    layer.tips('自已不能作为父资源', '#' + treeId, {time: 1000});
                    return
                }
                // 设置 父资源 传给表单
                parentPermission(treeNode.id, treeNode.name);
                layer.tips('指定成功', '#' + treeId, {time: 1000});
            }
        }
    };
    $.post(contextPath + "permission/list", function (data) {
        // 渲染树
        var permissionTree = $.fn.zTree.init($("#permissionTree"), menuSetting, data.data);
        // 回显父节点, 0则默认就是根节点
        var parentIdVal = $('#parentId').val();
        if (parentIdVal !== null && parentIdVal !== '' && parentIdVal !== undefined) {
            var nodes = permissionTree.getNodesByParam("id", parentIdVal, null);
            console.log(nodes[0]);
            parentPermission(parentIdVal, nodes[0].name);
        }

    });
}

// 父资源
function parentPermission(parentId, parentName) {
    if( parentId == null || parentId === '') {
        parentId = 0;
        parentName = '根菜单';
    }
    $("#parentId").val(parentId);
    $("#parentName").val(parentName);
}