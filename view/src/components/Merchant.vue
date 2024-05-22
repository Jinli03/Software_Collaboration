<!--
*@merchant
*@author Jinli
*@date 2024/5/13 10:55
-->
<template>
  <div>
    <el-row>
      <el-col :span="6">
        <div style="display: flex">
          <el-row>
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
              <div style="margin-right: 10px; margin-bottom: 10px;" @click="state='新增';">
                <el-radio v-model="radio" label="5">新增</el-radio>
              </div>
            </div>
          </el-row>
        </div>
      </el-col>
      <el-col :span="12">
        .
      </el-col>
      <el-col :span="2">
        <el-button type="success" plain @click="handleAdd">新增</el-button>
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
    <el-row>
      <div v-if="state === '待检查'">
        <h2>待检查页面</h2>
        <!-- 这里可以放置待检查页面的内容 -->
      </div>
      <div v-if="state === '上架'">
        <h2>上架页面</h2>
        <!-- 这里可以放置上架页面的内容 -->
      </div>
      <div v-if="state === '已购买'">
        <h2>已购买页面</h2>
        <!-- 这里可以放置已购买页面的内容 -->
      </div>
      <div v-if="state === '下架'">
        <h2>下架页面</h2>
        <!-- 这里可以放置下架页面的内容 -->
      </div>
      <div v-if="state === '新增'">
        <h2>新增页面</h2>
        <el-card style="width: 50%">
          <el-form :model="form" :rules="rules" ref="formRef" label-width="80px" style="padding-right: 20px">
            <el-form-item label="商家" prop="boss">
              <el-input v-model="form.boss" :placeholder="form.boss"></el-input>
            </el-form-item>
            <el-form-item label="书名" prop="name">
              <el-input v-model="form.school" placeholder="请输入书名"></el-input>
            </el-form-item>
            <el-form-item label="价格" prop="price">
              <el-input v-model="form.school" placeholder="请输入价格"></el-input>
            </el-form-item>
            <el-form-item label="科目" prop="sub">
              <el-select style="width: 100%" v-model="form.sub">
                <el-option v-for="item in ['专业课', '高数', '英语', '政治']" :key="item" :value="item"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="描述" prop="des">
              <el-input v-model="form.des" placeholder="请输入描述"></el-input>
            </el-form-item>
          </el-form>

            <el-button @click="formVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>

        </el-card>
        <!-- 这里可以放置下架页面的内容 -->
      </div>

    </el-row>
  </div>
</template>

<script>
export default {
  name: "Merchant",
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
      date: 'primary',
      formVisible: false,
      form: {
        title: '',
        description: '',
        content: '',
      },
      state: '待检查'
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
    handleAdd() {
      this.form = {}
      this.formVisible = true
    },
  },
}
</script>

<style>

</style>
