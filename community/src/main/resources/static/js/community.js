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
                //如果成功的话,就重新加载页面,然后清除输入框
                location.reload();
                $("#comment_content").val("");
            }else{
                alert(response.message);
            }
        },
        dataType:"json"
    });
}