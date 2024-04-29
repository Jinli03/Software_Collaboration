<template>
  <div style="height: 100vh; display: flex; flex-direction: column; align-items: center; justify-content: center; background-color: #0f98">
    <div style="display: flex; background-color: white; width: 50%; border-radius: 10px; overflow: hidden">
      <div style="flex: 1;">
        <img src="@/assets/login.png" style="width: 100%">
      </div>
      <div style="flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form :model="user" :rules="rules" ref="loginRef">
          <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 20px; margin-top: 20px">欢迎登录考研分析局</div>
          <el-form-item prop="name" style="width: 90%">
            <el-input prefix-icon="el-icon-user" size="medium" placeholder="请输入账号" v-model="user.name"></el-input>
          </el-form-item>
          <el-form-item prop="password" style="width: 90%">
            <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请输入密码" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="code" style="width: 100%">
            <div style="display: flex">
              <div>
                <el-input prefix-icon="el-icon-check" size="medium" placeholder="请输入验证码(小写)" style="flex: 1; width: 80%" v-model="user.code"></el-input>
              </div>
              <div style="flex: 1; height: 36px; margin-right: 30px" >
                <valid-code @update:value="getCode"></valid-code>
              </div>
            </div>
          </el-form-item>
          <el-form-item style="width: 100%">
            <el-button @click="login" type="success" style="margin-top: 40px">登录</el-button>
            <div style="display: flex; margin-top: 30px;">
              <div style="flex: 2">
                还没有账号？请<span style="color: dodgerblue; cursor: pointer" @click="$router.push('/register')"> 注册</span>
              </div>
              <div style="flex: 1; width: 50%; align-items: center">
                <el-button type="text" style="cursor: pointer" @click="handleForgetPass()">忘记密码</el-button>
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
                    <el-button @click="forgetPassDialogVis = false">取 消</el-button>
                    <el-button type="primary" @click="resetPassword">确 定</el-button>
                  </div>
                </el-dialog>
              </div>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <div style="text-align: center; padding: 10px; color: white; ">
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
      } else if (value.toLowerCase() !== this.code){
        callback(new Error('验证码错误'));
      } else{
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
          {required: true, message: '请输入账号', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        code: [
          { validator: validateCode, trigger: 'blur' }
        ]
      }
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    handleForgetPass() {
      this.forgetUserForm = {}
      this.forgetPassDialogVis = true
    },
    resetPassword() {
      this.$request.put('/password', this.forgetUserForm).then(res =>{
        if (res.code === '200'){
          this.$message.success('重置成功')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    getCode(code) {
      console.log(code)
      this.code = code.toLowerCase()
    },
    login() {
      // 拦截请求
      this.$refs['loginRef'].validate((valid) => {
        if (valid) {
          this.$request.post("/login", this.user).then(res =>{
            if (res.code === '200'){
              this.$router.push('/')
              this.$message.success('登陆成功')
              localStorage.setItem("pilot", JSON.stringify(res.data))
            } else {
              this.$message.error(res.msg)
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
  }
}

</script>

<style scoped>

</style>