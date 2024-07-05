<!--
*@AdminOrder
*@author Jinli
*@date 2024/7/1 15:24
-->
<template>
  <div style="margin-top: 10px">
    <div>
      <el-tabs v-model="activeTab" @tab-click="handleTabClick2">
        <el-tab-pane label="预约挂号" name="first" @click="selectAllDoctors()">
          <el-card>
            <el-select v-model="value1" placeholder="请选择科室">
              <el-option
                  v-for="item in options1"
                  :key="item.value1"
                  :label="item.label1"
                  :value="item.value1">
              </el-option>
            </el-select>
            <el-button type="primary" @click="selectDepartmentDoctor">查询</el-button>
            <el-button type="warning" @click="reset1">重置</el-button>
          </el-card>
          <div>
            <el-row :gutter="20" style="padding: 8px">
              <el-col :span="6" v-for="doctor in doctors" :key="doctor.id" style="padding: 10px">
                <el-card class="card-item" style="cursor: pointer; background-size: cover; width:300px; height: 500px" :style="{ backgroundImage: 'url(' + doctor.title + ')' }">
                  <div slot="header" style="color: black;">
                    <span>{{ doctor.name }}</span>
                    <span>{{ doctor.department }}</span>
                    <span style="float: right;">{{ doctor.des }}</span>
                  </div>
                  <div style="color: black;">
                    <p>.</p>
                    <p>.</p>
                    <!--                  <el-button type="primary" @click="order(doctor.id)">挂号</el-button>-->
                    <el-button type="primary" @click="open(doctor.name)">挂号</el-button>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-tab-pane>
        <el-tab-pane label="患者挂号" name="second" @click="selectAllRecords()">
          <el-card>
            <el-select v-model="value2" placeholder="请选择挂号状态">
              <el-option
                  v-for="item in options2"
                  :key="item.value2"
                  :label="item.label2"
                  :value="item.value2">
              </el-option>
            </el-select>
            <el-button type="primary" @click="selectStateRecords()">查询</el-button>
            <el-button type="warning" @click="reset2">重置</el-button>
          </el-card>
          <div>
            <el-table :data="records" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}" @selection-change="handleSelectionChange">
              <el-table-column label="id" prop="id" align="center"></el-table-column>
              <el-table-column label="name" prop="name" align="center"></el-table-column>
              <el-table-column label="doctor" prop="doctor" align="center"></el-table-column>
              <el-table-column label="department" prop="department" align="center"></el-table-column>
              <el-table-column label="time" prop="time" align="center"></el-table-column>
              <el-table-column label="state" prop="state" align="center"></el-table-column>
              <el-table-column label="操作" align="center">
                <template v-slot="scope">
                  <el-button size="mini" type="primary" plain @click="handleEdit(scope.row.id)">编辑</el-button>
                  <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div>
      <el-dialog title="信息" :visible.sync="recordVisible" width="70%">
        <el-form :model="record" :rules="rules" ref="formRef" label-width="80px" style="padding-right: 20px">
          <el-form-item label="name" prop="name">
            <el-input v-model="record.name" placeholder="null"></el-input>
          </el-form-item>
          <el-form-item label="doctor" prop="doctor">
            <el-input v-model="record.doctor" placeholder="null"></el-input>
          </el-form-item>
          <el-form-item label="department" prop="department">
            <el-input v-model="record.department" placeholder="null"></el-input>
          </el-form-item>
          <el-form-item label="state" prop="state">
            <el-select v-model="record.state" placeholder="请选择挂号状态">
              <el-option
                  v-for="item in options2"
                  :key="item.value2"
                  :label="item.label2"
                  :value="item.value2">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="recordVisible = false">取 消</el-button>
          <el-button type="primary" @click="save()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import AdminManager from "@/components/admin/AdminManager.vue";
import AdminInformation from "@/components/admin/AdminInformation.vue";
import AdminHomePage from "@/components/admin/AdminHomePage.vue";

export default {
  name: "AdminOrder",
  props: {},
  components: {AdminHomePage, AdminInformation, AdminManager},
  data() {
    return {
      activeTab: 'first',
      options1: [{
        value1: '儿科',
        label1: '儿科'
      }, {
        value1: '皮肤科',
        label1: '皮肤科'
      }, {
        value1: '烧伤科',
        label1: '烧伤科'
      }, {
        value1: '内科',
        label1: '内科'
      }, {
        value1: '外科',
        label1: '外科'
      }],
      value1: '',

      options2: [{
        value2: '待叫号',
        label2: '待叫号'
      }, {
        value2: '已叫号',
        label2: '已叫号'
      },{
        value2: '就诊中',
        label2: '就诊中'
      },{
        value2: '已就诊',
        label2: '已就诊'
      },{
        value2: '取消挂号',
        label2: '取消挂号'
      }],
      value2: '',
      doctors: [],
      records: [],
      record: {},
      recordVisible: false
    }
  },
  created() {
  },
  mounted() {

  },
  methods: {
    selectAllDoctors() {
      this.$request.get('/department/selectAllDoctors', {
      }).then(res => {
        if (res.data) {
          this.doctors = res.data;
          console.log('Response data:', this.doctors);
        } else {
        }
      });
    },
    selectDepartmentDoctor() {
      this.$request.get('/department/selectByDepartment', {
        params: {
          department: this.value1
        }
      }).then(res => {
        if (res.data) {
          this.doctors = res.data;
          console.log('Response data:', this.doctors);
        }
      });
    },
    reset1() {
      this.value1 = ''
      this.selectAllDoctors()
    },

    selectAllRecords() {
      this.$request.get('/records/select', {
      }).then(res => {
        if (res.data) {
          this.records = res.data;
          console.log('Response data:', this.records);
        } else {
        }
      });
    },
    selectStateRecords() {
      this.$request.get('/records/selectByState', {
        params: {
          state: this.value2
        }
      }).then(res => {
        if (res.data) {
          this.records = res.data;
          console.log('Response data:', this.records);
        }
      });
    },
    reset2() {
      this.value2 = ''
      this.selectAllRecords()
    },
    getRecordById(id) {
      this.$request.get('/records/getById', {
        params: {
          id: id
        }
      }).then(res => {
        if (res.data) {
          console.log('Record data:', res.data);
          this.record = res.data;
        } else {
          console.error('No record found');
        }
      }).catch(error => {
        console.error('Error fetching record:', error);
      });
    },
    handleEdit(id) {
      this.getRecordById(id)
      this.recordVisible = true
    },
    save() {
      this.$request.put('/records/updateRecord', this.record)
          .then(res => {
            if (res.data) {
              this.$message.success('更新成功');
              this.recordVisible = false;
              this.reset2()
              // Optionally, you can refresh the records list here
            } else {
              this.$message.error('更新失败');
            }
          })
          .catch(error => {
            console.error('Error updating user:', error);
            this.$message.error('更新失败');
          });
    },
    del(id) {
      this.$confirm('确认删除?', '确认', {type: "warning"}).then(response =>{
        this.$request.delete('/records/delete/' + id).then(res =>{

          if (res.code === '200') {
            this.$message.success('删除成功')
            this.reset2()
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {})
    },
    handleTabClick2(tab, event) {
      if (tab.name === 'first') {
        this.selectAllDoctors();
      }
      if (tab.name === 'second') {
        this.selectAllRecords();
      }
    },
  },
}
</script>

<style>

</style>
