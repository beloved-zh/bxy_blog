<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '300px'
    },
    name: {
      type: String,
      default: '所占条目数'
    },
    title: {
      type: Array,
      default: function() {
        return []
      }
    },
    value: {
      type: Array,
      default: function() {
        return []
      }
    }
  },
  data() {
    return {
      chart: null
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')

      this.chart.on('click', param => {
        this.$emit('clickPie', param.dataIndex)
      })

      this.chart.setOption({
        tooltip: { // 提示框组件。
          trigger: 'item', // 触发类型。https://echarts.apache.org/zh/option.html#tooltip.trigger
          formatter: '{a} <br/>{b} : {c} ({d}%)' // 提示内容模板 https://echarts.apache.org/zh/option.html#tooltip.formatter
        },
        legend: { // 图例组件
          left: 'center',
          bottom: '10',
          data: this.title
        },
        series: [ // https://echarts.apache.org/zh/option.html#series-pie
          {
            name: this.name, // 系列名称，用于tooltip的显示
            type: 'pie', // 饼图类型
            roseType: 'radius', // 是否展示成南丁格尔图，通过半径区分数据大小。 https://echarts.apache.org/zh/option.html#series-pie.roseType
            radius: [15, 95], // 饼图的半径。// https://echarts.apache.org/zh/option.html#series-pie.radius
            center: ['50%', '38%'], // 饼图的中心（圆心）坐标，数组的第一项是横坐标，第二项是纵坐标。 https://echarts.apache.org/zh/option.html#series-pie.center
            data: this.value,
            animationEasing: 'cubicInOut',
            animationDuration: 2600
          }
        ]
      })
    }
  }
}
</script>
