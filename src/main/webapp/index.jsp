<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>FileGenerator</title>
 <script src="js/jquery.min.js"></script>
	<script type="text/javascript">
		function checkNull(id){
			var obj = document.getElementById(id);
			var value = obj.value;
			if(trim(value)==""){
				alert(id+"不能为空!");
				obj.focus();
				return false;
			}
			return true;
		}
		function trim(str){ //删除左右两端的空格
			return str.replace(/(^\s*)|(\s*$)/g, "");
		}
		$(document).ready(function () {
		    $.ajax({
		        timeout: 3000,
		        async: false,
		        type: "POST",
		        url: "/fileGeneratorPro/getExistingId",
		        dataType: "json",
		        success: function (data) {
		            for (var i = 0; i < data.length; i++) {
		                $("#idSelect").append("<option>" + data[i].id + "</option>");
		            }
		        }
		    });
		});
	</script>
</head>
<body>
	<h1>fileGeneratorService</h1><hr>
	
	
	<h3>启动文件生成服务</h3>
	<form action="startGeneFiles" method="post" onsubmit="return checkNull('fileGeneServiceId')">
		<table>
			<tr>
				<td align="right">fileGeneServiceId(文件生成服务ID，即文件配置中的id):<span style="color: red">*</span></td>
				<td align="left">
					<select name="idSelect" id="idSelect" >
						<option value="1">first88888888888888</option>					
					</select>
				</td>
			</tr>
			<tr>
				<td align="right">fileGeneMode(文件生成服务模式ID):<span style="color: red">*</span></td>
				<td align="left"><input  id="fileGeneMode" name="fileGeneMode" value="byXML"></td>
			</tr>
			<tr>
				<td align="right">settleDate(账期日):</td><td align="left"><input name="settleDate" id="settleDate" value="20151012"></td>
			</tr>
			<tr>
				<td align="right">TDay(文件生成日):</td><td align="left"><input name="TDay" id="TDay" value="20151013"></td>
			</tr>
			<tr>
				<td align="right">province(省代码):</td><td align="left"><input name="province" id="province" value="100"></td>
			</tr>
			<tr>
				<td align="right">busiLine(业务线):</td><td align="left"><input name="busiLine" id="busiLine" value="0064"></td>
			</tr>
			<tr>
				<td align="right">dataSum(数据总量):</td><td align="left"><input name="dataSum" id="dataSum" value="1000000"></td>
			</tr>
			<tr>
				<td align="right">fileDataSum(文件记录最大数):</td><td align="left"><input name="fileDataSum" id="fileDataSum" value="1000000"></td>
			</tr>
			<tr>
				<td align="right">fileSum(需生成文件总数，默认auto自动计算):</td><td align="left"><input name="fileSum" id="fileSum" value="auto"></td>
			</tr>
			<tr>
				<td align="right"></td><td align="left"><input type="submit" value="提交">&nbsp;<input type="reset" value="重置"></td>
			</tr>
		</table>
	</form>
</body>
</html>