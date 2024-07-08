<!--
*@DoctorHomePage
*@author Jinli
*@date 2024/7/2 13:34
-->
<template>
  <div>
    <el-card style="border-radius: 10px; margin-bottom: 10px">
      <p style="font-weight: bold; font-size: 20px;">系统首页</p>
    </el-card>
    <el-card style="border-radius: 10px">
      <p>系统公告</p>
      <div>
        <el-row :gutter="20">
          <el-col :span="12" >
            <el-card style="border-radius: 10px">
              <el-timeline>
                <el-timeline-item
                    v-for="(activity, index) in activities"
                    :key="index"
                    :icon="activity.icon"
                    :type="activity.type"
                    :color="activity.color"
                    :size="activity.size"
                    :timestamp="activity.timestamp">
                  {{activity.content}}
                </el-timeline-item>
              </el-timeline>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card style="border-radius: 10px">
              <div id="pendingCount" style="width: 100%; height: 310px"></div>
            </el-card>
          </el-col>
        </el-row>
        <div style="margin: 10px"></div>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-card style="border-radius: 10px">
              <div id="count" style="width: 100%; height: 310px"></div>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card style="border-radius: 10px">
              <div id="all" style="width: 100%; height: 310px"></div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "DoctorHomePage",
  props: {},
  components: {},
  data() {
    return {
      activities: [
        {
          content: '需求',
          timestamp: '2024-07-01',
          color: '#0bbd87'
        },
        {
          content: '功能',
          timestamp: '2024-07-03',
          size: 'large',
          type: 'primary',
          color: '#0bbd87'
        },
        {
          content: '美化',
          timestamp: '2024-07-06',
          size: 'large',
          type: 'primary',
          color: '#0bbd87'
        },
        {
          content: '测试',
          timestamp: '2024-07-08',
          size: 'large',
          color: '#0bbd87'
        },
        {
          content: '结束',
          timestamp: '2024-07-10',
          type: 'primary',
          icon: 'el-icon-more'
        }
      ],
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
      pendingCount: '',
      count: '',
      all: ''
    }
  },
  created() {
  },
  mounted() {
    this.getPendingCount()
    this.getAllCount()
    this.getAll()
  },
  methods: {
    getPendingCount() {
      this.$request.get('/records/countPendingByDoctor', {
        params: {
          doctor: this.user.name,
        },
      })
          .then(res => {
            if (res.data) {
              this.pendingCount = res.data; // assuming the API response structure { data: { data: count } }
              console.log('待就诊' + this.pendingCount);
              this.initChart1()
            } else {
              console.error('No data found');
            }
          })
          .catch(error => {
            console.error('Error fetching data:', error);
          });
    },
    getAllCount() {
      this.$request.get('/records/countAllByDoctor', {
        params: {
          doctor: this.user.name,
        },
      })
          .then(res => {
            if (res.data) {
              this.count = res.data; // assuming the API response structure { data: { data: count } }
              console.log('已就诊' + this.count);
              this.initChart2()
            } else {
              console.error('No data found');
            }
          })
          .catch(error => {
            console.error('Error fetching data:', error);
          });
    },
    getAll() {
      this.$request.get('/priod/countAllByDoctor', {
        params: {
          doctor: this.user.name,
        },
      })
          .then(res => {
            if (res.data) {
              this.all = res.data; // assuming the API response structure { data: { data: count } }
              console.log('排班' + this.all);
              this.initChart3()
            } else {
              console.error('No data found');
            }
          })
          .catch(error => {
            console.error('Error fetching data:', error);
          });
    },
    initChart1() {
      const echart1Dom = document.getElementById('pendingCount');
      let echart1Chart = echarts.getInstanceByDom(echart1Dom);
      if (echart1Chart) {
        echarts.dispose(echart1Dom);
      }
      echart1Chart = echarts.init(echart1Dom);
      echart1Chart.setOption(this.getChartOption1());
    },
    // 初始化姓名挂号次数柱状图
    initChart2() {
      const echart2Dom = document.getElementById('count');
      let echart2Chart = echarts.getInstanceByDom(echart2Dom);
      if (echart2Chart) {
        echarts.dispose(echart2Dom);
      }
      echart2Chart = echarts.init(echart2Dom);
      echart2Chart.setOption(this.getChartOption2());
    },
    initChart3() {
      const echart3Dom = document.getElementById('all');
      let echart3Chart = echarts.getInstanceByDom(echart3Dom);
      if (echart3Chart) {
        echarts.dispose(echart3Dom);
      }
      echart3Chart = echarts.init(echart3Dom);
      echart3Chart.setOption(this.getChartOption3());
    },
    getChartOption1() {
      return {
        title: {
          text: '待就诊人数',
          subtext: ''
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        xAxis: [
          {
            type: 'category',
            data: ['待就诊人数'], // 替换为实际姓名数据
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '待就诊人数',
            type: 'bar',
            barWidth: '60%',
            data: [this.pendingCount] // 替换为实际挂号次数数据
          }
        ]
      };
    },
    getChartOption2() {
      return {
        title: {
          text: '已就诊人数',
          subtext: ''
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        xAxis: [
          {
            type: 'category',
            data: ['已就诊人数'], // 替换为实际姓名数据
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '已就诊人数',
            type: 'bar',
            barWidth: '60%',
            data: [this.count] // 替换为实际挂号次数数据
          }
        ]
      };
    },
    getChartOption3() {
      return {
        title: {
          text: '排班',
          subtext: ''
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        xAxis: [
          {
            type: 'category',
            data: ['排班'], // 替换为实际姓名数据
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '排班',
            type: 'bar',
            barWidth: '60%',
            data: [this.all] // 替换为实际挂号次数数据
          }
        ]
      };
    },
  },
}
</script>

<style>

</style>
