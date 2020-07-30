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
      default: '350px'
    },
    autoResize: {
      type: Boolean,
      default: true
    },
    chartData: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      chart: null
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        this.setOptions(val)
      }
    }
  },
  mounted() { // 实例渲染完成后
  // 将回调延迟到下次 DOM 更新循环之后执行
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() { // 在实例销毁之前
    if (!this.chart) {
      return
    }
    // 销毁图表
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    // 初始化图表
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      this.setOptions(this.chartData)
    },
    // 绘制图表
    setOptions({ date, uv, pv } = {}) {
      this.chart.setOption({
        // X轴
        xAxis: {
          data: date, // 所有类目名称列表
          boundaryGap: false, // 坐标轴两边留白策略
          axisTick: { // 坐标轴刻度相关设置。
            show: false // 是否显示坐标轴刻度。
          }
        },
        // 网格
        grid: {
          left: 10, // 离容器左侧的距离。
          right: 10, // 离容器右侧的距离。
          bottom: 20, // 离容器下侧的距离。
          top: 30, // 离容器上侧的距离。
          containLabel: true // 区域是否包含坐标轴的刻度标签。
        },
        // 提示框组件
        tooltip: {
          trigger: 'axis', // 触发类型 https://echarts.apache.org/zh/option.html#tooltip.trigger
          axisPointer: { // 坐标轴指示器配置项。 https://echarts.apache.org/zh/option.html#tooltip.axisPointer
            type: 'cross'
          },
          padding: [5, 10] // 提示框浮层内边距，单位px 设置上下的内边距为 5，左右的内边距为 10
        },
        //  y 轴
        yAxis: {
          axisTick: {
            show: false
          }
        },
        // 图例组件
        legend: {
          data: ['访问量(PV)', '独立用户(UV)']
        },
        // 系列列表 每个系列通过 type 决定自己的图表类型
        series: [
          {
            name: '访问量(PV)', // 系列名称，用于tooltip的显示
            itemStyle: { // 折线拐点标志的样式
              normal: {
                color: '#FF005A',
                lineStyle: { // 线条样式
                  color: '#FF005A',
                  width: 2
                }
              }
            },
            smooth: true, // 是否平滑曲线显示
            type: 'line', // 折线/面积图
            data: pv, // 系列中的数据内容数组。数组项通常为具体的数据项。
            animationDuration: 2800, // 初始动画的时长，支持回调函数，可以通过每个数据返回不同的时长实现更戏剧的初始动画效果：
            animationEasing: 'cubicInOut' // 初始动画的缓动效果。不同的缓动效果可以参考 https://echarts.apache.org/examples/zh/editor.html?c=line-easing
          },
          {
            name: '独立用户(UV)',
            smooth: true,
            type: 'line',
            itemStyle: {
              normal: {
                color: '#3888fa',
                lineStyle: {
                  color: '#3888fa',
                  width: 2
                },
                areaStyle: {
                  color: '#f3f8ff'
                }
              }
            },
            data: uv,
            animationDuration: 2800,
            animationEasing: 'quadraticOut'
          }
        ]
      })
    }
  }
}
</script>
