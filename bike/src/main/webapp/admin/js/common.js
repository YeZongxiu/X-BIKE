function gotoUrl(url){
    $.ajax({
        type : "get",
        url : '/login/error.htm',
        dataType : "text",
        success : function(data) {
            var obj = data;
            if(obj==0){
                $("#content",parent.document).attr("src",url);
            }else{
                window.parent.location.href="/login/index.htm";
            }

        },
        error : function() {
            alert("失败");
        }
    });
}