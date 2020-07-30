<template>
  <div class="dashboard-editor-container">
    <github-corner class="github-corner" />

    <panel-group :panel-data="panelData" @btnClick="btnClick" />

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-row>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart />
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="8" />
  </div>
</template>

<script>
import GithubCorner from '@/components/GithubCorner'
import PanelGroup from './components/PanelGroup'
import LineChart from './components/LineChart'
import PieChart from './components/PieChart'

import { init } from '@/api/index'

export default {
  name: 'DashboardAdmin',
  components: {
    GithubCorner,
    PanelGroup,
    LineChart,
    PieChart
  },
  data() {
    return {
      lineChartData: {
        date: ['07-24', '07-25', '07-26', '07-27', '07-28', '07-29', '07-30'],
        uv: [100, 120, 161, 134, 105, 160, 165],
        pv: [120, 82, 91, 154, 162, 140, 145]
      },
      panelData: {}
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      init().then(response => {
        this.panelData = response.data
      })
    },
    btnClick(url) {
      this.$router.push({ path: url })
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
