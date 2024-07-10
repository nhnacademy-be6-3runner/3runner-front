// product-detail.js

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

document.addEventListener('DOMContentLoaded', function () {
    updateTotalPrice(); // 초기 총 가격 설정
    fetchCoupons(); // 쿠폰 조회 함수 호출
    updateLikeCount(); // 초기 좋아요 수 설정
});

// 좋아요 버튼에 이벤트 리스너 추가
const likeButton = document.getElementById('like');
likeButton.addEventListener('click', function (event) {
    event.stopPropagation(); // 부모 요소로의 클릭 이벤트 전파 방지
    likeButton.classList.toggle('active');
    const path = likeButton.querySelector('path');
    if (likeButton.classList.contains('active')) {
        path.setAttribute('fill', '#e30d0d');
    } else {
        path.setAttribute('fill', '#777');
    }
});

// 좋아요 버튼
function increaseLikeCount() {
    const bookId = getBookIdFromUrl();
    $.ajax({
        url: `/api/book-likes/increase/${bookId}`,
        type: 'POST',
        success: function () {
            updateLikeCount();
        },
        error: function (xhr, status, error) {
            console.error('Error increasing like count:', error);
        }
    });
}

function decreaseLikeCount() {
    const bookId = getBookIdFromUrl();
    $.ajax({
        url: `/api/book-likes/decrease/${bookId}`,
        type: 'POST',
        success: function () {
            updateLikeCount();
        },
        error: function (xhr, status, error) {
            console.error('Error decreasing like count:', error);
        }
    });
}

function updateLikeCount() {
    const bookId = getBookIdFromUrl();
    $.ajax({
        url: `/api/books/${bookId}/likes`,
        type: 'GET',
        success: function (response) {
            console.log(`Received like count response: `, response);
            document.getElementById('likeCount').innerText = response.data;
        },
        error: function (xhr, status, error) {
            console.error('Error fetching like count:', error);
        }
    });
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