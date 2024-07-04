<!--
*@Manager
*@author Jinli
*@date 2024/7/1 13:03
-->
<template>
  <div>
    <el-tabs v-model="activeTab" @tab-click="handleTabClick">
      <el-tab-pane label="系统首页" name="first">
        <AdminHomePage></AdminHomePage>
      </el-tab-pane>
      <el-tab-pane label="信息管理" name="second">
        <AdminInformation ref="adminInformation"></AdminInformation>
      </el-tab-pane>
      <el-tab-pane label="预约就诊" name="third">
        <AdminOrder ref="adminOrder"></AdminOrder>
      </el-tab-pane>
      <el-tab-pane label="用户管理" name="forth">
        <AdminManager ref="adminManager"></AdminManager>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import AdminHomePage from "@/components/admin/AdminHomePage.vue";
import AdminInformation from "@/components/admin/AdminInformation.vue";
import AdminOrder from "@/components/admin/AdminOrder.vue";
import AdminManager from "@/components/admin/AdminManager.vue";

export default {
  name: "Admin",
  props: {},
  components: {
    AdminHomePage,AdminInformation,AdminOrder,AdminManager
  },
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
        this.$refs.adminInformation.selectDepartment();
      }
      if (tab.name === 'third') {
        this.$refs.adminOrder.selectAllDoctors();
      }
      if (tab.name === 'forth') {
        this.$refs.adminManager.admin();
      }
    }
  },
}
</script>

<style>

</style>
