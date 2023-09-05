document.addEventListener('DOMContentLoaded', function () {
    const modeSwitch = document.querySelector('.mode-switch');

    modeSwitch.addEventListener('click', function () {
        document.documentElement.classList.toggle('dark');
    });
});

function closeModal() {
    let modal = document.querySelector('#modal-window');
    modal.classList.remove("showModal");
}

document.getElementsByClassName('.mode-switch').onclick = function () {
    document.body.classList.toggle('dark');
}

const cardItems = document.querySelectorAll('.main-card');
const modalHeader = document.querySelector('.modalHeader-js');
const modalId = document.querySelector('.modal-id');
const modalCardPrice = document.querySelector('.amount');
const modalImage = document.querySelector('.modal-image-wrapper img');
const modalStatus = document.querySelector('.modalText');
const modalCapacity = document.querySelector('.modal-capacity');
const modalBath = document.querySelector('.modal-bath');
const modalSquare = document.querySelector('.modal-square');
const bookButton = document.getElementById('bookButton');

function openModal() {
    let modal = document.querySelector('#modal-window');
    modal.classList.add("showModal");
}

cardItems.forEach((cardItem) => {
    cardItem.addEventListener('click', function () {
        const cardId = cardItem.querySelector('.card-id');
        const cardHeader = cardItem.querySelector('.cardText-js');
        const cardPrice = cardItem.querySelector('.card-price');
        const cardImage = cardItem.querySelector('.card-image-wrapper img');
        const cardStatus = cardItem.querySelector('.card-status');
        const cardRooms = cardItem.querySelector('.capacity');

        modalId.value = cardId.value;
        modalCapacity.innerText = cardRooms.innerText + " Bedrooms";
        modalBath.innerText = cardRooms.innerText + " Bathrooms";
        modalImage.src = cardImage.src;
        modalStatus.value = cardStatus.innerText;

        modalHeader.innerText = cardHeader.innerText;
        modalCardPrice.innerText = cardPrice.innerText;

        if (modalStatus.value.trim() !== 'VACANT') {
            bookButton.disabled = true;
            bookButton.style.background = "gray";
        } else {
            bookButton.disabled = false;
            bookButton.style.background = "#d84851";
        }

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

function createCard(card) {
    const cardWrapper = document.createElement("div");
    cardWrapper.className = "card-wrapper main-card";

    const cardLink = document.createElement("a");
    cardLink.className = "card cardItem";
    cardLink.addEventListener("click", getRooms);

    const cardImage = document.createElement("img");
    cardImage.src = card.imageSrc;
    cardImage.alt = "Hotel";

    const cardInfo = create("div", "card-info", "");
    const cardImageWrapper = create("div", "card-image-wrapper", "");
    const cardTitle = create("div", "card-text big cardText-js", card.title);
    const cardLocation = create("div", "card-text small", card.location);
    const cardPriceWrapper = create("div", "card-text small", "");
    const cardPrice = create("span", "card-price", card.price);

    const cardPriceLabel = document.createTextNode("Starts from: ");
    cardPriceWrapper.appendChild(cardPriceLabel);
    cardPriceWrapper.appendChild(cardPrice);

    cardInfo.appendChild(cardTitle);
    cardInfo.appendChild(cardLocation);
    cardInfo.appendChild(cardPriceWrapper);

    cardImageWrapper.appendChild(cardImage);
    cardLink.appendChild(cardImageWrapper);
    cardLink.appendChild(cardInfo);
    cardWrapper.appendChild(cardLink);

    return cardWrapper;
}

function create(element, name, text) {
    const child = document.createElement(element);
    child.className = name;
    child.textContent = text;
    return child;
}

const cardData = [
    {
        imageSrc: "https://source.unsplash.com/featured/1200x900/?hotel-room,interior",
        title: "Room Twin",
        location: "VACANT",
        price: "$100",
    },
    {
        imageSrc: "https://source.unsplash.com/featured/1200x900/?interior,hotel",
        title: "The Queen",
        location: "RESERVED",
        price: "$300",
    },
    {
        imageSrc: "https://source.unsplash.com/featured/1200x900/?interior,modern",
        title: "The King",
        location: "OCCUPIED",
        price: "$140",
    },
    {
        imageSrc: "https://source.unsplash.com/featured/1200x900/?hotel,modern",
        title: "Room Quad",
        location: "VACANT",
        price: "$180",
    },
    {
        imageSrc: "https://source.unsplash.com/featured/1200x900/?room,design",
        title: "Room Standard",
        location: "MAINTENANCE",
        price: "$700",
    },
    {
        imageSrc: "https://source.unsplash.com/featured/1200x900/?room,modern",
        title: "Room Apartment",
        location: "OCCUPIED",
        price: "$150",
    },
];

const cardContainer = document.getElementById("latest-deals");

cardData.forEach((card) => {
    const cardElement = createCard(card);
    cardContainer.appendChild(cardElement);
});
