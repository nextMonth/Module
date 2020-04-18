/**
 * FN:承保机构功能事件  
 */
// 新增功能窗口
function addDeptWindow() {
	selectDept();
	editClick();
	$("#editDept").dialog("open").dialog('setTitle', '新增机构');
	$("#editfm").form("clear");
	$("#tb_deptId").validatebox("enable");
	$("#tb_kind").combobox("enable");
	$("#tb_abvbranch").combotree("enable");
	toUrl = getRootPath() + "/dept/add.do";
}
// 修改功能窗口
function editDeptWindow() {
	selectDept();
	editClick();
	var selectedrows;
	var isShow = $('#qy_list').is(':hidden');
	if (isShow) {
		selectedrows = $("#index").treegrid('getSelections');
	} else { 
		selectedrows = $("#querypage").datagrid('getSelections');
	}
	var selectedrow = selectedrows[0];
	var selectedLen = selectedrows.length;
	if (selectedLen > 0) { 
		$("#editDept").dialog("open").dialog('setTitle', '修改机构');
		$("#editfm").form("load", selectedrow);
		$("#tb_deptId").validatebox("disable");
		$("#tb_kind").combobox("disable");
		$("#tb_abvbranch").combotree("disable");
		toUrl = getRootPath() + "/dept/update.do?deptId="
				+ $("#tb_deptId").val();
	} else {
		$.messager.alert('系统提示', '请先选择机构！', 'info');
	}

}
// 删除一条记录
function delDeptWindow() {
	var selectedrows;
	var isShow = $('#qy_list').is(':hidden');
	if (isShow) {
		selectedrows = $("#index").treegrid('getSelections');
	} else { 
		selectedrows = $("#querypage").datagrid('getSelections');
	}
	var selectedLen = selectedrows.length;
	if (selectedLen > 0) {
		var selectedrow = selectedrows[0];
		$.messager.confirm('提示信息', '确定删除[' + selectedrow.deptName + ']吗?',
				function(res) {
					if (res) {
						var url = getRootPath() + "/dept/delete.do?deptId="
								+ selectedrow.deptId;
						$.getJSON(url, function(json) {
							$.messager.alert("提示信息", json.message);
							if(isShow){
							$("#index").treegrid("load");
							$("#index").treegrid("clearSelections");
							}else{
							$('#querypage').datagrid("load");
							$("#querypage").datagrid("clearSelections");
							}
						});
					}
				});
	} else {
		$.messager.alert('系统提示', '请先选择机构！', 'info');
	}

}

// 合法性校验判断，再提交
function checkInput() {
	var isShow = $('#qy_list').is(':hidden');
	$("#editfm").form("submit", {
		url : toUrl,
		onsubmit : function() {
			return $(this).form("validate");
		},
		success : function(resdata) {
			var data = eval("(" + resdata + ")");
			if (data.isSuccess) {
				$.messager.alert("提示信息", data.message);
				if(isShow){
				$("#index").treegrid("load");
				}else{ 
				$('#querypage').datagrid("load");
				}
				$("#editDept").dialog("close");
			} else {
				$.messager.alert("提示信息", data.message);
			}
		}
	});
}

/**
 * 切换显示隐藏treegrid组件、datagrid组件
 */
function queryDept() {
	var deptId = $("#qy_deptId").val(); 
	var deptName = $("#qy_deptName").textbox("getValue");
    $.trim(deptId);
	$.trim(deptName);
	if (deptId == '' && deptName == '') {
		$('#qy_tree').show();
		$('#qy_list').hide();
		$('#index').treegrid({
			toolbar :'#toolbar'
		});
		return false;
	} else {
		$('#qy_tree').hide();
		$('#qy_list').show();
		$('#querypage').datagrid({
			width : '99%',
			height : '500px',
			toolbar :'#toolbar',
			fitColumns : true,
			rownumbers : true,
			singleSelect : true,
			url : getRootPath() + "/dept/query.do",
			queryParams : {
				deptId : deptId,
				deptName : deptName
			},
			columns : [ [ {
				field : 'deptId',
				title : '机构代码',
				width : 100
			}, {
				field : 'deptName',
				title : '机构名称',
				width : 300
			}, {
				field : 'branchId',
				title : '分公司',
				width : 100
			}, {
				field : 'abvbranch',
				title : '上级机构',
				width : 100
			}, {
				field : 'kind',
				title : '机构级别',
				width : 100,
				formatter : function(value, row, index) {
					if (value == '0') {
						return "总公司"
					}
					if (value == '1') {
						return "分公司"
					}
					if (value == '2') {
						return "市公司"
					}
					if (value == '3') {
						return "承保机构"
					}
				}
			},{
				title : '使用状态',
				field : 'isUsed',
				width : 60,
				formatter : function(value, row, index) {
					if (value == '0') {
						return "禁用"
					}else{
						return "有效"
					}
					 
				}
			}] ]
		});

	} 
}

 