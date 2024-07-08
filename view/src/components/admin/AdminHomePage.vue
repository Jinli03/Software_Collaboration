<template>
  <div>
    <el-card style="border-radius: 10px; margin-bottom: 10px">
      <p style="font-weight: bold; font-size: 20px;">系统首页</p>
    </el-card>
    <el-card style="border-radius: 10px; margin: 20px">
      <p>系统公告</p>
      <div>
        <el-row :gutter="20">
          <el-col :span="12">
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
              <div id="records" style="width: 100%; height: 310px"></div>
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
              <div id="refuse" style="width: 100%; height: 310px"></div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "AdminHomePage",
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
      records: [], // 存储科室挂号次数数据
      count: [], // 存储姓名挂号次数数据
      refuse: []
    };
  },
  mounted() {
    this.countByDepartment(); // 获取科室挂号次数数据
    this.countByName(); // 获取姓名挂号次数数据
    this.countByRefuse()
  },
  methods: {
    // 获取科室挂号次数数据
    countByDepartment() {
      this.$request.get('/records/countByDepartment').then(res => {
        if (res.data) {
          this.records = res.data;
          console.log('科室挂号次数数据:', this.records);
          this.initChart1(); // 数据获取成功后初始化饼图
        } else {
          console.error('Failed to fetch department count data');
        }
      }).catch(err => {
        console.error('Error fetching department count data:', err);
      });
    },
    // 获取姓名挂号次数数据
    countByName() {
      this.$request.get('/records/countRecordsByNameSorted').then(res => {
        if (res.data) {
          this.count = res.data;
          console.log('姓名挂号次数数据:', this.count);
          this.initChart2(); // 数据获取成功后初始化柱状图
        } else {
          console.error('Failed to fetch name count data');
        }
      }).catch(err => {
        console.error('Error fetching name count data:', err);
      });
    },
    countByRefuse() {
      this.$request.get('/records/countRecordsByRefuse').then(res => {
        if (res.data) {
          this.refuse = res.data;
          console.log('姓名挂号次数数据:', this.refuse);
          this.initChart3(); // 数据获取成功后初始化柱状图
        } else {
          console.error('Failed to fetch name count data');
        }
      }).catch(err => {
        console.error('Error fetching name count data:', err);
      });
    },
    // 初始化科室挂号次数饼图
    initChart1() {
      const echart1Dom = document.getElementById('records');
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
      const echart3Dom = document.getElementById('refuse');
      let echart3Chart = echarts.getInstanceByDom(echart3Dom);
      if (echart3Chart) {
        echarts.dispose(echart3Dom);
      }
      echart3Chart = echarts.init(echart3Dom);
      echart3Chart.setOption(this.getChartOption3());
    },
    // 配置科室挂号次数饼图选项
    getChartOption1() {
      if (this.records.length === 0) {
        return {}; // 如果 records 为空，返回空对象或者适当的默认配置
      }
      return {
        title: {
          text: '科室挂号次数',
          subtext: ''
        },
        legend: {
          top: 'bottom'
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        series: [
          {
            name: '科室挂号次数',
            type: 'pie',
            radius: [30, 100],
            center: ['50%', '40%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            data: [
              { value: this.findDepartmentCount('儿科'), name: '儿科' },
              { value: this.findDepartmentCount('内科'), name: '内科' },
              { value: this.findDepartmentCount('外科'), name: '外科' },
            ]
          }
        ]
      };
    },
    // 配置姓名挂号次数柱状图选项
    getChartOption2() {
      if (this.count.length === 0) {
        return {}; // 如果 count 为空，返回空对象或者适当的默认配置
      }
      // 示例数据，需要根据实际后端数据进行替换
      const xAxisData = this.count.map(item => item.name); // X 轴数据
      const seriesData = this.count.map(item => item.count); // 柱状图数据

      return {
        title: {
          text: '患者挂号次数',
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
            data: xAxisData, // 替换为实际姓名数据
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
            name: '患者挂号次数',
            type: 'bar',
            barWidth: '60%',
            data: seriesData // 替换为实际挂号次数数据
          }
        ]
      };
    },

    getChartOption3() {
      if (this.refuse.length === 0) {
        return {}; // 如果 count 为空，返回空对象或者适当的默认配置
      }
      // 示例数据，需要根据实际后端数据进行替换
      const xAxisData1 = this.refuse.map(item => item.name); // X 轴数据
      const seriesData1 = this.refuse.map(item => item.count); // 柱状图数据

      return {
        title: {
          text: '患者取消挂号次数',
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
            data: xAxisData1, // 替换为实际姓名数据
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
            name: '取消挂号次数',
            type: 'bar',
            barWidth: '60%',
            data: seriesData1 // 替换为实际挂号次数数据
          }
        ]
      };
    },
    // 查找特定科室的挂号次数
    findDepartmentCount(department) {
      const record = this.records.find(record => record.department === department);
      return record ? record.count : 0;
    }
  }
};
</script>

<style scoped>
#records {
  width: 100%;
  height: 100%;
}

#count {
  width: 100%;
  height: 100%;
}
</style>
