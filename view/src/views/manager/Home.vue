<template>
  <div>
    <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px; margin-bottom: 10px">
      {{ users.name }} 开心每一天!
    </div>
    <div style="display: flex; width: 100%">
      <el-card style="width: 50%">
        <div slot="header" class="clearfix">
          <span>数据</span>
        </div>
        <div>
          <el-table :data="user">
            <el-table-column label="id" prop="id"></el-table-column>
            <el-table-column label="name" prop="name"></el-table-column>
            <el-table-column label="phone" prop="phone"></el-table-column>
            <el-table-column label="title" prop="title"></el-table-column>
            <el-table-column label="文件上传">
              <template v-slot="scope">
                <el-upload
                    :action="$baseUrl + '/file/upload'"
                    :headers="{token: users.token}"
                    :show-file-list="false"
                    :on-success="(row, file, fileList) => handleTableFileUpload(scope.row, file, fileList)"
                >
                  <el-button size="mini" type="primary">点击上传</el-button>
                </el-upload>
              </template>
            </el-table-column>
            <el-table-column label="文件上传">
              <template v-slot="scope">
                <el-image v-if="scope.row.title" :src="scope.row.title" style="width: 50px; height: 50px"></el-image>
                <el-button @click="preview(scope.row.title)">预览</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-card>
      <div style="margin-left: 10px; width: 50%">
        <el-card style="display: flex">
          <el-timeline style="padding: 0">
            <el-timeline-item v-for="item in notices" :key=item.id :timestamp="item.time" placement="top">
              <el-card>
                <h4>{{ item.title }}</h4>
                <p>{{ item.content }}</p>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </el-card>
        <el-card style="margin-top: 10px">
          <h2>系统公告</h2>
          <el-collapse v-model="activeName" accordion>
            <el-collapse-item v-for="(item, index) in notices" :key=item.id :name="index + ''">
              <template slot="title">
                <h4>{{ item.title }}</h4>
              </template>
              <div>{{ item.content }}</div>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </div>
    </div>
  </div>
</template>


<script>
import axios from 'axios';
export default {
  name: "Home",
  data() {
    return {
      users: JSON.parse(localStorage.getItem('pilot') || '{}'),
      user: [],
      notices: [],
      activeName: 0
    }
  },
  created(){
    this.load()
    this.loadNotice()
  },
  methods: {
    loadNotice() {
      this.$request.get('/notice/selectUserData').then(res => {
        this.notices = res.data
      })
    },
    load() {
      this.$request.get('/user/select').then(res =>{
        this.user = res.data
      })
    },
    handleTableFileUpload(row, file, fileList) {
      console.log(row, file, fileList);
      row.title = file.response.data;
      console.log(row);
      this.$request.put('/user/update', row).then(res => {
        if (res.data.code === 200) {
          this.$message.success("上传成功");
        } else {
          this.$message.error(res.data.msg);
        }
      }).catch(error => {
        console.error("上传文件时出错:", error);
      });
    },
  }
}
</script>


<style>

</style>