<!--
*@ForumInfo
*@author Jinli
*@date 2024/4/15 11:30
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
                <img :src="form.avatr || 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'" alt="" style="width: 30px; height: 30px; border-radius: 50%; margin: 2px">
                <span>{{ form.author }}</span>
                <span style="float: right;">{{ form.time }}</span>
              </div>
              <div style="color: black;">
                <div slot="footer">
                  <span>{{ form.comment }}</span><br>
                  <span>#</span>
                  <span>{{ form.tag }}</span>
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
  name: "Foruminfo",
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
  mounted() {
    this.load(1)
  },
  methods: {
    load(pageNum) {
      if (pageNum) {
        this.pageNum = pageNum
      }
      this.$request.get('/forum/allComments', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        if (res.data) {
          this.forms = res.data.records;
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
.card-item:hover {
  transform: scale(1.1);
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
</style>
