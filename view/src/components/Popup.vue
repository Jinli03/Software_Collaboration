<!-- Popup.vue -->
<template>
  <el-dialog
      :visible.sync="visible"
      title="提示"
      width="30%"
      :before-close="handleClose">
    <span>时间不早了，请早点休息吧。</span>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleClose">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  name: "Popup",
  props: {
    visible: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    handleClose() {
      this.$emit('update:visible', false);
    }
  }
}
</script>
