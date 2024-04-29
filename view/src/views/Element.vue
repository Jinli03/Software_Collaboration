<template>
  <div>
    <el-row>
      <el-col :span="24"><div class="grid-content bg-purple-dark"></div></el-col>
    </el-row>
    <el-row>
      <el-col :span="12"><div class="grid-content bg-purple"></div></el-col>
      <el-col :span="12"><div class="grid-content bg-purple-light"></div></el-col>
    </el-row>
    <el-row>
      <el-col :span="8"><div class="grid-content bg-purple"></div></el-col>
      <el-col :span="8"><div class="grid-content bg-purple-light"></div></el-col>
      <el-col :span="8"><div class="grid-content bg-purple"></div></el-col>
    </el-row>
    <el-row>
      <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
      <el-col :span="6"><div class="grid-content bg-purple-light"></div></el-col>
      <el-col :span="6"><div class="grid-content bg-purple"></div></el-col>
      <el-col :span="6"><div class="grid-content bg-purple-light"></div></el-col>
    </el-row>
    <el-row>
      <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
      <el-col :span="4"><div class="grid-content bg-purple-light"></div></el-col>
      <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
      <el-col :span="4"><div class="grid-content bg-purple-light"></div></el-col>
      <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
      <el-col :span="4"><div class="grid-content bg-purple-light"></div></el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-button type="primary">按钮</el-button>
        <el-button type="danger">按钮</el-button>
        <el-button type="warning">按钮</el-button>
        <el-button type="info">按钮</el-button>
        <el-button type="success">按钮</el-button>
        <el-button type="primary" plain>按钮</el-button>
        <el-button type="primary" round>按钮</el-button>
        <el-button type="primary" circle>伟</el-button>
        <el-button type="primary" circle icon="el-icon-edit"></el-button>
        <el-button type="primary" circle>
          <img src="" alt="图标">
        </el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-input clearable show-password style="width: 200px" v-model="value" placeholder="请输入文字"></el-input>
<!--        强制绑定v-model-->
        <el-input type="textarea" style="width: 200px" v-model="value"></el-input>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-autocomplete :fetch-suggestions="querySearch" :trigger-on-focus="false" v-model="value1"></el-autocomplete>
      </el-col>
    </el-row>
    <el-row>
      <el-select v-model="select" @change="change" multiple>
        <el-option v-for="item in fruits" :key="item.id" :label="item.name" :value="item.id"></el-option>
      </el-select>
    </el-row>

    <el-row>
      <el-select v-model="select1" @change="change" filterable>
        <el-option v-for="item in fruit" :key="item.id" :label="item.label" :value="item.id"></el-option>
      </el-select>
    </el-row>
<!--    单选-->
    <el-row>
      <el-radio-group v-model="sex">
        <el-radio label="男"></el-radio>
        <el-radio label="女"></el-radio>
      </el-radio-group>
    </el-row>
<!--    多选-->
    <el-checkbox-group v-model="color">
      <el-checkbox label="蓝"></el-checkbox>
      <el-checkbox label="粉"></el-checkbox>
    </el-checkbox-group>
    <el-date-picker v-model="date" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" @change="changeDate"></el-date-picker>
    <el-date-picker v-model="datetime" type="datetime" placeholder="选择日期和时间" value-format="yyyy-MM-dd HH:mm:ss" @change="changeDateTime"></el-date-picker>
    <el-date-picker v-model="daterange" type="daterange" start-placeholder="开始时间" end-placeholder="结束时间" value-format="yyyy-MM-dd" @change="changeDateRange" ></el-date-picker>
    <el-row style="margin: 20px 0">
      <el-table :data="tabledata" border :header-cell-style="{background: 'grey', fontSize: '16px'}">
        <el-table-column label="name" prop="name" align="center"></el-table-column>
        <el-table-column label="id" prop="id" align="center"></el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" @click="alert(scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return{
      value: '',
      value1: '',
      select: '',
      select1: '',
      sex: '',
      date: '',
      datetime: '',
      daterange: '',
      color: [],
      fruits: [
        {name: '苹果', id: 1},
        {name: '香蕉', id: 2},
        {name: '橘子', id: 3},
      ],
      fruit: [
        {label: '苹果', id: 1},
        {label: '香蕉', id: 2},
        {label: '橘子', id: 3},
      ],
      coffee: [{ value: '星巴克'}, { value: '瑞幸'}, { value: '库迪'},],
      tabledata: [
        {name: '你好', id: 1}
      ]
    }
  },
  methods: {
    querySearch(query, cb) {
      let result = query ? this.coffee.filter(v => v.value.includes(query)) : this.coffee
      cb(result)
    },
    change() {
      console.log(this.select)
    },
    changeDate() {
      console.log(this.date)
    },
    changeDateTime() {
      console.log(this.datetime)
    },
    changeDateRange() {
      console.log(this.daterange)
    },
    alert(row) {
      this.$message.success(row.name)
    }
  }
}
</script>

<style>
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>
<script setup>
</script>