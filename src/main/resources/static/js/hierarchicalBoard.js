var token = $("meta[name='_csrf']").attr('content');
var header = $("meta[name='_csrf_header']").attr('content');

$(document).ready(function(){
    //board Insert
    $("#insertProc").on('click', function(){
        var form = $("#insertBoardFrm");

        console.log("insert btn click");

        form.submit();
    })

    //board Modify
    $("#modifyProc").on('click', function(){
        var form = $("#insertBoardFrm");

        console.log("modify btn click");

        // form.attr('action', '/board/boardModify');
        form.submit();
    })

    //board ReplyInsert
    $("#replyInsertProc").on('click', function(){
        var form = $("#insertBoardFrm");

        console.log("replyInsertProc btn click");

        form.submit();
    })

    $("#keywordInput").keydown(function(key){
        if(key.keyCode == 13){
            $("#searchBtn").click();
        }
    })
})



$(function(){
    $("#reply").click(function(){
        console.log("board Reply");
        var boardNo = $("#boardNo").val();

        console.log("boardNo : " + boardNo);

        location.href = '/board/boardReply/' + boardNo;

    })
})

$(function(){
    $("#deleteBoard").click(function(){
        console.log("delete board");

        var boardNo = $("#boardNo").val();

        $.ajax({
            url: '/board/boardDelete/' + boardNo,
            method: 'delete',
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                if(data == 1){
                    console.log("delete success");
                    location.href = "/board/boardList";
                }else if(data == -1){
                    console.log("delete fail");
                    alert("오류가 발생했습니다.\n 문제가 계속되면 관리자에게 문의 부탁드립니다.");
                }else if(data == 0){
                    console.log("delete fail");
                    alert("처리할 수 없는 요청입니다.\n 문제가 계속되면 관리자에게 문의 부탁드립니다.");
                }

            },
            error: function(request, status, error){
                alert("code : " + request.status + "\n"
                    + "message : " + request.responseText + "\n"
                    + "error : " + error);
            }
        })

    })
})

$(function(){
    $("#modify").click(function(){
        console.log("modify");
        var boardNo = $("#boardNo").val();

        console.log("boardNo : " + boardNo);

        location.href = '/board/boardModify/' + boardNo;
    })
})