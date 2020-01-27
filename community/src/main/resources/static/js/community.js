function post() {
    //发送json数据
    let questionId = $("#question_id").val();//获取问题id
    let commentContent = $("#comment_content").val();//货物评论的内容
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:'application/json',
        data:JSON.stringify({
            "parentId":questionId,
            "content":commentContent,
            "type":1
        }),
        success:function (response){
            if(response.code==200){
                // $("#comment_content").hide();
                //回复完成后,重新跳转回/question?id页面
            }else{
                alert(response.message);
            }
        },
        dataType:"json"
    });
}