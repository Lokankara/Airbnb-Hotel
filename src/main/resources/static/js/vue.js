new Vue({
    el: '#app',
    data() {
        return {
            reservationSelected: [],
            pagination: {
                rowsPerPage: 10
            },

            status: null,
            reservationSelectedHeaders: [
                {text: 'Name', value: 'passportData'},
                {text: 'GuestStatus', value: 'guestStatus'},
                {text: 'Rooms', value: 'roomsNumber'},
                {text: 'Departure', value: 'departureDate'},
                {text: 'Arrive', value: 'arrivalDate'},
                {text: 'Type', value: 'roomTypes'},
                {text: 'RoomStatus', value: 'roomStatuses'},
                {text: 'Actions', align: 'left', value: 'title', sortable: false}
            ],
            reservations: guests
        };

    },
    computed: {

        combinedItems() {
            if (this.status) {
                return this.filteredItems;
            } else {
                return this.preparedItems;
            }
        },

        preparedItems() {
            return this.reservations.map(reservation => {
                const roomsNumber = reservation.rooms.map(room => room.capacity).join(', ');
                const roomTypes = reservation.rooms.map(room => room.roomType).join(', ');
                const roomStatuses = reservation.rooms.map(room => room.roomStatus).join(', ');

                return {
                    ...reservation,
                    roomsNumber,
                    roomTypes,
                    roomStatuses
                };
            });
        },
        filteredItems() {
            console.log('filteredItems computed property called');
            return this.preparedItems.filter(i => {
                return !this.status || i.roomStatuses === this.status;
            });
        }
    },
    methods: {
        updateData(id) {
            var xhr = new XMLHttpRequest();
            xhr.open('PATCH', `/guests/${id}`);
            xhr.onload = function () {
                if (xhr.status === 200) {
                    console.log(`Item with id ${id} updated successfully!`);
                } else {
                    console.error(`An error occurred while updating item with id ${id}: ${xhr.status}`);
                }
            };
            xhr.send();
        },
        cancelData(id) {
            var xhr = new XMLHttpRequest();
            xhr.open('DELETE', `/guests/${id}`);
            xhr.onload = function () {
                if (xhr.status === 200) {
                    console.log(`Item with id ${id} deleted successfully!`);
                } else {
                    console.error(`An error occurred while deleting item with id ${id}: ${xhr.status}`);
                }
            };
            xhr.send();
        }
    }
});


window.onload = () => {
    const radioInputs = document.querySelectorAll('input[type="radio"]');
    radioInputs.forEach(radio => {
        radio.addEventListener('mouseover', () => {
            radio.checked = true;
        });
    });
};