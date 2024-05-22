<template>
  <div class="not-found-container">
    <div class="text-content">
      <h1 class="not-found-title">404 找不到页面</h1>
      <p class="not-found-description">对不起，您请求的页面不存在。</p>
      <router-link to="/" class="back-to-home-link">返回至主页</router-link>
    </div>
    <div class="image-container">
      <img src="@/assets/404.png" alt="404 Not Found" class="not-found-image">
    </div>
  </div>
</template>

<script>
export default {
  name: "NotFound",
};
</script>

<style scoped>
.not-found-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f0f2f5;
  padding: 20px;
  text-align: center;
}

.text-content {
  margin-bottom: 20px;
}

.image-container {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.not-found-image {
  max-width: 100%;
  height: auto;
}

.not-found-title {
  font-size: 48px;
  color: #333;
  margin-bottom: 10px;
  animation: fadeInDown 1s ease-in-out;
}

.not-found-description {
  font-size: 20px;
  color: #666;
  margin-bottom: 20px;
  animation: fadeInUp 1s ease-in-out;
}

.back-to-home-link {
  font-size: 18px;
  color: #1890ff;
  text-decoration: none;
  transition: color 0.3s;
  &:hover {
    color: #40a9ff;
    text-decoration: underline;
  }
}

@keyframes fadeInDown {
  0% {
    opacity: 0;
    transform: translateY(-20px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeInUp {
  0% {
    opacity: 0;
    transform: translateY(20px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
