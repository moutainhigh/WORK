<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
</head>
<script type="text/javascript">

</script>

<body>
    <div id="reconiliationDiv">
		<table class="cus_table">
		<tr style="height:25px;">
			<td colspan="4">第2期</td>
		</tr>
	   <tr>
	       <td class="align_right" >逾期记息开始日：</td>
	       <td>
			 <input id="occ_name" class="easyui-validatebox" name="cusPerson.occName" style="width:220px;"  />
	       </td>
	       <td class="align_right">逾期记息结束日：</td>
	       <td>
	       	  <input id="occ_name" class="easyui-validatebox" name="cusPerson.occName" style="width:220px;"  />
   			  <input type="button" class="cus_save_btn" name="save" value="保     存" onclick="saveBaseInfo();"/>&nbsp;&nbsp;&nbsp;
		   </td>
	   </tr>
	   <tr>
	       <td class="align_right" >假期收息：</td>
	       <td>
			 1000
	       </td>
	       <td class="align_right">逾期记息结束日：</td>
	       <td>
	       	  <input id="occ_name" class="easyui-validatebox" name="cusPerson.occName" style="width:220px;"  />
   			  <input type="button" class="cus_save_btn" name="save" value="保     存" onclick="saveBaseInfo();"/>&nbsp;&nbsp;&nbsp;
		   </td>
	   </tr>
	   <tr>
	       <td class="align_right" colspan="4">
			假日收息：1,000.00 元
			未减免
			减免设定
			</td>
	     
	   </tr>
	</div>  
      <div style="text-align: center;"><input type="button" class="cus_save_btn" name="save" value="保     存" onclick="saveBaseInfo();"/>&nbsp;&nbsp;&nbsp; <input type="button" class="cus_save_btn" name="cancel" value="取     消" onclick="cancelCusBusiness();"/></div>
 </div>	 

</body>
</html>
