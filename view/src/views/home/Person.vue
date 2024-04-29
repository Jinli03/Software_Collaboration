<!--
*@Person
*@author Jinli
*@date 2024/2/21 20:14
-->
<template>
  <div>
    <el-card style="width: 50%">
      <el-form :model="user" :rules="rules" label-width="80px" style="padding-right: 20px">
        <div style="margin: 15px; text-align: center">
          <el-upload
              class="avatar-uploader"
              :action="$baseUrl + '/file/upload'"
              :headers="{ token: user.token}"
              :show-file-list="false"
              list-type="picture"
              :on-success="handleAvatarSuccess">
            <img v-if="user.title" :src="user.title" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <el-form-item label="name" prop="name">
          <el-input v-model="user.name" placeholder="null" disabled></el-input>
        </el-form-item>
        <el-form-item label="phone" prop="phone">
          <el-input v-model="user.phone" placeholder="null"></el-input>
        </el-form-item>
        <el-form-item label="email" prop="email">
          <el-input v-model="user.email" placeholder="null"></el-input>
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

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-radius: 50%;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
  border-radius: 50%;
}
.avatar {
  width: 100px;
  height: 100px;
  display: block;
  border-radius: 50%;
}
.el-form-item__label {
  font-weight: bold;
}
</style>
