<!--
*@Menu
*@author Jinli
*@date 2024/3/5 20:05
-->
<template>
  <div>
    <el-container>
      <el-header style="border-bottom: 1px solid #ebeef5; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);">
        <el-row type="flex" justify="space-between" align="middle" style="height: 100%;">
          <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px; margin-top: 5px">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: $route.path }">{{ $route.meta.name }}</el-breadcrumb-item>
          </el-breadcrumb>
          <div style="display: flex; align-items: center;">
            <i class="el-icon-full-screen" style="font-size: 26px; margin-right: 10px" @click="handleFull"></i>
            <el-dropdown placement="bottom">
              <div style="display: flex; align-items: center; cursor: pointer;">
                <img :src="user.title || 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'" alt="" style="width: 40px; height: 40px; border-radius: 50%;">
                <span>{{ user.name }}</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="$router.push('/person')">个人信息</el-dropdown-item>
                <el-dropdown-item @click.native="$router.push('/password')">修改密码</el-dropdown-item>
                <el-dropdown-item @click.native="logout()">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-row>
      </el-header>



      <!--        主体-->
      <el-main>
        <router-view @update:user="updateTitle"></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Menu",
  props: {},
  components: {},
  data() {
    return {
      isCollapse: false,
      asideWidth: '200px',
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
      fileList: [],
    }
  },
  created() {
  },
  mounted() {
    if (!this.user.id) {
      this.$router.push('/login')
    }
  },
  methods: {
    updateTitle(user) { //获取子组件传过来的数据 更新
      this.$set(this.user, 'title', user.title);
      this.user = JSON.parse(JSON.stringify(user))//让父级的对象和子级的对象毫无关联
    },
    logout() {
      localStorage.removeItem("pilot")
      this.$router.push("/login")
    },
    handleFull(){
      document.documentElement.requestFullscreen()
    },
  },
  computed: {
  },
}
</script>

<style>

</style>
