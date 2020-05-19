
var contact = contact || {};
contact.showContact = function () {
    $.ajax({
        url: `http://localhost:8080/contact/`,
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
                if (value.checked) {
                    $('#list').append(
                        `<tr>
                            <th>${index+1}</th>
                            <td> ${value.writer}</td>
                            <td> ${value.info}</td>
                            <td> ${value.email}</td>
                            <td> ${value.timeDisplay}</td>
                            <th>
                            <i class='fa fa-check'></i></th>
                            <th>
                            <a class='btn btn-primary' id='reply' onclick="contact.showForm('${value.email}')">Reply</a>
                            </th>
                         </tr>
                        `
                    );
                } else {
                    $('#list').append(
                        `
                        <tr>
                            <th>${index+1}</th>
                            <td> ${value.writer}</td>
                            <td> ${value.info}</td>
                            <td> ${value.email}</td>
                            <td> ${value.timeDisplay}</td>
                            <th>
                            <input type="checkbox" id="check" onclick="contact.click(this)"></th>
                            <th><a class='btn btn-primary' id='reply' onclick="contact.showForm('${value.email}')">Reply</a>
                            <a class="btn btn-primary" id="enforce" onclick="contact.change(${value.id})" style="visibility: hidden">Enforce</a></th>
                         </tr>
                        `
                    )

                }
            })
        }
    })
}
contact.showForm=function(email){
    $('#myModal').modal('show');
    $('#email').val(email);
}
contact.replyMail=function(){
    let message={};
    message.email=$('#email').val();
    message.info=$('#message').val();
    message.subject=$('#subject').val();
    $.ajax({
        url:`http://localhost:8080/sendmail`,
        method:`POST`,
        dataType:`json`,
        contentType:`application/json`,
        data:JSON.stringify(message),
        success:function () {
            alert("Sent successfully.");
            $('#myModal').modal('hide');
        }
    })
}
contact.click=function(cb){

    $("#enforce").css("visibility", cb.checked ? "visible" : "hidden")

}
contact.change = function (id) {
    var contactOject = {};
    contactOject.checked = $('#check').is(":checked");
    // console.log(dataValue);
    // console.log(dataValue.attributes["data-checked"].value);
    $.ajax({
        url: `http://localhost:8080/contact/` +id,
        method: `PUT`,
        dataType: `json`,
        contentType: `application/json`,
        data: JSON.stringify(contactOject),
        success: function () {
            contact.showContact();
        }
    })

}

contact.init = function () {
    contact.showContact();

};
$(document).ready(function () {
    contact.init();
})