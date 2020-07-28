<template>
  <v-md-preview ref="editor" :text="text" />
</template>

<script>
import VMdPreview from '@kangc/v-md-editor/lib/preview'
import '@kangc/v-md-editor/lib/style/codemirror-editor.css'
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js' // 主题
import './prism' // 语法高亮
VMdPreview.use(vuepressTheme)

// 快捷复制代码
import createCopyCodePlugin from '@kangc/v-md-editor/lib/plugins/copy-code/index'
VMdPreview.use(createCopyCodePlugin())

// 高亮代码行
import createHighlightLinesPlugin from '@kangc/v-md-editor/lib/plugins/highlight-lines/index'
VMdPreview.use(createHighlightLinesPlugin())

// 显示代码行号
import createLineNumbertPlugin from '@kangc/v-md-editor/lib/plugins/line-number/index'
VMdPreview.use(createLineNumbertPlugin())

// 任务列表 createTodoListPlugin 时可以传入的参数。例如 createTodoListPlugin({ color: '' })
// 默认值: #3eaf7c
import createTodoListPlugin from '@kangc/v-md-editor/lib/plugins/todo-list/index'
VMdPreview.use(createTodoListPlugin())

// emoji表情
import createEmojiPlugin from '@kangc/v-md-editor/lib/plugins/emoji/index'
VMdPreview.use(createEmojiPlugin())

// 提示信息 vuepress 主题已内置此插件
// import createTipPlugin from '@kangc/v-md-editor/lib/plugins/tip/index'
// VMdPreview.use(createTipPlugin())
export default {
  name: 'MyVMdPreview',
  components: {
    VMdPreview
  },
  props: {
    value: { // 初始值
      type: String,
      default: ''
    }
  },
  data() {
    return {
      text: this.value,
      titles: []
    }
  },
  watch: {
    value: function() {
      this.text = this.value
    }
  }
}
</script>

<style>

</style>
