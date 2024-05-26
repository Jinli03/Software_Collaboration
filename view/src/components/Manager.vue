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
            <div style="margin-right: 10px; margin-bottom: 10px;" @click="noChecked">
              <el-radio v-model="radio" label="1">待检查</el-radio>
            </div>
            <div style="margin-right: 10px; margin-bottom: 10px;" @click="onShelves">
              <el-radio v-model="radio" label="2">上架</el-radio>
            </div>
            <div style="margin-right: 10px; margin-bottom: 10px;" @click="bought">
              <el-radio v-model="radio" label="3">已购买</el-radio>
            </div>
            <div style="margin-right: 10px; margin-bottom: 10px;" @click="state='不合规';">
              <el-radio v-model="radio" label="4">不合规</el-radio>
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

    <el-row>
      <div v-if="state === '待检查'">
        <h2>待检查页面</h2>
        <el-table :data="forms" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}" @selection-change="handleSelectionChange" style="width: 100%">
          <el-table-column type="selection" align="center"></el-table-column>
          <el-table-column label="id" prop="id" align="center"></el-table-column>
          <el-table-column label="商家" prop="boss" align="center"></el-table-column>
          <el-table-column label="书名" prop="name" align="center"></el-table-column>
          <el-table-column label="价格" prop="price" align="center"></el-table-column>
          <el-table-column label="科目" prop="sub" align="center"></el-table-column>
          <el-table-column label="描述" prop="des" align="center"></el-table-column>
          <el-table-column label="状态" align="center">
            <template v-slot="scope">
              <el-button size="mini" type="warning" plain disabled>待检查</el-button>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template v-slot="scope">
              <el-button size="mini" type="primary" plain @click="handleUp(scope.row.id)">上架</el-button>
              <el-button size="mini" type="danger" plain @click="handleDown(scope.row.id)">不合规</el-button>
            </template>
          </el-table-column>
          <!--          <el-table-column label="操作" align="center">-->
          <!--            <template v-slot="scope">-->
          <!--              <el-button size="mini" type="danger" plain @click="handleRemove(scope.row.id)">下架</el-button>-->
          <!--            </template>-->
          <!--          </el-table-column>-->
        </el-table>
      </div>
      <div v-if="state === '上架'">
        <h2>上架页面</h2>
        <el-table :data="forms" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}" @selection-change="handleSelectionChange" style="width: 100%">
          <el-table-column type="selection" align="center"></el-table-column>
          <el-table-column label="id" prop="id" align="center"></el-table-column>
          <el-table-column label="商家" prop="boss" align="center"></el-table-column>
          <el-table-column label="书名" prop="name" align="center"></el-table-column>
          <el-table-column label="价格" prop="price" align="center"></el-table-column>
          <el-table-column label="科目" prop="sub" align="center"></el-table-column>
          <el-table-column label="描述" prop="des" align="center"></el-table-column>
          <el-table-column label="状态" align="center">
            <template v-slot="scope">
              <el-button size="mini" type="primary" plain disabled>上架</el-button>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template v-slot="scope">
              <el-button size="mini" type="danger" plain @click="handleRemove(scope.row.id)">下架</el-button>
            </template>
          </el-table-column>
        </el-table>
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
          <el-table-column label="状态" align="center">
            <template v-slot="scope">
              <el-button size="mini" type="success" plain disabled>上架</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

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
      pageSize: 8,
      total: 0, // 添加total属性
      date: 'primary'
    }
  },
  created() {
  },
  mounted() {
    this.load(1)
    this.noChecked()
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    noChecked() {
      this.state = "待检查"
      this.checkMerchantState()
    },
    onShelves() {
      this.state = "上架"
      this.checkState()
    },
    removeShelves() {
      this.state = "下架"
      this.checkState()
    },
    bought() {
      this.state = "已购买"
      this.checkState()
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
          this.forms = res.data.records;
          console.log('Response data:', this.forms);
          this.total = res.data.total;
        } else {
          this.forms = [];
          this.total = 0;
        }
      });
    },
    checkState() {
      this.$request.get(`/exchange/selectByState/${this.state}`)
          .then(res => {
            // 请求成功处理
            console.log(res.data); // 打印返回的数据
            // 这里可以将返回的数据赋值给组件中的 data 属性，供页面渲染使用
            this.forms = res.data
          })
          .catch(error => {
            // 请求失败处理
            console.error('Error fetching books:', error);
          });
    },

    checkMerchantState() {
      this.$request.get(`/exchange/selectByManagerState`, {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          state: this.state
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

    handleUp(id) {
      this.$confirm('确定上架?', '确认', {type: "warning"}).then(response =>{
        this.$request.delete('/exchange/upShelves/' + id).then(res =>{

          if (res.code === '200') {
            this.load(1)
            this.$message.success('成功')
            this.checkMerchantState()
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {})
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.checkMerchantState() // 传递pageNum参数
    },
  },
}
</script>

<style>

</style>
