<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<base href="<%=basePath%>">
<head>
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>

<script type="text/javascript">

	$(function(){
		queryClueByConditionForPage(1, 10);
		//给创建按钮添加单击事件
		$("#createClueBtn").click(function () {
			//重置表单
			$("#createClueForm").get(0).reset()
			//弹出创建市场活动的模态窗口
			$("#createClueModal").modal("show")
		});
		//给创建线索保存按钮添加单击事件
		$("#SaveCreateClueBtn").click(function(){
			//收集参数
			var fullname       =$.trim($("#create-fullname").val());
			var appellation    =$("#create-appellation").val();
			var owner          =$("#create-owner").val();
			var company        =$.trim($("#create-company").val());
			var job            =$.trim($("#create-job").val());
			var email          =$.trim($("#create-email").val());
			var phone          =$.trim($("#create-phone").val());
			var website        =$.trim($("#create-website").val());
			var mphone         =$.trim($("#create-mphone").val());
			var state          =$("#create-status").val();
			var source         =$("#create-source").val();
			var description    =$.trim($("#create-description").val());
			var contactSummary =$.trim($("#create-contactSummary").val());
			var nextContactTime=$.trim($("#create-nextContactTime").val());
			var address        =$.trim($("#create-address").val());
			//表单验证
			if (company=== "") {
				alert("公司不能为空");
				return;
			}
			if (fullname==="") {
				alert("姓名不能为空");
				return;
			}
			//发送请求
			$.ajax({
				url:"workbench/clue/saveCreateClue.do",
				data:{
					fullname       :fullname       ,
					appellation    :appellation    ,
					owner          :owner          ,
					company        :company        ,
					job            :job            ,
					email          :email          ,
					phone          :phone          ,
					website        :website        ,
					mphone         :mphone         ,
					state          :state          ,
					source         :source         ,
					description    :description    ,
					contactSummary :contactSummary ,
					nextContactTime:nextContactTime,
					address        :address
				},
				dataType: "json",
				type: "POST",
				success:function (data) {
					if (data.code === "1") {
						//关闭模态窗口
						$("#createClueModal").modal("hide");
						//刷新市场活动列，显示第一页数据，保持每页显示条数不变(保留)
						queryClueByConditionForPage(1, $("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
					} else {
						//提示信息
						alert(data.message);
						//模态窗口不关闭
						$("#createClueModal").modal("show");//可以不写。
					}
				}

			});


		});
		//给查询按钮添加事件
		$("#queryClueBtn").click(function(){
			queryClueByConditionForPage(1, $("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
		});
		//给全选按钮添加单击事件
		$("#checkAll").click(function () {
			//如果"全选"按钮是选中状态，则列表中所有checkbox都选中
			/*if(this.checked==true){
				$("#tBody input[type='checkbox']").prop("checked",true);
			}else{
				$("#tBody input[type='checkbox']").prop("checked",false);
			}*/
			$("#tBody input[type='checkbox']").prop("checked", this.checked);
		});
		//如果所有CheckBox都被选中 则全选框也被选中
		$("#tBody").on("click", "input[type='checkbox']", function () {
			if ($("#tBody input[type='checkbox']").size() === $("#tBody input[type='checkbox']:checked").size()) {
				$("#checkAll").prop("checked", true);
			} else {
				$("#checkAll").prop("checked", false);
			}
		})
		//给修改按钮添加事件
		$("#editClueBtn").click(function () {
			//收集参数

			//获取列表中选中的CheckBox
			var checkIds = $("#tBody input[type='checkbox']:checked");
			if (checkIds.size() === 0) {
				alert("请选择需要修改的线索");
				return;
			}
			if (checkIds.size() > 1) {
				alert("每次只能修改一条线索");
				return;
			}
			var id = checkIds[0].value;
			//发送请求
			$.ajax({
				url:"workbench/clue/queryClueById.do",
				data:{id:id},
				dataType:"JSON",
				type:"POST",
				success:function (data) {
					//将查找到的clue显示在修改线索的模态窗口
					//收集参数
					$.trim($("#edit-fullname").val(data.fullname));
					$("#edit-appellation").val(data.appellation);
					$("#edit-owner").val(data.owner);
					$.trim($("#edit-company").val(data.company));
					$.trim($("#edit-job").val(data.job));
					$.trim($("#edit-email").val(data.email));
					$.trim($("#edit-phone").val(data.phone));
					$.trim($("#edit-website").val(data.website));
					$.trim($("#edit-mphone").val(data.mphone));
					$("#edit-status").val(data.state);
					$("#edit-source").val(data.source);
					$.trim($("#edit-description").val(data.description));
					$.trim($("#edit-contactSummary").val(data.contactSummary));//获取不到数据
					$.trim($("#edit-nextContactTime").val(data.nextContactTime));
					$.trim($("#edit-address").val(data.address));

					//弹出模态窗口
					$("#editClueModal").modal("show");
				}
			})
		});
		//给更新按钮添加事件
		$("#updateClueBtn").click(function () {
			//收集参数
			var checkIds = $("#tBody input[type='checkbox']:checked");
			var id = checkIds[0].value;
			var fullname= $.trim($("#edit-fullname").val());
			var appellation=$("#edit-appellation").val();
			var owner=$("#edit-owner").val();
			var company=$.trim($("#edit-company").val());
			var job=$.trim($("#edit-job").val());
			var email=$.trim($("#edit-email").val());
			var phone=$.trim($("#edit-phone").val());
			var website=$.trim($("#edit-website").val());
			var mphone=$.trim($("#edit-mphone").val());
			var state=$("#edit-status").val();
			var source=$("#edit-source").val();
			var description=$.trim($("#edit-description").val());
			var contactSummary=$.trim($("#edit-contactSummary").val());//获取不到数据
			var nextContactTime=$.trim($("#edit-nextContactTime").val());
			var address=$.trim($("#edit-address").val());

			//发送请求
			$.ajax({
				url:"workbench/clue/editClue.do",
				data:{
					id:id,
					fullname       :fullname       ,
					appellation    :appellation    ,
					owner          :owner          ,
					company        :company        ,
					job            :job            ,
					email          :email          ,
					phone          :phone          ,
					website        :website        ,
					mphone         :mphone         ,
					state          :state          ,
					source         :source         ,
					description    :description    ,
					contactSummary :contactSummary ,
					nextContactTime:nextContactTime,
					address        :address
				},
				dataType: "json",
				type: "POST",
				success:function (data) {
					if (data.code === "1") {
						//关闭模态窗口
						$("#editClueModal").modal("hide");
						//刷新市场活动列，显示第一页数据，保持每页显示条数不变(保留)
						queryClueByConditionForPage(1, $("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
					} else {
						//提示信息
						alert(data.message);
						//模态窗口不关闭
						$("#editClueModal").modal("show");//可以不写。
					}
				}
			})
		})
		//给删除按钮绑定事件
		$("#deleteClueBtn").click(function () {
			//收集参数
			//获取列表中所有被选中的CheckBox
			var checkIds = $("#tBody input[type='checkbox']:checked");
			if (checkIds.size() === 0) {
				alert("请选择要删除的市场活动");
				return;
			}
			var ids = "";
			$.each(checkIds, function () {
				ids += "ids=" + this.value + "&";
			});
			ids = ids.substr(0, ids.length - 1);
			if (window.confirm("确认删除吗")){
				$.ajax({
					url:"workbench/clue/deleteClueByIds.do",
					data:ids,
					dataType:"JSON",
					type:"POST",
					success:function (data) {
						if (data.code==='1'){
							queryClueByConditionForPage(1, $("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
						}
					}
				})
			}
		})


		function queryClueByConditionForPage(pageNo, pageSize) {
			//收集参数
			var fullname = $("#search-fullName").val();
			var owner = $("#search-owner").val();
			var company = $("#search-company").val();
			var phone = $("#search-phone").val();
			var mphone = $("#search-mPhone").val();
			var source=$("#search-source").val();
			var state=$("#search-state").val();
			//发送请求
			$.ajax({
				url: 'workbench/clue/queryClueByConditionForPage.do',
				data: {
					fullname: fullname,
					company:company,
					phone:phone,
					source:source,
					owner: owner,
					mphone:mphone,
					state:state,
					pageNo:pageNo,
					pageSize:pageSize
				},
				type: 'post',
				dataType: 'json',
				success: function (data) {
					//显示总条数
					//$("#totalRowsB").text(data.totalRows);
					//显示市场活动的列表
					//遍历clueList，拼接所有行数据
					//把id绑定到checkBox
					var htmlStr = "";
					$.each(data.clueList, function (index, obj) {
						htmlStr+="<tr>";
						htmlStr+="<td><input type=\"checkbox\" value=\"" + obj.id + "\" /></td>";
						htmlStr+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/clue/detailClueByIds.do?id="+obj.id+"'\">"+obj.fullname+obj.appellation+"</a></td>";
						htmlStr+="<td>"+obj.company+"</td>";
						htmlStr+="<td>"+obj.phone+"</td>";
						htmlStr+="<td>"+obj.mphone+"</td>";
						htmlStr+="<td>"+obj.source+"</td>";
						htmlStr+="<td>"+obj.owner+"</td>";
						htmlStr+="<td>"+obj.state+"</td>";
						htmlStr+="</tr>";
					});
					$("#tBody").html(htmlStr);
					//取消全选按钮
					//$("#checkAll").prop("checked", false);
					//计算总页数
					var totalPages = 1;
					if (data.totalRows % pageSize === 0) {
						totalPages = data.totalRows / pageSize;
					} else {
						totalPages = parseInt(data.totalRows / pageSize) + 1;
					}
					//对容器调用bs_pagination工具函数，显示翻页信息
					$("#demo_pag1").bs_pagination({
						currentPage: pageNo,//当前页号,相当于pageNo
						rowsPerPage: pageSize,//每页显示条数,相当于pageSize
						totalRows: data.totalRows,//总条数
						totalPages: totalPages,  //总页数,必填参数.
						visiblePageLinks: 5,//最多可以显示的卡片数
						showGoToPage: true,//是否显示"跳转到"部分,默认true--显示
						showRowsPerPage: true,//是否显示"每页显示条数"部分。默认true--显示
						showRowsInfo: true,//是否显示记录的信息，默认true--显示

						//用户每次切换页号，都自动触发本函数;
						//每次返回切换页号之后的pageNo和pageSize
						onChangePage: function (event, pageObj) { // returns page_num and rows_per_page after a link has clicked
							//js代码
							queryClueByConditionForPage(pageObj.currentPage, pageObj.rowsPerPage);
						}
					});
				}
			});
		};
	});
	
</script>
</head>
<body>

	<!-- 创建线索的模态窗口 -->
	<div class="modal fade" id="createClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">创建线索</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="createClueForm">
					
						<div class="form-group">
							<label for="create-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">
									<c:forEach items="${users}" var="u">
										<option value="${u.id}">${u.name}</option>
									</c:forEach>
								</select>
							</div>
							<label for="create-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-company">
							</div>
						</div>
						<div class="form-group">
							<label for="create-appellation" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-appellation">
									<c:forEach items="${appellations}" var="a">
										<option value="${a.id}">${a.value}</option>
									</c:forEach>
								</select>
							</div>
							<label for="create-fullname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-fullname">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-job">
							</div>
							<label for="create-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-email">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-phone">
							</div>
							<label for="create-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-website">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-mphone">
							</div>
							<label for="create-status" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-status">
									<c:forEach items="${clueStates}" var="c">
										<option value="${c.id}">${c.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-source">
									<c:forEach items="${sources}" var="s">
										<option value="${s.id}">${s.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						

						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">线索描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="date" class="form-control" id="create-nextContactTime">
								</div>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>
						
						<div style="position: relative;top: 20px;">
							<div class="form-group">
                                <label for="create-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-address"></textarea>
                                </div>
							</div>
						</div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="SaveCreateClueBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改线索的模态窗口 -->
	<div class="modal fade" id="editClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">修改线索</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">
									<c:forEach items="${users}" var="u">
										<option value="${u.id}">${u.name}</option>
									</c:forEach>
								</select>
							</div>
							<label for="edit-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-company" value="动力节点">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-appellation" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-appellation">
									<c:forEach items="${appellations}" var="a">
										<option value="${a.id}">${a.value}</option>
									</c:forEach>
								</select>
							</div>
							<label for="edit-fullname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-fullname" value="李四">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-job" value="CTO">
							</div>
							<label for="edit-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-email" value="lisi@bjpowernode.com">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-phone" value="010-84846003">
							</div>
							<label for="edit-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-website" value="http://www.bjpowernode.com">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-mphone" value="12345678901">
							</div>
							<label for="edit-status" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-status">
									<c:forEach items="${clueStates }" var="c">
										<option value="${c.id}">${c.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-source">
									<c:forEach items="${sources}" var="s">
										<option value="${s.id}">${s.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description">这是一条线索的描述信息</textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="edit-contactSummary">这个线索即将被转换</textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="date" class="form-control" id="edit-nextContactTime" value="2017-05-01">
								</div>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address">北京大兴区大族企业湾</textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateClueBtn" >更新</button>
				</div>
			</div>
		</div>
	</div>

	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>线索列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon" >名称</div>
				      <input class="form-control" type="text" id="search-fullName">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司</div>
				      <input class="form-control" type="text" id="search-company">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司座机</div>
				      <input class="form-control" type="text" id="search-phone">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">线索来源</div>
					  <select class="form-control" id="search-source">
					  	  <option></option>
					  	  <option>广告</option>
						  <option>推销电话</option>
						  <option>员工介绍</option>
						  <option>外部介绍</option>
						  <option>在线商场</option>
						  <option>合作伙伴</option>
						  <option>公开媒介</option>
						  <option>销售邮件</option>
						  <option>合作伙伴研讨会</option>
						  <option>内部研讨会</option>
						  <option>交易会</option>
						  <option>web下载</option>
						  <option>web调研</option>
						  <option>聊天</option>
					  </select>
				    </div>
				  </div>
				  
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search-owner">
				    </div>
				  </div>
				  
				  
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">手机</div>
				      <input class="form-control" type="text" id="search-mPhone">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">线索状态</div>
					  <select class="form-control" id="search-state">
					  	<option></option>
					  	<option>试图联系</option>
					  	<option>将来联系</option>
					  	<option>已联系</option>
					  	<option>虚假线索</option>
					  	<option>丢失线索</option>
					  	<option>未联系</option>
					  	<option>需要条件</option>
					  </select >
				    </div>
				  </div>

				  <button type="button" class="btn btn-default" id="queryClueBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 40px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" data-toggle="modal"  id="createClueBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" data-toggle="modal" id="editClueBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteClueBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
				
			</div>
			<div style="position: relative;top: 50px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAll" /></td>
							<td>名称</td>
							<td>公司</td>
							<td>公司座机</td>
							<td>手机</td>
							<td>线索来源</td>
							<td>所有者</td>
							<td>线索状态</td>
						</tr>
					</thead>
					<tbody id="tBody">
						<tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">李四先生</a></td>
							<td>动力节点</td>
							<td>010-84846003</td>
							<td>12345678901</td>
							<td>广告</td>
							<td>zhangsan</td>
							<td>已联系</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">李四先生</a></td>
                            <td>动力节点</td>
                            <td>010-84846003</td>
                            <td>12345678901</td>
                            <td>广告</td>
                            <td>zhangsan</td>
                            <td>已联系</td>
                        </tr>
					</tbody>
				</table>
				<div id="demo_pag1"></div>
			</div>
			
		<%--	<div style="height: 50px; position: relative;top: 60px;">
				<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>
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
			</div>
			--%>
		</div>
		
	</div>
</body>
</html>