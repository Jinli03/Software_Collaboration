<!--
*@User
*@author Jinli
*@date 2024/2/21 15:38
-->
<template>
  <div>
    <div style="margin-bottom: 10px">
      <el-input style="width: 200px" placeholder="查询用户名" v-model="name"></el-input>
      <el-input style="width: 200px; margin:0 5px" placeholder="查询电话" v-model="phone"></el-input>
      <el-button type="primary" @click="load(1)">查询</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>
    <div style="margin-bottom: 10px">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
      <el-button type="info" plain @click="exportData">批量导出</el-button>
      <el-upload :action="$baseUrl + '/user/import'" :headers="{token: user.token}" style="display: inline-block; margin: 10px" :show-file-list="false" :on-success="handleImport">
        <el-button type="primary" plain>批量导入</el-button>
      </el-upload>
    </div>
    <el-card style="width: 100%">
      <div slot="header" class="clearfix">
        <span>数据</span>
      </div>
      <div>
        <el-table :data="tableData" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}" @selection-change="handleSelectionChange">
          <el-table-column type="selection" align="center"></el-table-column>
          <el-table-column label="id" prop="id" align="center"></el-table-column>
          <el-table-column label="name" prop="name" align="center"></el-table-column>
          <el-table-column label="phone" prop="phone" align="center"></el-table-column>
          <el-table-column label="title" prop="title" align="center">
            <template v-slot="scope">
              <el-image style="width: 50px; height: 50px; border-radius: 50px" :src="scope.row.title" v-if="scope.row.title" :preview-src-list="[scope.row.title]"></el-image>
            </template>
          </el-table-column>
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
      <el-dialog title="外层 Dialog" :visible.sync="formVisible" width="30%">
        <el-form :model="form" :rules="rules" ref="formRef" label-width="80px" style="padding-right: 20px">
          <el-form-item label="name" prop="name">
            <el-input v-model="form.name" placeholder="null"></el-input>
          </el-form-item>
          <el-form-item label="phone" prop="phone">
            <el-input v-model="form.phone" placeholder="null"></el-input>
          </el-form-item>
          <el-form-item label="email" prop="email">
            <el-input v-model="form.email" placeholder="null"></el-input>
          </el-form-item>
          <el-form-item label="头像">
            <el-upload
                class="avatar-uploader"
                :action="$baseUrl + '/file/upload'"
                :headers="{ token: user.token}"
                :file-list="form.title? [form.title] : []"
                list-type="picture"
                :on-success="handleAvatarSuccess">
              <el-button type="primary">上传头像</el-button>
            </el-upload>
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
  name: "Home",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
      tableData: [],
      pageNum: 1,
      pageSize: 5,
      name: '',
      phone: '',
      total: 0,
      formVisible: false,
      form: {
        name: '',
        phone: '',
        email: '',
      },
      ids: [],
      rules: {
        name: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        phone: [
          {required: true, message: '请输入电话', trigger: 'blur'}
        ],
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    exportData() {
      this.$nextTick(() => {
        if (!this.ids.length) {
          window.open(this.$baseUrl + '/user/export?token=' + this.user.token + "&name=" + this.name + "&phone=" + this.phone)
        } else {
          let idStr = this.ids.join(',') //[1,2,3] -> "1,2,3"
          window.open(this.$baseUrl + '/user/export?token=' + this.user.token + "&ids=" + idStr)
        }
      });
    },
    handleImport(res, file, fileList) {
      if (res.code === '200') {
        this.$message.success('操作成功')
        this.load(1)
      } else {
        this.$message.error(res.msg)
      }
    },
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
        this.$request.delete('/user/delete/' + id).then(res =>{

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
        this.$request.delete('/user/delete/batch', {
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
            url: this.form.id ? '/user/update' : '/user/add',
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
    reset() {
      this.name = ''
      this.phone = ''
    },
    load(pageNum) {
      if (pageNum) {
        this.pageNum = pageNum
      }
      this.$request.get('/user/selectByPage',{
      params: {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        name: this.name,
        phone: this.phone
      }
      }).then(res =>{
        this.tableData = res.data.records
        this.total = res.data.total
      })

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
