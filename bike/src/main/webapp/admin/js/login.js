function submit() {
    document.onkeydown = function(e) {
        var ev = document.all ? window.event : e;
        if (ev.keyCode == 13) {
            checkPassword();
        }
    };
}

function checkPassword() {
    var username = $("#username").val();
    var password = $("#password").val();
    if (username == '') {
        alert("用户名不能为空");
        return false;
    }
    if (password == '') {
        alert("密码不能为空");
        return false;
    }
    $.ajax({
        type : "post",
        url : '/login/checkPassword.htm?username=' + username + '&password='
        + password,
        dataType : "text",
        success : function(data) {
            var obj = eval("(" + data + ")");
            var retCode = obj.ret_code;
            var retInfor = obj.ret_info;
            if (retCode == 0) {
                window.location.href = "/login/super.htm?";
            } else {
                alert(retInfor);
            }

        },
        error : function() {

        }
    });
}