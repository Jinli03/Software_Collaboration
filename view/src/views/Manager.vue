<template>
  <div >
    <el-container>
<!--      侧边栏-->
<!--      vh窗口的百分之多少-->
      <el-aside :width="asideWidth" style=" min-height: 100vh; background-color: #001529">
        <div style="color:white; height: 60px; line-height: 60px; display: flex; align-items: center; justify-content: center" >
          <img src="@/assets/logo1.png" alt="" style=" width: 40px; height: 40px;">
          <span class="logo-title" style="margin-left: 10px; font-size: 20px;" v-show="!isCollapse">胡桃小姐姐</span>
        </div>

        <el-menu :default-openeds="['info']" :collapse="isCollapse" :collapse-transition="false" router active-text-color="white" background-color="#001529" style="border: none;  active-text-color: #fff" :default-active="$route.path">
          <el-menu-item index="/home">
              <!--              图标-->
              <i class="el-icon-s-home"></i>
              <span slot="title">系统首页</span>
          </el-menu-item>
          <el-menu-item index="/element">
            <i class="el-icon-house"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
          <el-submenu index="info" v-if="user.role === '管理员'">
            <template slot="title">
<!--              图标-->
              <i class="el-icon-menu"></i>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/user">用户信息</el-menu-item>
            <el-menu-item index="/news">新闻信息</el-menu-item>
            <el-menu-item index="/notice">系统公告</el-menu-item>
            <el-menu-item index="/echarts">图表信息</el-menu-item>
            <el-menu-item index="/tables">考研信息</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-container>
<!--        右头-->
        <el-header>
          <i :class="collapseIcon" style="margin-left: 10px; font-size: 26px;" @click="handleCollapse"></i>
          <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: $route.path }">{{ $route.meta.name}}</el-breadcrumb-item>
          </el-breadcrumb>
          <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
            <i class="el-icon-full-screen" style="font-size: 26px; margin-right: 10px" @click="handleFull"></i>
            <el-dropdown placement="bottom">
              <div style="display: flex; align-items: center; cursor: default">
                <img :src="user.title || 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'" alt="" style=" width: 40px; height: 40px; border-radius: 50%">
                <span>{{ user.name }}</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="$router.push('/person')">个人信息</el-dropdown-item>
                <el-dropdown-item @click.native="$router.push('/password')">修改密码</el-dropdown-item>
                <el-dropdown-item @click.native="logout()">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-header>
<!--        主体-->
        <el-main>
          <router-view @update:user="updateTitle"></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: 'HomeView',
  data(){
    return {
      isCollapse: false,
      asideWidth: '200px',
      collapseIcon: 'el-icon-s-fold',
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
      fileList: [],

    }
  },
  mounted() {
  //   axios.get('http://localhost:9090/user/select').then(res => {
  //     console.log(res.data.data)
  //     this.user = res.data.data
  //   })
  // },
    if (!this.user.id) {
      this.$router.push('/login')
    }
    // this.$request.get('/user/select').then(res => {
    //   console.log(res.data.data)
    //   this.user = res.data.data
    // })
  },
  methods: {
    updateTitle(user) { //获取子组件传过来的数据 更新
      this.$set(this.user, 'title', user.title);
      this.user = JSON.parse(JSON.stringify(user))//让父级的对象和子级的对象毫无关联
    },
    handleCollapse(){
      this.isCollapse = !this.isCollapse
      this.asideWidth =  this.isCollapse ? '64px' : '200px'
      this.collapseIcon = this.isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'
    },
    handleFull(){
      document.documentElement.requestFullscreen()
    },
    logout() {
      localStorage.removeItem("pilot")
      this.$router.push("/login")
    }
  }
}
</script>

<style>
.el-menu--inline{
  background-color: #000c17 !important;
}
.el-menu--inline .el-menu-item{
  background-color: #000c17 !important;
  padding-left: 49px !important;
}
.el-menu-item:hover,.el-submenu__title:hover{
  color: #fff !important;
}
.el-submenu__title:hover i{
  color: #fff !important;
}
.el-menu-item.is-active {
  margin-left: 5px !important;
  background-color: #1890ff !important;
  border-radius: 10px !important;
  width: calc(100% - 8px);
}
.el-menu-item.is-active i, .el-menu-item.is-active.el.tooltip{
  margin-left: -5px !important;
}
.el-menu-item{

  height: 40px !important;
  line-height: 40px !important;
}
.el-submenu__title{
  height: 40px !important;
  line-height: 40px !important;
}
.el-submenu__icon-arrow{
  margin-top: -5px !important;
}
.el-aside{
  transition: width .3s;
  box-shadow: 2px 0 6px rgba(0,21,41,35);
}
.logo-title{
  transition: all .3s;
}
.el-header {
  box-shadow: 2px 0 6px rgba(0,21,41,35);
  display: flex;
  align-items: center;
}
</style>
