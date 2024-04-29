<template>
  <div >
    <div class="background-image-container" >
      <div>
        <div>
          <div>
            <img src="../../assets/222.png" alt="First Image" class="image1" style="width: 200px; height: 200px; position: absolute; top: 480px; left: 400px;" @click="$router.push('search')">
            <p style="position: absolute; top: 650px; left: 430px; font-size: 26px; color: black">分析局</p>
          </div>
          <div>
            <img src="@/assets/333.png" alt="Second Image" class="image2" style="width: 200px; height: 200px; position: absolute; bottom: 480px; right: 320px" @click="$router.push('personal')">
            <p style="position: absolute; bottom: 430px; right:380px; font-size: 26px; color: black">个人档案局</p>
          </div>
          <div>
            <img src="@/assets/444.png" alt="Third Image" class="image3" style="width: 200px; height: 200px; position: absolute; bottom: 470px; left: 300px" @click="$router.push('exchange')">
            <p style="position: absolute; bottom: 420px; left:330px; font-size: 26px; color: black">图书交易局</p>
          </div>
        </div>

        <div class="watermark" >&copy; Designd by Agency.Icons by icons8</div>


      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomePage",
  data() {
    return {};
  },
};
</script>

<style scoped>

.watermark {
  position: fixed;
  bottom: 40px; /* 距离底部的距离 */
  left: 20px; /* 距离左侧的距离 */
  font-size: 20px; /* 水印字体大小 */
  color: rgba(0, 0, 0, 0.1); /* 水印字体颜色 */
  z-index: 9999; /* 确保水印在其他元素之上 */
}


.background-image-container {
  background-image: url('@/assets/background/homepage.png');
  background-size: 90%; /* 使用 cover 让背景图片填满整个容器 */
  background-position: center; /* 图片居中显示 */
  height: 100vh; /* 使用视口高度作为容器高度 */
  width: 100vw; /* 使用视口宽度作为容器宽度 */
  display: flex;
  justify-content: center;
  align-items: center;
  color: white; /* 文字颜色 */
}


/* 针对内容的额外样式 */
.background-image-container > div {
  text-align: center;
}

.background-image-container h1 {
  font-size: 2em;
  margin-bottom: 10px;
}

.background-image-container p {
  font-size: 1.2em;
}

.image1 {
  position: absolute;
  top: 500px;
  left: 350px;
  transition: transform 0.3s ease-out;
}

.image2 {
  transition: transform 0.3s ease-out;
}

.image1:hover {
  transform: scale(1.1); /* 放大1.1倍 */
}

.image2:hover {
  transform: scale(1.1); /* 放大1.1倍 */
}

.image3 {
  transition: transform 0.3s ease-out;
}

.image3:hover {
  transform: scale(1.1); /* 放大1.1倍 */
}
</style>
