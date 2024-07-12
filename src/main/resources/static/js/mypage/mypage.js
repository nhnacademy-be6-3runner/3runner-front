let reviewPage = 0;

document.addEventListener('DOMContentLoaded', function () {
    loadReviews(reviewPage);
});

function loadReviews(page = 0) {
    const url = `/api/books/mypage/reviews?page=${page}`;
    console.log("Request URL:", url);

    fetch(url, {
        method: 'GET'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log("Review data:", data);
            const reviewListItems = document.getElementById('reviewsContent');
            if (page === 0) {
                reviewListItems.innerHTML = "";
                reviewPage = 0;
            }

            data.body.data.content.forEach(review => {
                const reviewItem = document.createElement('div');
                reviewItem.className = 'list-group-item';
                reviewItem.innerHTML = `
                        <div class="row g-0 align-items-center">
                            <div class="col-md-2">
                                <img src="/api/images/review/download?fileName=${review.imgUrl}" class="img-fluid rounded-start" alt="review img">
                            </div>
                            <div class="col-md-10">
                                <div class="review-content" data-id="${review.reviewId}">
                                    <div class="rating">
                                        ${Array.from({ length: review.rating }, () => '<i class="bi bi-star-fill"></i>').join('')}
                                        ${Array.from({ length: 5 - review.rating }, () => '<i class="bi bi-star"></i>').join('')}
                                    </div>
                                    <h5 class="review-title">${review.title}</h5>
                                </div>
                            </div>
                        </div>
                    `;
                reviewListItems.appendChild(reviewItem);
                console.log('review Id', review.reviewId)

                // 리뷰 클릭 이벤트 리스너 추가
                reviewItem.addEventListener('click', () => {
                    window.location.href = `http://localhost:3000/review/${review.reviewId}`;
                });

            });
        })
        .catch(error => console.error('Error loading reviews:', error));
}

function loadMoreReviews() {
    reviewPage++;
    loadReviews(reviewPage);
}