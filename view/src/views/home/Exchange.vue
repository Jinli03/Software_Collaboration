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
        :with-header="false"
        width="50%">
      <span>购物车</span>
      <el-table :data="forms" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}" @selection-change="handleSelectionChange" >
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column label="id" prop="id" align="center"></el-table-column>
        <el-table-column label="书名" prop="name" align="center"></el-table-column>
        <el-table-column label="价格" prop="price" align="center"></el-table-column>
        <el-table-column label="科目" prop="sub" align="center"></el-table-column>
        <el-table-column label="商家" prop="boss" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template v-slot="scope">
            <el-button size="mini" type="danger" plain @click="handlePurchase(scope.row.id)">购买</el-button>
          </template>
        </el-table-column>
      </el-table>
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
      forms:[],
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

    //购买
    handlePurchase(id) {
      this.$confirm('确定购买?', '确认', {type: "warning"}).then(response => {
        this.$request.put('/exchange/purchaseShelves/' + id, { state: '已购买' }).then(res => {
          if (res.code === '200') {
            this.load(1);
            this.$message.success('购买成功');
            // 这里可以执行其他操作或者刷新页面等
          } else {
            this.$message.error(res.msg);
          }
        }).catch(error => {
          console.error('购买请求失败:', error);
          this.$message.error('购买请求失败');
        });
      }).catch(() => {});
    },

    load(pageNum) {
      if (pageNum) {
        this.pageNum = pageNum
      }
      this.$request.get('/exchange/allBooks', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        if (res.data) {
          this.forms = res.data;
          console.log('Response data:', this.forms);
          this.total = res.data.total;
        } else {
          this.forms = [];
          this.total = 0;
        }
      });
    },


    handleClick(tab, event) {
      console.log(tab, event);
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
