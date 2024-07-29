let reviewPage = 0;
let commentPage = 0;
let bookLikePage = 0;

document.addEventListener('DOMContentLoaded', function () {
    loadReviews(reviewPage);
    loadUserComments(commentPage);
    loadBookLikes(bookLikePage);
    setupEventDelegation();
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
            reviewListItems.innerHTML = "";

            data.body.data.content.forEach(review => {
                const reviewItem = document.createElement('div');
                reviewItem.className = 'list-group-item';
                reviewItem.innerHTML = `
                <div class="row g-0 align-items-center">
                    <div class="col-md-2">
                        <img src="/api/images/review/download?fileName=${review.imgUrl}" class="img-fluid rounded-start" alt="review img">
                    </div>
                    <div class="col-md-8">
                        <div class="review-content" data-id="${review.reviewId}">
                            <div class="rating">
                                ${Array.from({length: review.rating}, () => '<i class="bi bi-star-fill"></i>').join('')}
                                ${Array.from({length: 5 - review.rating}, () => '<i class="bi bi-star"></i>').join('')}
                            </div>
                            <h5 class="review-title">${review.title}</h5>
                        </div>
                    </div>
                    <div class="col-md-2 text-end">
                        <button class="btn btn-outline-primary btn-sm edit-review-btn" data-id="${review.reviewId}">수정</button>
                    </div>
                </div>
            `;
                reviewListItems.appendChild(reviewItem);
                console.log('review Id', review.reviewId)

                // 리뷰 클릭 이벤트 리스너 추가
                reviewItem.addEventListener('click', () => {
                    window.location.href = `/review/${review.reviewId}`;
                });

                // 수정 버튼 클릭 이벤트 리스너 추가
                const editButton = reviewItem.querySelector('.edit-review-btn');
                editButton.addEventListener('click', (event) => {
                    event.stopPropagation(); // 클릭 이벤트가 상위 요소로 전달되지 않도록 막음
                    const reviewId = event.target.dataset.id;
                    window.location.href = `/review/edit/${reviewId}`;
                });
            });

            const pagination = document.getElementById('reviewPagination');
            pagination.innerHTML = '';

            const totalPages = data.body.data.totalPages;
            const currentPage = data.body.data.number;

            for (let i = 0; i < totalPages; i++) {
                const pageButton = document.createElement('button');
                pageButton.textContent = i + 1;
                pageButton.classList.add('btn', 'btn-secondary', 'me-2');
                if (i === currentPage) {
                    pageButton.classList.add('active');
                }
                pageButton.addEventListener('click', function () {
                    loadReviews(i);
                });
                pagination.appendChild(pageButton);
            }
        })
        .catch(error => console.error('Error loading reviews:', error));
}


function loadUserComments(page = 0) {
    const url = `/api/mypage/comments?page=${page}&size=10`;

    fetch(url, {
        method: 'GET'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            const comments = data.body?.data?.content || [];
            const commentList = document.getElementById('commentsContent');
            commentList.innerHTML = '';

            comments.forEach(comment => {
                const commentDiv = document.createElement('div');
                commentDiv.classList.add('comment');
                const createdAt = new Date(comment.createdAt).toLocaleString(); // 날짜와 시간 표시
                commentDiv.innerHTML = `
                <p><small class="text-muted">${comment.commentId}</small></p>
                <p>${comment.content}</p>
                <p><small class="text-muted">작성일: ${createdAt}</small></p>
                <button class="delete-btn" onclick="confirmDeleteComment(${comment.commentId})">&times;</button>
            `;
                commentList.appendChild(commentDiv);
            });

            const pagination = document.getElementById('commentPagination');
            pagination.innerHTML = '';

            const totalPages = data.body.data.totalPages;
            const currentPage = data.body.data.number;

            for (let i = 0; i < totalPages; i++) {
                const pageButton = document.createElement('button');
                pageButton.textContent = i + 1;
                pageButton.classList.add('btn', 'btn-secondary', 'me-2');
                if (i === currentPage) {
                    pageButton.classList.add('active');
                }
                pageButton.addEventListener('click', function () {
                    loadUserComments(i);
                });
                pagination.appendChild(pageButton);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function loadBookLikes(page = 0) {
    const url = `/api/mypage/likes?page=${page}&size=12`;
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
            const bookListItems = document.getElementById('likedBooksContent');
            bookListItems.innerHTML = "";

            data.body.data.content.forEach(book => {
                const bookItem = document.createElement('div');
                bookItem.className = 'col-lg-4 col-md-6 col-sm-12 book-item'; // 수정된 부분
                bookItem.dataset.bookId = book.id;
                bookItem.innerHTML = `
                <div class="card">
                    <img class="card-img-top book-thumbnail" src="/api/images/book/download?fileName=${book.thumbnail}" alt="Book Thumbnail">
                    <div class="card-body">
                        <h5 class="card-title">${book.title}</h5>
                        <p class="card-text-author">${book.author}</p>
                        <p class="card-text-price">${book.price}원 (${book.sellingPrice}원)</p>
                    </div>
                </div>
            `;
                bookListItems.appendChild(bookItem);
            });

            const pagination = document.getElementById('bookPagination');
            pagination.innerHTML = '';

            const totalPages = data.body.data.totalPages;
            const currentPage = data.body.data.number;

            for (let i = 0; i < totalPages; i++) {
                const pageButton = document.createElement('button');
                pageButton.textContent = i + 1;
                pageButton.classList.add('btn', 'btn-secondary', 'me-2');
                if (i === currentPage) {
                    pageButton.classList.add('active');
                }
                pageButton.addEventListener('click', function () {
                    loadBookLikes(i);
                });
                pagination.appendChild(pageButton);
            }
        })
        .catch(error => console.error('Error loading books:', error));
}

function confirmDeleteComment(commentId) {
    if (confirm('댓글을 삭제하시겠습니까?')) {
        deleteComment(commentId);
    }
}

function deleteComment(commentId) {
    const url = `/api/mypage/comment/${commentId}/delete`;
    fetch(url, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log('Comment deleted:', data);
            loadUserComments(commentPage);
        })
        .catch(error => {
            console.error('Error deleting comment:', error);
        });
}

function setupEventDelegation() {
    const bookListItems = document.getElementById('likedBooksContent');

    bookListItems.addEventListener('click', function (event) {
        const bookItem = event.target.closest('.book-item');
        if (bookItem) {
            const bookId = bookItem.dataset.bookId; // data-book-id 속성에서 bookId 추출
            window.location.href = `/book/${bookId}`;
        }
    });
}
