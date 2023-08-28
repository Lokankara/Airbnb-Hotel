document.addEventListener('DOMContentLoaded', function () {
    const modeSwitch = document.querySelector('.mode-switch');

    modeSwitch.addEventListener('click', function () {
        document.documentElement.classList.toggle('dark');
    });
});

function openModal() {
    let modal = document.querySelector('#modal-window');
    modal.classList.add("showModal");
}

function closeModal() {
    let m = document.querySelector('#modal-window');
    m.classList.remove("showModal");
}

document.getElementsByClassName('.mode-switch').onclick = function () {
    document.body.classList.toggle('dark');
}

const cardItems = document.querySelectorAll('.main-card');
const modalHeader = document.querySelector('.modalHeader-js');
const modalCardPrice = document.querySelector('.amount');
const modalImage = document.querySelector('.modal-image-wrapper img');
const modalStatus = document.querySelector('.modalText');
const modalCapacity = document.querySelector('.modal-capacity');
const modalBath = document.querySelector('.modal-bath');
const modalSquare = document.querySelector('.modal-square');

cardItems.forEach((cardItem) => {
    cardItem.addEventListener('click', function () {
        const cardHeader = cardItem.querySelector('.cardText-js');
        const cardPrice = cardItem.querySelector('.card-price');
        const cardImage = cardItem.querySelector('.card-image-wrapper img');
        const cardStatus = cardItem.querySelector('.card-status');
        const cardRooms = cardItem.querySelector('.capacity');

        modalCapacity.innerText =  cardRooms.innerText + " Bedrooms";
        modalBath.innerText =  cardRooms.innerText + " Bathrooms";
        modalImage.src = cardImage.src;
        modalStatus.innerText = cardStatus.innerText;
        modalHeader.innerText = cardHeader.innerText;
        modalCardPrice.innerText = cardPrice.innerText;

        let price = parseFloat(cardPrice.innerText.replace("$", ""));
        let rooms = parseInt(cardRooms.innerText);
        if (isNaN(price) || isNaN(rooms)) {
            console.log("One of the values is not a number!");
        } else {
            let square = price * rooms;
            modalSquare.innerHTML = square + " m<sup>2</sup>";
        }
    });
});

window.onkeydown = function (event) {
    if (event.key === "Escape") {
        closeModal();
    }
}

const modal = document.querySelector('#modal-window');
window.onclick = function (event) {
    if (event.target === modal) {
        closeModal();
    }
}
