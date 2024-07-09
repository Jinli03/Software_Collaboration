<!--
*@Patient
*@author Jinli
*@date 2024/7/1 13:03
-->
<template>
  <div>
    <div>
      <el-tabs v-model="activeTab" @tab-click="handleTabClick">
        <el-tab-pane label="系统首页" name="first">
          <span slot="label"><i class="el-icon-house"></i> 系统首页</span>
          <PatientHomePage></PatientHomePage>
        </el-tab-pane>
        <el-tab-pane label="预约就诊" name="second">
          <span slot="label"><i class="el-icon-date"></i> 预约就诊</span>
          <PatientOrder ref="patientOrder"></PatientOrder>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>


import PatientHomePage from "@/components/patient/PatientHomePage.vue";
import PatientOrder from "@/components/patient/PatientOrder.vue";

export default {
  name: "Patient",
  props: {},
  components: {PatientHomePage, PatientOrder},
  data() {
    return {
      activeTab: 'first',
    }
  },
  created() {
  },
  mounted() {

  },
  methods: {
    handleTabClick(tab) {
      if (tab.name === 'second') {
        this.$refs.patientOrder.selectAllDoctors();
      }
    }
  },
}
</script>

<style>

</style>
