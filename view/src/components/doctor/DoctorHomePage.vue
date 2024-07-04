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

            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "DoctorHomePage",
  props: {},
  components: {},
  data() {
    return {
      activities: [{
        content: '需求',
        timestamp: '2024-07-01',
        color: '#0bbd87'
      }, {
        content: '开发',
        timestamp: '2024-07-01',
        size: 'large',
        type: 'primary',
        icon: 'el-icon-more'
      }, {
        content: '测试',
        timestamp: '2024-07-01',
        size: 'large'
      }, {
        content: '结束',
        timestamp: '2024-07-01'
      }]
    }
  },
  created() {
  },
  mounted() {

  },
  methods: {},
}
</script>

<style>

</style>
