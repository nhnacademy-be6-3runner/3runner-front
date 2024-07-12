// 현재 페이지 번호와 정렬 상태를 추적하는 전역 변수
let currentPage = 0;
let currentKeyword;


function loadBooks(keyword, page = 0) {

    currentKeyword = keyword;
    const url = `/api/books/search?keyword=${keyword}&page=${page}`;
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
                    </div>
                `;
                bookItem.addEventListener('click', () => {
                    window.location.href = `/book/${book.id}`;
                });
                bookListItems.appendChild(bookItem);
            });
        })
        .catch(error => console.error('Error loading books:', error));
}

function changeKeyword(keyword) {
    currentPage = 0; // 페이지 번호 초기화
    loadBooks(keyword, currentPage); // 새로 정렬된 책 목록 로드
}


function loadMoreBooks() {

    currentPage++; // 페이지 번호 증가
    console.log("Current Page:", currentPage);
    loadBooks(currentKeyword, currentPage); // 기존 정렬 기준 유지하며 다음 페이지 로드
}
