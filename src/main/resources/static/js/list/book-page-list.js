let currentSort = 'viewCount,desc';
let currentPage = 0;

document.addEventListener("DOMContentLoaded", function() {
    // 초기 정렬 옵션 설정
    const [primarySort, sortOrder] = currentSort.split(',');
    document.getElementById('primarySort').value = primarySort;
    document.getElementById('sortOrder').value = sortOrder;

    // 초기 페이지 로드
    loadBooks(currentSort, currentPage);

    // 이벤트 위임 설정
    setupEventDelegation();
});

// "더 보기" 버튼 클릭 시 이벤트 리스너 추가
document.getElementById('load-more-btn').addEventListener('click', function() {
    loadMoreBooks();
});

function loadBooks(sort = currentSort, page = currentPage) {
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
            }

            console.log("Loaded Data:", data.body.data.content);

            data.body.data.content.forEach(book => {
                const bookItem = document.createElement('div');
                bookItem.className = 'col-lg-3 col-md-3 col-sm-3 book-item';
                bookItem.dataset.bookId = book.id; // data-book-id 속성에 bookId 저장
                bookItem.innerHTML = `
                    <img class="card-img-top book-thumbnail" src="/api/images/book/download?fileName=${book.thumbnail}" alt="Book Thumbnail">
                    <div class="card-body">
                        <h5 class="card-title">${book.title}</h5>
                        <p class="card-text-author">${book.author}</p>
                        <p class="card-text-price">${book.price}원 (${book.sellingPrice}원)</p>
                    </div>
                `;
                bookListItems.appendChild(bookItem);
            });
        })
        .catch(error => console.error('Error loading books:', error));
}

// 이벤트 위임을 통해 동적으로 추가된 요소에 이벤트 리스너 추가
function setupEventDelegation() {
    const bookListItems = document.getElementById('book-list-items');

    bookListItems.addEventListener('click', function(event) {
        const bookItem = event.target.closest('.book-item');
        if (bookItem) {
            const bookId = bookItem.dataset.bookId; // data-book-id 속성에서 bookId 추출
            window.location.href = `/book/${bookId}`;
        }
    });
}

function changeSorting() {
    const primarySort = document.getElementById('primarySort').value;
    const sortOrder = document.getElementById('sortOrder').value;
    currentSort = `${primarySort},${sortOrder}`; // 현재 정렬 상태 업데이트
    currentPage = 0; // 페이지 번호 초기화

    loadBooks(currentSort, 0); // 새로 정렬된 책 목록 로드
}

function loadMoreBooks() {
    currentPage++; // 페이지 번호 증가

    loadBooks(currentSort, currentPage); // 기존 정렬 기준 유지하며 다음 페이지 로드
}