<!--
*@Exchange
*@author Popeyeeee111
*@date 2024/5/27 17:06
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
    <!--购物车-->
    <div class="fixed-button-container">
      <el-button circle size="medium" icon="el-icon-shopping-cart-1" @click="drawer = true" />
    </div>

    <el-drawer
        title="我是标题"
        :visible.sync="drawer"
        :with-header="false">
      <span>购物车</span>
    </el-drawer>

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
      drawer:false,
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
.fixed-button-container {
  position: fixed;
  bottom: 40px;
  right: 40px;

  z-index: 1000; /* 确保按钮在其他内容之上 */
}


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
