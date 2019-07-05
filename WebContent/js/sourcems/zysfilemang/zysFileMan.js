//数据列表
function zysFileManDataGrid(baseUrl, buttons, type){
	var zysFileMan_url = baseUrl + '/zysFileMan/zysFileManDataGrid.do';
	var initZysFileMangToolbar = [ {
		text : '新增',
		buttonType:'add',
		iconCls : 'icon-add',
		handler : function() {
			popWindow('zysFileMan-edit-win', 'zysFileMan-mainBody');
			//popWindow('user-import-win', 'zysFileMan-mainBody');
			//$('#zysFileManEditForm').form('clear');
		}
	},'-',{
		text : '修改',
		buttonType:'edit',
		iconCls : 'icon-edit',
		handler : function() {
			var record = Utils.getCheckedRows($('#zysFileMan-data-list'));
			if (Utils.checkSelectOne(record)){
					zysFileManSaveById(baseUrl,record[0]);
			}
		}
	},'-',{
		text : '删除',
		buttonType:'delete',
		iconCls : 'icon-remove',
		handler : function() {
			var record = Utils.getCheckedRows($('#zysFileMan-data-list'));
			if (Utils.checkSelectOne(record)){
					popConfirm('确认删除这条数据吗?','zysFileMan-mainBody');
					$("#popConfirmYes").unbind('click');
					$("#popConfirmYes").click(function (){
						
					});
				}
			}
	},'-',{
		text : '上传文件',
		buttonType:'upload',
		iconCls : 'icon-upload',
		handler : function() {
			
		}
	}];
	var initZysFileMangDataGrid = {
		url : zysFileMan_url,
		fit : true,
		fitColumns : false,
		border : true,
		pagination : true,
		idField : 'pkId',
		pageSize : 10,
		pageList : [10,20,30,40,50 ],
		singleSelect : true,
		checkOnSelect : true,
		selectOnCheck : false,
		remoteSort : false,// 服务器端排序
		striped : true,// 奇偶行不同颜色
		nowrap : false,// 设置为true,当数据长度超出列宽时将会自动截取
		onDblClickRow:function(rowIndex, rowData) {
				
		},
		columns : [ [ {
			field : 'pkId',
			title : '复选框',
			width : 50,
			checkbox : true
		}
		, {
			field : 'fileNm',
			title : '文件名称',
			width : 240,
		}
		, {
			field : 'fileSize',
			title : '文件大小',
			width : 80,
		}
		, {
			field : 'fileRemark',
			title : '文件说明',
			width : 250,
		}
		, {
			field : 'compPwd',
			title : '压缩密码',
			width : 80,
		}
		, {
			field : 'costPrice',
			title : '原价',
			width : 80,
		}
		, {
			field : 'nowPrice',
			title : '现价',
			width : 80,
		}
		, {
			field : 'downloadCount',
			title : '下载次数',
			width : 80,
		}
		, {
			field : 'oper',
			title : '操作',
			width : 200,
			formatter : function(value, row, index) {
				var str = '<span >';
				str = str+ '<a href="#" onclick="queryDmInfo('+index+')" style="color:green" >下载</a>';
				str = str+ '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="queryDmInfo('+index+')" style="color:green" >联系管理员</a>';
				str = str +'</span>';
				return str;
			}	
		}
		 ] ]
	};
	var newBars = getAccessButton(initZysFileMangToolbar,buttons,type);
	if(newBars.length>0){
		initZysFileMangDataGrid['toolbar'] = newBars;
	}
	$('#zysFileMan-data-list').datagrid(initZysFileMangDataGrid);
	
	$('#zysFileManBtnSearch').click(function(){// 查询
		$('#zysFileMan-data-list').datagrid('load', serializeObject($('#zysFileManSearchForm')));
	});
	$('#zysFileManBtnClean').click(function(){// 全部
		$('#zysFileMan-data-list').datagrid('load', {});
		$('#zysFileManSearchForm').form('clear');
	});
	queryTableCodePara(baseUrl);
	
	//获取上传文件绝对路径
/*	$('#upload-file').change(function(){
		console.info('ddd');
		var oFReader = new FileReader();
		var file = document.getElementById('upload-file').files[0];
		oFReader.readAsDataURL(file);
		oFReader.onloadend = function(oFRevent){
			var src = oFRevent.target.result;
			//绝对路径赋值
			$('#filePath').val(src);
	    }
	})*/

}
//查询码表参数
function queryTableCodePara(baseUrl){
	$.ajax(baseUrl + '/zysFileMan/queryTbCodePara.do', {
		type:'post',
		 	dataType:'json',
		 	success:function(result){
		 		checkSession(result);
		 		console.info(result);
				$('#fileType').combobox({
					data:result.fileTypeList,
					valueField:'id',
					textField:'text'
				});
		 	}
	});	
}

//保存file记录
function saveFileRec(baseUrl){
	$('#filePath').val($('#upload-file').val());
	$('#zysFileManEditForm').form('submit',{
		url : baseUrl + '/zysFileMan/saveFileRec.do',
		success : function(result){
			try{
				var r = $.parseJSON(result);
				if(r.success){
					$('#zysFileMan-data-list').datagrid('reload');
				}else{
					popInfo('zysFileMan-edit-error', r.message);
					return false;
				}
				popInfo('zysFileMan-edit-info', r.message);
			}catch(e){
				popInfo('zysFileMan-edit-error', result);
			}
		}
	});
	
	/*$('#user-import-form').form('submit',{
		url : baseUrl + '/zysFileMan/uploadExcel.do',
		success : function(result){
			try{
				var r = $.parseJSON(result);
				if(r.success){
					//$('#user-data-list').datagrid('reload');
				}else{
					popInfo('user-import-error', r.message);
					return false;
				}
				popInfo('user-import-info', r.message);
			}catch(e){
				popInfo('user-import-error', result);
			}
		}
	});*/
}


// 获取选中记录,弹出修改窗口
function zysFileManUpdateById(baseUrl,row){
	$.ajax(baseUrl + '/zysFileMan/getZysFileMangById.do?PK_ID='+row.pkId, {
		type:'post',
		 	dataType:'json',
		 	success:function(result){
		 		checkSession(result);
				$('#zysFileManEditForm').form('load',result.data);
				popWindow('zysFileMan-edit-win', 'zysFileMan-mainBody');
		 	}
	});	
}

// 删除选中记录
function zysFileManDeleteById(row){
	$.ajax(baseUrl + '/zysFileMan/deleteZysFileMangById.do?PK_ID='+row.pkId, {
		type:'post',
		 	dataType:'json',
		 	success:function(result){
		 		r = eval(result);
		 		if(r.success){
					//刷新界面
					$('#zysFileMan-data-list').datagrid('load', {});
				}else{
					rollDown("imf_roll",r.message);
				}
		 	}
	});
}
// 新增\修改 提交
function zysFileManSave(url){
	if($('#zysFileManEditForm').form('validate')){
		$('#zysFileManEditForm').form('submit', {
			url : baseUrl + '/zysFileMan/saveZysFileMang.do',
			success : function(result) {
				try {
					var r = $.parseJSON(result);
					if (r.success) {
						//刷新界面
						$('#zysFileMan-data-list').datagrid('load', {});
						popInfo('zysFileMan-edit-info', r.message);
						popClosed('zysFileMan-edit-win');
					}else{
						popInfo('zysFileMan-edit-error', r.message);
					}
				} catch (e) {
					popInfo('zysFileMan-edit-error', result);
				}
			}
		});
	}
}

$(document).ready(function () {
	$(".imf_intxt,textarea,.selectlist").focus(function () {
		$(this).addClass("input_focus");
	}).blur(function () {
		$(this).removeClass("input_focus");
	});
	$(".imf_pop" ).uidraggable({cancel:"input,textarea,button,select,option,.datagrid,.tree"});
});
