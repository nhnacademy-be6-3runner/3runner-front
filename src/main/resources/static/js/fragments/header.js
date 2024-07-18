document.addEventListener('DOMContentLoaded', async function () {
    try {
        const response = await fetch('/api/categories');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();
        console.log('Fetched categories:', data); // 데이터가 제대로 오는지 확인

        const categoryContainer = document.getElementById('categoryMenu');
        const categoryMap = new Map();

        data.forEach(category => {
            categoryMap.set(category.id, {
                ...category,
                parentId: null,
                parentName: null
            });
            createCategoryTree(category, categoryContainer, categoryMap, null, null);
        });
    } catch (error) {
        console.error('카테고리 로드 실패:', error);
    }

    function createCategoryTree(category, parentElement, categoryMap, parentId, parentName) {
        const li = document.createElement('li');
        li.className = 'nav-item';

        const a = document.createElement('a');
        a.className = 'nav-link';
        a.textContent = category.name;
        a.href = `#${category.name}`;
        a.onclick = (event) => {
            event.preventDefault();
            selectCategory(category.id, category.name);
        };

        if (parentId !== null) {
            categoryMap.set(category.id, {
                ...category,
                parentId: parentId,
                parentName: parentName
            });
        }

        if (category.childrenList && category.childrenList.length > 0) {
            li.className += ' dropdown-submenu';
            a.className += ' dropdown-toggle';
            li.appendChild(a);

            const ul = document.createElement('ul');
            ul.className = 'dropdown-menu nested';
            category.childrenList.forEach(child => {
                createCategoryTree(child, ul, categoryMap, category.id, category.name);
            });
            li.appendChild(ul);
        } else {
            li.appendChild(a);
        }
        parentElement.appendChild(li);
    }

    function selectCategory(id, name) {
        window.location.href = `/categories/books?categoryId=${id}`;
    }
});
