<template>
  <div class="background-image-container">
    <div class="content-container">
      <div class="image-container">
        <!-- 图片和链接省略 -->
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

  },
  mounted() {
    // 监听页面是否超过20分钟未操作
    this.checkInactiveTime();
  },

    checkInactiveTime() {
      // 监听用户是否操作页面
      window.addEventListener("mousemove", this.resetTimer);
      window.addEventListener("keypress", this.resetTimer);
      window.addEventListener("scroll", this.resetTimer);

      // 检查停留时间是否超过20分钟
      setInterval(() => {
        const currentTime = new Date().getTime();
        const elapsedTime = currentTime - this.startTime;
        const elapsedTimeMinutes = Math.floor(elapsedTime / 60000); // 转换为分钟
        if (elapsedTimeMinutes >= 20) {
          this.isPopupVisible = true;
        }
      }, 1000); // 每秒检查一次
    },
    resetTimer() {
      // 重置计时器
      this.startTime = new Date().getTime();
    }
  }
};
</script>

<style scoped>
/* 夜间模式按钮样式和其他样式省略 */
</style>
