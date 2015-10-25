<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,user-scalable=0">
<title>dispatcher.jsp</title>
</head>
<body>

1.是不是直接header("Location: http://new1.com/handle?key1=111&key2=222")?

2.还有如何获取所有_REQUEST的字符串, 就是得到原来url的"type=aaa&key1=111&key2=222"?

3.我原来的url是个 http post 操作 后面不但有req参数还有设置的setEntity数据... 怎么才能重定向时也带上这些数据呢?

1.是
注意调用header("Location: http://new1.com/handle?key1=111&key2=222")之后需要加exit(0)之类的退出代码，不然后面的php代码还会执行。

2.用$_SERVER['QUERY_STRING']获取。

3.重定向无法post,只能放url后面带过去。

</body>
</html>