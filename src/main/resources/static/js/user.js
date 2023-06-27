$(function(){
    $("#join").click(function(){
        var token = $("meta[name='_csrf']").attr('content');
        var header = $("meta[name='_csrf_header']").attr('content');
        var form = $('#joinForm')[0];

        var formData = new FormData(form);

        $.ajaxSettings.traditional = true;
        $.ajax({
            url: '/member/joinProc',
            contentType: false,
            processData: false,
            cache: false,
            type: 'post',
            data: formData,
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                if(data == 0){
                    alert("가입 실패");
                }else{
                    alert("가입 되었습니다.")
                    location.href='/member/loginForm';
                }
                // console.log("ok");
            },
            error: function(request, status, error){
                alert("code : " + request.status + "\n"
                    + "message : " + request.responseText
                    + "\n" + "error : " + error);
            }
        })
    })
});

$(function(){
    $('#UserLogin').click(function(){
        console.log("login");
        $('#LoginForm').submit();
    })
});