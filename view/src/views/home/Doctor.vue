<!--
*@Doctor
*@author Jinli
*@date 2024/7/1 13:03
-->
<template>
  <div>
    <el-tabs v-model="activeTab" @tab-click="handleTabClick">
      <el-tab-pane label="系统首页" name="first">
        <span slot="label"><i class="el-icon-house"></i> 系统首页</span>
        <DoctorHomePage></DoctorHomePage>
      </el-tab-pane>
      <el-tab-pane label="信息管理" name="second">
        <span slot="label"><i class="el-icon-files"></i> 信息管理</span>
        <DoctorInformation ref="doctorInformation"></DoctorInformation>
      </el-tab-pane>
      <el-tab-pane label="预约就诊" name="third">
        <span slot="label"><i class="el-icon-date"></i> 预约就诊</span>
        <DoctorOrder ref="doctorOrder"></DoctorOrder>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>

import DoctorHomePage from "@/components/doctor/DoctorHomePage.vue";
import DoctorInformation from "@/components/doctor/DoctorInformation.vue";
import DoctorOrder from "@/components/doctor/DoctorOrder.vue";

export default {
  name: "Doctor",
  props: {},
  components: {DoctorHomePage, DoctorInformation, DoctorOrder},
  data() {
    return {
      activeTab: 'first',
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
    }
  },
  created() {
  },
  mounted() {

  },
  methods: {
    handleTabClick(tab) {
      if (tab.name === 'second') {
        this.$refs.doctorInformation.selectDoctorPriod();
      }
      if (tab.name === 'third') {
        this.$refs.doctorOrder.selectAllRecordsByNameDoctor();
      }
    }
  },
}
</script>

<style>

</style>
