


function post() {
    var questionId =$("#question_id").val();
    var content = $("#comment_content").val();

    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success:function (reponse) {
            if(reponse.code==200){
                $("#comment-section").hidden;
            }else {
                if (reponse.code == 2003) {
                    var isAccepted = confirm(reponse.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=12b7415968a1fbb1aecd&redirect_uri=http://localhost:8088/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                } else {
                    alert(reponse.message);
                }
            }
        },
        dataType:"json"
    });
}