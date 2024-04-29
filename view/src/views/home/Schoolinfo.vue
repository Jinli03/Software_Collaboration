<!--
*@Schoolinfo
*@author Jinli
*@date 2024/3/24 21:14
-->
<template>
  <div>
    <div>
      <h1>
        hello, {{ school }}
        <template>
          <a-affix :offset-top="80">
            <a-button type="primary" @click="$router.push('Search')">返回到分析局</a-button>
          </a-affix>
        </template>
      </h1>
      <el-col :span="8">
        <el-card>
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
            <<template v-slot="scope">
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
    orient: 'vertical',
    left: 'left'
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
    orient: 'vertical',
    left: 'left'
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
    orient: 'vertical',
    left: 'left'
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
  name: "schoolinfo",
  props: {},
  components: {},
  data() {
    return {
      school: '',
      tables: [],
      tablesdata: [],
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
    }
  },
  created() {
  },
  mounted() {
    let school = this.$route.query.school
    this.school = school
    if (!school) {
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
      let school = this.school
      this.$request.get('/tables/distinctSchool/' + school ).then(res => {
        this.tables = res.data
        //录取人数
        let personDom = document.getElementById('person')
        let personChart = echarts.init(personDom)
        // 初始化系列数组
        const series1 = [];

// 循环遍历personGroups对象中的所有子属性
        for (const sub in this.tables.personGroups) {
          if (Object.hasOwnProperty.call(this.tables.personGroups, sub)) {
            // 获取子属性对应的数组
            const subArray1 = this.tables.personGroups[sub];

            // 生成系列对象
            const seriesItem1 = {
              name: sub, // 使用子属性作为系列名称
              data: subArray1, // 使用子属性对应的数组作为系列数据
              type: 'line',
              smooth: true
            };

            // 将系列对象添加到系列数组中
            series1.push(seriesItem1);
          }
        }

// 设置ECharts的series属性
        option1.series = series1;

// 更新图表
        personChart.setOption(option1);


        //复试人数
        let repersonDom = document.getElementById('reperson')
        let repersonChart = echarts.init(repersonDom)
        // 初始化系列数组
        const series2 = [];

// 循环遍历personGroups对象中的所有子属性
        for (const sub in this.tables.repersonGroups) {
          if (Object.hasOwnProperty.call(this.tables.repersonGroups, sub)) {
            // 获取子属性对应的数组
            const subArray2 = this.tables.repersonGroups[sub];

            // 生成系列对象
            const seriesItem2 = {
              name: sub, // 使用子属性作为系列名称
              data: subArray2, // 使用子属性对应的数组作为系列数据
              type: 'line',
              smooth: true
            };

            // 将系列对象添加到系列数组中
            series2.push(seriesItem2);
          }
        }

// 设置ECharts的series属性
        option2.series = series2;

// 更新图表
        repersonChart.setOption(option2);


        //复试分数
        let rescoreDom = document.getElementById('rescore')
        let rescoreChart = echarts.init(rescoreDom)
        // 初始化系列数组
        const series3 = [];

// 循环遍历personGroups对象中的所有子属性
        for (const sub in this.tables.rescoreGroups) {
          if (Object.hasOwnProperty.call(this.tables.rescoreGroups, sub)) {
            // 获取子属性对应的数组
            const subArray3 = this.tables.rescoreGroups[sub];

            // 生成系列对象
            const seriesItem3 = {
              name: sub, // 使用子属性作为系列名称
              data: subArray3, // 使用子属性对应的数组作为系列数据
              type: 'line',
              smooth: true
            };

            // 将系列对象添加到系列数组中
            series3.push(seriesItem3);
          }
        }

// 设置ECharts的series属性
        option3.series = series3;

// 更新图表
        rescoreChart.setOption(option3);
      }).catch(error => {
        console.error('Error fetching data:', error);
      });
    },
    load2() {
      let school = this.school
      this.$request.get('/tables/distinctSchoolInfo/' + school ).then(res => {
        this.tablesdata = res.data
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
    }
  },
}
</script>

<style>

</style>
