<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-image">
        <img src="@/assets/login.png" alt="login">
      </div>
      <div class="form-container">
        <el-form :model="user" :rules="rules" ref="loginRef">
          <div class="form-header">欢迎登录考研分析局</div>
          <el-form-item prop="name" class="form-item">
            <el-input prefix-icon="el-icon-user" size="medium" placeholder="请输入账号" v-model="user.name" @keyup.enter.native="login"></el-input>
          </el-form-item>
          <el-form-item prop="password" class="form-item">
            <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请输入密码" v-model="user.password" @keyup.enter.native="login"></el-input>
          </el-form-item>
          <el-form-item prop="code" class="form-item">
            <div class="code-container">
              <el-input prefix-icon="el-icon-check" size="medium" placeholder="请输入验证码(小写)" v-model="user.code" @keyup.enter.native="login"></el-input>
              <valid-code @update:value="getCode" class="valid-code"></valid-code>
            </div>
          </el-form-item>
          <el-form-item class="form-actions">
            <el-button @click="login" type="success" class="login-button">登录</el-button>
            <div class="extra-actions">
              <div>还没有账号？请<span class="register-link" @click="$router.push('/register')"> 注册</span></div>
              <div class="forgot-password" @click="handleForgetPass">忘记密码点这里</div>
              <el-dialog title="忘记密码" :visible.sync="forgetPassDialogVis">
                <el-form :model="forgetUserForm">
                  <el-form-item label="用户名" :label-width="formLabelWidth">
                    <el-input v-model="forgetUserForm.name" autocomplete="off" placeholder="请输入用户名"></el-input>
                  </el-form-item>
                  <el-form-item label="手机号" :label-width="formLabelWidth">
                    <el-input v-model="forgetUserForm.phone" autocomplete="off" placeholder="请输入手机号"></el-input>
                  </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                  <el-button @click="forgetPassDialogVis = false">取消</el-button>
                  <el-button type="primary" @click="resetPassword">确定</el-button>
                </div>
              </el-dialog>
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
import ValidCode from "@/components/ValidCode.vue";

export default {
  name: "Login",
  components: {
    ValidCode
  },
  data() {
    const validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'));
      } else if (value.toLowerCase() !== this.code) {
        callback(new Error('验证码错误'));
      } else {
        callback()
      }
    }
    return {
      code: '',
      forgetUserForm: {},
      user: {
        name: '',
        password: '',
        code: '',
      },
      formLabelWidth: '100px',
      forgetPassDialogVis: false,
      // 提示输入内容
      rules: {
        name: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        code: [
          { validator: validateCode, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleForgetPass() {
      this.forgetUserForm = {}
      this.forgetPassDialogVis = true
    },
    resetPassword() {
      this.$request.put('/password', this.forgetUserForm).then(res => {
        if (res.code === '200') {
          this.$message.success('重置成功')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    getCode(code) {
      this.code = code.toLowerCase()
    },
    login() {
      this.$refs['loginRef'].validate((valid) => {
        if (valid) {
          this.$request.post("/login", this.user).then(res => {
            if (res.code === '200') {
              this.$router.push('/')
              this.$message.success('登陆成功')
              localStorage.setItem("pilot", JSON.stringify(res.data))
            } else {
              this.$message.error(res.msg)
            }
          })
        } else {
          return false;
        }
      });
    },
  }
}
</script>

<style scoped>
.login-page {
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #0f98;
}

.login-container {
  display: flex;
  background-color: white;
  width: 50%;
  border-radius: 10px;
  overflow: hidden;
}

.login-image {
  flex: 1;
}

.login-image img {
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

.code-container {
  display: flex;
  align-items: center;
}

.valid-code {
  flex: 1;
  height: 50px;
  margin-left: 10px;
}

.form-actions {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.login-button {
  width: 200px;
  height: 50px;
  line-height: 25px;
  background-color: #f5f5f5;
  color: #333;
  border: 1px solid #ccc;
  transition: background-image 0.3s, color 0.3s;
}

.login-button:hover {
  background-image: linear-gradient(to right, #44c767, #5cbf2a);
  color: white;
}

.extra-actions {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 30px;
}

.register-link {
  color: dodgerblue;
  cursor: pointer;
}

.forgot-password {
  text-align: center;
  color: #888;
  font-size: 14px;
  cursor: pointer;
  margin-top: 20px;
  transition: text-decoration 0.3s;
}

.forgot-password:hover {
  text-decoration: underline;
}

.footer {
  text-align: center;
  padding: 10px;
  color: white;
}
</style>
