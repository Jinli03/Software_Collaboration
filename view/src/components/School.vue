<!--
*@School
*@author Jinli
*@date 2024/3/24 19:15
-->
<template>
  <div>

      <div>
        <el-row :gutter="20">
          <el-col :span="4">
            <div style="display: flex">
              <el-button style="margin-right: 10px">地区</el-button>
              <div style="display: flex; flex-wrap: wrap; margin-top: 20px; height: auto;">
                <div style="margin-right: 10px; margin-bottom: 10px;" @click="square='A';">
                  <el-radio v-model="radio" label="1">A</el-radio>
                </div>
                <div style="margin-right: 10px; margin-bottom: 10px;" @click="square='B';">
                  <el-radio v-model="radio" label="2">B</el-radio>
                </div>
              </div>
            </div>

          </el-col>
          <el-col :span="6">
            <div style="display: flex">
              <el-button style="margin-right: 15px">城市</el-button>
              <el-select v-model="value1" multiple placeholder="请选择">
                <el-option
                    v-for="item in options1"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </div>
          </el-col>
          <el-col :span="1">
            <el-button @click="load(1)">查询</el-button>
          </el-col>
          <el-col :span="8">
            .
          </el-col>
          <el-col :span="5">
            <div style="margin: 10px 0">
              <el-pagination
                  @current-change="handleCurrentChange"
                  :current-page="pageNum"
                  :page-sizes="[100, 200, 300, 400]"
                  :page-size="pageSize"
                  layout="total,prev, pager, next"
                  :total="total">
              </el-pagination>
            </div>
          </el-col>
        </el-row>
      </div>



    <div>
      <el-row :gutter="20" style="padding: 8px">
        <el-col :span="6" v-for="school in schools" :key="school.id" style="padding: 10px">
          <el-card class="card-item" style="cursor: pointer; background-size: cover" :style="{ backgroundImage: 'url(' + school.pic + ')' }">
            <div slot="header" style="color: black;">
              <span>{{ school.school }}</span>
              <span style="float: right;">{{ school.city }}</span>
            </div>
            <div style="color: black;">
              <p>.</p>
              <p>.</p>
              <div slot="footer">
                <el-button @click="$router.push('Schoolinfo?school=' + school.school)" size="mini">查看详情</el-button>
              </div>
              <!-- 在这里添加更多数据 -->
            </div>
          </el-card>

        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "School",
  props: {},
  components: {},
  data() {
    return {
      radio: null,
      pageNum: 1,
      pageSize: 8,
      square: '',
      city: '',
      total: 0,
      form: {
        title: '',
        description: '',
        content: '',
      },
      schools: [],
      options1: [{
        value: '北京',
        label: '北京'
      }, {
        value: '上海',
        label: '上海'
      }, {
        value: '西安',
        label: '西安'
      }, {
        value: '南京',
        label: '南京'
      }, {
        value: '广州',
        label: '广州'
      }, {
        value: '深圳',
        label: '深圳'
      },{
        value: '武汉',
        label: '武汉'
      }, {
        value: '成都',
        label: '成都'
      }, {
        value: '杭州',
        label: '杭州'
        },],
      value1: [],
    }
  },
  created() {
    this.load()
  },
  mounted: {},
  methods: {
    load(pageNum) {
      if (pageNum) {
        this.pageNum = pageNum
      }
      const city = this.value1.join(',')
      this.$request.get('/tables/selectBySchool', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          square: this.square,
          city: city
        }
      }).then(res => {
        if (res.data) {
          // 确保在访问其属性之前，res.data不为null
          this.schools = res.data.records;
          console.log('Response data:', res.data);
          this.total = res.data.total;
        } else {
          // 处理res.data为null的情况
          this.tableData = [];
          this.total = 0;
        }
      });
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
  },
}
</script>

<style>

</style>
