<template>
  <div>
    <div>
      <el-row>
        <el-col :span="4">
          <div style="display: flex">
            <div style="display: flex; flex-wrap: wrap; margin-top: 20px; height: auto;">
              <div style="margin-right: 10px; margin-bottom: 10px;" @click="purchase">
                <el-radio v-model="radio" label="1">购买</el-radio>
              </div>
              <div style="margin-right: 10px; margin-bottom: 10px;" @click="purchased">
                <el-radio v-model="radio" label="2">已购买</el-radio>
              </div>
              <div style="margin-right: 10px; margin-bottom: 10px;" @click="state='收藏';">
                <el-radio v-model="radio" label="3">收藏</el-radio>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="16">
          <p>`</p>
          <!-- Conditional content based on state -->
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
        <div v-if="state === '上架'">
          <!-- Display purchasing content -->
          <el-row :gutter="5" style="padding: 5px">
            <el-col :span="5" v-for="form in forms" :key="form.id" style="padding:12px">
              <el-card class="card-item" style="background-size: cover;width: 200px;height:300px">
                <div slot="header" class="header-image" :style="{ backgroundImage: 'url(' + form.picture + ')' }"></div>
                <div style="color: black;display: flex;">
                  <div style=";white-space: nowrap">
                    <span>{{ form.name }}</span><br>
                    <span>￥</span>
                    <span>{{ form.price }}</span>
                    <br>
                    <span>{{ form.sub }}</span>
                  </div>
                  <div style="display: flex; flex-direction: column; justify-content: space-between;position: relative;width: 100%;height: 100%">
                    <el-button icon="el-icon-plus" circle style="position: absolute;top: -15px;right: -5px" @click="handleAdd(form.id)"></el-button>
                    <el-button style="position: absolute;top:30px;right: -15px" slot="reference" @click="handleBuy(form.id)">购买</el-button>
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </div>
        <div v-if="state === '收藏'">
          <!-- Display collection content -->
          <!-- Add your collection content here -->
        </div>
        <div v-if="state === '已购买'">
          <h2>已购买页面</h2>
          <el-table :data="forms" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}" @selection-change="handleSelectionChange" style="width: 100%">
            <el-table-column type="selection" align="center"></el-table-column>
            <el-table-column label="id" prop="id" align="center"></el-table-column>
            <el-table-column label="商家" prop="boss" align="center"></el-table-column>
            <el-table-column label="书名" prop="name" align="center"></el-table-column>
            <el-table-column label="价格" prop="price" align="center"></el-table-column>
            <el-table-column label="科目" prop="sub" align="center"></el-table-column>
            <el-table-column label="描述" prop="des" align="center"></el-table-column>
          </el-table>
        </div>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "User",
  data() {
    const user =  JSON.parse(localStorage.getItem('pilot') || '{}');
    return {
      radio: '1',
      user: user,
      name: '',
      forms: [],
      pageNum: 1,
      pageSize: 12,
      total: 0, // 添加total属性
      state: '购买' // 初始化为 '购买'
    }
  },
  mounted() {
    this.purchase()
  },
  methods: {
    purchase() {
      this.state = "上架"
      this.checkBooksState()
    },
    purchased() {
      this.state = "已购买";
      this.checkUserState();
    },
    checkBooksState() {
      this.$request.get(`/exchange/selectByNotBoughtBooks`, {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          state: this.state,
        }
      })
          .then(res => {
            // 请求成功处理
            console.log(res.data.records) // 打印返回的数据
            // 这里可以将返回的数据赋值给组件中的 data 属性，供页面渲染使用
            this.forms = res.data.records
            this.total = res.data.total;
          })
          .catch(error => {
            // 请求失败处理
            console.error('Error fetching books:', error);
          });
    },
    checkUserState() {
      this.$request.get(`/exchange/selectByUserState`, {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          state: this.state,
          buyer: this.user.name
        }
      })
          .then(res => {
            // 请求成功处理
            console.log(res.data.records); // 打印返回的数据
            // 这里可以将返回的数据赋值给组件中的 data 属性，供页面渲染使用
            this.forms = res.data.records;
            this.total = res.data.total;
          })
          .catch(error => {
            // 请求失败处理
            console.error('Error fetching books:', error);
          });
    },

    handleAdd(id) {
      this.$request.put('/exchange/AddToShopCar/' + id, { shopcar:'是' }).then(res => {
        if (res.code === '200') {
          this.load(1);
          this.$notify({
            message: "成功加入购物车",
            type: 'success'
          });
        } else {
          this.$message.error(res.msg);
        }
      });
    },

    handleBuy(id) {
      const buyerName = this.user.name; // Assuming this.user contains buyer information
      this.$confirm('确定购买?', '确认', {type: "warning"}).then(response => {
        this.$request.put('/exchange/purchaseShelves/' + id, null, { params: { name: buyerName } }).then(res => {
          if (res.code === '200') {
            this.purchase()
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
        this.pageNum = pageNum;
      }
      this.$request.get('/exchange/allBooks', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        if (res.data) {
          this.forms = res.data.records || res.data; // Update here to handle nested data
          this.total = res.data.total || res.total; // Update here to handle nested total
        } else {
          this.forms = [];
          this.total = 0;
        }
      });
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      if (this.state === '已购买') {
        this.checkMerchantState();
      } else {
        this.load(pageNum); // 传递pageNum参数
      }
    },
  },
}
</script>

<style>
.header-image {
  background-size: cover;
  background-position: center;
  color: black;
  height: 170px;
}
</style>
