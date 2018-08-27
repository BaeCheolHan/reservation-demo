
// init day picker
let dateInputBox = document.getElementById('current-date');
dateInputBox.addEventListener("change", function (ev) {
    window.location.href = "/?date=" + dateInputBox.value;
});

// init accordion
let acc = document.getElementsByClassName("accordion");
let i;
for (i = 0; i < acc.length; i++) {
    acc[i].addEventListener("click", function () {
        this.classList.toggle("active");
        let panel = this.nextElementSibling;
        if (panel.style.display === "block") {
            panel.style.display = "none";
        } else {
            panel.style.display = "block";
        }
    });
}

// init submit
let submitButton = document.getElementById('submit');
submitButton.addEventListener("click",
    // function (ev) {


    function () {
        let params = {
            startTime: document.getElementById('start-time').value,
            endTime: document.getElementById('end-time').value,
            day: document.getElementById('current-date').value,
            roomId: document.getElementById('room-id').value,
            userId: document.getElementById('user-id').value,
            repeatCount: document.getElementById('repeat-count').value
        };

        $.ajax({
            dataType: 'json',
            contentType: 'application/json',
            type: 'POST',
            url: '/api/reservation',
            data: JSON.stringify(params),
            statusCode: {
                409: function () {
                    alert('entered conflict data. please try again.');
                },
                201: function () {
                    alert('you have a reservation.')
                }
            }

        })
// const http = new XMLHttpRequest();
// http.open('POST', '/api/reservation');
// http.setRequestHeader('Content-Type', 'application/json');
// http.send(JSON.stringify(params));
//
// http.onload = function () {
//     console.log(http.responseText);
//     console.log(http.response.statusCode);
//     console.log(http.response.statusMessage);
//     console.log(http.response.statusText);
// }
    })
;