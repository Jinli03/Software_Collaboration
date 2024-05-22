<template>
  <div class="register-page">
    <div class="register-container">
      <div class="register-image">
        <img src="@/assets/register.png" alt="register">
      </div>
      <div class="form-container">
        <el-form :model="user" :rules="rules" ref="registerRef">
          <div class="form-header">欢迎注册</div>
          <el-form-item prop="name" class="form-item">
            <el-input prefix-icon="el-icon-user" size="medium" placeholder="请输入账号" v-model="user.name"></el-input>
          </el-form-item>
          <el-form-item prop="password" class="form-item">
            <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请输入密码" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPass" class="form-item">
            <el-input prefix-icon="el-icon-check" size="medium" show-password placeholder="请确认密码" v-model="user.confirmPass"></el-input>
          </el-form-item>
          <el-form-item class="form-actions">
            <el-button @click="register" type="success" class="register-button">注 册</el-button>
            <div class="extra-actions">
              <div>已经有账号？请<span class="login-link" @click="$router.push('/login')"> 登录</span></div>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <div class="footer">
      &copy; 2024 @Jinli @hfy | 考研分析局.
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    const validatePass = (rule, confirmPass, callback) => {
      if (confirmPass === '') {
        callback(new Error('请确认密码'));
      } else if (confirmPass !== this.user.password) {
        callback(new Error('两次输入密码不一致'));
      } else {
        callback();
      }
    }
    return {
      user: {
        name: '',
        password: '',
        confirmPass: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        confirmPass: [
          { validator: validatePass, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    register() {
      this.$refs['registerRef'].validate((valid) => {
        if (valid) {
          this.$request.post("/register", this.user).then(res => {
            if (res.code === '200') {
              this.$router.push('/login');
              this.$message.success('注册成功');
            } else {
              this.$message.error(res.msg);
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>
.register-page {
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: rgba(11, 71, 220, 0.65);
}

.register-container {
  display: flex;
  background-color: white;
  width: 50%;
  border-radius: 10px;
  overflow: hidden;
}

.register-image {
  flex: 1;
}

.register-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.form-container {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

.form-header {
  font-size: 20px;
  font-weight: bold;
  text-align: center;
  margin: 20px 0;
}

.form-item {
  width: 100%;
  margin-bottom: 20px;
}

.form-actions {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.register-button {
  width: 200px;
  height: 50px;
  line-height: 25px;
  background-color: #f5f5f5;
  color: #333;
  border: 1px solid #ccc;
  transition: background-image 0.3s, color 0.3s;
}

.register-button:hover {
  background-image: linear-gradient(to right, #007bff, #0056b3);
  color: white;
}

.extra-actions {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 30px;
}

.login-link {
  color: dodgerblue;
  cursor: pointer;
}

.footer {
  text-align: center;
  padding: 10px;
  color: white;
}
</style>
