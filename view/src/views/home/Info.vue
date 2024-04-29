<!--
*@Info
*@author Jinli
*@date 2024/3/12 13:32
-->
<template>
  <div>
    <el-col :span="12">
      <el-card>
        <div id="test" style="width: 100%; height: 400px"></div>
      </el-card>
    </el-col>
  </div>
</template>

<script>
import * as echarts from "echarts";

const option = {
  title: {
    text: '历年复试人数',
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
      name: '',
      data: [],
    },
  ]
};

export default {
  name: "Info",
  props: {},
  components: {},
  data() {
    return {
      tables: {},
      user: {},

    }
  },
  created() {
  },
  mounted() {
    let id = this.$route.query.id
    if (!id) {
      return
    }
    let testDom = document.getElementById('test')
    let testChart = echarts.init(testDom)

    this.$request.get('/tables/charts/' + id ).then(res => {
      console.log('Response data:', res.data);
      const chartData = res.data;
      option.series = [
        {
          name: '复试人数',
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
