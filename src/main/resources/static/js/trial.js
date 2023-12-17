const slides = document.querySelectorAll('.slide');
const slider = document.querySelector('.slider');
const prevBtn = document.querySelector('.prev-btn');
const nextBtn = document.querySelector('.next-btn');
let counter = 0;
const slideWidth = slides[0].clientWidth;
let isTransitioning = false;

prevBtn.addEventListener('click', () => {
  if (!isTransitioning) {
    counter--;
    if (counter < 0) {
      counter = slides.length - 1;
    }
    transitionSlider();
  }
});

nextBtn.addEventListener('click', () => {
  if (!isTransitioning) {
    counter++;
    if (counter >= slides.length) {
      counter = 0;
    }
    transitionSlider();
  }
});

function transitionSlider() {
  isTransitioning = true;
  slider.style.transition = 'transform 0.5s ease-in-out';
  slider.style.transform = `translateX(${-slideWidth * counter}px)`;

  slider.addEventListener('transitionend', () => {
    isTransitioning = false;
    slider.style.transition = ''; // Remove transition once slide transition finishes
  }, { once: true });
}

// Automatic slide change (if needed)
const automaticSlideChange = () => {
  setInterval(() => {
    if (!isTransitioning) {
      counter++;
      if (counter >= slides.length) {
        counter = 0;
      }
      transitionSlider();
    }
  }, 3000);
};

automaticSlideChange(); // Uncomment if you want automatic sliding
