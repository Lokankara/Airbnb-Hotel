function updateStatus() {
    closeModal();
    const selectedValue = document.getElementById("status").value;
    const id = modalId.value;
    const url = `/rooms/${id}?roomStatus=${selectedValue}`;
    sendPatch(url);
}

function sendPatch(url) {
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({}),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const contentType = response.headers.get('Content-Type');
            return contentType && contentType.includes('application/json') ? response.json() : response.text();
        })
        .then(data => {
            window.location.reload();
        })
        .catch(error => {
            console.error('Error:', error);
        });
}


function handleKeyPressGuest(event) {
    if (event.key === 'Enter') {
        findGuests();
    }
}

function handleKeyPress(event) {
    if (event.key === 'Enter') {
        findRooms();
    }
}

function findGuests() {
    const name = document.getElementById("name").value;
    const checkIn = document.getElementById("checkIn").value;
    const checkOut = document.getElementById("checkOut").value;
    const departure = document.getElementById("departure").value;
    const gender = document.getElementById("gender").value;
    const guestStatus = document.getElementById("guestStatus").value;
    const params = [];
    if (name) params.push("name=" + name);
    if (checkIn) params.push("checkIn=" + checkIn);
    if (departure) params.push("departure=" + departure);
    if (checkOut) params.push("checkOut=" + checkOut);
    if (gender) params.push("gender=" + gender);
    if (guestStatus) params.push("guestStatus=" + guestStatus);
    const query = params.join("&");
    window.location.href = "/guests/search" + (query ? "?" + query : "");
}

function findRooms() {
    const roomType = document.getElementById("roomType").value;
    const roomStatus = document.getElementById("roomStatus").value;
    const capacityInput = document.getElementById("capacity").value;
    const capacity = !isNaN(capacityInput) ? parseInt(capacityInput) : 0;
    const params = [];
    if (roomType) params.push("roomType=" + roomType);
    if (roomStatus) params.push("roomStatus=" + roomStatus);
    if (capacity > 0) params.push("capacity=" + capacity);
    const query = params.join("&");
    window.location.href = "/available" + (query ? "?" + query : "");
}

function getRooms() {
    window.location.href = "/guests"; //TODO
}

function booking() {
    const params = [];
    if (modalId) params.push("id=" + modalId.value);
    if (modalHeader) params.push("roomType=" + modalHeader.textContent);
    if (modalStatus) params.push("roomStatus=" + modalStatus.value);
    if (modalCapacity) params.push("capacity=" + parseInt(modalCapacity.textContent));
    const query = params.join("&");
    window.location.href = "/checkin" + (query ? "?" + query : "");
}
