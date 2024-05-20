<template>
  <div id="app">
    <router-view />
    <ReminderPopUp :visible="showReminder" />
  </div>
</template>

<script>
import ReminderPopUp from "@/views/home/ReminderPopUp.vue";

export default {
  components: {
    ReminderPopUp
  },
  data() {
    return {
      showReminder: false
    };
  },
  created() {
    this.checkReminder(); // 立即执行一次检查以更新状态
    this.setupReminder(); // 设置定时检查
  },
  methods: {
    checkReminder() {
      const now = new Date();
      const hours = now.getHours();
      this.showReminder = hours >= 23 || hours < 6;
    },
    setupReminder() {
      setInterval(this.checkReminder, 60000); // 每分钟检查一次时间
    }
  }
}
</script>
