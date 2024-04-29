<!--
*@Echarts
*@author Jinli
*@date 2024/3/2 14:12
-->
<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="12">
        <el-card>
          <div id="line" style="width: 100%; height: 400px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div id="bar" style="width: 100%; height: 400px"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="10" style="margin: 10px">
      <el-col :span="12">
        <el-card>
          <div id="pie" style="width: 100%; height: 400px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div id="test" style="width: 100%; height: 400px"></div>
        </el-card>
      </el-col>
    </el-row>



  </div>
</template>

<script>
import * as echarts from 'echarts'

const option = {
  title: {
    text: 'Referer of a Website',
    subtext: 'Fake Data',
    left: 'center'
  },
  xAxis: {
    type: 'category',
    data: ['1', '2', '3']
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
      name: '金额',
      data: [],
      type: 'line',
      smooth: true
    },
  ]
};

const option1 = {
  title: {
    text: 'Referer of a Website',
    subtext: 'Fake Data',
    left: 'center'
  },
  xAxis: {
    type: 'category',
    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
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
      name: '金额',
      data: [820, 932, 901, 934, 1290, 1330, 1320],
      type: 'line',
      smooth: true
    },
    {
      name: '库存',
      data: [111, 222, 333, 444, 555, 666, 777],
      type: 'line',
      smooth: true
    }
  ]
};

const option2 = {
  title: {
    text: 'Referer of a Website',
    subtext: 'Fake Data',
    left: 'center'
  },
  xAxis: {
    type: 'category',
    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
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
      name: '金额',
      data: [120, 200, 150, 80, 70, 110, 130],
      type: 'bar',
      showBackground: true,
      backgroundStyle: {
        color: 'rgba(180, 180, 180, 0.2)'
      }
    },
    {
      name: '库存',
      data: [120, 200, 150, 80, 70, 110, 130],
      type: 'bar',
      showBackground: true,
      backgroundStyle: {
        color: 'rgba(180, 180, 180, 0.2)'
      }
    }
  ]
};

const option3 = {
  title: {
    text: 'Referer of a Website',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: 'Access From',
      type: 'pie',
      radius: ['40%', '70%'],
      center: ['50%', '60%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 40,
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      data: [
        { value: 1048, name: 'Search Engine' },
        { value: 735, name: 'Direct' },
        { value: 580, name: 'Email' },
        { value: 484, name: 'Union Ads' },
        { value: 300, name: 'Video Ads' }
      ]
    }
  ]
};
export default {
  name: "Echarts",
  props: {},
  components: {},
  data() {
    return {
      option: {
        title: {
          text: 'Referer of a Website',
          subtext: 'Fake Data',
          left: 'center'
        },
        xAxis: {
          type: 'category',
          data: []
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
            name: '金额',
            data: [],
            type: 'line',
            smooth: true
          },
          {
            name: '库存',
            data: [],
            type: 'line',
            smooth: true
          }
        ]
      }
    };
  },

  created() {
  },
  mounted() {
    let lineDom = document.getElementById('line')
    let lineChart = echarts.init(lineDom)
    lineChart.setOption(option1)

    let barDom = document.getElementById('bar')
    let barChart = echarts.init(barDom)
    barChart.setOption(option2)

    let pieDom = document.getElementById('pie')
    let pieChart = echarts.init(pieDom)
    pieChart.setOption(option3)

    let testDom = document.getElementById('test')
    let testChart = echarts.init(testDom)

    this.$request.get('/tables/charts').then(res => {
      console.log('Response data:', res.data);
      const chartData = res.data;
      option.series = [
        {
          name: '金额',
          data: chartData,
          type: 'line',
          smooth: true
        },
      ];
      testChart.setOption(option);
    }).catch(error => {
      console.error('Error fetching data:', error);
    });

  },
  methods: {},
}
</script>

<style>

</style>
