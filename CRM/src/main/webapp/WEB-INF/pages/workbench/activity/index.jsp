<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<!-- PAGINATION plugin -->
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>

<script type="text/javascript">

	$(function(){
		$("#createActivityBtn").click(function (){
			$("#newActivityForm").get(0).reset();
			$("#createActivityModal").modal("show");
		})

		$("#saveActivityBtn").click(function () {
			var owner =$("#create-marketActivityOwner").val();
			var name =$.trim($("#create-marketActivityName").val());
			var startTime =$("#create-startTime").val();
			var endTime =$("#create-endTime").val();
			var cost =$.trim($("#create-cost").val());
			var describe =$.trim($("#create-describe").val());
			if(owner==""){
				alert("所有者不能为空");
				return;
			}
			if(name==""){
				alert("名称不能为空");
				return;
			}
			if(startTime!=""&&endTime!=""){
				if(startTime>endTime){
					alert("结束时间不能比开始时间小");
					return;
				}
			}else{
				alert("开始和结束时间都不能为空");
				return;
			}
			var regExp=/^(([1-9]\d*)|0)$/;
			if(!regExp.test(cost)){
				alert("成本只能为非负整数");
				return;
			}

			$.ajax({
				url:'workbench/activity/new',
				data:{
					owner:owner,
					name:name,
					startDate:startTime,
					endDate:endTime,
					cost:cost,
					description:describe
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					if(data.code==1){
						$("#createActivityModal").modal("hide");
						//刷新新增活动
						queryActivityByTiaoJian(1,$("#demo_page").bs_pagination('getOption','rowsPerPage'));
					}else{
						alert(data.message);
						$("#createActivityModal").modal("show");
					}
				}
			})
		})
		//$("input[name='mydate']").datetimepicker({})   标签中name='mydate'
		$(".mydate").datetimepicker({
			language:'zh-CN',  //语言
			format:'yyyy-mm-dd',  //日期格式
			minView:'month',   //可以选择最小的视图   到日则选month  到时则选day
			initialDate:new Date(),  //显示初始化的日期
			autoclose:true,  //选择完后自动关闭日历
			todayBtn:true,  //显示今天按钮
			clearBtn:true  //显示清空按钮
		})
		queryActivityByTiaoJian(1,10);

		$("#queryActivityBtn").click(function () {
			queryActivityByTiaoJian(1,$("#demo_page").bs_pagination('getOption','rowsPerPage'));
		});

		$("#checkAll").click(function () {
			/*if(this.checked==true){
				$("#showData input[type='checkbox']").prop("checked",true);
			}else{
				$("#showData input[type='checkbox']").prop("checked",false);
			}*/
			$("#showData input[type='checkbox']").prop("checked",this.checked);
		})
		/*$("#showData input[type='checkbox']").click(function () {
			if($("#showData input[type='checkbox']").size()==$("#showData input[type='checkbox']:checked").size()){
				$("#checkAll").prop("checked",false)
			}else{
				$("#checkAll").prop("checked",false);
			}
		})*/
		$("#showData").on("click","input[type='checkbox']",function () {
			if($("#showData input[type='checkbox']").size()==$("#showData input[type='checkbox']:checked").size()){
				$("#checkAll").prop("checked",true)
			}else{
				$("#checkAll").prop("checked",false);
			}
		})
		$("#deleteActivityBtn").click(function (){
			//获取所有被选中的id
			var ides=$("#showData input[type='checkbox']:checked");
			if(ides.size()==0){
				alert("请选择要删除的市场活动");
				return;
			}
			if(window.confirm("确定要删除吗？")){
				var ids="";
				$.each(ides,function (){
					ids+="id="+this.value+"&";
				})
				ids=ids.substr(0,ids.length-1);
				$.ajax({
					url:'workbench/activity/removeActivityByIds',
					data:ids,
					type:'post',
					dataType: 'json',
					success:function (data){
						if(data.code=="1"){
							queryActivityByTiaoJian(1,$("#demo_page").bs_pagination('getOption','rowsPerPage'));
						}else{
							alert(data.message);
						}
					}
				})
			}
		})
		$("#insertActivityBtn").click(function (){
			var checkedes=$("#showData input[type='checkbox']:checked");
			if(checkedes.size()==0){
				alert("请选择要修改的活动");
				return;
			}else if(checkedes.size()>1){
				alert("一次只能修改一个活动");
				return;
			}
			//var id=checkedes.val();
			//var id=	checkedes.get(0).value;
			var id=	checkedes[0].value;
			$.ajax({
				url:'workbench/activity/selectActivityById',
				data:{
					id:id
				},
				type:'post',
				dataType:'json',
				success:function (data){
					$("#edit_id").val(data.id);
					$("#edit-marketActivityOwner").val(data.owner);
					$("#edit-marketActivityName").val(data.name);
					$("#edit-startTime").val(data.startDate);
					$("#edit-endTime").val(data.endDate);
					$("#edit-cost").val(data.cost);
					$("#edit-describe").val(data.description);
					//显示模态窗口
					$("#editActivityModal").modal("show");
				}
			})
		})

		$("#colseEdit").click(function (){
			$("#editActivityModal").modal("hide");
		})

		$("#openEdit").click(function () {
			var id=$("#edit_id").val();
			var owner=$("#edit-marketActivityOwner").val();
			var name=$.trim($("#edit-marketActivityName").val());
			var startDate=$("#edit-startTime").val();
			var endDate=$("#edit-endTime").val();
			var cost=$.trim($("#edit-cost").val());
			var description=$.trim($("#edit-describe").val());
			//表单验证
			if(owner==""){
				alert("所有者不能为空");
				return;
			}
			if(name==""){
				alert("名称不能为空");
				return;
			}
			if(startDate!=""&&endDate!=""){
				if(startDate>endDate){
					alert("结束时间不能比开始时间小");
					return;
				}
			}else{
				alert("开始和结束时间都不能为空");
				return;
			}
			var regExp=/^(([1-9]\d*)|0)$/;
			if(!regExp.test(cost)){
				alert("成本只能为非负整数");
				return;
			}
			$.ajax({
				url:'workbench/activity/updateActivityById',
				data:{
					id:id,
					owner:owner,
					name:name,
					startDate:startDate,
					endDate:endDate,
					cost:cost,
					description:description
				},
				type:'post',
				dataType:'json',
				success:function (data){
					if(data.code=="0"){
						alert(data.message);
						$("#editActivityModal").modal("show");
					}else{
						$("#editActivityModal").modal("hide");
						queryActivityByTiaoJian($("#demo_page").bs_pagination('getOption','currentPage'),$("#demo_page").bs_pagination('getOption','rowsPerPage'));

					}
				}
			})
		})
		$("#exportActivityAllBtn").click(function (){
			window.location.href="workbench/activity/exportAllActivities?news=all";
		})
		$("#exportActivityXzBtn").click(function (){
			var ides=$("#showData input[type='checkbox']:checked");
			if(ides.size()==0){
				alert("请选择要导出的内容，至少选中一条数据");
				return;
			}
			var ids="workbench/activity/exportAllActivities?news=some";
			$.each(ides,function (){
				ids+="&id="+this.value;
			})
			window.location.href=ids;
		})

		$("#colseImportBtn").click(function () {
			$("#importActivityModal").modal("hide");
		})

		$("#importActivityBtn").click(function (){
			var fileName=$("#activityFile").val();
			var fileHouzui=fileName.substr(fileName.lastIndexOf(".")+1).toLocaleLowerCase();
			if(fileHouzui!="xls"){
				alert("只支持xls格式的文件！");
				return;
			}
			// $("#activityFile").get(0).files
			var file=$("#activityFile")[0].files[0];
			if(file.size>5*1024*1024){
				alert("所上传的文件不能超过5M！");
				return;
			}
			var fromdata=new FormData();
			fromdata.append("multipartFile",file);
			$.ajax({
				url:'workbench/activity/importActivityByExcel',
				data:fromdata,
				processData:false,//设置ajax向后台发送数据之前是否将参数转为字符串 默认为true
				contentType:false,//设置ajax向后台发送数据之前是否按照urlencoded编码 	 默认为true
				type:'post',
				dataType:'json',
				success:function (data) {
					if(data.code=="1"){
						alert(data.message);
						$("#importActivityModal").modal("hide");
						queryActivityByTiaoJian(1,$("#demo_page").bs_pagination('getOption','rowsPerPage'));
					}else{
						alert(data.message);
						$("#importActivityModal").modal("show");

					}
				}
			})

		})



	});

	function queryActivityByTiaoJian(pageNo,pageSize) {
		var name=$("#queryByname").val();
		var owner=$("#queryByOwner").val();
		var startDate=$("#startTime").val();
		var endDate=$("#endTime").val();
		/*var pageNo=1;
		var pageSize=10;*/
		var htmlStr="";
		$.ajax({
			url:'workbench/activity/selectActivityByCondition',
			data:{
				name:name,
				owner:owner,
				startDate:startDate,
				endDate:endDate,
				pageNo:pageNo,
				pageSize:pageSize
			},
			type:'post',
			dataType: 'json',
			success:function (data){
				//$("#tiaoShu").text(data.counts);
				$.each(data.activityList,function (index, obj) {
					htmlStr+="<tr class=\"active\">";
					htmlStr+="<td><input type=\"checkbox\" value=\""+obj.id+"\"/></td>";
					htmlStr+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/detailActivity?id="+obj.id+"'\">"+obj.name+"</a></td>";
					htmlStr+="<td>"+obj.owner+"</td>";
					htmlStr+="<td>"+obj.startDate+"</td>";
					htmlStr+="<td>"+obj.endDate+"</td>";
					htmlStr+="</tr>";
				})
				$("#showData").html(htmlStr);
				$("#checkAll").prop("checked",false);
				var yeShu=1;
				if(data.counts%pageSize==0){
					yeShu=data.counts/pageSize;
				}else{
					yeShu=parseInt(data.counts/pageSize)+1;
				}
				$("#demo_page").bs_pagination({
					currentPage:pageNo, //当前页号 相当于pageNo

					rowsPerPage:pageSize, //每页显示条数 相当于pageSize
					totalRows:data.counts, //总条数
					totalPages:yeShu, //总页数

					visiblePageLinks: 5, //最多显示卡片数 1 2 3 4 5页

					showGoToPage: true,//是否显示 跳转到 页  默认true显示
					showRowsPerPage: true,//是否显示每页显示条数部分 默认true显示
					showRowsInfo: true,//是否显示记录的信息 默认true显示

					//用户每次切换页面 自动调本函数
					//每次返回pageNo,pageSize
					onChangePage:function (event, pageObj) {
						queryActivityByTiaoJian(pageObj.currentPage,pageObj.rowsPerPage);
					}
				})
			}
		})
	}


</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">

					<form id="newActivityForm" class="form-horizontal" role="form">

						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">
								  <c:forEach items="${users}" var="user">
									  <option value="${user.id}">${user.name}</option>
								  </c:forEach>
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>

						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control mydate" name="mydate" id="create-startTime" readonly>
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control mydate" name="mydate" id="create-endTime" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveActivityBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="edit_id">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">
									<c:forEach items="${users}" var="user">
										<option value="${user.id}">${user.name}</option>
									</c:forEach>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control mydate" id="edit-startTime" value="2020-10-10" readonly>
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control mydate" id="edit-endTime" value="2020-10-20" readonly>
							</div>
						</div>

						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" id="colseEdit">关闭</button>
					<button type="button" class="btn btn-primary" id="openEdit">更新</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 导入市场活动的模态窗口 -->
    <div class="modal fade" id="importActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
                </div>
                <div class="modal-body" style="height: 350px;">
                    <div style="position: relative;top: 20px; left: 50px;">
                        请选择要上传的文件：<small style="color: gray;">[仅支持.xls]</small>
                    </div>
                    <div style="position: relative;top: 40px; left: 50px;">
                        <input type="file" id="activityFile">
                    </div>
                    <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
                        <h3>重要提示</h3>
                        <ul>
                            <li>操作仅针对Excel，仅支持后缀名为XLS的文件。</li>
                            <li>给定文件的第一行将视为字段名。</li>
                            <li>请确认您的文件大小不超过5MB。</li>
                            <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                            <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                            <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                            <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                        </ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" id="colseImportBtn">关闭</button>
                    <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
                </div>
            </div>
        </div>
    </div>


	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">

			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="queryByname">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text"  id="queryByOwner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control mydate" type="text" id="startTime" readonly/>
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control mydate" type="text" id="endTime" readonly>
				    </div>
				  </div>

				  <button type="button" class="btn btn-default" id="queryActivityBtn">查询</button>

				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createActivityBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="insertActivityBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteActivityBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal" ><span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）</button>
                    <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）</button>
                    <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）</button>
                </div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAll"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="showData">
						<%--<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>--%>
					</tbody>
				</table>
				<div id="demo_page"></div>
			</div>

			<%--<div style="height: 50px; position: relative;top: 30px;">
				<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b id="tiaoShu">0</b>条记录</button>
				</div>
				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							10
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">20</a></li>
							<li><a href="#">30</a></li>
						</ul>
					</div>
					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
				</div>
				<div style="position: relative;top: -88px; left: 285px;">
					<nav>
						<ul class="pagination">
							<li class="disabled"><a href="#">首页</a></li>
							<li class="disabled"><a href="#">上一页</a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">下一页</a></li>
							<li class="disabled"><a href="#">末页</a></li>
						</ul>
					</nav>
				</div>
			</div>--%>
		</div>

	</div>
</body>
</html>
