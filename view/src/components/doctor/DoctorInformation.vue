<!--
*@DoctorInformation
*@author Jinli
*@date 2024/7/2 13:46
-->
<template>
  <div>
    <el-card>
      <p style="font-weight: bold; font-size: 20px;">我的排班</p>
    </el-card>
    <div>
      <el-table :data="priods" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}">
        <el-table-column label="id" prop="id" align="center"></el-table-column>
        <el-table-column label="doctor" prop="doctor" align="center"></el-table-column>
        <el-table-column label="department" prop="department" align="center"></el-table-column>
        <el-table-column label="time" prop="time" align="center"></el-table-column>
        <el-table-column label="state" prop="state" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template v-slot="scope">
            <el-button size="mini" type="warning" plain v-if="scope.row.state === '未预约' ? true : false" @click="go(scope.row.id)">暂离</el-button>
            <el-button size="mini" type="success" plain v-if="scope.row.state === '暂离' ? true : false" @click="come(scope.row.id)">在</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: "DoctorInformation",
  props: {},
  components: {},
  data() {
    return {
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
      priods: []
    }
  },
  created() {
  },
  mounted() {

  },
  methods: {
    go(id) {
      this.$request.put('/priod/updateStateById', {
        id: id,
        state: '暂离'
      }, {
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(res => {
        if (res.data) {
          this.$message.success(res.data)
          this.selectDoctorPriod()
        }
      });
    },
    come(id) {
      this.$request.put('/priod/updateStateById', {
        id: id,
        state: '未预约'
      }, {
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(res => {
        if (res.data) {
          this.$message.success(res.data)
          this.selectDoctorPriod()
        }
      });
    },
    selectDoctorPriod() {
      this.$request.get('/priod/selectByDoctor', {
        params: {
          name: this.user.name
        }
      }).then(res => {
        if (res.data) {
          this.priods = res.data;
          console.log('Response data:', this.priods);
        }
      });
    },
  },
}
</script>

<style>

</style>
