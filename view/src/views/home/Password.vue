<!--
*@Password
*@author Jinli
*@date 2024/2/21 22:02
-->
<template>
  <div>
    <el-card style="width: 50%">
      <el-form ref="formRef" :model="user" :rules="rules" label-width="80px" style="padding-right: 20px">
        <el-form-item label="originPassword" prop="password">
          <el-input show-password v-model="user.password" placeholder="null" disabled></el-input>
        </el-form-item>
        <el-form-item label="updatePassword" prop="newPassword">
          <el-input show-password v-model="user.newPassword" placeholder="null"></el-input>
        </el-form-item>
        <el-form-item label="confirmPassword" prop="confirmPassword">
          <el-input show-password v-model="user.confirmPassword" placeholder="null"></el-input>
        </el-form-item>
        <div style="text-align: center">
          <el-button type="primary" @click="update">修改</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Password",
  props: {},
  components: {},
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请确认密码'));
      } else if (value!== this.user.newPassword){
        callback(new Error('确认密码错误'));
      } else{
        callback()
      }
    }
    return {
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
      rules: {
        password: [
          {required: true, message: '请输入原始密码', trigger: 'blur'}
        ],
        newPassword: [
          {required: true, message: '请输入修改密码', trigger: 'blur'}
        ],
        confirmPassword: [
          {validator: validatePassword, required: true, trigger: 'blur'}
        ],
      }
    }
  },
  mounted: {},
  methods: {
    update() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.user.password = this.user.newPassword
          this.$request.put('/user/update', this.user).then(res =>{
            if (res.code === '200') {
              this.$message.success('更新成功')
              this.$router.push('/login')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  },
}
</script>

<style>
.el-form-item__label {
  font-weight: bold;
}
</style>
