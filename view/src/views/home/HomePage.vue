<template>
  <div class="background-image-container">
    <div class="content-container">
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
  bottom: 40px;
  left: 20px;
  font-size: 20px;
  color: rgba(0, 0, 0, 0.1);
  z-index: 9999;
  user-select: none;
}

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
</style>
