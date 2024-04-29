<template>
  <div class="not-found-container">
    <h1 class="not-found-title">404 找不到页面</h1>
    <p class="not-found-description">对不起，您请求的页面不存在。</p>
    <router-link to="/" class="back-to-home-link">返回至主页</router-link>
    <div class="image-container">
      <img src="@/assets/404.png" alt="404 Not Found" class="not-found-image">
    </div>
  </div>
</template>

<script>
export default {
  name: "NotFound",
  props: {},
  components: {},
  data() {
    return {};
  },
  mounted: {},
  methods: {},
};
</script>

<style scoped>
.not-found-container {
  text-align: center;
  padding: 50px;
}

.image-container {
  background-color: white;
  width: 50%;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  border-radius: 8px;
}

.not-found-image {
  max-width: 100%;
  height: auto;
  margin-bottom: 20px;
}

.not-found-title {
  font-size: 32px;
  margin-bottom: 10px;
}

.not-found-description {
  font-size: 18px;
  color: #888;
  margin-bottom: 30px;
}

.back-to-home-link {
  font-size: 16px;
  color: #1890ff;
  text-decoration: none;
  &:hover {
    text-decoration: underline;
  }
}
</style>
