<!--
*@AdminManager
*@author Jinli
*@date 2024/7/1 15:24
-->
<template>
  <div>
    <el-tabs v-model="activeTab2" @tab-click="handleTabClick2">
      <el-tab-pane label="管理员信息" name="first" @click="admin()">
        <div>
          <el-table :data="users" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}" @selection-change="handleSelectionChange">
            <el-table-column label="id" prop="id" align="center"></el-table-column>
            <el-table-column label="name" prop="name" align="center"></el-table-column>
            <el-table-column label="phone" prop="phone" align="center"></el-table-column>
            <el-table-column label="email" prop="email" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
              <template v-slot="scope">
                <el-button size="mini" type="primary" plain @click="handleEdit(scope.row.id)">编辑</el-button>
                <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
      <el-tab-pane label="医生信息" name="second" @click="doctor()">
        <div>
          <el-table :data="users" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}" @selection-change="handleSelectionChange">
            <el-table-column label="id" prop="id" align="center"></el-table-column>
            <el-table-column label="name" prop="name" align="center"></el-table-column>
            <el-table-column label="phone" prop="phone" align="center"></el-table-column>
            <el-table-column label="email" prop="email" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
              <template v-slot="scope">
                <el-button size="mini" type="primary" plain @click="handleEdit(scope.row.id)">编辑</el-button>
                <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
      <el-tab-pane label="用户信息" name="third" @click="patient()">
        <div>
          <el-table :data="users" stripe :header-cell-style="{backgroundColor: 'aliceblue', fontWeight: 'bold', color: '#666'}" @selection-change="handleSelectionChange">
            <el-table-column label="id" prop="id" align="center"></el-table-column>
            <el-table-column label="name" prop="name" align="center"></el-table-column>
            <el-table-column label="phone" prop="phone" align="center"></el-table-column>
            <el-table-column label="email" prop="email" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
              <template v-slot="scope">
                <el-button size="mini" type="primary" plain @click="handleEdit(scope.row.id)">编辑</el-button>
                <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
    </el-tabs>
    <div>
      <el-dialog title="信息" :visible.sync="userVisible" width="70%">
        <el-form :model="user" :rules="rules" ref="formRef" label-width="80px" style="padding-right: 20px">
          <el-form-item label="name" prop="name">
            <el-input v-model="user.name" placeholder="null"></el-input>
          </el-form-item>
          <el-form-item label="password" prop="password">
            <el-input v-model="user.password" placeholder="null"></el-input>
          </el-form-item>
          <el-form-item label="phone" prop="phone">
            <el-input v-model="user.phone" placeholder="null"></el-input>
          </el-form-item>
          <el-form-item label="email" prop="email">
            <el-input v-model="user.email" placeholder="null"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="save()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import AdminOrder from "@/components/admin/AdminOrder.vue";
import AdminInformation from "@/components/admin/AdminInformation.vue";
import AdminHomePage from "@/components/admin/AdminHomePage.vue";

export default {
  name: "AdminManager",
  props: {},
  components: {AdminHomePage, AdminInformation, AdminOrder},
  data() {
    return {
      activeTab2: 'first',
      role: '',
      users: [],
      userVisible: false,
      user: {}
    }
  },
  created() {
  },
  mounted() {

  },
  methods: {
    admin() {
      this.role = 'admin'
      this.$request.get('/user/selectAllAdmins', {
      }).then(res => {
        if (res.data) {
          this.users = res.data;
          console.log('Response data:', this.users);
        } else {
        }
      });
    },
    doctor() {
      this.role = 'doctor'
      this.$request.get('/user/selectAllDoctors', {
      }).then(res => {
        if (res.data) {
          this.users = res.data;
          console.log('Response data:', this.users);
        } else {
        }
      });
    },
    patient() {
      this.role = 'patient'
      this.$request.get('/user/selectAllPatients', {
      }).then(res => {
        if (res.data) {
          this.users = res.data;
          console.log('Response data:', this.users);
        } else {
        }
      });
    },
    getUserById(id) {
      this.$request.get('/user/getById', {
        params: {
          id: id
        }
      }).then(res => {
        if (res.data) {
          console.log('Record data:', res.data);
          this.user = res.data;
        } else {
          console.error('No record found');
        }
      }).catch(error => {
        console.error('Error fetching record:', error);
      });
    },
    handleEdit(id) {
      this.getUserById(id)
      this.userVisible = true
    },
    save() {
      this.$request.put('/user/update', this.user)
          .then(res => {
            if (res.data) {
              this.$message.success('更新成功');
              this.userVisible = false;
              const role = this.user.role;
              if(role === 'admin') {
                this.admin()
              }
              if(role === 'doctor') {
                this.doctor()
              }
              if(role === 'patient') {
                this.patient()
              }
              // Optionally, you can refresh the records list here
            } else {
              this.$message.error('更新失败');
            }
          })
          .catch(error => {
            console.error('Error updating user:', error);
            this.$message.error('更新失败');
          });
    },
    del(id) {
      this.$confirm('确认删除?', '确认', {type: "warning"}).then(response =>{
        this.$request.delete('/user/delete/' + id).then(res =>{

          if (res.code === '200') {
            if(this.role === 'admin') {
              this.admin()
            }
            if(this.role === 'doctor') {
              this.doctor()
            }
            if(this.role === 'patient') {
              this.patient()
            }
            this.$message.success('删除成功')
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {})
    },
    handleTabClick2(tab) {
      if (tab.name === 'first') {
        this.admin()
      }
      if (tab.name === 'second') {
        this.doctor()
      }
      if (tab.name === 'third') {
        this.patient()
      }
    }
  },
}
</script>

<style>

</style>
