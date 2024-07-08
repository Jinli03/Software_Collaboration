<!--
*@DoctorOrder
*@author Jinli
*@date 2024/7/2 13:47
-->
<template>
  <div>
    <el-tabs v-model="activeTab" @tab-click="handleTabClick3">
      <el-tab-pane label="我的挂号" name="first">
        <el-card>

            <el-card>
              <el-select v-model="value1" placeholder="请选择状态">
                <el-option
                  v-for="item in options1"
                  :key="item.value1"
                  :label="item.label1"
                  :value="item.value1">
                </el-option>
              </el-select>
              <el-button type="primary" @click="selectStateAndNameRecords()">查询</el-button>
              <el-button type="warning" @click="reset1()">重置</el-button>
            </el-card>

          <div>
            <el-table :data="records" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}">
              <el-table-column label="id" prop="id" align="center"></el-table-column>
              <el-table-column label="name" prop="name" align="center"></el-table-column>
              <el-table-column label="doctor" prop="doctor" align="center"></el-table-column>
              <el-table-column label="department" prop="department" align="center"></el-table-column>
              <el-table-column label="state" prop="state" align="center"></el-table-column>
              <el-table-column label="操作" align="center">
                <template v-slot="scope">
                  <el-button size="mini" type="primary" plain v-if="scope.row.state === '待叫号' ? true : false" @click="call(scope.row.id)">叫号</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="我的就诊" name="second">
        <div>
          <el-table :data="records" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}">
            <el-table-column label="id" prop="id" align="center"></el-table-column>
            <el-table-column label="name" prop="name" align="center"></el-table-column>
            <el-table-column label="doctor" prop="doctor" align="center"></el-table-column>
            <el-table-column label="department" prop="department" align="center"></el-table-column>
            <el-table-column label="state" prop="state" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
              <template v-slot="scope">
                <el-button size="mini" type="primary" plain @click="write(scope.row.id)">填写病历</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
    </el-tabs>
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
            <el-input v-model="record.prescription" placeholder="null"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="recordVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import DoctorInformation from "@/components/doctor/DoctorInformation.vue";
import DoctorHomePage from "@/components/doctor/DoctorHomePage.vue";

export default {
  name: "DoctorOrder",
  props: {},
  components: {DoctorHomePage, DoctorInformation},
  data() {
    return {
      activeTab: 'first',
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
      options1: [{
        value1: '待叫号',
        label1: '待叫号'
      }, {
        value1: '已叫号',
        label1: '已叫号'
      }, {
        value1: '就诊中',
        label1: '就诊中'
      }],
      value1: '',
      records: [],
      recordVisible: false,
      record: {}
    }
  },
  created() {
  },
  mounted() {

  },
  methods: {
    selectAllRecordsByNameDoctor() {
      this.$request.get('/records/selectByNameDoctor', {
        params: {
          doctor: this.user.name,
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
      this.$request.get('/records/selectByNameAndStateDoctor', {
        params: {
          doctor: this.user.name,
          state: this.value1
        }
      }).then(res => {
        if (res.data) {
          this.records = res.data;
          console.log('Response data:', this.records);
        }
      });
    },
    reset1() {
      this.value1 = ''
      this.selectAllRecordsByNameDoctor()
    },
    call(id) {
      this.$request.get('/records/editState', {
        params: {
          id: id,
          state: '已叫号'
        }
      }).then(res => {
        if (res.data) {
          console.log('Response data:', res.data);
          this.selectAllRecordsByNameDoctor()
        }
      });
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

    own() {
      this.value1 = '已就诊'
      this.selectStateAndNameRecords()
    },
    write(id) {
      this.getRecordById(id)
      this.recordVisible = true
    },
    save() {
      this.$request.put('/records/updateRecord', this.record)
          .then(res => {
            if (res.data) {
              this.$message.success('更新成功');
              this.recordVisible = false;
              // Optionally, you can refresh the records list here
            } else {
              this.$message.error('更新失败');
            }
          })
          .catch(error => {
            console.error('Error updating record:', error);
            this.$message.error('更新失败');
          });
    },
    handleTabClick3(tab) {
      if (tab.name === 'first') {
        this.reset1()
      }
      if (tab.name === 'second') {
        this.own();
      }
    }
  },
}
</script>

<style>

</style>
