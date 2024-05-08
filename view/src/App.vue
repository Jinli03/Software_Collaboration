<template>
  <div id="app">
    <router-view />
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
    this.setupReminder();
  },
  methods:{
    setupReminder() {
      const checkInterval = setInterval(() => {
        const now = new Date();
        const hours = now.getHours();
        const minutes = now.getMinutes();
        if (hours === 23 && minutes === 0) {
          this.setupReminder = true;
          clearInterval(checkInterval);
        }
      }, 60000);
    }
  }
}
</script>