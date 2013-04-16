<#assign s=JspTaglibs["http://www.springframework.org/tags"] />
<#assign c=JspTaglibs["http://www.cheoeumin.com/tags/ui"] />
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>check</title>
	<link rel="shortcut-icon" type="image/x-icon" href="/favicon.ico" />
	<link rel="stylesheet" type="text/css" href="/css/cei.tree.css" media="all" />
	<script src="https://raw.github.com/douglascrockford/JSON-js/master/json2.js"></script>
	<script src="/js/cei.jquery.js"></script>
	<script src="/js/cei.jquery.ui.js"></script>
	<script src="/js/cei/tree.js"></script>
	<script src="/js/cei/cei.js"></script>
<body>
	<@s.message code="confirm.execute" />
	<@c.tree items=tree id='check' />
	${date?string("yyyy-MM-dd HH:mm:ss.SSS")}
	
	<script type="text/javascript">
		var tree = new Tree( "#check" );
	</script>
</body>
</html>