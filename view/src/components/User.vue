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

    <el-row :gutter="5" style="padding: 5px">
      <el-col :span="5" v-for="form in forms" :key="form.id" style="  padding:12px">
        <el-card class="card-item" style="background-size: cover;width: 200px;height:300px" >
          <div slot="header" class="header-image"  :style="{ backgroundImage: 'url(' + form.picture + ')' }">


          </div>
          <div style="color: black;display: flex;">


            <div style=";white-space: nowrap">
              <span>{{ form.name}}</span><br>
              <span>￥</span>
              <span>{{ form.price }}</span>
              <br>
              <span >{{ form.sub }}</span>
            </div>


            <div style="display: flex; flex-direction: column; justify-content: space-between;position: relative;width: 100%;height: 100%">
              <el-button  icon="el-icon-plus" circle style="position: absolute;top: -15px;right: -5px" @click="open1"></el-button>
              <el-popconfirm  title="确认购买吗？">
                <el-button style="position: absolute;top:30px;right: -15px" slot="reference" >购买</el-button>
              </el-popconfirm>
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
    open1(){
      this.$notify({
        title: '成功',
        message: '成功加入购物车',
        type: 'success'
      });
    },

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
.header-image{
  background-size: cover;
  background-position: center;
  color: black;
  height: 170px;
}
</style>