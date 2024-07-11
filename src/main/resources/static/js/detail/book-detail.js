document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('cartButton').addEventListener('click', function(e) {
        e.preventDefault();
        submitCart();
    });
    updateTotalPrice();
    loadReviewData();
    reviewRatings();
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
    const bookId = getBookIdFromUrl();
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



