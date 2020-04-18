/**
 * FN:承保机构初始化界面  
 */
var toUrl = "";
// 加载承保机构数据
$(function() {
	$('#index').treegrid({
		width : '99%',
		height : $(window).height(),
		fitColumns : true,
		rownumbers : true,
		toolbar :'#toolbar',
		url : getRootPath() + '/dept/getall.do',
		idField : 'deptId',
		treeField : 'deptName',
		columns : [ [ {
			title : '机构代码',
			field : 'deptId',
			width : 100
		}, {
			title : '机构名称',
			field : 'deptName',
			width : 300
		}, {
			title : '分公司代码',
			field : 'branchId',
			width : 100
		}, {
			title : '上级机构',
			field : 'abvbranch',
			width : 100
		}, {
			title : '机构级别',
			field : 'kind',
			width : 60,
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
					return "支机构"
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
		}
		] ] 
	});
	$('#qy_list').hide();
});

// 字段下拉选项
function selectDept() { 
	$('#tb_kind').combobox({
		required : true,
		editable:false,
		url : getRootPath() + '/dept/getdeptlevel.do',
		valueField : 'levelId',
		textField : 'levelName',
		onChange : function(newval, oldval) {
			$('#tb_abvbranch').combotree('clear');
			if (newval == '0') {
				// 隐藏上级机构
				$('#updept').hide();
				$('#tb_abvbranch').combotree({
					required : false 
				});
			} else {
				$('#updept').show(); 
				$('#tb_abvbranch').combotree({
					required : true,
					url : getRootPath() + '/dept/getdeptmap.do?kind=' + newval,
					onBeforeSelect : function(node) {
						var curentnum = parseInt(newval);
						var upnum = parseInt(node.kind);
						if (curentnum != upnum + 1) {
							$('#tb_abvbranch').tree('uncheck', node.target);
						}
					}
				});
			}

		}
	});

	$('#tb_abvbranch').combotree({
		required : true,
		editable:false,
		url : getRootPath() + '/dept/getdeptmap.do'
	});

	$('#tb_isUsed').combobox({
		required : true, 
		editable:false,
		value :'1',
	    textField:'label',
	    valueField:'value',   
	    data: [{
			label: '禁用',
			value: '0'
		},{
			label: '有效',
			value: '1'
		}] 
	});
}

// 编辑窗口--确定取消按钮监听事件
function editClick() {
	$("#editDept").dialog({
		buttons : [ {
			text : '保存',
			iconCls : 'icon-ok',
			handler : function() {
				checkInput();
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#editDept').window('close');
			}
		} ]
	});
}
