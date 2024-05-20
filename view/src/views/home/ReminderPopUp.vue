<template>
  <transition name="fade" mode="out-in">
    <div v-if="isVisible" class="reminder-popup" :class="{ 'animated': isAnimated }">
      <div class="moon">
        <div class="crater crater1"></div>
        <div class="crater crater2"></div>
        <div class="crater crater3"></div>
      </div>
      <div class="star star1"></div>
      <div class="star star2"></div>
      <div class="star star3"></div>
      <div class="text-content">
        主人主人，已经很晚了，注意保持健康哦！
      </div>
      <button class="close-btn" @click="closePopup">✕</button>
    </div>
  </transition>
</template>



<script>
export default {
  props: ['visible'],
  data() {
    return {
      isVisible: this.visible,
      isAnimated: true  // 控制动画
    };
  },
  watch: {
    visible(newVal) {
      this.isVisible = newVal;
      if (!newVal) {
        this.isAnimated = false;  // 在准备关闭时去掉动画类
      }
    }
  },
  methods: {
    closePopup() {
      this.isVisible = false;
      this.$emit('update:visible', false);
    }
  }
}
</script>


<style scoped>
.animated {
  animation: fadeInUp 0.5s ease-out forwards;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s ease;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.reminder-popup {
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: fixed;
  bottom: 20px;
  right: 20px;
  padding: 20px;
  border-radius: 10px;
  background: #003366; /* Deep blue background */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  color: white;
  z-index: 1000;
  animation: fadeInUp 0.5s ease-out forwards;
  user-select: none; /* Prevent text selection */
}

.moon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background: #f0e68c; /* Moon-like color */
  position: absolute;
  left: 20px;
  /* 添加发光效果 */
  box-shadow: 0 0 20px 10px rgba(255, 255, 224, 0.5); /* 淡黄色光环 */
}

.crater {
  position: absolute;
  border-radius: 50%;
  background-color: rgba(0, 51, 102, 0.6); /* Darker shade for craters */
}

.crater1 {
  width: 12px;
  height: 12px;
  top: 15px;
  left: 15px;
}

.crater2 {
  width: 8px;
  height: 8px;
  top: 10px;
  right: 18px;
}

.crater3 {
  width: 10px;
  height: 10px;
  bottom: 15px;
  left: 30px;
}

.star {
  width: 10px;
  height: 10px;
  background: white;
  position: absolute;
  clip-path: polygon(50% 0%, 61% 35%, 98% 35%, 68% 57%, 79% 91%, 50% 70%, 21% 91%, 32% 57%, 2% 35%, 39% 35%);
}

.star1 {
  top: 10px;
  right: 50px;
}

.star2 {
  top: 30px;
  right: 110px;
}

.star3 {
  top: 50px;
  right: 80px;
}

.text-content {
  margin-left: 80px; /* Adjusted margin to accommodate larger moon */
  font-size: 16px;
}

.close-btn {
  font-size: 24px;
  border: none;
  background: none;
  color: white;
  cursor: pointer;
}
</style>
