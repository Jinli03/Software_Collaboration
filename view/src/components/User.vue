<!--
*@user
*@author Jinli
*@date 2024/5/13 10:55
-->
<template>
  <div>
    <div>
      <el-row>
        <el-col :span="4">
          <div style="display: flex">
            <div style="display: flex; flex-wrap: wrap; margin-top: 20px; height: auto;">
              <div style="margin-right: 10px; margin-bottom: 10px;" @click="state='购买';">
                <el-radio v-model="radio" label="1">购买</el-radio>
              </div>
              <div style="margin-right: 10px; margin-bottom: 10px;" @click="state='已购买';">
                <el-radio v-model="radio" label="2">已购买</el-radio>
              </div>
              <div style="margin-right: 10px; margin-bottom: 10px;" @click="state='收藏';">
                <el-radio v-model="radio" label="3">收藏</el-radio>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="16">
          .
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
    </div>

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
              <el-button style="float: right">购买</el-button>
              <el-button :type="date">goumai</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "User",
  props: {},
  components: {},
  data() {
    return {
      radio: '1',
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
      forms: [],
      pageNum: 1,
      pageSize: 12,
      total: 0, // 添加total属性
      state: ''
    }
  },
  created() {
  },
  mounted() {
    this.load(1)
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
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
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load(pageNum); // 传递pageNum参数
    },
  },
}
</script>

<style>

</style>
