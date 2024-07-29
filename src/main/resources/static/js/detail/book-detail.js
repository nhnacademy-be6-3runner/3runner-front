document.addEventListener('DOMContentLoaded', function () {
    const cartButton = document.getElementById('cartButton');
    if (cartButton) {
        cartButton.addEventListener('click', function(e) {
            e.preventDefault();
            submitCart();
        });
    }

    const purchaseButton = document.getElementById('purchaseButton');
    if (purchaseButton) {
        purchaseButton.addEventListener('click', function (e) {
            e.preventDefault();
            submitPurchase();
        });
    }

    const likeButton = document.getElementById('like');
    if (likeButton) {
        likeButton.addEventListener('click', function(e) {
            e.preventDefault();
            handleLikeClick();
        });
    }

    updateTotalPrice();
    loadReviewData();
    reviewRatings();
    checkAccessTokenForComments();
    checkAccessTokenForCoupon();
});


function handleLikeClick() {
    const accessToken = getCookie('Access');
    if (!accessToken) {
        showPopupMessage();
    } else {
        // 실제 찜 기능 코드 추가 (여기서 필요한 찜 기능 관련 코드를 작성하세요)
        const likeIcon = document.getElementById('like');
        likeIcon.setAttribute('fill', likeIcon.getAttribute('fill') === 'red' ? 'none' : 'red');
    }
}

function showPopupMessage() {
    const popup = new bootstrap.Modal(document.getElementById('popupMessage'), {});
    popup.show();
}

function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

function checkAccessTokenForComments() {
    const accessToken = getCookie('Access');
    if (!accessToken) {
        document.getElementById('commentForm').style.display = 'none';
        document.getElementById('loginMessage').style.display = 'block';
    }
}

function checkAccessTokenForCoupon() {
    const accessToken = getCookie('Access');
    if (accessToken) {
        document.querySelector('.coupon').style.display = 'block';
    }
}

function addCart(bookId, quantity) {
    $.ajax({
        url: '/carts',
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        data: $.param({ bookId: bookId, quantity: quantity }),

        success: function (response) {
            window.location.href = '/carts';
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
        url: '/purchases',
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        data: $.param({ bookId: bookId, quantity: quantity }),

        success: function (response) {
            if (response.redirectUrl) {
                window.location.href = response.redirectUrl; // 서버로부터 받은 URL로 리다이렉트
            } else {
                console.log(response);
            }
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
            console.error('Status:', status);
            console.error('XHR:', xhr);
        }
    });
}

function submitCart() {
    const bookId = getBookIdFromUrl();
    const quantity = document.getElementById('quantity').value;
    addCart(bookId, quantity);
}

function submitPurchase() {
    const bookId = getBookIdFromUrl();
    const quantity = document.getElementById('quantity').value;
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
    document.getElementById('quantity1').value = quantity;
    const price = parseInt(document.querySelector('.selling-price span').innerText);
    const totalPrice = quantity * price;
    document.getElementById('totalPrice').innerText = totalPrice;
}


function getBookIdFromUrl() {
    const pathSegments = window.location.pathname.split('/');
    return pathSegments[pathSegments.length - 1];
}

function reviewRatings() {
    const bookId = getBookIdFromUrl();
    // 평균 별점 가져오기
    fetch(`/api/books/${bookId}/reviews/avg`)
        .then(response => response.json())
        .then(data => {
            if (data.body && data.body.data !== undefined) {
                const rating = data.body.data;
                document.getElementById("average-rating-container").textContent = `이 책의 별점 평균은 ${rating.toFixed(2)} 입니다.`;
            } else {
                console.error('Invalid response structure:', data);
            }
        })
        .catch(error => console.error('Error fetching average rating:', error));
}

function loadReviewData() {
    const bookId = getBookIdFromUrl();

    // 리뷰 수 가져오기
    fetch(`/api/books/${bookId}/reviews/count`)
        .then(response => response.json())
        .then(data => {
            if (data.body && data.body.data !== undefined) {
                document.getElementById("review-count").textContent = data.body.data;
            } else {
                console.error('Invalid response structure:', data);
            }
        })
        .catch(error => console.error('Error fetching review count:', error));

    // 평균 별점 가져오기
    fetch(`/api/books/${bookId}/reviews/avg`)
        .then(response => response.json())
        .then(data => {
            if (data.body && data.body.data !== undefined) {
                const rating = data.body.data;
                const starContainer = document.getElementById("star-container");
                starContainer.innerHTML = ''; // 기존 별 초기화

                // 별점 채우기
                for (let i = 1; i <= 5; i++) {
                    const starElement = document.createElement("i");
                    starElement.className = (i <= rating) ? "bi bi-star-fill" : "bi bi-star";
                    starContainer.appendChild(starElement);
                }
            } else {
                console.error('Invalid response structure:', data);
            }
        })
        .catch(error => console.error('Error fetching average rating:', error));
}

function downloadSelectedCoupon() {
    const bookId = getBookIdFromUrl();

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
