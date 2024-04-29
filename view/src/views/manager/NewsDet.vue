<template>
  <div>
    <div style="margin: 10px; font-size: 30px; font-weight: bold">标题：{{ news.title }}</div>
    <div style="margin: 10px; font-size: 13px; color: #666">{{ user.name }}
      <span style="margin-left: 20px">{{ news.time }}</span>
    </div>
    <div v-html="news.content" style="box-shadow: rgba(0, 0, 10, .1)"></div>
  </div>
</template>

<script>
export default {
  name: "NewsDet",
  data() {
    return {
      news: {},
      user: {}
    }
  },
  created() {
    let id = this.$route.query.id
    if (!id) {
      return
    }

    this.$request.get('/news/selectById/' + id).then(res => {
      this.news = res.data
      this.news.id = res.data.authorid

      // 发起第二个请求
      this.getUserInfo().then(userRes => {
        this.user = userRes.data
      });
    });
  },
  methods: {
    getUserInfo() {
      return this.$request.get('/user/selectById/' + this.news.id);
    }
  }
}
</script>

<style>
</style>
