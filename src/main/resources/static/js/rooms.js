let previous = document.getElementById('btnPrevious')
let next = document.getElementById('btnNext')
let gallery = document.getElementById('image-gallery')
let pageIndicator = document.getElementById('page')
let galleryDots = document.getElementById('gallery-dots');


let images = [];
for (let i = 1; i < 401; i++) {
    images.push({
        title: "Room #" + (i),
        source: "https://raw.githubusercontent.com/Lokankara/Patterns/main/img/image_" + i + ".jpg"
    });
}

let perPage = 16;
let page = 1;
let pages = Math.ceil(images.length / perPage)


for (let i = 0; i < pages; i++) {
    let dot = document.createElement('button')
    let dotSpan = document.createElement('span')
    let dotNumber = document.createTextNode("" + i + 1)
    dot.classList.add('gallery-dot');
    dot.setAttribute('data-index', "" + i);
    dotSpan.classList.add('sr-only');

    dotSpan.appendChild(dotNumber);
    dot.appendChild(dotSpan)

    dot.addEventListener('click', function (e) {
        let self = e.target
        goToPage(self.getAttribute('data-index'))
    })

    galleryDots.appendChild(dot)
}

// Previous Button
previous.addEventListener('click', function () {
    if (page === 1) {
        page = 1;
    } else {
        page--;
        showImages();
    }
})

// Next Button
next.addEventListener('click', function () {
    if (page < pages) {
        page++;
        showImages();
    }
})

function goToPage(rooms) {
    rooms = parseInt(rooms);
    page = rooms + 1;

    showImages();
}

// Load images
function showImages() {
    while (gallery.firstChild) gallery.removeChild(gallery.firstChild)

    let offset = (page - 1) * perPage;
    let dots = document.querySelectorAll('.gallery-dot');

    for (const element of dots) {
        element.classList.remove('active');
    }

    dots[page - 1].classList.add('active');

    for (let i = offset; i < offset + perPage; i++) {
        if (images[i]) {
            let template = document.createElement('div');
            let title = document.createElement('p');
            let titleText = document.createTextNode(images[i].title);
            let img = document.createElement('img');

            template.classList.add('template')
            img.setAttribute("src", images[i].source);
            img.setAttribute('alt', images[i].title);

            title.appendChild(titleText);
            template.appendChild(img);
            template.appendChild(title);
            gallery.appendChild(template);
        }
    }

    let galleryItems = document.querySelectorAll('.template')
    for (let i = 0; i < galleryItems.length; i++) {
        let onAnimateItemIn = animateItemIn(i);
        setTimeout(onAnimateItemIn, i * 100);
    }

    function animateItemIn(i) {
        let item = galleryItems[i];
        return function () {
            item.classList.add('animate');
        }
    }

    pageIndicator.textContent = "Page " + page + " of " + pages;

}

showImages();


let modal = document.getElementById('modal');

let modalClose = document.getElementById('modal');
modalClose.addEventListener('click', function () {
    modal.style.display = "none";
});

document.addEventListener('click', function (e) {
    let el = e.target.closest('.images');
    console.log(el)
    if (el) {
        let img = e.target;
        let modalImg = document.getElementById("modal-content");
        modal.style.display = "block";
        modalImg.src = img.src;
    }
});


const btnPrevious = document.querySelector('#btnPrevious');
const btnNext = document.querySelector('#btnNext');

window.addEventListener('keydown', function (event) {
    switch (event.key) {
        case 'ArrowLeft':
            btnPrevious.click();
            break;
        case 'ArrowRight':
            btnNext.click();
            break;
        default:
            return;
    }
    event.preventDefault();
});
