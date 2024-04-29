<template>
  <div style="height: 100vh; display: flex; flex-direction: column; align-items: center; justify-content: center; background-color: rgba(11,71,220,0.65)">
    <div style="display: flex; background-color: white; width: 50%; border-radius: 10px; overflow: hidden">
      <div style="flex: 1;">
        <img src="@/assets/register.png" style="width: 100%">
      </div>
      <div style="flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form :model="user" :rules="rules" ref="registerRef">
          <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 20px; margin-top: 20px">欢迎注册</div>
          <el-form-item prop="name" style="width: 100%">
            <el-input prefix-icon="el-icon-user" size="medium" placeholder="请输入账号" v-model="user.name"></el-input>
          </el-form-item>
          <el-form-item prop="password" style="width: 100%">
            <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请输入密码" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPass" style="width: 100%">
            <el-input prefix-icon="el-icon-check" size="medium" show-password placeholder="请确认密码" style="flex: 1;" v-model="user.confirmPass"></el-input>
          </el-form-item>
          <el-form-item style="width: 100%">
            <el-button @click="register" type="primary" style="margin-top: 20px; margin-bottom: 40px; width: 100%">注 册</el-button>
          </el-form-item>
          <div style="flex: 2; margin-bottom: 20px" @click="$router.push('/login')">
            已经有账号？请<span style="color: dodgerblue; cursor: pointer" @click="$router.push('/register')"> 登录</span>
          </div>
        </el-form>
      </div>
    </div>
    <div style="text-align: center; padding: 10px; color: white; ">
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
      } else if (confirmPass !== this.user.password){
        callback(new Error('两次输入密码不一致'));
      } else{
        callback()
      }
    }
    return {
      user: {
        name: '',
        password: '',
        confirmPass: ''
      },
      // 提示输入内容
      rules: {
        name: [
          {required: true, message: '请输入账号', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        confirmPass: [
          { validator: validatePass, trigger: 'blur' }
        ]
      }
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    register() {
      // 拦截请求
      this.$refs['registerRef'].validate((valid) => {
        if (valid) {
          this.$request.post("/register", this.user).then(res =>{
            if (res.code === '200'){
              this.$router.push('/login')
              this.$message.success('注册成功')
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