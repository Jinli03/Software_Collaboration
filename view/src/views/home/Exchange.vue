<!--
*@Exchange
*@author Jinli
*@date 2024/4/22 11:21
-->
<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="20">
        <template>
          <a-affix :offset-top="80">
            <a-button type="primary" @click="$router.push('Personal')">返回到个人档案局</a-button>
          </a-affix>
        </template>
      </el-col>
      <el-col :span="4">
        <div style="margin: 10px 0">
          <el-pagination
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-sizes="[100, 200, 300, 400]"
              :page-size="pageSize"
              layout="total,prev, pager, next"
              :total="total">
          </el-pagination>
        </div>
      </el-col>
    </el-row>
    <el-container class="background-image-container">
      <div style="backdrop-filter: blur(8px); width: 95%; height: 95%; border-radius: 2%;">
        <el-row :gutter="20" style="padding: 8px">
          <el-col :span="6" v-for="form in forms" :key="form.id" style="padding: 10px">
            <el-card class="card-item" style="background-size: cover" >
              <div slot="header" style="color: black;">
                <img :src="form.pic || 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'" alt="" style="width: 30px; height: 30px; border-radius: 50%; margin: 2px">
                <span>{{ form.name }}</span>
                <span style="float: right;">{{ form.sub }}</span>
              </div>
              <div style="color: black;">
                <div slot="footer">
                  <span>{{ form.des }}</span><br>
                  <span>#</span>
                  <span>{{ form.price }}</span>
                </div>
                <!-- 在这里添加更多数据 -->
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Exchange",
  props: {},
  components: {},
  data() {
    return {
      forms: [],
      pageNum: 1,
      pageSize: 12,
      total: 0, // 添加total属性
    }
  },
  created() {
  },
  mounted() {
    this.load(1)
  },
  methods: {
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
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load(pageNum); // 传递pageNum参数
    },
  },
}
</script>

<style>
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
</style>
