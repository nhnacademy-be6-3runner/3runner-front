// 현재 페이지 번호와 정렬 상태를 추적하는 전역 변수
let currentPage = 0;
let currentSort = 'publishedDate,desc';

document.addEventListener("DOMContentLoaded", function() {
    const savedSort = localStorage.getItem('currentSort');
    const savedPage = localStorage.getItem('currentPage');
    const savedBooks = localStorage.getItem('loadedBooks');

    if (savedSort) {
        currentSort = savedSort;
    }
    if (savedPage) {
        currentPage = parseInt(savedPage, 10);
    }

    // 정렬 옵션을 설정
    const [primarySort, sortOrder] = currentSort.split(',');
    document.getElementById('primarySort').value = primarySort;
    document.getElementById('sortOrder').value = sortOrder;

    // 저장된 책 목록이 있으면 복원, 없으면 책 목록 로드
    if (savedBooks) {
        document.getElementById('book-list-items').innerHTML = savedBooks;
    } else {
        loadBooks(currentSort, 0);
    }

    // 이벤트 위임 설정
    setupEventDelegation();
});

// "더 보기" 버튼 클릭 시 이벤트 리스너 추가
document.getElementById('load-more-btn').addEventListener('click', function() {
    loadMoreBooks();
});

function loadBooks(sort = 'publishedDate,desc', page = 0) {
    const url = `/api/books/main?sort=${sort}&page=${page}`;
    console.log("Request URL:", url);

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const bookListItems = document.getElementById('book-list-items');

            if (page === 0) {
                bookListItems.innerHTML = ""; // 기존 목록 초기화 (첫 페이지 로드 시)
                currentPage = 0; // 페이지 번호 초기화
            }

            console.log("Loaded Data:", data.body.data.content);

            data.body.data.content.forEach(book => {
                const bookItem = document.createElement('div');
                bookItem.className = 'col-lg-3 col-md-3 col-sm-3 book-item';
                bookItem.innerHTML = `
                    <img class="card-img-top book-thumbnail" src="/api/images/book/download?fileName=${book.thumbnail}" alt="Book Thumbnail">
                    <div class="card-body">
                        <h5 class="card-title">${book.title}</h5>
                        <p class="card-text-author">${book.author}</p>
                        <p class="card-text-price">${book.price}원 (${book.sellingPrice}원)</p>
                        <div class="book-actions">
                            <svg id="like-${book.id}" class="like" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" width="16" height="16">
                                <path d="M47.6 300.4L228.3 469.1c7.5 7 17.4 10.9 27.7 10.9s20.2-3.9 27.7-10.9L464.4 300.4c30.4-28.3 47.6-68 47.6-109.5v-5.8c0-69.9-50.5-129.5-119.4-141C347 36.5 300.6 51.4 268 84L256 96 244 84c-32.6-32.6-79-47.5-124.6-39.9C50.5 55.6 0 115.2 0 185.1v5.8c0 41.5 17.2 81.2 47.6 109.5z"/>
                            </svg>
                            <svg id="cart-${book.id}" class="cart" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 576 512" width="24" height="24">
                                <path d="M0 24C0 10.7 10.7 0 24 0H69.5c22 0 41.5 12.8 50.6 32h411c26.3 0 45.5 25 38.6 50.4l-41 152.3c-8.5 31.4-37 53.3-69.5 53.3H170.7l5.4 28.5c2.2 11.3 12.1 19.5 23.6 19.5H488c13.3 0 24 10.7 24 24s-10.7 24-24 24H199.7c-34.6 0-64.3-24.6-70.7-58.5L77.4 54.5c-.7-3.8-4-6.5-7.9-6.5H24C10.7 48 0 37.3 0 24zM128 464a48 48 0 1 1 96 0 48 48 0 1 1 -96 0zm336-48a48 48 0 1 1 0 96 48 48 0 1 1 0-96zM252 160c0 11 9 20 20 20h44v44c0 11 9 20 20 20s20-9 20-20V180h44c11 0 20-9 20-20s-9-20-20-20H356V96c0-11-9-20-20-20s-20 9-20 20v44H272c-11 0-20 9-20 20z"/>
                            </svg>
                            <a href="/book/${book.id}/reviews" class="btn btn-outline-secondary btn-sm">리뷰 보기</a>
                        </div>
                    </div>
                `;
                bookListItems.appendChild(bookItem);
            });

            // 현재 상태를 localStorage에 저장
            localStorage.setItem('currentSort', currentSort);
            localStorage.setItem('currentPage', currentPage);
            localStorage.setItem('loadedBooks', bookListItems.innerHTML);
        })
        .catch(error => console.error('Error loading books:', error));
}

// 이벤트 위임을 통해 동적으로 추가된 요소에 이벤트 리스너 추가
function setupEventDelegation() {
    const bookListItems = document.getElementById('book-list-items');

    // 좋아요 버튼에 이벤트 위임
    bookListItems.addEventListener('click', function(event) {
        const likeButton = event.target.closest('.like');
        if (likeButton) {
            event.stopPropagation(); // 부모 요소로의 클릭 이벤트 전파 방지
            likeButton.classList.toggle('active');
            const path = likeButton.querySelector('path');
            if (likeButton.classList.contains('active')) {
                path.setAttribute('fill', '#e30d0d');
            } else {
                path.setAttribute('fill', '#777');
            }
            return; // 다른 이벤트 전파 방지
        }

        const cartButton = event.target.closest('.cart');
        if (cartButton) {
            event.stopPropagation(); // 부모 요소로의 클릭 이벤트 전파 방지
            cartButton.classList.toggle('active');
            const path = cartButton.querySelector('path');
            if (cartButton.classList.contains('active')) {
                path.setAttribute('fill', '#000080'); // 네이비 색상
                addToCart(cartButton.id.split('-')[1]); // 장바구니에 담는 함수 호출
            } else {
                path.setAttribute('fill', '#777'); // 기본 색상
            }
            return; // 다른 이벤트 전파 방지
        }

        const bookItem = event.target.closest('.book-item');
        if (bookItem) {
            const bookId = bookItem.querySelector('svg').id.split('-')[1];
            window.location.href = `/book/${bookId}`;
        }
    });
}


function addToLike(bookId) {
    console.log(`Book with ID ${bookId} added to like.`);
}

function addToCart(bookId) {
    console.log(`Book with ID ${bookId} added to cart.`);
}

function changeSorting() {
    const primarySort = document.getElementById('primarySort').value;
    const sortOrder = document.getElementById('sortOrder').value;
    currentSort = `${primarySort},${sortOrder}`; // 현재 정렬 상태 업데이트
    currentPage = 0; // 페이지 번호 초기화

    // 정렬 상태와 페이지 번호를 localStorage에 저장
    localStorage.setItem('currentSort', currentSort);
    localStorage.setItem('currentPage', currentPage);

    loadBooks(currentSort, 0); // 새로 정렬된 책 목록 로드
}

function loadMoreBooks() {
    currentPage++; // 페이지 번호 증가
    // 페이지 번호를 localStorage에 저장
    localStorage.setItem('currentPage', currentPage);

    loadBooks(currentSort, currentPage); // 기존 정렬 기준 유지하며 다음 페이지 로드
}