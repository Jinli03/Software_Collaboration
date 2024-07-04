<template>
  <el-container class="background-image-container">
    <div style="backdrop-filter: blur(8px); width: 95%; height: 95%; border-radius: 2%;">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="图书商城" name="first" @click="load(1)">
          <User/>
        </el-tab-pane>
        <el-tab-pane label="检查" name="second" v-if="user.role === '管理员'" @click="load1(1)">
          <Manager/>
        </el-tab-pane>
        <el-tab-pane label="上传" name="third" v-if="user.role === '商家'">
          <Merchant/>
        </el-tab-pane>
      </el-tabs>
    </div>
  </el-container>
</template>

<script>
import Merchant from "@/components/Merchant.vue";
import User from "@/components/User.vue";
import Manager from "@/components/Manager.vue";

export default {
  name: "HomePage",
  components: {Manager, User, Merchant},
  data() {
    return {
      drawer:false,
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
      value: [300, 350],
      activeName: 'first',
      forms:[],
      shopcar:[]
    };
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
