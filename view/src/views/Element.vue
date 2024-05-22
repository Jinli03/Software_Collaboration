<template>
  <div class="main-container">
    <header class="header">
      <el-row>
        <el-col :span="24">
          <div class="header-content">页面标题</div>
        </el-col>
      </el-row>
    </header>

    <section class="grid-section">
      <el-row>
        <el-col :span="24">
          <div class="grid-content bg-purple-dark">大网格</div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <div class="grid-content bg-purple">左侧网格</div>
        </el-col>
        <el-col :span="12">
          <div class="grid-content bg-purple-light">右侧网格</div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <div class="grid-content bg-purple">三分之一网格</div>
        </el-col>
        <el-col :span="8">
          <div class="grid-content bg-purple-light">三分之一网格</div>
        </el-col>
        <el-col :span="8">
          <div class="grid-content bg-purple">三分之一网格</div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <div class="grid-content bg-purple">四分之一网格</div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content bg-purple-light">四分之一网格</div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content bg-purple">四分之一网格</div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content bg-purple-light">四分之一网格</div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="4">
          <div class="grid-content bg-purple">六分之一网格</div>
        </el-col>
        <el-col :span="4">
          <div class="grid-content bg-purple-light">六分之一网格</div>
        </el-col>
        <el-col :span="4">
          <div class="grid-content bg-purple">六分之一网格</div>
        </el-col>
        <el-col :span="4">
          <div class="grid-content bg-purple-light">六分之一网格</div>
        </el-col>
        <el-col :span="4">
          <div class="grid-content bg-purple">六分之一网格</div>
        </el-col>
        <el-col :span="4">
          <div class="grid-content bg-purple-light">六分之一网格</div>
        </el-col>
      </el-row>
    </section>

    <section class="button-section">
      <el-row>
        <el-col :span="24">
          <div class="button-group">
            <el-button type="primary">主要按钮</el-button>
            <el-button type="danger">危险按钮</el-button>
            <el-button type="warning">警告按钮</el-button>
            <el-button type="info">信息按钮</el-button>
            <el-button type="success">成功按钮</el-button>
            <el-button type="primary" plain>朴素按钮</el-button>
            <el-button type="primary" round>圆角按钮</el-button>
            <el-button type="primary" circle>伟</el-button>
            <el-button type="primary" circle icon="el-icon-edit"></el-button>
            <el-button type="primary" circle>
              <img src="" alt="图标">
            </el-button>
          </div>
        </el-col>
      </el-row>
    </section>

    <section class="input-section">
      <el-row>
        <el-col :span="24">
          <div class="input-group">
            <el-input clearable show-password style="width: 100%" v-model="value" placeholder="请输入文字"></el-input>
            <el-input type="textarea" style="width: 100%" v-model="value"></el-input>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-autocomplete :fetch-suggestions="querySearch" :trigger-on-focus="false" v-model="value1" placeholder="搜索咖啡店"></el-autocomplete>
        </el-col>
      </el-row>
    </section>

    <section class="select-section">
      <el-row>
        <el-col :span="24">
          <el-select v-model="select" @change="change" multiple placeholder="请选择水果">
            <el-option v-for="item in fruits" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-select v-model="select1" @change="change" filterable placeholder="请选择水果">
            <el-option v-for="item in fruit" :key="item.id" :label="item.label" :value="item.id"></el-option>
          </el-select>
        </el-col>
      </el-row>
    </section>

    <section class="radio-section">
      <el-row>
        <el-col :span="24">
          <el-radio-group v-model="sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-col>
      </el-row>
    </section>

    <section class="checkbox-section">
      <el-row>
        <el-col :span="24">
          <el-checkbox-group v-model="color">
            <el-checkbox label="蓝"></el-checkbox>
            <el-checkbox label="粉"></el-checkbox>
          </el-checkbox-group>
        </el-col>
      </el-row>
    </section>

    <section class="date-picker-section">
      <el-row>
        <el-col :span="24">
          <el-date-picker v-model="date" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" @change="changeDate"></el-date-picker>
          <el-date-picker v-model="datetime" type="datetime" placeholder="选择日期和时间" value-format="yyyy-MM-dd HH:mm:ss" @change="changeDateTime"></el-date-picker>
          <el-date-picker v-model="daterange" type="daterange" start-placeholder="开始时间" end-placeholder="结束时间" value-format="yyyy-MM-dd" @change="changeDateRange"></el-date-picker>
        </el-col>
      </el-row>
    </section>

    <section class="table-section" style="margin: 20px 0">
      <el-row>
        <el-col :span="24">
          <el-table :data="tabledata" border :header-cell-style="{ background: 'grey', fontSize: '16px' }">
            <el-table-column label="名字" prop="name" align="center"></el-table-column>
            <el-table-column label="ID" prop="id" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
              <template v-slot="scope">
                <el-button type="primary" @click="alert(scope.row)">编辑</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </section>
  </div>
</template>

<script>
export default {
  data() {
    return {
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
        { name: '苹果', id: 1 },
        { name: '香蕉', id: 2 },
        { name: '橘子', id: 3 },
      ],
      fruit: [
        { label: '苹果', id: 1 },
        { label: '香蕉', id: 2 },
        { label: '橘子', id: 3 },
      ],
      coffee: [{ value: '星巴克' }, { value: '瑞幸' }, { value: '库迪' }],
      tabledata: [{ name: '你好', id: 1 }],
    };
  },
  methods: {
    querySearch(query, cb) {
      let result = query ? this.coffee.filter((v) => v.value.includes(query)) : this.coffee;
      cb(result);
    },
    change() {
      console.log(this.select);
    },
    changeDate() {
      console.log(this.date);
    },
    changeDateTime() {
      console.log(this.datetime);
    },
    changeDateRange() {
      console.log(this.daterange);
    },
    alert(row) {
      this.$message.success(row.name);
    },
  },
};
</script>

<style scoped>
.main-container {
  padding: 20px;
  background-color: #f5f5f5;
  font-family: 'Arial', sans-serif;
}

.header {
  margin-bottom: 20px;
}

.header-content {
  font-size: 24px;
  font-weight: bold;
  color: #fff;
  background-color: #409EFF;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
}

.grid-section,
.button-section,
.input-section,
.select-section,
.radio-section,
.checkbox-section,
.date-picker-section,
.table-section {
  margin-bottom: 40px;
}

.grid-content {
  border-radius: 8px;
  min-height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  color: white;
  margin-bottom: 10px;
  padding: 20px;
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

.button-group {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 20px;
}

.input-group {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-bottom: 20px;
}

.el-row {
  margin-bottom: 20px;
}

.el-col {
  border-radius: 8px;
  padding: 10px;
}

.el-input,
.el-autocomplete,
.el-select,
.el-radio-group,
.el-checkbox-group,
.el-date-picker,
.el-table {
  width: 100%;
}

.el-table {
  background-color: #fff;
  border-radius: 8px;
  overflow: hidden;
}

.el-button {
  margin-right: 10px;
}
</style>
