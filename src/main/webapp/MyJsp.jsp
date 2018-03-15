<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!DOCTYPE html> 
<html> 
<head> 
<meta charset="utf-8" > 
<title>JS实现倒计时(天数,时,分,秒)</title> 
  
</head> 
<body onload="leftTimer()"> 
  
<h2>剩余时间:</h2> 
<div id="timer"></div> 
</body> 
</html>
<script language="javascript" type="text/javascript">  
function leftTimer(year,month,day,hour,minute,second){ 
  var leftTime = (new Date(year,month-1,day,hour,minute,second)) - (new Date()); //计算剩余的毫秒数 
  var days = parseInt(leftTime / 1000 / 60 / 60 / 24 , 10); //计算剩余的天数 
  var hours = parseInt(leftTime / 1000 / 60 / 60 % 24 , 10); //计算剩余的小时 
  var minutes = parseInt(leftTime / 1000 / 60 % 60, 10);//计算剩余的分钟 
  var seconds = parseInt(leftTime / 1000 % 60, 10);//计算剩余的秒数 
  days = checkTime(days); 
  hours = checkTime(hours); 
  minutes = checkTime(minutes); 
  seconds = checkTime(seconds); 
  setInterval("leftTimer(2016,11,11,11,11,11)",1000); 
  document.getElementById("timer").innerHTML = days+"天" + hours+"小时" + minutes+"分"+seconds+"秒";  
} 
function checkTime(i){ //将0-9的数字前面加上0，例1变为01 
  if(i<10) 
  { 
    i = "0" + i; 
  } 
  return i; 
} 
  
</script>
