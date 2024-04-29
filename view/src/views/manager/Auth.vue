<!--
*@Auth
*@author Jinli
*@date 2024/2/21 16:04
-->
<template>
  <div>
    <h1>无权限访问</h1>
    <p>对不起，您没有访问该页面的权限。</p>
    <router-link to="/">返回至主页</router-link>
  </div>
</template>

<script>
export default {
  name: "Auth",
  props: {},
  components: {},
  data() {
    return {}
  },
  mounted: {},
  methods: {},
}
</script>

<style>

</style>
