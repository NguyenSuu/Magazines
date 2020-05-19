var technology = technology || {};

technology.info = function () {
    $.ajax({
        url: `http://localhost:8080/technology/`,
        method: `GET`,
        dataType: `json`,
        contentType: `application/json`,
        success: function (data) {
            $('#list').html("");

            const result = data.map((elem) => ({
                ...elem,
                timeDisplay: `${elem.time.year}-${elem.time.monthValue}-${elem.time.dayOfMonth}`
            }))
            $.each(result, function (index, value) {
                $('#list').append(
                    '<div class="post_item post_h_large">'
                    + '<div class="row">'
                    + '<div class="col-lg-5">'
                    + '<div class="post_image"><img src="/images/' + value.image + '" alt="" width="240px" height="280px"></div>'
                    + '</div>'
                    + '<div class="col-lg-7">'
                    + '<div class="post_content">'
                    + '<div class="post_category cat_technology"><a href="/technology">technology</a></div>'
                    + '<div class="post_title"><a href="/technology/' + value.id + '">' + value.title + '</a></div>'
                    + '<div class="post_info d-flex flex-row align-items-center justify-content-start">'
                    + '<div class="post_author d-flex flex-row align-items-center justify-content-start">'
                    + '<div><div class="post_author_image"><img src="/template/images/author_1.jpg" alt=""></div></div>'
                    + '<div class="post_author_name"><a href="#">' + value.author + '</a></div>'
                    + '</div>'
                    + '<div class="post_date">' + value.timeDisplay + '</div>'
                    + '</div>'
                    + '<div class="post_text">'
                    + '<p>' + value.summary + '</p>'
                    + '</div>'
                    + '</div>'
                    + '</div>'
                    + '</div>'
                    + '</div>'
                );
            });
        }
    });
};
technology.posts = function () {
    let id = document.getElementById('idPosts').value;
    $.ajax({
        url: `http://localhost:8080/posts/` + id,
        method: `GET`,
        dataType: `json`,
        textContent: `application/json`,
        success: function (data) {
            $('#main').html("");

            data.timeDisplay = data.time.year + "-" + data.time.monthValue + "-" + data.time.dayOfMonth;
            $('#main').append(
                '<div class="post_image"><img src="/images/' + data.image + '" alt="" height="550px" width="690px"></div>' +
                '<div class="post_content">' +
                '<div class="post_category cat_technology"><a href="/' + data.category.name + '">' + data.category.name + '</a>' +
                '</div>' +
                '<div class="post_title"><p>' + data.title + '</p></div>' +
                '<div class="post_info d-flex flex-row align-items-center justify-content-start">' +
                '<div class="post_author d-flex flex-row align-items-center justify-content-start">' +
                '<div>' +
                '<div class="post_author_image"><img src="/template/images/author_1.jpg" alt=""></div>' +
                '</div>' +
                '<div class="post_author_name"><p>' + data.author + '</p></div>' +
                '</div>' +
                '<div class="post_date" >' + data.timeDisplay + '</div>' +
                '<div class="post_comments_num ml-auto"><a></a></div>' +
                '</div>' +
                '<div class="post_text">' +
                '<p>' + data.description + '</p>' +
                '</div>' +
                '</div>');

            //)})
        }
    })
}
technology.message = function () {
    let id = document.getElementById('idPosts').value;
    $.ajax({
        url: `http://localhost:8080/message/` + id,
        method: `GET`,
        dataType: `json`,
        textContent: `application/json`,
        success: function (data) {
            $('#message').html("");
            const result = data.map((elem) => ({
                ...elem,
                timeDisplay: `${elem.time.year}-${elem.time.monthValue}-${elem.time.dayOfMonth}`
            }))
            $.each(result, function (index, value) {
                $('#message').append(
                    `<li class="comment">
                    <div class="comment_info d-flex flex-row align-items-center justify-content-start">
                    <div>
                    <div class="comment_image"><img src="/template/images/comment_1.jpg" alt=""></div>
                    </div>
                    <div class="comment_author" style="text-align: left;width: 300px"><h4>${value.writer}</h4></div>
                    <div style="text-align: right;width: 300px">${value.timeDisplay}</div>
                    </div>
                    <div class="comment_content">
                    <div class="comment_text">
                    <p>${value.info}</p>
                    </div>
                    </div>
                    </li>`
                )
            });
        }
    })
};
technology.saveMess = function () {
    var message = {};
    message.id_post = document.getElementById('idPosts').value;
    message.info = $('#info').val();
    message.writer = $('#writer').val();
    message.email = $('#email').val();
    console.log(message);
    $.ajax({
        url: `http://localhost:8080/message/add`,
        method: `POST`,
        dataType: `json`,
        contentType: `application/json`,
        data: JSON.stringify(message),
        success: function () {
            console.log("Done!");
            $('#info').val("");
            $('#writer').val("");
            $('#email').val("");
            technology.message();
        }

    })

};
technology.init = function () {
    technology.info();
    technology.message();
    technology.posts();
};

$(document).ready(function () {
    technology.init();
});