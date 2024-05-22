<template>
  <div class="background-image-container">
    <div class="content-container">
      <!-- 添加夜间模式按钮，居中显示 -->
      <div class="image-container">
        <div>
          <img src="../../assets/222.png" alt="First Image" class="image image1" @click="$router.push('search')">
          <p class="image-label" style="top: 650px; left: 430px;">分析局</p>
        </div>
        <div>
          <img src="@/assets/333.png" alt="Second Image" class="image image2" @click="$router.push('personal')">
          <p class="image-label" style="bottom: 430px; right: 380px;">个人档案局</p>
        </div>
        <div>
          <img src="@/assets/444.png" alt="Third Image" class="image image3" @click="$router.push('exchange')">
          <p class="image-label" style="bottom: 420px; left: 330px;">图书交易局</p>
        </div>
      </div>
      <div class="watermark">&copy; Designed by Agency. Icons by icons8</div>
    </div>
    <Popup :visible.sync="isPopupVisible" />
  </div>
</template>

<script>
import Popup from '@/components/Popup.vue';

export default {
  name: "HomePage",
  components: {
    Popup
  },
  data() {
    return {
      isPopupVisible: false,
      isNightMode: false // 添加夜间模式状态
    };
  },
  methods: {
    // 切换夜间模式
    toggleNightMode() {
      this.isNightMode = !this.isNightMode;
      // 根据夜间模式状态修改整体颜色明亮度
      if (this.isNightMode) {
        document.body.classList.add('night-mode');
      } else {
        document.body.classList.remove('night-mode');
      }
    },
    checkTime() {
      const now = new Date();
      const hours = now.getHours();
      const minutes = now.getMinutes();
      // 条件修改为下午4点15分之后
      if (1) {
        this.isPopupVisible = true;
      }
    }
  }
};
</script>

<style scoped>
/* 夜间模式按钮样式 */
.night-mode-button {
  position: fixed;
  top: 20px; /* 距离顶部20px，可以根据需要调整 */
  left: 50%; /* 居中显示 */
  transform: translateX(-50%); /* 水平居中 */
  z-index: 1000; /* 确保按钮在最上层 */
}



/* 其他样式保持不变 */
.background-image-container {
  background-image: url('@/assets/background/homepage.png');
  background-size: 90%;
  background-position: center;
  height: 100vh;
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
}

.content-container {
  text-align: center;
  position: relative;
  width: 100%;
  height: 100%;
}

.image-container {
  position: relative;
  width: 100%;
  height: 100%;
}

.image {
  width: 200px;
  height: 200px;
  transition: transform 0.3s ease-out;
}

.image1 {
  position: absolute;
  top: 480px;
  left: 400px;
}

.image2 {
  position: absolute;
  bottom: 480px;
  right: 320px;
}

.image3 {
  position: absolute;
  bottom: 470px;
  left: 300px;
}

.image:hover {
  transform: scale(1.1);
}

.image-label {
  position: absolute;
  font-size: 26px;
  color: black;
  user-select: none;
}

.watermark {
  position: fixed;
  bottom: 40px;
  left: 20px;
  font-size: 20px;
  color: rgba(0, 0, 0, 0.1);
  z-index: 9999;
  user-select: none;
}
</style>
