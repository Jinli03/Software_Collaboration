<template>
  <div class="center-container">
    <el-card class="password-card">
      <div class="form-header">修改密码</div>
      <el-form ref="formRef" :model="user" :rules="rules" label-width="100px">
        <el-form-item label="原始密码" prop="password">
          <el-input show-password v-model="user.password" placeholder="请输入原始密码" prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input show-password v-model="user.newPassword" placeholder="请输入新密码" prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPassword">
          <el-input show-password v-model="user.confirmPassword" placeholder="确认新密码" prefix-icon="el-icon-check"></el-input>
        </el-form-item>
        <div class="form-actions">
          <el-button type="primary" @click="update">修改</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Password",
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请确认密码'));
      } else if (value !== this.user.newPassword) {
        callback(new Error('两次输入的密码不一致'));
      } else {
        callback();
      }
    };

    return {
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
      rules: {
        password: [
          { required: true, message: '请输入原始密码', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' }
        ],
        confirmPassword: [
          { validator: validatePassword, required: true, trigger: 'blur' }
        ],
      }
    };
  },
  methods: {
    update() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.user.password = this.user.newPassword;
          this.$request.put('/user/update', this.user).then(res => {
            if (res.code === '200') {
              this.$message.success('密码更新成功');
              this.$router.push('/login');
            } else {
              this.$message.error(res.msg);
            }
          });
        }
      });
    }
  },
}
</script>

<style scoped>
.center-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f2f5;
}

.password-card {
  width: 50%;
  max-width: 600px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  padding: 20px;
}

.form-header {
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
  color: #409eff;
}

.el-input__inner, .el-button {
  border-radius: 5px;
}

.el-input__inner:focus {
  border-color: #409EFF;
  box-shadow: 0 0 8px rgba(64, 158, 255, 0.2);
}

.el-button {
  background-color: #409EFF;
  color: white;
  transition: background-color 0.2s;
}

.el-button:hover {
  background-color: #337ecc;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2);
}

.el-form-item__label {
  font-weight: bold;
}

.form-actions {
  text-align: center;
  margin-top: 20px;
}
</style>
