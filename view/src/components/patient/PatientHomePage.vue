<!--
*@PatientHomePage
*@author Jinli
*@date 2024/7/2 8:06
-->
<template>
  <div>
    <el-card style="border-radius: 10px; margin-bottom: 10px">
      <p style="font-weight: bold; font-size: 20px;">系统首页</p>
      <el-card>
        <div v-if="record" style="display: flex; flex-direction: column;">
          <div>
            <p>姓名: {{ record.name }}</p>
            <p>医生: {{ record.doctor }}</p>
            <p>时间: {{ record.time }}</p>
            <p>状态: {{ record.state }}</p>
            <!-- 其他字段根据需要显示 -->
          </div>
          <div style="margin-top: auto;">
            <el-button type="primary" @click="fetchLatestRecord()">获取状态</el-button>
            <el-button type="warning" @click="charge">充值</el-button>
            <el-button type="success" @click="ok">就诊</el-button>
          </div>
        </div>
      </el-card>
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
              <div id="count" style="width: 100%; height: 310px"></div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-card>
    <el-dialog title="充值" :visible.sync="chargeVisible" width="70%">
      <el-form  :rules="rules" ref="formRef" label-width="80px" style="padding-right: 20px">
        <el-form-item label="avenue" prop="avenue">
          <el-radio v-model="radio" label="1">微信</el-radio>
          <el-radio v-model="radio" label="2">支付宝</el-radio>
        </el-form-item>
        <el-form-item label="balance" prop="balance">
          <el-input v-model="balance" placeholder="null"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="chargeVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateBalance">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "PatientHomePage",
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
          timestamp: '2024-07-02',
          size: 'large',
          type: 'primary',
          icon: 'el-icon-more'
        },
        {
          content: '美化',
          timestamp: '2024-07-04',
          size: 'large',
          type: 'primary',
          icon: 'el-icon-more'
        },
        {
          content: '测试',
          timestamp: '2024-07-01',
          size: 'large'
        },
        {
          content: '结束',
          timestamp: '2024-07-01'
        }
      ],
      chargeVisible: false,
      user: JSON.parse(localStorage.getItem('pilot') || '{}'),
      balance: '',
      radio: '1',
      count: '',
      record: {}
    }
  },
  created() {
  },
  mounted() {
    this.fetchLatestRecord()
    this.getAllCount()
  },
  methods: {
    ok() {
      this.$request.get('/records/editState', {
        params: {
          id: this.record.id,
          state: '已就诊'
        }
      }).then(res => {
        if (res.data) {
          console.log('Response data:', res.data);
        }
      });
    },
    fetchLatestRecord() {
      // 发送请求获取最大ID记录信息
      this.$request.get('/records/selectMaxIdRecordByName', {
        params: {
          name: this.user.name  // 替换成实际的姓名
        }
      })
          .then(response => {
            this.record = response.data; // 假设返回的数据结构中包含最大ID的记录信息
            console.log('获取到最大ID记录信息:', this.record);
          })
          .catch(error => {
            console.error('请求获取最大ID记录信息失败:', error);
          });
    },
    charge() {
      this.chargeVisible = true
    },
    updateBalance() {
      this.$request.put('/user/updateBalance', null, {
        params: {
          name: this.user.name,
          balance: this.balance
        }
      })
          .then(res => {
            if (res.data) {
              this.$message.success('更新成功');
              this.balance = ''
              this.chargeVisible = false
            } else {
              this.$message.error('更新失败');
            }
          })
          .catch(error => {
            console.error('Error updating balance:', error);
            this.$message.error('更新失败');
          });
    },
    getAllCount() {
      this.$request.get('/records/countAllByPatient', {
        params: {
          name: this.user.name,
        },
      })
          .then(res => {
            if (res.data) {
              this.count = res.data; // assuming the API response structure { data: { data: count } }
              console.log('就诊次数' + this.count);
              this.initChart1()
            } else {
              console.error('No data found');
            }
          })
          .catch(error => {
            console.error('Error fetching data:', error);
          });
    },
    initChart1() {
      const echart1Dom = document.getElementById('count');
      let echart1Chart = echarts.getInstanceByDom(echart1Dom);
      if (echart1Chart) {
        echarts.dispose(echart1Dom);
      }
      echart1Chart = echarts.init(echart1Dom);
      echart1Chart.setOption(this.getChartOption1());
    },
    getChartOption1() {
      return {
        title: {
          text: '就诊次数',
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
            data: ['就诊次数'], // 替换为实际姓名数据
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
            name: '就诊次数',
            type: 'bar',
            barWidth: '60%',
            data: [this.count] // 替换为实际挂号次数数据
          }
        ]
      };
    },
  },
}
</script>

<style>

</style>
