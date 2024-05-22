<template>
  <div class="center-container">
    <el-card class="profile-card">
      <div class="avatar-container">
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
      <el-form ref="formRef" :model="user" :rules="rules" label-width="80px" class="profile-form">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="user.name" placeholder="请输入姓名" disabled></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="user.phone" placeholder="请输入电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="user.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <div class="form-actions">
          <el-button type="primary" @click="update">保存</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Person",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
      rules: {
        phone: [
          { required: true, message: '请输入电话', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' }
        ]
      },
    }
  },
  methods: {
    update() {
      this.$request.put('/user/update', this.user).then(res => {
        if (res.code === '200') {
          this.$message.success('更新成功');
          localStorage.setItem('pilot', JSON.stringify(this.user)); //更新浏览器
          //触发父级更新
          this.$emit('update:user', this.user);
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    handleAvatarSuccess(response, file, fileList) {
      this.user.title = response.data;
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
  background-color: #f0f2f5;
}

/* Form and Card Styles */
.profile-card {
  width: 50%;
  max-width: 600px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1); /* Light shadow for depth */
  border-radius: 10px; /* Rounded corners */
  padding: 20px; /* Padding for content spacing */
  background-color: white;
}

/* Avatar Styles */
.avatar-container {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.avatar-uploader .el-upload {
  border: none; /* Clean look without border */
  cursor: pointer;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  line-height: 100px; /* Center icon vertically */
  width: 100px;
  height: 100px;
  border-radius: 50%;
  border: 1px dashed #dcdfe6;
  display: flex;
  justify-content: center;
  align-items: center;
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%; /* Circular avatar */
  object-fit: cover;
}

/* Input and Button Styles */
.el-input__inner, .el-button {
  border-radius: 5px; /* Rounded borders for inputs and buttons */
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
  color: white; /* Text color for buttons */
  transition: background-color 0.2s, box-shadow 0.2s; /* Smooth transition for hover */
}

.el-button:hover {
  background-color: #337ecc; /* Slightly darker on hover */
  box-shadow: 0 2px 4px 0 rgba(0,0,0,0.2); /* Shadow for 3D effect on hover */
}

/* Additional styles for labels and form items */
.el-form-item__label {
  font-weight: bold; /* Make labels bold */
}

.form-actions {
  text-align: center;
  margin-top: 20px;
}
</style>
