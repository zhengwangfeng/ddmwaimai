<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">

  <head>
      <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">	 
    <title></title>
    
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
  <link rel="stylesheet" type="text/css" href="${webRoot}/plug-in/webuploader/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${webRoot}/plug-in/webuploader/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" href="${webRoot}/plug-in/webuploader/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${webRoot}/plug-in/webuploader/css/syntax.css">
    <link rel="stylesheet" type="text/css" href="${webRoot}/plug-in/webuploader/css/style.css">
    
    <link rel="stylesheet" type="text/css" href="${webRoot}/plug-in/webuploader/css/webuploader.css">
    
    <link rel="stylesheet" type="text/css" href="${webRoot}/plug-in/webuploader/css/demo.css">
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript" src="${webRoot}/plug-in/layer/layer.js"></script>
  </head>
  
  <body style="height: 300px;">
  <input type="hidden" id="publineId" name="publineId" value="${publineId}"/>
 <div class="page-container">
<div style="height:300px;" id="uploader" class="wu-example">
    <div class="queueList">
        <div id="dndArea" class="placeholder">
            <div id="filePicker"></div>
            <p>或将照片拖到这里，单次最多可选5张</p>
        </div>
    </div>
    <div class="statusBar" style="display:none;">
        <div class="progress">
            <span class="text">0%</span>
            <span class="percentage"></span>
        </div><div class="info"></div>
        <div class="btns">
            <div id="filePicker2"></div><div class="uploadBtn">开始上传</div>
        </div>
    </div>
</div>

    </div>

 <script type="text/javascript">
    // 添加全局站点信息
    var BASE_URL = '${webRoot}';
    var FILENAME = '${fileName}'
    </script>
 <%-- <script type="text/javascript" src="${webRoot}/plug-in/webuploader/js/jquery-1.10.2.min.js"></script> --%>
 <script type="text/javascript" src="${webRoot}/plug-in/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="${webRoot}/plug-in/webuploader/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${webRoot}/plug-in/webuploader/js/global.js"></script>
    
        <script type="text/javascript" src="${webRoot}/plug-in/webuploader/js/webuploader.min.js"></script>
    
        <script type="text/javascript" src="${webRoot}/plug-in/webuploader/js/jimmyUpload.js"></script>
  </body>
</html>
