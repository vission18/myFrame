<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/js/sourcems/zysfilemang/zysFileMan.js">
</script>

<script type="text/javascript">
	$(function() {
		zysFileManDataGrid("${pageContext.request.contextPath}","${accessButtons.data}","${accessButtons.type}");
	});
</script>
<div class="easyui-layout" fit="true" id="zysFileMan-mainBody">

	<div data-options="region:'north',title:'',border:false"
		style="overflow:hidden;padding:10px;" align="left" split="true">
		<form id="zysFileManSearchForm">
			<div class="searchmore">
				<label>文件匹配:</label>
				<input class="imf_intxt" id="keyWord" style="width:180px;" name="keyWord" type="text"/>
				<span class="imf_more"><input id="zysFileManBtnSearch" type="button" value="搜索" class="imf_searchmore"/></span>						
				<span class="imf_all"><input id="zysFileManBtnClean" type="button" value="显示全部" class="imf_showall"/></span>
			</div>
		</form>
	</div>
	
	<div data-options="region:'center',border:false" style="padding:5px;">
		<table id="zysFileMan-data-list"></table>
	</div>
	<div id="zysFileMan-edit-win" class="imf_pop" style="width:720px;">
		<div class="imf_pop_title"><strong>文件管理信息列表</strong><span class="imf_pop_closed" onClick="popClosed('zysFileMan-edit-win')">关闭</span></div>
		<form id="zysFileManEditForm" class="ui-form" method="post" enctype="multipart/form-data">
			<input type="hidden" id="pkId" name="pkId">
			<input type="hidden" id="filePath" name="filePath">
			<div class="imf_pop_con">
			<ul>
				<li>
					<strong>文件类型:</strong>
					<span>
						<input  id="fileType" name="fileType" maxlength="100" class="imf_intxt easyui-combobox" 
						style="width:200px;" editable="false" type="text" >
					</span>
					<strong>上传文件:</strong>
					<span>
						<input id="upload-file" name="file" type="file" class="imf_intxt"/>
					</span>
				</li>
				<li>
					<strong>原价:</strong>
					<span>
						<input  id="costPrice" name="costPrice" maxlength="100" class="imf_intxt easyui-validatebox" type="text" >
					</span>
					<strong>现价:</strong>
					<span>
						<input  id="nowPrice" name="nowPrice" maxlength="100" class="imf_intxt easyui-validatebox" type="text" >
					</span>
				</li>
				<li>
					<strong>压缩密码:</strong>
					<span>
						<input  id="compPwd" name="compPwd" maxlength="100" class="imf_intxt easyui-validatebox" type="text" >
					</span>
				</li>
				<li>
					<strong>文件说明:</strong>
					<span> <textarea id="fileRemark" name="fileRemark" rows="8" cols="70" class="imf_textarea"></textarea> </span>
				</li>
			</ul>
			</div>
			<div class="imf_pop_btn">
				<span><input type="button" id="zysFileMan-save" value="保存" class="imf_pop_btn_save" onClick="saveFileRec('${pageContext.request.contextPath}')"/></span>
				<span><input type="button" id="zysFileMan-close" value="关闭" class="imf_pop_btn_closed" onClick="popClosed('zysFileMan-edit-win')"/></span>
			</div>
			<div class="imf_pop_error" id="zysFileMan-edit-error"><p></p></div>
			<div class="imf_pop_correct" id="zysFileMan-edit-info"><p></p></div>
		</form>
	</div>
	
	<div id="user-import-win" class="imf_pop" style="width:440px;">
		<div class="imf_pop_title"><strong>用户批量导入</strong><span class="imf_pop_closed" onClick="popClosed('user-import-win')">关闭</span></div>
		<form id="user-import-form" class="ui-form" method="post" enctype="multipart/form-data">
			<div class="imf_pop_con">
			<ul>
			<li><strong>模板下载：</strong><span><a id="download-btn" href="${pageContext.request.contextPath}/user/downExcel.do"></a></span></li>
			<li><strong>导入文件：</strong><span><input id="user-file" name="file" type="file" class="imf_intxt"/></span></li>
			</ul>
			</div>
			<div class="imf_pop_btn">
				<span><input type="button" id="import-user-save" value="导入" class="imf_pop_btn_save" onClick="saveFileRec('${pageContext.request.contextPath}')"/></span>
				<span><input type="button" id="import-user-close" value="关闭" class="imf_pop_btn_closed" onClick="popClosed('user-import-win')"/></span>
			</div>
			<div class="imf_pop_error" id="user-import-error"><p></p></div>
			<div class="imf_pop_correct" id="user-import-info"><p></p></div>
		</form>
	</div>
</div>

