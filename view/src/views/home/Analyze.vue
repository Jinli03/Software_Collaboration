<template>
  <div>
    <el-container class="background-image-container">
      <div style="backdrop-filter: blur(8px); width: 95%; height: 95%">
        <el-col :span="6">
          <div>
            <el-row style="display:flex; justify-content: center; width: 80%; margin-left: 20px; margin-top: 10px; border-radius: 20px">
              <img :src="user.title || 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'" alt="" style=" width: 50px; height: 50px; border-radius: 50%; margin: 10px">
              <p style="color: #000c17; margin-top: 15px">"静水流深<br>沧笙踏歌"</p>
            </el-row>
            <el-row>
              <el-card style="justify-content: center; width: 80%; margin-left: 20px; border-radius: 20px">
                <div>
                  <el-statistic
                      group-separator=","
                      :value="value1"
                      :title="title1"
                  >
                    <template slot="prefix">
                      <i class="el-icon-user"></i>
                    </template>
                  </el-statistic>
                </div>
              </el-card>

            </el-row>
            <el-row>
              <el-card style="justify-content: center; width: 80%; margin-left: 20px; margin-top: 10px; border-radius: 20px">
                <div>
                  <el-statistic
                      group-separator=","
                      :value="value2"
                      :title="title2"
                  >
                    <template slot="prefix">
                      <i class="el-icon-house"></i>
                    </template>
                  </el-statistic>
                </div>
              </el-card>
            </el-row>
            <el-row>
              <el-card style="justify-content: center; width: 80%; margin-left: 20px; margin-top: 10px; border-radius: 20px">
                <div>
                  <el-statistic
                      group-separator=","
                      :value="value3"
                      :title="title3"
                  >
                    <template slot="prefix">
                      <i class="el-icon-search"></i>
                    </template>
                  </el-statistic>
                </div>
              </el-card>
            </el-row>
            <el-row>
              <el-card style="justify-content: center; width: 80%; margin-left: 20px; margin-top: 10px; border-radius: 20px">
                <div style="width: 100%; display: inline-block;">
                  <el-statistic
                      format="DD天"
                      :value="deadline"
                      time-indices
                      title="🚩距离考研还有："
                  >
                    <template slot="prefix">
                      <i class="el-icon-sunrise"></i>
                    </template>
                  </el-statistic>
                </div>
              </el-card>
            </el-row>
          </div>
        </el-col>
        <el-col :span="12">
          <div style="backdrop-filter: blur(8px); width: 90%; height: 100%">
            <div style="margin-left: 100px">
              <template>
                <el-carousel :interval="3000" type="card" height="180px">
                  <el-carousel-item v-for="item in 6" :key="item">
                  </el-carousel-item>
                </el-carousel>
              </template>
            </div>
            <div>
              <img src="../../assets/icon/school.png" alt="First Image" class="image1" style="width: 100px; height: 100px; position: absolute; top: 300px; left: 550px;" @click="$router.push('search')">
              <p style="position: absolute; top: 300px; left: 580px; font-size: 20px">学校</p>
            </div>
            <div>
              <img src="@/assets/icon/major.png" alt="Second Image" class="image2" style="width: 100px; height: 100px; position: absolute; top: 300px; left: 650px" @click="$router.push('personal')">
              <p style="position: absolute; top: 300px; left: 680px; font-size: 20px">专业</p>
            </div>
          </div>
        </el-col>

        <el-col :span="6">
          <div class="vertical-text">
            <p ref="welcomeText" style="font-size: 42px; color: #8c939d">{{ displayedMessage }}</p>
          </div>
        </el-col>
      </div>
    </el-container>
  </div>

</template>

<script>
export default {
  name: "HomePage",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
      welcomeMessage: "欢迎来到考研分析局",
      displayedMessage: "",
      deadline: new Date("2024-12-25"),
      title1: "已注册用户",
      title2: "已上传院校数量",
      title3: "网页访问量",
      value1: 4154.564,
      value2: 1314,
      value3: 1314,
    };
  },
  mounted() {
    this.displayWelcomeMessage();
  },
  methods: {
    displayWelcomeMessage() {
      let index = 0;
      const intervalId = setInterval(() => {
        this.displayedMessage += this.welcomeMessage[index];
        index++;
        if (index === this.welcomeMessage.length) {
          clearInterval(intervalId);
        }
      }, 100); // 逐字显示的时间间隔，单位为毫秒
    }
  }
};
</script>

<style scoped>
.background-image-container {
  background-image: url('@/assets/background/desktop.png');
  background-size: cover; /* 将背景图片铺满整个容器 */
  background-position: center; /* 图片居中显示 */
  height: 84vh; /* 调整容器的高度，根据需要更改 */
  background-size: 75%; /* 缩小背景图片 */
  display: flex;
  justify-content: center;
  align-items: center;
  color: white; /* 文字颜色 */
}

/* 针对内容的额外样式 */
.background-image-container > div {
  text-align: center;
}

.background-image-container h1 {
  font-size: 2em;
  margin-bottom: 10px;
}

.background-image-container p {
  font-size: 1.2em;
}

.image1:hover {
  transform: scale(1.1); /* 放大1.1倍 */
}

.image2:hover {
  transform: scale(1.1); /* 放大1.1倍 */
}

.vertical-text {
  writing-mode: vertical-rl; /* 竖直显示文本，从右到左 */
  position: absolute;
  top: 20%;
  right: 80px; /* 调整文本距离右侧的位置 */
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(6n) {
  background-image: url('@/assets/pic/thu.png');
  background-size: cover; /* 以填充整个元素为目标 */
  background-repeat: no-repeat; /* 不重复平铺背景图 */
  background-position: center; /* 图片居中显示 */
}

.el-carousel__item:nth-child(6n+1) {
  background-image: url('@/assets/pic/pku.png');
  background-size: cover; /* 以填充整个元素为目标 */
  background-repeat: no-repeat; /* 不重复平铺背景图 */
  background-position: center; /* 图片居中显示 */
}
.el-carousel__item:nth-child(6n+2) {
  background-image: url('@/assets/pic/sjtu.png');
  background-size: cover; /* 以填充整个元素为目标 */
  background-repeat: no-repeat; /* 不重复平铺背景图 */
  background-position: center; /* 图片居中显示 */
}
.el-carousel__item:nth-child(6n+3) {
  background-image: url('@/assets/pic/fdu.png');
  background-size: cover; /* 以填充整个元素为目标 */
  background-repeat: no-repeat; /* 不重复平铺背景图 */
  background-position: center; /* 图片居中显示 */
}
.el-carousel__item:nth-child(6n+4) {
  background-image: url('@/assets/pic/ustc.png');
  background-size: cover; /* 以填充整个元素为目标 */
  background-repeat: no-repeat; /* 不重复平铺背景图 */
  background-position: center; /* 图片居中显示 */
}
.el-carousel__item:nth-child(6n+5) {
  background-image: url('@/assets/pic/xdu.png');
  background-size: cover; /* 以填充整个元素为目标 */
  background-repeat: no-repeat; /* 不重复平铺背景图 */
  background-position: center; /* 图片居中显示 */
}
</style>
