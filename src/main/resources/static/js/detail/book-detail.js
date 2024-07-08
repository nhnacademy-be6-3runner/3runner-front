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
    const couponSelect = document.getElementById('couponSelect');
    const couponId = couponSelect.value;

    if (couponId && couponId !== '쿠폰을 선택하세요') {
        $.ajax({
            url: `/api/coupons/download/${couponId}`,
            type: 'POST',
            success: function (response) {
                alert('쿠폰이 다운로드되었습니다!');
            },
            error: function (xhr, status, error) {
                console.error('Error downloading coupon:', error);
            }
        });
    } else {
        alert('다운로드할 쿠폰을 선택하세요.');
    }
}

function getBookIdFromUrl() {
    const pathSegments = window.location.pathname.split('/');
    return pathSegments[pathSegments.length - 1];
}

// review-page-list.js

// 현재 페이지 번호와 정렬 상태를 추적하는 전역 변수
let reviewPage = 0;
let reviewSort = 'createdAt,desc';

document.addEventListener('DOMContentLoaded', function () {
    loadReviews(reviewSort, reviewPage);
});

function loadReviews(sort = 'createdAt,desc', page = 0) {
    const bookId = getBookIdFromUrl();
    const url = `/api/books/${bookId}/reviews?sort=${sort}&page=${page}`;
    console.log("Request URL:", url);

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const reviewListItems = document.getElementById('review-list-items');
            if (page === 0) {
                reviewListItems.innerHTML = ""; // 기존 목록 초기화 (첫 페이지 로드 시)
                reviewPage = 0; // 페이지 번호 초기화
            }

            data.body.data.content.forEach(review => {
                const reviewItem = document.createElement('div');
                reviewItem.className = 'list-group-item';
                const memberEmail = review.memberEmail;
                const anonymizedEmail = `***${memberEmail.split('@')[0].slice(3)}@${memberEmail.split('@')[1]}`;
                reviewItem.innerHTML = `
                    <img class="review-img" src="/api/images/review/download?fileName=${review.imgUrl}" alt="review img">
                    <div class="review-content">
                        <div class="rating">
                            ${Array.from({ length: review.rating }, () => '<i class="bi bi-star-fill"></i>').join('')}
                            ${Array.from({ length: 5 - review.rating }, () => '<i class="bi bi-star"></i>').join('')}
                        </div>
                        <h5 class = "review-title" data-id="${review.id}">${review.title}</h5>
                        <small>작성자: ${anonymizedEmail}</small>
                    </div>
                `;
                reviewListItems.appendChild(reviewItem);
            });

            document.addEventListener('click', function(event) {
                if (event.target.classList.contains('review-title')) {
                    const reviewId = event.target.getAttribute('data-id');
                    if (reviewId) {
                        event.target.classList.add('click-animation');
                        setTimeout(() => {
                            window.location.href = `/review/detail/${reviewId}`;
                        }, 100);  // 애니메이션 지속 시간 (300ms) 후에 페이지 이동
                    }
                }
            });
        })
        .catch(error => console.error('Error loading reviews:', error));
}

function loadMoreReviews() {
    reviewPage++;
    loadReviews(reviewSort, reviewPage);
}

function changeReviewSorting() {
    const reviewSortField = document.getElementById('reviewSort').value;
    const reviewSortOrder = document.getElementById('reviewSortOrder').value;

    reviewSort = `${reviewSortField},${reviewSortOrder}`; // 현재 정렬 상태 업데이트
    reviewPage = 0; // 페이지 번호 초기화
    loadReviews(reviewSort, reviewPage); // 새로 정렬된 리뷰 목록 로드
}

function getBookIdFromUrl() {
    const pathSegments = window.location.pathname.split('/');
    return pathSegments[pathSegments.length - 1];
}

