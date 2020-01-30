function post() {
    //发送json数据
    let questionId = $("#question_id").val();//获取问题id
    let commentContent = $("#comment_content").val();//货物评论的内容
    //如果内容为空
    if (!commentContent) {
        alert("内容不能为空");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": questionId,
            "content": commentContent,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200) {
                //如果成功的话,就重新加载页面,然后清除输入框
                location.reload();
                $("#comment_content").val("");
            } else {
                alert(response.message);
            }
        },
        dataType: "json"
    });
}

//展开二级评论
function collapseComments(e) {
    let id = e.getAttribute("data-id");
    let comments = $("#comment-" + id);
    //获取二级评论的展开状态
    let s = e.getAttribute("data-collapse");
    if (s) {
        //如果存在,则移除这个状态
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        //否则,添加这个状态
        comments.addClass("in");//添加这个类
        e.setAttribute("data-collapse", "in");//标记已经展开
        e.classList.add("active");
    }
}

