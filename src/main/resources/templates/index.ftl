<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
    <script
            src="https://code.jquery.com/jquery-2.2.4.min.js"
            integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="
            crossorigin="anonymous"></script>
</head>

<style>
    .accordion {
        background-color: #eee;
        color: #444;
        cursor: pointer;
        padding: 18px;
        width: 100%;
        border: none;
        text-align: left;
        outline: none;
        font-size: 15px;
        transition: 0.4s;
    }

    .active, .accordion:hover {
        background-color: #ccc;
    }

    .panel {
        padding: 0 18px;
        display: none;
        background-color: white;
        overflow: hidden;
    }

    .btn {
        border: 2px solid black;
        background-color: white;
        color: black;
        padding: 14px 28px;
        font-size: 16px;
        cursor: pointer;
    }

    .success {
        border-color: #4CAF50;
        color: green;
    }

    .success:hover {
        background-color: #4CAF50;
        color: white;
    }
</style>
<body>

<h2 class="hello-title">Conference Room Calendar</h2>
<label for="current-date">Current Date</label>
<input type="date" id="current-date" value="${current}">

<button class="accordion">make a reservation</button>
<div class="panel">
    <table>
        <tr>
            <td>
                <label for="start-time">Start Time</label>
            </td>
            <td>
                <input type="time" id="start-time" name="startTime">
            </td>
        </tr>
        <tr>
            <td>
                <label for="end-time">End Time</label>
            </td>
            <td>
                <input type="time" id="end-time" name="endTime" required pattern="[0-9]{2}:[30|00]">
            </td>
            <td>
                <span class="validity"></span>
            </td>
        </tr>
        <tr>
            <td>
                <label>Room</label>
            </td>
            <td>
                <select id="room-id" name="roomId">
                    <#list rooms as room>
                        <option value="${room.id}">${room.name}</option>
                    </#list>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <label>User</label>
            </td>
            <td>
                <select id="user-id" name="userId">
                    <#list users as user>
                        <option value="${user.id}">${user.name}</option>
                    </#list>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <label>Repeat</label>
            </td>
            <td>
                <input type="number" id="repeat-count" name="repeatCount" value="0">
            </td>
        </tr>
    </table>

    <button class="btn success" type="submit" id="submit">submit</button>
</div>

<table style="border: 1px solid">
<#if calender??>
    <tr>
    <#list calender.header as roomName>
        <th>${roomName}</th>
    </#list>
    </tr>

    <#list calender.rows as row>
    <tr>
        <#list row.cells as cell>
            <td style="border: 1px solid aqua">${cell.value}</td>
        </#list>
    </tr>
    </#list>


</#if>
</table>
<script>

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
                        201: function (response) {
                            alert('you have a reservation.');
                            console.log(response);
                            location.reload();
                        },
                        400: function (response) {
                            alert('could not allow empty field.');
                            console.log(response);

                        },
                        409: function (response) {
                            alert('entered conflict data. please try again.');
                            console.log(response);
                        },
                        422: function (response) {
                            alert(response.responseText);
                            console.log(response);
                        },
                        500: function (response) {
                            alert('logic error.');
                            console.log(response);
                        }
                    }

                })
            });
</script>

<#--<script src="/index.js"></script>-->
</body>
</html>