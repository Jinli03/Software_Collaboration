<!--
*@Person
*@author Jinli
*@date 2024/2/21 20:14
-->
<template>
  <div class="center-container">
    <el-card style="width: 50%; max-width: 600px;">
      <el-form :model="user" :rules="rules" label-width="80px" style="padding-right: 20px;">
        <div style="margin: 15px; text-align: center">
          <el-upload
              class="avatar-uploader"
              :action="$baseUrl + '/file/upload'"
              :headers="{ token: user.token }"
              :show-file-list="false"
              list-type="picture"
              :on-success="handleAvatarSuccess">
            <img v-if="user.title" :src="user.title" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <el-form-item label="name" prop="name">
          <el-input v-model="user.name" placeholder="请输入姓名" disabled></el-input>
        </el-form-item>
        <el-form-item label="phone" prop="phone" required>
          <el-input v-model="user.phone" placeholder="请输入电话"></el-input>
        </el-form-item>
        <el-form-item label="email" prop="email" required>
          <el-input v-model="user.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <div style="text-align: center">
          <el-button type="primary" @click="update">保存</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>


<script>
export default {
  name: "Person",
  props: {},
  components: {
  },
  data() {
    return {
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
      rules: {
        phone: [
          {required: true, message: '请输入电话', trigger: 'blur'}
        ],
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'}
        ]
      },
    }
  },
  mounted: {},
  methods: {
    update() {
      this.$request.put('/user/update', this.user).then(res =>{
        if (res.code === '200') {
          this.$message.success('更新成功')
          localStorage.setItem('pilot', JSON.stringify(this.user))//更新浏览器
          //触发父级更新
          this.$emit('update:user', this.user)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAvatarSuccess(response, file, fileList) {
      this.user.title = response.data
    }
  },
}
</script>

<style scoped>
/* Center Container and Background Style */
.center-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh; /* Full viewport height */
  width: 100vw; /* Full viewport width */
  background-image: url('@/assets/background/homepage.png'); /* Add background image */
  background-size: cover; /* Ensure the image covers the entire container */
  background-position: center; /* Center the background image */
  background-repeat: no-repeat; /* Prevent image repetition */
}

/* Form and Card Styles */
.el-card {
  width: 50%;
  max-width: 600px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1); /* Light shadow for depth */
  border-radius: 10px; /* Rounded corners */
  overflow: hidden; /* Ensures no child element leaks out */
}

/* Input and Button Styles */
.el-input__inner, .el-button {
  border-radius: 5px; /* Rounded borders for inputs and buttons */
  border: solid 1px #dcdfe6; /* Slight border for definition */
}

.el-input__inner {
  transition: border-color 0.2s, box-shadow 0.2s; /* Smooth transition for focus */
}

.el-input__inner:focus {
  border-color: #409EFF; /* Highlight color when focused */
  box-shadow: 0 0 8px rgba(64, 158, 255, 0.2); /* Subtle glow effect */
}

.el-button {
  background-color: #409EFF; /* Primary button color */
  border: none; /* Remove default border */
  color: white; /* Text color for buttons */
  transition: background-color 0.2s, box-shadow 0.2s; /* Smooth transition for hover */
}

.el-button:hover {
  background-color: #337ecc; /* Slightly darker on hover */
  box-shadow: 0 2px 4px 0 rgba(0,0,0,0.2); /* Shadow for 3D effect on hover */
}

/* Additional styles for avatars and labels */
.avatar-uploader .el-upload {
  border: none; /* Clean look without border */
  cursor: pointer;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  line-height: 178px; /* Center icon vertically */
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%; /* Circular avatar */
}

.el-form-item__label {
  font-weight: bold; /* Make labels bold */
}
/* Input and Button Styles with increased specificity */
.el-form .el-input__inner, .el-form .el-button {
  border-radius: 5px; /* Rounded borders for inputs and buttons */
  border: solid 1px #dcdfe6; /* Slight border for definition */
}

.el-form .el-input__inner:focus {
  border-color: #409EFF; /* Highlight color when focused */
  box-shadow: 0 0 8px rgba(64, 158, 255, 0.2); /* Subtle glow effect */
}

.el-form .el-button {
  background-color: #409EFF; /* Primary button color */
  color: white; /* Text color for buttons */
}

.el-form .el-button:hover {
  background-color: #337ecc; /* Slightly darker on hover */
  box-shadow: 0 2px 4px 0 rgba(0,0,0,0.2); /* Shadow for 3D effect on hover */
}
</style>


