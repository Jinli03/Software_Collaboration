<!--
*@PatientOrder
*@author Jinli
*@date 2024/7/2 8:08
-->
<template>
  <div>
    <el-tabs v-model="activeTab" @tab-click="handleTabClick1">
      <el-tab-pane label="预约挂号" name="first" @click="selectAllDoctors">
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
      <el-tab-pane label="我的挂号" name="second" @click="selectAllRecordsByName">
        <el-card>
          <el-select v-model="value2" placeholder="请选择状态">
            <el-option
                v-for="item in options2"
                :key="item.value2"
                :label="item.label2"
                :value="item.value2">
            </el-option>
          </el-select>
          <el-button type="primary" @click="selectStateAndNameRecords()">查询</el-button>
          <el-button type="warning" @click="reset2()">重置</el-button>
          <div>
            <el-table :data="records" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}">
              <el-table-column label="id" prop="id" align="center"></el-table-column>
              <el-table-column label="name" prop="name" align="center"></el-table-column>
              <el-table-column label="doctor" prop="doctor" align="center"></el-table-column>
              <el-table-column label="department" prop="department" align="center"></el-table-column>
              <el-table-column label="state" prop="state" align="center"></el-table-column>
              <el-table-column label="操作" align="center">
                <template v-slot="scope">
                  <el-button size="mini" type="primary" plain v-if="scope.row.state !== '待叫号' ? false : true" @click="quit(scope.row.id, scope.row.doctor, scope.row.time)">取消挂号</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="我的就诊" name="third">
        <div>
          <el-table :data="records" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}">
            <el-table-column label="id" prop="id" align="center"></el-table-column>
            <el-table-column label="name" prop="name" align="center"></el-table-column>
            <el-table-column label="doctor" prop="doctor" align="center"></el-table-column>
            <el-table-column label="department" prop="department" align="center"></el-table-column>
            <el-table-column label="state" prop="state" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
              <template v-slot="scope">
                <el-button size="mini" type="primary" plain @click="write(scope.row.id)">查看病历</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
    </el-tabs>
    <div>
      <el-dialog title="预约" :visible.sync="priodVisible" width="70%">
        <el-table :data="priods" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}">
          <el-table-column label="id" prop="id" align="center"></el-table-column>
          <el-table-column label="doctor" prop="doctor" align="center"></el-table-column>
          <el-table-column label="department" prop="department" align="center"></el-table-column>
          <el-table-column label="time" prop="time" align="center"></el-table-column>
          <el-table-column label="state" prop="state" align="center"></el-table-column>
          <el-table-column label="操作" align="center">
            <template v-slot="scope">
              <el-button size="mini" type="primary" plain v-if="scope.row.state === '未预约' ? true : false" @click="order(scope.row.doctor, scope.row.id, scope.row.time)">预约</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>
    </div>
    <div>
      <el-dialog title="病历" :visible.sync="recordVisible" width="70%">
        <el-form :model="record" :rules="rules" ref="formRef" label-width="80px" style="padding-right: 20px">
          <el-form-item label="name" prop="name">
            <el-input v-model="record.name" placeholder="null" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="doctor" prop="name">
            <el-input v-model="record.doctor" placeholder="null" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="time" prop="name">
            <el-input v-model="record.time" placeholder="null" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="state" prop="name">
            <el-input v-model="record.state" placeholder="null" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="prescription" prop="name">
            <el-input v-model="record.prescription" placeholder="null" :disabled="true"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="recordVisible = false">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>

import doctor from "@/views/home/Doctor.vue";

export default {
  name: "PatientOrder",
  props: {},
  components: {},
  data() {
    const user = JSON.parse(localStorage.getItem('pilot') || '{}');
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
      doctors: [],
      doctor: {},
      record: {
        name: user.name || ''
      },
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
      priodVisible: false,
      priods: [],
      options2: [{
        value2: '待叫号',
        label2: '待叫号'
      }, {
        value2: '已叫号',
        label2: '已叫号'
      }, {
        value2: '就诊中',
        label2: '就诊中'
      }, {
        value2: '取消挂号',
        label2: '取消挂号'
      }],
      value2: '',
      records: [],
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
    open(name) {
      this.selectDoctorPriod(name)
      this.doctor.name = name
      this.priodVisible = true
    },
    selectDoctorPriod(name) {
      this.$request.get('/priod/selectByDoctor', {
        params: {
          name: name
        }
      }).then(res => {
        if (res.data) {
          this.priods = res.data;
          console.log('Response data:', this.priods);
        }
      });
    },
    order(name, id, time) {
      this.$request.get('/user/selectDoctorByName', {
        params: {
          name: name
        }
      }).then(res => {
        if (res.data) {
          const doctor = res.data;
          console.log('Doctor Details:', doctor);
          const currentDate = new Date().toISOString().slice(0, 10); // 获取当前日期，格式为 'YYYY-MM-DD'
          this.$request.post('/records/addRecord', {
            name: this.user.name,
            doctor: doctor.name,
            department: doctor.department,
            time: time, // Assuming you want to set the current time
            state: '待叫号' // Assuming you want to set a default state
          }).then(res => {
            if (res.data) {
              this.$message.success(res.data)
              this.updateState(id)
              this.priodVisible = false
            } else {
              this.$message.error(res.msg)
            }
          });
        } else {
          console.error('Doctor not found');
        }
      });
    },
    updateState(id) {
      this.$request.put(`/priod/setStateById?id=${id}`)
          .then(res => {
            if (res.data) {
              console.log('Response data:', res.data);
            }
          })
          .catch(error => {
            console.error('Error updating state:', error);
          });
    },
    quit(id, doctor, time) {
      this.$request.get('/records/editState', {
        params: {
          id: id,
          state: '取消挂号'
        }
      }).then(res => {
        if (res.data) {
          console.log('Response data:', res.data);
          this.selectStateAndNameRecords()
        }
      });
      this.$request.get('/priod/editState', {
        params: {
          doctor: doctor,
          time: time,
          state: '未预约'
        }
      }).then(res => {
        if (res.data) {
          console.log('Response data:', res.data);
          this.selectStateAndNameRecords()
        }
      });
    },
    selectAllRecordsByName() {
      this.$request.get('/records/selectByName', {
        params: {
          name: this.user.name,
        }
      }).then(res => {
        if (res.data) {
          this.records = res.data;
          console.log('Response data:', this.records);
        } else {
        }
      });
    },
    selectStateAndNameRecords() {
      this.$request.get('/records/selectByNameAndState', {
        params: {
          name: this.user.name,
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
      this.selectAllRecordsByName()
    },
    handleTabClick1(tab) {
      if (tab.name === 'first') {
        this.selectAllDoctors();
      }
      if (tab.name === 'second') {
        this.selectAllRecordsByName();
      }
      if (tab.name === 'third') {
        this.value2 = '已就诊'
        this.selectStateAndNameRecords()
        this.value2 = ''
      }
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
    write(id) {
      this.getRecordById(id)
      this.recordVisible = true
    }
  },
}
</script>

<style>

</style>
