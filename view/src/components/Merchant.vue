<!--
*@merchant
*@author Jinli
*@date 2024/5/13 10:55
-->
<template>
  <div>
    <el-row>
      <el-col :span="8">
        <div style="display: flex">
          <el-row>
            <div style="display: flex; flex-wrap: wrap; margin-top: 20px; height: auto;">
              <div style="margin-right: 10px; margin-bottom: 10px;" @click="noChecked">
                <el-radio v-model="radio" label="1" >待检查</el-radio>
              </div>
              <div style="margin-right: 10px; margin-bottom: 10px;" @click="onShelves">
                <el-radio v-model="radio" label="2">上架</el-radio>
              </div>
              <div style="margin-right: 10px; margin-bottom: 10px;" @click="bought">
                <el-radio v-model="radio" label="3">已购买</el-radio>
              </div>
              <div style="margin-right: 10px; margin-bottom: 10px;" @click="removeShelves">
                <el-radio v-model="radio" label="4">下架</el-radio>
              </div>
              <div style="margin-right: 10px; margin-bottom: 10px;" @click="wrong">
                <el-radio v-model="radio" label="5">不合规</el-radio>
              </div>
              <div style="margin-right: 10px; margin-bottom: 10px;" @click="newOne">
                <el-radio v-model="radio" label="6">新增</el-radio>
              </div>
            </div>
          </el-row>
        </div>
      </el-col>
      <el-col :span="10">
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
      <div v-if="state === '下架'">
        <h2>下架页面</h2>
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
              <el-button size="mini" type="danger" plain disabled>下架</el-button>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template v-slot="scope">
              <el-button size="mini" type="primary" plain @click="handleUp(scope.row.id)">上架</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-if="state === '不合规'">
        <h2>下架页面</h2>
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
              <el-button size="mini" type="danger" plain disabled>不合规</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-if="state === '新增'">
        <h2>新增页面</h2>
        <el-card style="width: 50%">
          <el-form :model="form" :rules="rules" ref="formRef" label-width="80px" style="padding-right: 20px">
            <el-form-item label="商家" prop="boss">
              <el-input v-model="form.boss" readonly></el-input>
            </el-form-item>
            <el-form-item label="书名" prop="name">
              <el-input v-model="form.name" placeholder="请输入书名"></el-input>
            </el-form-item>
            <el-form-item label="价格" prop="price">
              <el-input v-model="form.price" placeholder="请输入价格"></el-input>
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
          <el-button type="primary" @click="post">上 传</el-button>
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
    const user = JSON.parse(localStorage.getItem('pilot') || '{}');
    return {
      radio: '1',
      user: user,
      pageNum: 1,
      pageSize: 8,
      total: 0, // 添加total属性
      date: 'primary',
      formVisible: false,
      form: {
        boss: user.name || '' // 初始化 form.boss 为 user.name
      },
      forms: {

      },
      rules: {
        name: [
          {required: true, message: '请输入书名', trigger: 'blur'}
        ],
        price: [
          {required: true, message: '请输入价格', trigger: 'blur'}
        ],
        sub: [
          {required: true, message: '请输入科目', trigger: 'blur'}
        ],
        des: [
          {required: true, message: '请输入描述', trigger: 'blur'}
        ],
      },
      state: '待检查',
    }
  },
  created() {
  },

  mounted() {
    this.load(1)
    this.noChecked()
  },
  methods: {
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
    wrong() {
      this.state = "不合规"
      this.checkMerchantState()
    },
    newOne() {
      this.state = "新增"
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
      this.$request.get(`/exchange/selectByMerchantState`, {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          state: this.state,
          boss: this.user.name
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
    handleRemove(id) {
      this.$confirm('确定下架?', '确认', {type: "warning"}).then(response =>{
        this.$request.delete('/exchange/removeShelves/' + id).then(res =>{

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

    post() {
      // 上传表单数据
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request.post('/exchange/addBook', this.form)
              .then(res => {
                if (res.code === '200') { // 修改这里的变量名为 response
                  this.$message.success('上传成功');
                  this.form = {}
                } else {
                  this.$message.error(res.msg); // 修改这里的变量名为 response
                }
              })
              .catch(error => {
                // 处理异常
                console.error('上传失败:', error);
                this.$message.error('上传失败');
              });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.checkMerchantState(); // 传递pageNum参数
    },
  }
}
</script>

<style>

</style>
