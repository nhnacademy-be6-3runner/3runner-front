// product-detail.js

document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('cartButton').addEventListener('click', function(e) {
        e.preventDefault();
        submitCart();
    });
    updateTotalPrice();
});

function addCart(bookId, quantity) {
    $.ajax({
        url: '/carts',
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        data: $.param({ bookId: bookId, quantity: quantity }),
        success: function (response) {
            console.log(response);
            window.location.href = window.location.origin + '/carts';
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
            console.error('Status:', status);
            console.error('XHR:', xhr);
        }
    });
}

function addPurchase(bookId, quantity) {
    $.ajax({
        url: window.location.origin + '/purchases',
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        data: $.param({ bookId: bookId, quantity: quantity }),
        success: function (response) {
            console.log(response);
            window.location.href = window.location.origin + '/purchases';
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
            console.error('Status:', status);
            console.error('XHR:', xhr);
        }
    });
}

function submitCart() {
    const bookId = getBookIdFromUrl()
    const quantity = document.getElementById('quantity').value;
    addCart(bookId, quantity);
}

function submitPurchase() {
    const bookId = getBookIdFromUrl();
    const quantity = document.getElementById('quantityPurchase').value;
    addPurchase(bookId, quantity);
}

function increaseQuantity() {
    let quantity = document.getElementById('quantity').value;
    quantity = parseInt(quantity) + 1;
    document.getElementById('quantity').value = quantity;
    updateTotalPrice();
}

function decreaseQuantity() {
    let quantity = document.getElementById('quantity').value;
    if (quantity > 1) {
        quantity = parseInt(quantity) - 1;
        document.getElementById('quantity').value = quantity;
        updateTotalPrice();
    }
}

function updateTotalPrice() {
    const quantity = document.getElementById('quantity').value;
    const price = parseInt(document.querySelector('.selling-price span').innerText);
    const totalPrice = quantity * price;
    document.getElementById('totalPrice').innerText = totalPrice;
}

function getBookIdFromUrl() {
    const pathSegments = window.location.pathname.split('/');
    return pathSegments[pathSegments.length - 1];
}
