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
                <el-card class="card-item" style="cursor: pointer; background-size: cover" :style="{ backgroundImage: 'url(' + doctor.title + ')' }">
                  <div slot="header" style="color: black;">
                    <span>{{ doctor.name }}</span>
                    <span>{{ doctor.department }}</span>
                    <span style="float: right;">{{ doctor.des }}</span>
                  </div>
                  <div style="color: black;">
                    <p>.</p>
                    <p>.</p>
                    <el-button type="primary">挂号</el-button>
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
              <el-table-column label="state" prop="state" align="center"></el-table-column>
              <el-table-column label="操作" align="center">
                <template v-slot="scope">
                  <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
                  <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
      </el-tabs>
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
      records: []
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
