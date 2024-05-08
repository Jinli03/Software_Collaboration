<script>
export default {
  props: ['visible']
}
</script>

<template>
  <div v-if="visible" class="reminder-popup">
    主人主人，已经很晚了，注意保持健康哦！
  </div>
</template>

<style>
.reminder-popup {
  position: fixed;
  top: 80%;
  right: 20%;
  transform: translateX(-50%);
  padding: 20px;
  background-color: white;
  border: black;
  z-index: 1000;
}
</style>