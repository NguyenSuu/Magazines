var fashion=fashion||{};
var idPosts=null;
fashion.showModal=function(id){
    $('#myModal').modal('show');
    idPosts = id;
    $.ajax({
        url:`http://localhost:8080/message/`+ idPosts,
        method:`GET`,
        dataType:`json`,
        contentType:`application/json`,
        success:function (data) {
            $('#infomation').html("");
            const result = data.map((elem) => ({
                ...elem,
                timeDisplay: `${elem.time.year}-${elem.time.monthValue}-${elem.time.dayOfMonth}`
            }))
            $.each(result,function(index,value){
                $('#infomation').append(
                    '<tr>'+
                    '<td>'+value.writer+'</td>'+
                    '<td>'+value.email+'</td>'+
                    '<td>'+value.timeDisplay+'</td>'+
                    '<td>'+value.info+'</td>'+
                    '</tr>'
                )
            })
        }
    })
}
fashion.reply=function(){
    var message={};
    message.id_post=idPosts;
    message.writer="admin";
    message.info=$("#message-text").val();
    message.email="admin@gmail.com";
    $.ajax({
        url:`http://localhost:8080/message/add`,
        method: `POST`,
        dataType:`json`,
        contentType: `application/json`,
        data:JSON.stringify(message),
        success:function () {
            $('#message-text').val("");
            fashion.showModal(idPosts);
        }
    })
};

fashion.init=function(){
};

$(document).ready(function () {
    fashion.init();
});