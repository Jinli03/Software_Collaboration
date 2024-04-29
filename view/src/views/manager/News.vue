<!--
*@User
*@author Jinli
*@date 2024/2/21 15:38
-->
<template>
  <div>
    <div style="margin-bottom: 10px">
      <el-input style="width: 200px" placeholder="查询标题" v-model="title"></el-input>
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
          <el-table-column label="id" prop="id" align="center"></el-table-column>
          <el-table-column label="title" prop="title" align="center"></el-table-column>
          <el-table-column label="description" prop="description" align="center"></el-table-column>
          <el-table-column label="content" prop="content" align="center">
            <template v-slot="scope">
              <el-button @click="showContent(scope.row.content)" size="mini">显示内容</el-button>
            </template>
          </el-table-column>
          <el-table-column label="content" prop="content" align="center">
            <template v-slot="scope">
              <el-button @click="$router.push('NewsDet?id=' + scope.row.id)" size="mini">单独显示</el-button>
            </template>
          </el-table-column>
          <el-table-column label="author" prop="author" align="center"></el-table-column>
          <el-table-column label="time" prop="time" align="center"></el-table-column>
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
      <el-dialog title="新闻" :visible.sync="formVisible" width="60%" @close="closeDialog" :close-on-click-modal="false">
        <el-form :model="form" :rules="rules" ref="formRef" label-width="80px" style="padding-right: 20px">
          <el-form-item label="title" prop="title">
            <el-input v-model="form.title" placeholder="null"></el-input>
          </el-form-item>
          <el-form-item label="description" prop="description">
            <el-input v-model="form.description" placeholder="null"></el-input>
          </el-form-item>
          <el-form-item label="content" prop="content">
            <div id="editor"></div>
          </el-form-item>
        </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="formVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </div>
      </el-dialog>
    </div>
    <div>
      <el-card>
        <el-dialog title="内容" :visible.sync="formVisible1" width="60%">
          <div>
            <div v-html="content" class="w-e-text"></div>
            <div slot="footer" class="dialog-footer">
              <el-button type="primary" @click="formVisible1 = false">确 定</el-button>
            </div>
          </div>
        </el-dialog>
      </el-card>
    </div>
  </div>
</template>

<script>
import E from "wangeditor"
import hljs from "highlight.js"
export default {
  name: "News",
  data() {
    return {
      editor: null,
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
      tableData: [],
      pageNum: 1,
      pageSize: 5,
      title: '',
      total: 0,
      formVisible: false,
      formVisible1: false,
      form: {
        title: '',
        description: '',
        content: '',
      },
      ids: [],
      content: '',
      rules: {
        title: [
          {required: true, message: '请输入标题', trigger: 'blur'}
        ],
        description: [
          {required: true, message: '请输入简介', trigger: 'blur'}
        ],
        // content: [
        //   {required: true, message: '请输入内容', trigger: 'blur'}
        // ]
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    setRichText(row) {
      this.$nextTick(() => {
        this.editor = new E('#editor');
        this.editor.highlight = hljs
        this.editor.config.uploadImgServer = this.$baseUrl + '/file/editor/upload'
        this.editor.config.uploadFileName = 'file'
        this.editor.config.uploadImgHeaders = {
          token: this.user.token
        }
        this.editor.config.uploadImgParams = {
          type: 'img'
        }
        this.editor.config.uploadVideoServer = this.$baseUrl + '/file/editor/upload'
        this.editor.config.uploadVideoName = 'file'
        this.editor.config.uploadVideoHeaders = {
          token: this.user.token
        }
        this.editor.config.uploadVideoParams = {
          type: 'video'
        }
        this.editor.create()
        this.editor.txt.html(row.content);
        this.editor.txt.on('change', () => {
          this.form.content = this.editor.txt.html()
        })
      })
    },
    showContent(content) {
      this.content = content
      this.formVisible1 = true
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.formVisible = true
      this.setRichText(row)
      setTimeout(() => {
        this.editor.txt.html(row.content)
      })
    },
    closeDialog() {
      this.editor.destroy()
      this.editor = null
    },
    handleAdd() {
      this.form = {}
      this.formVisible = true
      this.setRichText()
    },
    del(id) {
      this.$confirm('确认删除?', '确认', {type: "warning"}).then(response =>{
        this.$request.delete('/news/delete/' + id).then(res =>{

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
        this.$request.delete('/news/delete/batch', {
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
          let content = this.editor.txt.html()
          this.form.content = content
          this.$request({
            url: this.form.id ? '/news/update' : '/news/add',
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
      this.title = ''
      this.load()
    },
    load(pageNum) {
      if (pageNum) {
        this.pageNum = pageNum
      }
      this.$request.get('/news/selectByPage', {
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
