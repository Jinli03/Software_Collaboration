<!--
*@Exchange
*@author Jinli
*@date 2024/4/22 11:21
-->
<template>
  <div>
    <template>
      <a-affix :offset-top="80">
        <a-button type="primary" @click="$router.push('HomePage')">返回到首页</a-button>
      </a-affix>
    </template>
    <div style="margin: 10px">
      <p>开始选择图书之旅吧！</p>
    </div>
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
  </div>
</template>



<script>
import Merchant from "@/components/Merchant.vue";
import Manager from "@/components/Manager.vue";
import User from "@/components/User.vue";
export default {
  components:{
    User, Manager, Merchant
  },
  name: 'search',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
      value: [300, 350],
      activeName: 'first',
    }
  },
  created() {
    if (!this.user.id) {
      this.$router.push('/login')
    }
    this.load()
  },
  mounted() {

  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    load(pageNum) {
      // Your load function
    },
    load1(pageNum) {
      // Your load1 function
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
  }
}
</script>


<style>

.background-image-container {
  background-image: url('@/assets/background/up.png');
  background-size: cover; /* 将背景图片铺满整个容器 */
  background-position: center; /* 图片居中显示 */
  height: 84vh; /* 调整容器的高度，根据需要更改 */
  background-size: 75%; /* 缩小背景图片 */
  display: flex;
  justify-content: center;
  align-items: center;
  color: white; /* 文字颜色 */
}

.like {
  cursor: pointer;
  font-size: 25px;
  display: inline-block;
}

.card-item {
  transition: transform 0.3s ease-in-out;
}

.card-item:hover {
  transform: scale(1.1);
}

</style>
