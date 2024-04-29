<!--
*@PersonWishes
*@author Jinli
*@date 2024/4/6 1:23
-->
<template>
  <div>
    <template>
      <a-affix :offset-top="80">
        <a-button type="primary" @click="$router.push('Personal')">返回到个人档案局</a-button>
      </a-affix>
    </template>
    <el-card>
      <el-table :data="tables" style="width: 100%" height="500" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="50" align="center">
        </el-table-column>
        <el-table-column  prop="id" label="id" align="center" visible="false">
        </el-table-column>
        <el-table-column fixed prop="school" label="学校" width="150" align="center">
        </el-table-column>
        <el-table-column fixed prop="sub" label="专业" width="120" align="center">
        </el-table-column>
        <el-table-column prop="subcode" label="专业代码" width="120" align="center">
        </el-table-column>
        <el-table-column prop="city" label="城市" width="120" align="center">
        </el-table-column>
        <el-table-column prop="square" label="地区" width="120" align="center">
        </el-table-column>
        <el-table-column prop="person21" label="21年招收人数" width="120" align="center">
        </el-table-column>
        <el-table-column prop="person22" label="22年招收人数" width="120" align="center">
        </el-table-column>
        <el-table-column prop="person23" label="23年招收人数" width="120" align="center">
        </el-table-column>
        <el-table-column prop="rescore21" label="21年复试分数" width="120" align="center">
        </el-table-column>
        <el-table-column prop="rescore22" label="22年复试分数" width="120" align="center">
        </el-table-column>
        <el-table-column prop="rescore23" label="23年复试分数" width="120" align="center">
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template v-slot="scope">
            <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <p>请选择两个院校进行对比</p>
      <el-button type="info" @click="compare">对比</el-button>


    </el-card>
    <el-drawer
        title="我是学校1"
        :visible.sync="drawer1"
        direction="ltr"
        :modal="false"
        size="40%"
        :with-header="false">
      <div>
        {{ selectedRows[0].city }}
        {{ selectedRows[0].school }}
        {{ selectedRows[0].sub }}
        <el-row>
          <el-card>
            <div id="rescore1" style="height: 400px"></div>
          </el-card>
        </el-row>
        <el-row>
          <el-card>
            <div id="person1" style="height: 400px"></div>
          </el-card>
        </el-row>
      </div>
    </el-drawer>
    <el-drawer
        title="我是学校2"
        :visible.sync="drawer2"
        direction="rtl"
        :modal="false"
        size="40%"
        :with-header="false">
      <div>
        {{ selectedRows[1].city }}
        {{ selectedRows[1].school }}
        {{ selectedRows[1].sub }}
        <el-row>
          <el-card>
            <div id="rescore2" style="height: 400px"></div>
          </el-card>
        </el-row>
        <el-row>
          <el-card>
            <div id="person2" style="height: 400px"></div>
          </el-card>
        </el-row>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import * as echarts from 'echarts'
const option1 = {
  title: {
    text: '分数线',
    subtext: 'Fake Data',
    left: 'center'
  },
  xAxis: {
    type: 'category',
    data: ['2021', '2022', '2023']
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '分数线',
      data: [],
      type: 'line',
      smooth: true
    }
  ]
};

const option2 = {
  title: {
    text: '招收人数',
    subtext: 'Fake Data',
    left: 'center'
  },
  xAxis: {
    type: 'category',
    data: ['2021', '2022', '2023']
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '招收人数',
      data: [],
      type: 'line',
      smooth: true
    }
  ]
};

const option3 = {
  title: {
    text: '分数线',
    subtext: 'Fake Data',
    left: 'center'
  },
  xAxis: {
    type: 'category',
    data: ['2021', '2022', '2023']
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '分数线',
      data: [],
      type: 'line',
      smooth: true
    }
  ]
};

const option4 = {
  title: {
    text: '招收人数',
    subtext: 'Fake Data',
    left: 'center'
  },
  xAxis: {
    type: 'category',
    data: ['2021', '2022', '2023']
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '招收人数',
      data: [],
      type: 'line',
      smooth: true
    }
  ]
};
export default {
  name: "PersonWishes",
  props: {},
  components: {},
  data() {
    return {
      name: '',
      tables: [],
      selectedRows: [{ id: 0, max: 0, min: 0, name: 0, person21: 0, person22: 0, person23: 0, pic: 0, reperson21: 0, reperson22: 0, reperson23: 0, rescore21: 0, rescore22: 0, rescore23: 0, school: 0, square: 0, sub: 0, subcode: 0, city: 0 },
        { id: 0, max: 0, min: 0, name: 0, person21: 0, person22: 0, person23: 0, pic: 0, reperson21: 0, reperson22: 0, reperson23: 0, rescore21: 0, rescore22: 0, rescore23: 0, school: 0, square: 0, sub: 0, subcode: 0, city: 0}],
      drawer1: false,
      drawer2: false,
    }
  },
  created() {
  },
  mounted() {
    let name = this.$route.query.name
    this.name = name
    this.load()
  },
  methods: {
    load() {
      let name = this.name
      this.$request.get('/wishes/selectAll/' + name).then(res => {
        this.tables = res.data
        console.log(tables)
      }).catch(error => {
        console.error('Error fetching data:', error);
      });
    },
    handleSelectionChange(selection) {
      this.selectedRows = selection; // 更新选中的行数据
    },
    compare() {
      console.log(this.selectedRows.length)
      // 检查选中的行数是否为两行
      if (this.selectedRows.length === 2) {
        this.drawer1 = true
        this.$nextTick(() => {
          option1.series[0].data = [
            this.selectedRows[0].rescore21,
            this.selectedRows[0].rescore22,
            this.selectedRows[0].rescore23
          ];

          let rescore1Dom = document.getElementById('rescore1')
          let rescore1Chart = echarts.init(rescore1Dom)
          rescore1Chart.setOption(option1)


          option2.series[0].data = [
            this.selectedRows[0].person21,
            this.selectedRows[0].person22,
            this.selectedRows[0].person23
          ];

          let person1Dom = document.getElementById('person1')
          let person1Chart = echarts.init(person1Dom)
          person1Chart.setOption(option2)

        });
        this.drawer2 = true
        this.$nextTick(() => {
          option3.series[0].data = [
            this.selectedRows[1].rescore21,
            this.selectedRows[1].rescore22,
            this.selectedRows[1].rescore23
          ];

          let rescore2Dom = document.getElementById('rescore2')
          let rescore2Chart = echarts.init(rescore2Dom)
          rescore2Chart.setOption(option3)


          option4.series[0].data = [
            this.selectedRows[1].person21,
            this.selectedRows[1].person22,
            this.selectedRows[1].person23
          ];

          let person2Dom = document.getElementById('person2')
          let person2Chart = echarts.init(person2Dom)
          person2Chart.setOption(option4)

        });
      } else {
        // 提示用户选择两行进行对比
        this.$message.error('请选择两个院校进行对比');
      }
    },
    del(id) {
      this.$confirm('确认删除?', '确认', {type: "warning"}).then(response =>{
        this.$request.delete('/wishes/delete/' + id ).then(res =>{

          if (res.code === '200') {
            this.$message.success('删除成功')
            this.load()
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {})
    },
  }
}
</script>

<style>

</style>
