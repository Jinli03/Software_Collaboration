<!--
*@manager
*@author Jinli
*@date 2024/5/13 10:55
-->
<template>
  <div>
    <el-row>
      <el-col :span="6">
        <div style="display: flex">
          <div style="display: flex; flex-wrap: wrap; margin-top: 20px; height: auto;">
            <div style="margin-right: 10px; margin-bottom: 10px;" @click="state='待检查';">
              <el-radio v-model="radio" label="1">待检查</el-radio>
            </div>
            <div style="margin-right: 10px; margin-bottom: 10px;" @click="state='上架';">
              <el-radio v-model="radio" label="2">上架</el-radio>
            </div>
            <div style="margin-right: 10px; margin-bottom: 10px;" @click="state='已购买';">
              <el-radio v-model="radio" label="3">已购买</el-radio>
            </div>
            <div style="margin-right: 10px; margin-bottom: 10px;" @click="state='下架';">
              <el-radio v-model="radio" label="4">下架</el-radio>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="14">
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
</template>

<script>
export default {
  name: "Manager",
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
      date: 'primary'
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
