var files = {};
var previewIndex = 0;
var deleteFiles = {};
var step = 0;
var deleteNo = 0;
var token = $("meta[name='_csrf']").attr('content');
var header = $("meta[name='_csrf_header']").attr('content');

$(document).ready(function(){
    var imageNo = $("#modifyImageNo").val();

    console.log("imageNo : " + imageNo);

    if(imageNo != undefined){
        $.getJSON("/imageBoard/imageList", {imageNo: imageNo}, function(arr){
            $(arr).each(function(i, attach){
                $("#preview").append(
                    "<div class=\"preview-box\" value=\"" + attach.imageStep + "\">" +
                    "<img class=\"thumbnail\" id=\"imgName\" src=\"/img/" + attach.imageName + "\"\/>" +
                    "<p>" + attach.oldName + "</p>" +
                    "<a href=\"#\" value=\"" + attach.imageStep + "\" onclick=\"deleteOldPreview(this)\">" +
                    "삭제" + "</a>" +
                    "</div>"
                );
                step = attach.imageStep;
            });
        }).fail(function(err){
            alert(err.responseText);
        })
    }

    $("#imageModify").on('click', function(){
        var form = $("#uploadForm")[0];
        var formData = new FormData(form);

        for(var index = 0; index < Object.keys(files).length; index++){
            formData.append('files', files[index]);
        }

        for(var index = 0; index < Object.keys(deleteFiles).length; index++){
            formData.append('deleteFiles', deleteFiles[index]);
        }

        console.log("files size : " + formData.getAll('files').length);
        console.log("deletefiles size : " + formData.getAll('deleteFiles').length);


        $.ajax({
            type: 'put',
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            url: '/imageBoard/imageModify',
            data: formData,
            beforeSend: function(xhr){
              xhr.setRequestHeader(header, token);
            },
            success: function(result){
                if(result == -1){
                    alert("오류가 발생했습니다 다시 시도해주세요.\n 문제가 계속되면 관리자에게 문의해주세요.");
                }else if(result == 2){
                    alert("파일 사이즈를 초과했습니다.");
                }else{
                    location.href='/imageBoard/imageBoardDetail/' + result;
                }
            },
            error : function(request, status, error){
                alert("code : "+request.status + "\n"
                    + "message : "+request.responseText + "\n"
                    + "error : "+error);
            }
        })
    });

    $("#imageInsert").on('click', function(){
        var form = $("#uploadForm")[0];
        var formData = new FormData(form);

        console.log("files.length : " + Object.keys(files).length);

        for(var index = 0; index < Object.keys(files).length; index++){
            formData.append('files', files[index]);
        }

        $.ajax({
            type: 'post',
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            url: '/imageBoard/imageInsert',
            data: formData,
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(result){
                if(result == -1) {
                    alert("오류가 발생했습니다 다시 시도해주세요.\n 문제가 계속되면 관리자에게 문의해주세요.");
                }else if(result == 2){
                    alert("파일 사이즈를 초과했습니다.");
                }else{
                    location.href='/imageBoard/imageBoardDetail/' + result;
                }
            },
            error : function(request, status, error){
                alert("code : "+request.status + "\n"
                    + "message : "+request.responseText + "\n"
                    + "error : "+error);
            }
        })
    });

    $(".attach input[type=file]").change(function(){
        console.log("attach input");
        addPreview($(this));
    });
});

function addPreview(input){
    console.log("addPreview");
    if(input[0].files.length <= (5 - ($('.preview-box').length))) {
        for(var fileIndex = 0; fileIndex < input[0].files.length; fileIndex++){
            var file = input[0].files[fileIndex];
            if(validation(file.name))
                continue;

            setPreviewForm(file);
        }
    }else {
        alert('5장만 업로드 가능합니다.');
    }
}

function setPreviewForm(file, img){
    console.log("setPreviewForm");
    var reader = new FileReader();
    reader.onload = function(img){
        var imgNum = ++step;

        $("#preview").append(
                "<div class=\"preview-box\" id=\"newImg\" value=\"" + imgNum +"\">" +
                "<img class=\"thumbnail\" id=\"imgName\" src=\"" + img.target.result + "\"\/>" +
                "<p>" + file.name + "</p>" +
                "<a href=\"#\" value=\"" + imgNum + "\" onclick=\"deletePreview(this)\">" +
                "삭제" + "</a>"
                + "</div>"
        );
        files[previewIndex] = file;
        previewIndex++;
    };
    reader.readAsDataURL(file);
}

function deleteOldPreview(obj){
    console.log("deleteOldPreview");
    var imgNum = obj.attributes['value'].value;
    var imgName = $("#preview .preview-box[value=" + imgNum +"] .thumbnail").attr('src');
    var idx = imgName.lastIndexOf('/');
    var deleteImg = imgName.substring(idx + 1);

    deleteFiles[deleteNo] = deleteImg;
    deleteNo++;

    $("#preview .preview-box[value=" + imgNum + "]").remove();
}

function deletePreview(obj){
    console.log("deletePreview");
    var imgNum = obj.attributes['value'].value;
    delete files[imgNum];

    $("#preview .preview-box[value=]" + imgNum + "]").remove();
}

function validation(fileName){
    console.log("validation");
    fileName = fileName + "";
    var fileNameExtensionIndex = fileName.lastIndexOf('.') + 1;
    var fileNameExtension = fileName.toLowerCase().substring(
        fileNameExtensionIndex, fileName.length);

    console.log("fileNameExtension : " + fileNameExtension);

    if(!((fileNameExtension === 'jpg') || (fileNameExtension === 'gif') || (fileNameExtension === 'png') || (fileNameExtension === 'jpeg'))){
        alert('jpg, gif, png 확장자만 업로드가 가능합니다.');
        return true;
    }else {
        return false;
    }
}

$(function(){
    $("#modify").click(function(){
        var imageNo = $("#imageNo").val();

        location.href="/imageBoard/imageBoardModify/"+imageNo;
    })
})

$(function(){
    $("#delete").click(function(){
        var delData = {
            imageNo : $("#imageNo").val()
        };
        console.log("imageNo : " + delData.toString());

        delData = JSON.stringify(delData);

        $.ajax({
            url: '/imageBoard/imageDelete',
            method: 'delete',
            data: delData,
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(result){
                if(result == -1){
                    alert("오류가 발생했습니다 다시 시도해주세요.\n 문제가 계속되면 관리자에게 문의해주세요.");
                }else if(result == 1){
                    location.href='/imageBoard/imageBoardList';
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