<!--
*@AdminInformation
*@author Jinli
*@date 2024/7/1 14:04
-->
<template>
  <div>
    <div style="margin-top: 10px">
      <el-tabs v-model="activeTab" @tab-click="handleTabClick1">
      <el-tab-pane label="科室信息" name="first" @click="selectDepartment()">
        <el-card>
          <el-button type="success" @click="addDepartment">新增</el-button>
        </el-card>
        <div>
          <el-row :gutter="20" style="padding: 8px">
            <el-col :span="6" v-for="department in departments" :key="department.id" style="padding: 10px">
              <el-card class="card-item" style="cursor: pointer; background-size: cover" :style="{ backgroundImage: 'url(' + department.title + ')' }">
                <div slot="header" style="color: black;">
                  <span>{{ department.name }}</span>
                  <span style="float: right;">{{ department.des }}</span>
                </div>
                <div style="color: black;">
                  <p>.</p>
                  <p>.</p>
                  <el-button size="mini" type="primary" plain @click="handleEditDepartment(department)">编辑</el-button>
                  <el-button size="mini" type="danger" plain @click="delDepartment(department.id)">删除</el-button>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </div>
        <div>
          <el-dialog title="添加科室" :visible.sync="departmentVisible" width="30%">
            <el-form :model="department" :rules="rules" ref="formRef" label-width="80px" style="padding-right: 20px">
              <el-form-item label="name" prop="name">
                <el-input v-model="department.name" placeholder="null"></el-input>
              </el-form-item>
              <el-form-item label="des" prop="des">
                <el-input v-model="department.des" placeholder="null"></el-input>
              </el-form-item>
              <el-form-item label="title" prop="title">
                <el-input v-model="department.title" placeholder="null"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="departmentVisible = false">取 消</el-button>
              <el-button type="primary" @click="saveDepartment">确 定</el-button>
            </div>
          </el-dialog>
        </div>
      </el-tab-pane>
      <el-tab-pane label="排班信息" name="second">
        <el-card>
          <el-button type="success" @click="addPriod">新增</el-button>
        </el-card>
        <div>
          <el-table :data="priods" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}">
            <el-table-column label="doctor" prop="doctor" align="center"></el-table-column>
            <el-table-column label="department" prop="department" align="center"></el-table-column>
            <el-table-column label="time" prop="time" align="center"></el-table-column>
            <el-table-column label="state" prop="state" align="center"></el-table-column>
          </el-table>
        </div>
        <el-dialog title="添加排班" :visible.sync="priodVisible" width="30%">
          <el-form :model="priod" :rules="rules" ref="formRef" label-width="80px" style="padding-right: 20px">
            <el-form-item label="doctor" prop="doctor">
              <el-input v-model="priod.doctor" placeholder="null"></el-input>
            </el-form-item>
            <el-form-item label="department" prop="department">
              <el-input v-model="priod.department" placeholder="null"></el-input>
            </el-form-item>
            <el-form-item label="time" prop="time">
              <el-input v-model="priod.time" placeholder="null"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="departmentVisible = false">取 消</el-button>
            <el-button type="primary" @click="savePriod">确 定</el-button>
          </div>
        </el-dialog>
      </el-tab-pane>
    </el-tabs>
    </div>
  </div>
</template>

<script>
import AdminHomePage from "@/components/admin/AdminHomePage.vue";

export default {
  name: "AdminInformation",
  props: {},
  components: {AdminHomePage},
  data() {
    return {
      activeTab: 'first',
      departments: [],
      departmentVisible: false,
      department: {},
      priods: [],
      priod: {},
      priodVisible: false
    }
  },
  created() {
  },
  mounted() {
  },
  methods: {
    addDepartment() {
      this.departmentVisible = true
    },
    addPriod() {
      this.priodVisible = true
    },
    saveDepartment() {
      this.$refs.formRef.validate((valid) =>{
        if (valid) {
          this.$request({
            url: this.department.id ? '/department/update' : '/department/add',
            method: this.department.id ? 'PUT' : 'POST',
            data: this.department
          }).then(res => {
            if (res.code === '200') {
              this.$message.success('成功')
              this.department = ''
              this.departmentVisible = false
              this.selectDepartment()
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    savePriod() {
      this.$refs.formRef.validate((valid) =>{
        if (valid) {
          this.$request({
            url: this.priod.id ? '/priod/update' : '/priod/add',
            method: this.priod.id ? 'PUT' : 'POST',
            data: this.priod
          }).then(res => {
            if (res.code === '200') {
              this.$message.success('成功')
              this.priod = ''
              this.priodVisible = false
              this.selectAllPriods()
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    handleEditDepartment(row) {
      this.department = JSON.parse(JSON.stringify(row))
      this.departmentVisible = true
    },
    delDepartment(id) {
      this.$confirm('确认删除?', '确认', {type: "warning"}).then(response =>{
        this.$request.delete('/department/delete/' + id).then(res =>{
          if (res.code === '200') {
            this.$message.success('成功')
            this.selectDepartment()
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {})
    },
    handleTabClick1(tab, event) {
      if (tab.name === 'first') {
        this.selectDepartment();
      }
      if (tab.name === 'second') {
        this.selectAllPriods();
      }
    },
    selectDepartment() {
      this.$request.get('/department/select', {
      }).then(res => {
        if (res.data) {
          this.departments = res.data;
          console.log('Response data:', this.departments);
        } else {
        }
      });
    },
    selectAllPriods() {
      this.$request.get('/priod/selectAllPriods', {
      }).then(res => {
        if (res.data) {
          this.priods = res.data;
          console.log('Response data:', this.priods);
        } else {
        }
      });
    },

  },
}
</script>

<style>

</style>
