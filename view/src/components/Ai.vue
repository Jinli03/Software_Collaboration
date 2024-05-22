<!--
*@Ai
*@author Jinli
*@date 2024/3/24 19:16
-->
<template>
  <div class="ai-page">
    <header class="header">
      <h1>欢迎来到彩蛋页面</h1>
    </header>
    <section class="profile">
      <img :src="user.avatar" alt="User Avatar" class="avatar">
      <h2>{{ user.name }}</h2>
      <p>{{ user.bio }}</p>
    </section>
    <section class="details">
      <div class="card">
        <h3>个人信息</h3>
        <ul>
          <li><strong>年龄:</strong> {{ user.age }}</li>
          <li><strong>位置:</strong> {{ user.location }}</li>
          <li><strong>职业:</strong> {{ user.job }}</li>
        </ul>
      </div>
      <div class="card">
        <h3>最近活动</h3>
        <ul>
          <li v-for="(activity, index) in user.activities" :key="index">
            {{ activity }}
          </li>
        </ul>
      </div>
      <div class="card">
        <h3>技能</h3>
        <ul>
          <li v-for="(skill, index) in user.skills" :key="index">
            {{ skill }}
          </li>
        </ul>
      </div>
      <div class="card">
        <h3>兴趣爱好</h3>
        <ul>
          <li v-for="(hobby, index) in user.hobbies" :key="index">
            {{ hobby }}
          </li>
        </ul>
      </div>
      <div class="card">
        <h3>社交媒体</h3>
        <div class="social-links">
          <a :href="user.social.twitter" target="_blank" class="social-link twitter">Twitter</a>
          <a :href="user.social.linkedin" target="_blank" class="social-link linkedin">LinkedIn</a>
          <a :href="user.social.github" target="_blank" class="social-link github">GitHub</a>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: "Ai",
  data() {
    return {
      user: {
        name: "张三",
        bio: "热爱前端开发和人工智能的技术狂热者",
        age: 28,
        location: "北京",
        job: "前端工程师",
        avatar: "https://randomuser.me/api/portraits/men/75.jpg",
        activities: [
          "完成了Vue.js项目",
          "学习了新的JavaScript特性",
          "参与了开源社区的讨论",
          "阅读了前端开发的最新文章",
        ],
        skills: [
          "JavaScript",
          "Vue.js",
          "React",
          "Node.js",
          "CSS",
          "HTML",
          "Webpack"
        ],
        hobbies: [
          "篮球",
          "阅读",
          "音乐",
          "旅行",
          "电影"
        ],
        social: {
          twitter: "https://twitter.com",
          linkedin: "https://linkedin.com",
          github: "https://github.com"
        }
      }
    }
  }
}
</script>

<style scoped>
.ai-page {
  font-family: 'Arial', sans-serif;
  color: #333;
  text-align: center;
  padding: 20px;
  background: linear-gradient(120deg, #f6d365 0%, #fda085 100%);
  min-height: 100vh;
  animation: fadeIn 1s ease-in-out;
}

.header {
  background-color: #ff5722;
  color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.profile {
  margin: 20px 0;
  animation: slideIn 1s ease-in-out;
}

.avatar {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  border: 5px solid white;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.details {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  animation: fadeIn 2s ease-in-out;
}

.card {
  background: white;
  border-radius: 8px;
  margin: 10px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 300px;
  transition: transform 0.2s;
}

.card:hover {
  transform: translateY(-10px);
}

.card h3 {
  margin-bottom: 10px;
  color: #ff5722;
}

.card ul {
  list-style: none;
  padding: 0;
}

.card li {
  text-align: left;
  margin: 5px 0;
}

.social-links {
  display: flex;
  justify-content: space-around;
  margin-top: 10px;
}

.social-link {
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  text-decoration: none;
  transition: background-color 0.3s;
}

.social-link.twitter {
  background-color: #1DA1F2;
}

.social-link.linkedin {
  background-color: #0077B5;
}

.social-link.github {
  background-color: #333;
}

.social-link:hover {
  opacity: 0.8;
}

@media (max-width: 600px) {
  .details {
    flex-direction: column;
    align-items: center;
  }

  .card {
    width: 90%;
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes slideIn {
  from {
    transform: translateY(-20px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}
</style>
