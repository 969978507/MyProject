<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/script/jquery.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<title>Insert title here</title>
</head>
<body>
	HELLO MY FRIEND!
	<script type="text/javascript">
		wx.config({
		    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: 'wx930dc276d21313e0', // 必填，公众号的唯一标识
		    timestamp: '', //必填，生成签名的时间戳
		    nonceStr: '', // 必填，生成签名的随机串
		    signature: '',// 必填，签名
		    jsApiList: [
		    	"chooseImage",
		    	"previewImage",
		    	"uploadImage",
		    	"downloadImage",
		    	"getLocalImgData"
		    ] // 必填，需要使用的JS接口列表
		});
		function upload() {
			wx.chooseImage({
	           count:5,//设置一次能选择的图片的数量 
	           sizeType:['original','compressed'],//指定是原图还是压缩,默认二者都有
	           sourceType:['album','camera'],//可以指定来源是相册还是相机,默认二者都有
	           success:function(res){   //微信返回了一个资源对象 
	        	   var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
	            }
	       });
		}
		function uploadImg(localIds) {
			wx.uploadImage({
				localId: localIds, // 需要上传的图片的本地ID，由chooseImage接口获得
				isShowProgressTips: 1, // 默认为1，显示进度提示
				success: function (res) {
					var serverId = res.serverId; // 返回图片的服务器端ID
				}
			});
		}
	</script>
</body>
</html>