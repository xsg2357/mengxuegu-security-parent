$(function () {
    loadPermissionTree();
});

// 加载权限资源树,并勾选角色所属权限
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
            enable: true // 显示复选框或单选框
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
            }
        }
    };
    $.post(contextPath + "permission/list", function (data) {
        // 渲染树
        var permissionTree = $.fn.zTree.init($("#permissionTree"), menuSetting, data.data);
        //如果id不为空，类型为修改，勾选菜单
        if ($('#id').val() !== '' && $('#id').val() !== null && $('#id').val() !== undefined) {
            //获取用户对应的菜单
            var perIds = JSON.parse($('#perIds').val());
            for (var i = 0; i < perIds.length; i++) {
                //根据节点属性名id的值，获取到匹配的节点数组
                var nodes = permissionTree.getNodesByParam("id", perIds[i], null);
//勾选当前选中的节点（ 需要勾选节点，勾选节点，父子节点的勾选联动操作）
                permissionTree.checkNode(nodes[0], true, false);
                //展开这个节点（需要展开节点， 展开节点，只影响此节点false）
                permissionTree.expandNode(nodes[0], true, false);
            }
        }
    });
}

// 提交数据
$("#form").submit(function(){
    // 根据 treeId 获取 zTree 对象
    var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
    // 获取所有勾选的节点对象数组
    var nodes = treeObj.getCheckedNodes(true);
    // 获取所有选中的资源权限id
    var perIds = [];
    for (var i = 0; i < nodes.length; i = i + 1) {
        perIds.push(nodes[i].id);
    }
    $("#perIds").val(perIds);
    // 返回true则提交
    return true;
});

