import Vue from 'vue'
import VueRouter from 'vue-router'
import School from "@/components/School.vue";

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)

const routes = [
  {
    path: '/Manager',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect: 'home',//重定向
    children: [
      { path: 'home', name: 'Home', meta: { name: '系统首页'}, component: () => import('../views/manager/Home.vue')},
      { path: 'user', name: 'User', meta: { name: '用户信息'}, component: () => import('../views/manager/User.vue')},
      { path: '403', name: 'Auth', meta: { name: '不知道'}, component: () => import('../views/manager/Auth.vue')},
      { path: 'news', name: 'News', meta: { name: '新闻信息'}, component: () => import('../views/manager/News.vue')},
      { path: 'newsdet', name: 'NewsDet', meta: { name: '新闻详情'}, component: () => import('../views/manager/NewsDet.vue')},
      { path: 'echarts', name: 'Echarts', meta: { name: '图表信息'}, component: () => import('../views/manager/Echarts.vue')},
      { path: 'notice', name: 'Notice', meta: { name: '系统公告'}, component: () => import('../views/manager/Notice.vue')},
      { path: 'tables', name: 'Tables', meta: { name: '查询名称'}, component: () => import('../views/manager/Tables.vue')},
    ]
  },
  {
    path: '/',
    name: 'Menu',
    component: () => import('../views/Menu.vue'),
    redirect: 'homepage',//重定向
    children: [
      { path: 'homepage', name: 'HomePage', meta: { name: '主页'}, component: () => import('../views/home/HomePage.vue')},
      { path: 'analyze', name: 'Analyze', meta: { name: '分析局'}, component: () => import('../views/home/Analyze.vue'),},
      { path: 'search', name: 'Search', meta: { name: '分析局'}, component: () => import('../views/home/Search.vue')},
      { path: 'exchange', name: 'Exchange', meta: { name: '图书交易局'}, component: () => import('../views/home/Exchange.vue')},
      { path: 'info', name: 'Info', meta: { name: '详情'}, component: () => import('../views/home/Info.vue')},
      { path: 'schoolinfo', name: 'Schoolinfo', meta: { name: '学校详情'}, component: () => import('../views/home/Schoolinfo.vue')},
      { path: 'personwishes', name: 'PersonWishes', meta: { name: '心愿单'}, component: () => import('../views/home/PersonWishes.vue')},
      { path: 'personal', name: 'Personal', meta: { name: '个人档案局'}, component: () => import('../views/home/Personal.vue')},
      { path: 'person', name: 'Person', meta: { name: '个人信息'}, component: () => import('../views/home/Person.vue')},
      { path: 'password', name: 'Password', meta: { name: '修改密码'}, component: () => import('../views/home/Password.vue')},
      { path: 'subinfo', name: 'Subinfo', meta: { name: '专业详情'}, component: () => import('../views/home/Subinfo.vue')},
      { path: 'foruminfo', name: 'Foruminfo', meta: { name: '评论区'}, component: () => import('../views/home/Foruminfo.vue')},
    ]
  },
  { path: '/element', name: 'Element', meta: { name: 'element'}, component: () => import('../views/Element.vue')},
  { path: '/login', name: 'Login', meta: { name: '登录'}, component: () => import('../views/Login.vue')},
  { path: '/register', name: 'Register', meta: { name: '注册'}, component: () => import('../views/Register.vue')},
  { path: '/ai', name: 'Ai', meta: { name: '彩蛋'}, component: () => import('../components/Ai.vue')},
  { path: '/*', name: '404', meta: { name: '无法访问'}, component: () => import('../views/404.vue')},
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  let adminPath = ['/user']
  let users = JSON.parse(localStorage.getItem('pilot') || '{}')
  if (users.role !== '管理员' && adminPath.includes(to.path)) {
    next('/403')
  } else {
    next()
  }
})

export default router
