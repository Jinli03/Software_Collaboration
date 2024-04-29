<!--
*@oneSchool
*@author Jinli
*@date 2024/3/19 0:00
-->
<template>
  <div>
    <h1>
      hello, {{ tables.school }}  {{ tables.sub }}
      <template>
        <a-affix :offset-top="80">
          <a-button type="primary" @click="$router.push('Search')">返回到分析局</a-button>
        </a-affix>
      </template>
    </h1>
    <div>
      <el-row :gutter="20" style="padding: 8px">
        <el-col :span="8">
          <el-card style="height: 40%">
            <div id="person" style="width: 100%; height: 400px"></div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card>
            <div id="reperson" style="width: 100%; height: 400px"></div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card>
            <div id="rescore" style="width: 100%; height: 400px"></div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <div>
      <el-card>
        <el-table
            :data="tablesdata"
            style="width: 100%"
            height="250">
          <el-table-column
              fixed
              prop="school"
              label="学校"
              width="150"
              align="center">
          </el-table-column>
          <el-table-column
              fixed
              prop="sub"
              label="专业"
              width="120"
              align="center">
          </el-table-column>
          <el-table-column
              prop="subcode"
              label="专业代码"
              width="120"
              align="center">
          </el-table-column>
          <el-table-column
              prop="city"
              label="城市"
              width="120"
              align="center">
          </el-table-column>
          <el-table-column
              prop="square"
              label="地区"
              width="120"
              align="center">
          </el-table-column>
          <el-table-column
              prop="person21"
              label="21年招收人数"
              width="120"
              align="center">
          </el-table-column>
          <el-table-column
              prop="person22"
              label="22年招收人数"
              width="120"
              align="center">
          </el-table-column>
          <el-table-column
              prop="person23"
              label="23年招收人数"
              width="120"
              align="center">
          </el-table-column>
          <el-table-column
              prop="rescore21"
              label="21年复试分数"
              width="120"
              align="center">
          </el-table-column>
          <el-table-column
              prop="rescore22"
              label="22年复试分数"
              width="120"
              align="center">
          </el-table-column>
          <el-table-column
              prop="rescore23"
              label="23年复试分数"
              width="120"
              align="center">
          </el-table-column>
          <el-table-column
              label="收藏"
              width="120"
              align="center">
            <template v-slot="scope">
              <el-button size="mini" type="primary" plain @click="handleWish(scope.row)">收藏</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>

    </div>
  </div>
</template>

<script>

import * as echarts from "echarts";

const option1 = {
  title: {
    text: '历年录取人数',
    subtext: '仅供参考',
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
    orient: 'horizontal', // 设置为水平方向
    bottom: 0 // 底部距离为0，您可以根据需要调整
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '',
      data: [],
    },
  ]
};
const option2 = {
  title: {
    text: '历年复试人数',
    subtext: '仅供参考',
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
    orient: 'horizontal', // 设置为水平方向
    bottom: 0 // 底部距离为0，您可以根据需要调整
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '',
      data: [],
    },
  ]
};
const option3 = {
  title: {
    text: '历年复试分数',
    subtext: '仅供参考',
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
    orient: 'horizontal', // 设置为水平方向
    bottom: 0 // 底部距离为0，您可以根据需要调整
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '',
      data: [],
    },
  ]
};
export default {
  name: "oneSchool",
  props: {},
  components: {},
  data() {
    return {
      id: '',
      tables: [],
      tablesdata: [],
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
    }
  },
  created() {
  },
  mounted() {
    let id = this.$route.query.id
    this.id = id
    if (!id) {
      return
    }
    this.load()
    this.load2()
  },
  methods: {
    load() {
      if (this.pageNum) { // 使用 this.pageNum 而不是 pageNum
        this.pageNum = this.pageNum;
      }

      let id = this.id;

      this.$request.get('/tables/distinctSub/' + id ).then(res => {
        let tables = res.data;
        console.log(tables)
        console.log(tables.personGroups)
        // 更新招收人数图表
        this.updateChart('person', tables.personGroups, option1);
        console.log(tables.personGroups)
        // 更新复试人数图表
        this.updateChart('reperson', tables.repersonGroups, option2);
        // 更新复试分数图表
        this.updateChart('rescore', tables.rescoreGroups, option3);
      }).catch(error => {
        console.error('Error fetching data:', error);
      });
    },
    load2() {
      let id = this.$route.query.id
      this.id = id
      this.$request.get('/tables/distinctSubInfo/' + id ).then(res => {
        this.tablesdata = res.data
        console.log(this.tablesdata)
      }).catch(error => {
        console.error('Error fetching data:', error);
      });
    },
    handleWish(row) {
      const data = {
        name: this.user.name,
        school: row.school,
        sub: row.sub,
        subcode: row.subcode,
        city: row.city,
        square: row.square,
        person21: row.person21,
        person22: row.person22,
        person23: row.person23,
        rescore21: row.rescore21,
        rescore22: row.rescore22,
        rescore23: row.rescore23,
        max: row.max,
        min: row.min,
        pic: row.pic
      };
      this.$request.put('/wishes/save', data).then(res => {
        if (res.code === '200') {
          this.$message.success('收藏成功')
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      });
    },

    updateChart(elementId, dataGroups, option) {
      let elementDom = document.getElementById(elementId);
      let elementChart = echarts.init(elementDom);

      const series = [];

      option.series = [
        {
          data: dataGroups,
          type: 'line',
          smooth: true
        },
      ];
      // 更新图表
      elementChart.setOption(option);
    }


  },
}
</script>

<style>

</style>
