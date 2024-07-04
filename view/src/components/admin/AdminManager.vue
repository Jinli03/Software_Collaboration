<!--
*@AdminManager
*@author Jinli
*@date 2024/7/1 15:24
-->
<template>
  <div>
    <el-tabs v-model="activeTab2" @tab-click="handleTabClick2">
      <el-tab-pane label="管理员信息" name="first" @click="admin()">
        <div>
          <el-table :data="user" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}" @selection-change="handleSelectionChange">
            <el-table-column label="id" prop="id" align="center"></el-table-column>
            <el-table-column label="name" prop="name" align="center"></el-table-column>
            <el-table-column label="phone" prop="phone" align="center"></el-table-column>
            <el-table-column label="email" prop="email" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
              <template v-slot="scope">
                <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
                <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
      <el-tab-pane label="医生信息" name="second" @click="doctor()">
        <div>
          <el-table :data="user" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}" @selection-change="handleSelectionChange">
            <el-table-column label="id" prop="id" align="center"></el-table-column>
            <el-table-column label="name" prop="name" align="center"></el-table-column>
            <el-table-column label="phone" prop="phone" align="center"></el-table-column>
            <el-table-column label="email" prop="email" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
              <template v-slot="scope">
                <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
                <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
      <el-tab-pane label="用户信息" name="third" @click="patient()">
        <div>
          <el-table :data="user" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}" @selection-change="handleSelectionChange">
            <el-table-column label="id" prop="id" align="center"></el-table-column>
            <el-table-column label="name" prop="name" align="center"></el-table-column>
            <el-table-column label="phone" prop="phone" align="center"></el-table-column>
            <el-table-column label="email" prop="email" align="center"></el-table-column>
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
</template>

<script>
import AdminOrder from "@/components/admin/AdminOrder.vue";
import AdminInformation from "@/components/admin/AdminInformation.vue";
import AdminHomePage from "@/components/admin/AdminHomePage.vue";

export default {
  name: "AdminManager",
  props: {},
  components: {AdminHomePage, AdminInformation, AdminOrder},
  data() {
    return {
      activeTab2: 'first',
      role: '',
      user: []
    }
  },
  created() {
  },
  mounted() {

  },
  methods: {
    admin() {
      this.role = 'admin'
      this.$request.get('/user/selectAllAdmins', {
      }).then(res => {
        if (res.data) {
          this.user = res.data;
          console.log('Response data:', this.user);
        } else {
        }
      });
    },
    doctor() {
      this.role = 'doctor'
      this.$request.get('/user/selectAllDoctors', {
      }).then(res => {
        if (res.data) {
          this.user = res.data;
          console.log('Response data:', this.user);
        } else {
        }
      });
    },
    patient() {
      this.role = 'patient'
      this.$request.get('/user/selectAllPatients', {
      }).then(res => {
        if (res.data) {
          this.user = res.data;
          console.log('Response data:', this.user);
        } else {
        }
      });
    },
    handleTabClick2(tab) {
      if (tab.name === 'first') {
        this.admin()
      }
      if (tab.name === 'second') {
        this.doctor()
      }
      if (tab.name === 'third') {
        this.patient()
      }
    }
  },
}
</script>

<style>

</style>
