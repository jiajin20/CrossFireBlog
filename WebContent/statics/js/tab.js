document.addEventListener("DOMContentLoaded", function() {
    const menuBox = document.querySelector('.menu-box');
    const menuButton = document.querySelector('.menu-button');

    menuButton.addEventListener('click', function() {
        menuBox.classList.toggle('active');
    });

    let isDragging = false;
    let startX, startY, initialX, initialY;

    menuBox.addEventListener('mousedown', (e) => {
        isDragging = true;
        startX = e.clientX;
        startY = e.clientY;
        initialX = menuBox.offsetLeft;
        initialY = menuBox.offsetTop;
        menuBox.style.cursor = 'grabbing';

        const onMouseMove = (e) => {
            if (isDragging) {
                const dx = e.clientX - startX;
                const dy = e.clientY - startY;
                menuBox.style.left = `${initialX + dx}px`;
                menuBox.style.top = `${initialY + dy}px`;
            }
        };

        const onMouseUp = () => {
            isDragging = false;
            menuBox.style.cursor = 'grab';
            document.removeEventListener('mousemove', onMouseMove);
            document.removeEventListener('mouseup', onMouseUp);
        };

        document.addEventListener('mousemove', onMouseMove);
        document.addEventListener('mouseup', onMouseUp);
    });
});
