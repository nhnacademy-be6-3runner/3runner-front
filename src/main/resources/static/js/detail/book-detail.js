// product-detail.js

document.addEventListener('DOMContentLoaded', function () {
    updateTotalPrice();
    fetchCoupons();
});

function addCart(bookId, quantity) {
    $.ajax({
        url: window.location.origin + '/carts',
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        data: $.param({ bookId: bookId, quantity: quantity }),
        success: function (response) {
            console.log(response);
            window.location.href = window.location.origin + '/api/carts';
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
            console.error('Status:', status);
            console.error('XHR:', xhr);
        }
    });
}

function submitCart() {
    const bookId = document.getElementById('bookIdCart').value;
    const quantity = document.getElementById('quantityCart').value;
    addCart(bookId, quantity);
}

function increaseQuantity() {
    let quantity = document.getElementById('quantityCart').value;
    quantity = parseInt(quantity) + 1;
    document.getElementById('quantityCart').value = quantity;
    updateTotalPrice();
}

function decreaseQuantity() {
    let quantity = document.getElementById('quantityCart').value;
    if (quantity > 1) {
        quantity = parseInt(quantity) - 1;
        document.getElementById('quantityCart').value = quantity;
        updateTotalPrice();
    }
}

function updateTotalPrice() {
    const quantity = document.getElementById('quantityCart').value;
    const price = parseInt(document.querySelector('.selling-price span').innerText);
    const totalPrice = quantity * price;
    document.getElementById('totalPrice').innerText = totalPrice;
}

// 쿠폰 조회 및 다운로드 기능
function fetchCoupons() {
    const bookId = document.getElementById('bookIdCart').value;
    $.ajax({
        url: `/api/coupons/book/${bookId}`,
        type: 'GET',
        success: function (response) {
            displayCoupons(response);
        },
        error: function (xhr, status, error) {
            console.error('Error fetching coupons:', error);
        }
    });
}

function displayCoupons(coupons) {
    const couponSelect = document.getElementById('couponSelect');
    couponSelect.innerHTML = '<option selected>쿠폰을 선택하세요</option>'; // 기본 옵션 초기화

    coupons.forEach(coupon => {
        const option = document.createElement('option');
        option.value = coupon.id;
        option.text = `${coupon.name} - ${coupon.discount}% 할인`;
        couponSelect.appendChild(option);
    });
}

function downloadSelectedCoupon() {
    const bookId = document.getElementById('bookId').value;

    $.ajax({
        url: `/coupons/books/${bookId}`,
        type: 'POST',
        success: function (response) {
            if(response === 'true'){
                alert('쿠폰이 다운로드되었습니다!');
            } else {
                alert('발급 받을 수 있는 쿠폰이 없습니다');
            }
        },
        error: function (xhr, status, error) {
            alert('발급 받을 수 있는 쿠폰이 없습니다');
            console.error('Error downloading coupon:', error);
        }
    });
}

function getBookIdFromUrl() {
    const pathSegments = window.location.pathname.split('/');
    return pathSegments[pathSegments.length - 1];
}
