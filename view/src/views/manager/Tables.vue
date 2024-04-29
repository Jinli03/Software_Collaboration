<!--
*@User
*@author Jinli
*@date 2024/2/21 15:38
-->
<template>
  <div>
    <div style="margin-bottom: 10px">
      <el-input style="width: 200px" placeholder="查询城市" v-model="city"></el-input>
      <el-button type="primary" @click="load(1)" style="margin-left: 10px">查询</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>
    <div style="margin-bottom: 10px">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>
    <el-card style="width: 100%">
      <div slot="header" class="clearfix">
        <span>数据</span>
      </div>
      <div>
        <el-table :data="tableData" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}" @selection-change="handleSelectionChange">
          <el-table-column type="selection" align="center"></el-table-column>
          <el-table-column label="school" prop="school" align="center"></el-table-column>
          <el-table-column label="sub" prop="sub" align="center"></el-table-column>
          <el-table-column label="city" prop="city" align="center" show-overflow-tooltip></el-table-column>
          <el-table-column label="person23" prop="person23" align="center"></el-table-column>
          <el-table-column label="person22" prop="person22" align="center"></el-table-column>
          <el-table-column label="person21" prop="person21" align="center"></el-table-column>
          <el-table-column label="操作" align="center">
            <template v-slot="scope">
              <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" plain @click="del(scope.row.id)" v-if="scope.row.id === user.id ? false : true">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
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
      </div>
    </el-card>
    <div>
      <el-dialog title="公告" :visible.sync="formVisible" width="40%" :close-on-click-modal="false">
        <el-form :model="form" :rules="rules" ref="formRef" label-width="80px" style="padding-right: 20px">
          <el-form-item label="school" prop="school">
            <el-input v-model="form.school" placeholder="学校"></el-input>
          </el-form-item>
          <el-form-item label="sub" prop="sub">
            <el-select style="width: 100%" v-model="form.sub">
              <el-option v-for="item in ['计算机', '软件工程', '自动化', '电子信息']" :key="item" :value="item"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="formVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "Tables",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
      tableData: [],
      pageNum: 1,
      pageSize: 5,
      city: '',
      total: 0,
      formVisible: false,
      form: {
        title: '',
        description: '',
        content: '',
      },
      ids: [],
      content: '',
      rules: {
        school: [
          {required: true, message: '请输入学校', trigger: 'blur'}
        ],
        sub: [
          {required: true, message: '请选择学科', trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    this.load()
  },
  mounted() {

  },
  methods: {
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.formVisible = true
    },
    handleAdd() {
      this.form = {}
      this.formVisible = true
    },
    del(id) {
      this.$confirm('确认删除?', '确认', {type: "warning"}).then(response =>{
        this.$request.delete('/tables/delete/' + id).then(res =>{

          if (res.code === '200') {
            this.load(1)
            this.$message.success('成功')
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {})
    },
    delBatch() {
      if (!this.ids.length) {
        this.$message.warning("请选择数据")
        return
      }
      this.$confirm('确认批量删除?', '确认', {type: "warning"}).then(response =>{
        this.$request.delete('/tables/delete/batch', {
              data: this.ids
            }
        ).then(res =>{

          if (res.code === '200') {
            this.load(1)
            this.$message.success('成功')
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {})
    },
    handleSelectionChange(rows) {
      this.ids = rows.map(v => v.id)
      console.log(rows)
    },
    save() {
      this.$refs.formRef.validate((valid) =>{
        if (valid) {
          this.$request({
            url: this.form.id ? '/tables/update' : '/tables/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {
              this.load(1)
              this.$message.success('成功')
              this.formVisible = false
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    sendSaveRequest() {
      this.$request({
        url: this.form.id ? '/tables/update' : '/tables/add',
        method: this.form.id ? 'PUT' : 'POST',
        data: this.form
      }).then(res => {
        if (res.code === '200') {
          this.load(1)
          this.$message.success('成功')
          this.formVisible = false
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    reset() {
      this.city = ''
      this.load()
    },
    load(pageNum) {
      if (pageNum) {
        this.pageNum = pageNum
      }
      this.$request.get('/tables/selectByPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          title: this.title
        }
      }).then(res => {
        if (res.data) {
          // 确保在访问其属性之前，res.data不为null
          this.tableData = res.data.records || [];
          this.total = res.data.total;
        } else {
          // 处理res.data为null的情况
          this.tableData = [];
          this.total = 0;
        }
      });
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    handleAvatarSuccess(response, file, fileList) {
      this.form.title = response.data
    }
  }
}
</script>


<style>

</style>
