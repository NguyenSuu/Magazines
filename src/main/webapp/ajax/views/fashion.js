var fashion = fashion || {};

fashion.info = function () {
    $.ajax({
        url: `http://localhost:8080/fashion/`,
        method: `GET`,
        dataType: `json`,
        contentType: `application/json`,
        success: function (data) {
            $('#listF').html("");
            const result = data.map((elem) => ({
                ...elem,
                timeDisplay: `${elem.time.year}-${elem.time.monthValue}-${elem.time.dayOfMonth}`
            }))
            $.each(result, function (index, value) {
                $('#listF').append(
                    '<div class="post_item post_h_large">'
                    + '<div class="row">'
                    + '<div class="col-lg-5">'
                    + '<div class="post_image"><img src="/images/' + value.image + '" alt="" width="240px" height="280px"></div>'
                    + '</div>'
                    + '<div class="col-lg-7">'
                    + '<div class="post_content">'
                    + '<div class="post_category cat_world"><a href="/fashion">fashion</a></div>'
                    + '<div class="post_title"><a href="/fashion/' + value.id + '">' + value.title + '</a></div>'
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


fashion.init = function () {
    fashion.info();
};

$(document).ready(function () {
    fashion.init();
});