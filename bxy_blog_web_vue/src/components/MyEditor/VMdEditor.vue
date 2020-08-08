<template>
  <v-md-editor
    v-model="text"
    v-loading="loading"
    :height="height"
    :mode="mode"
    :placeholder="placeholder"
    :include-level="includeLevel"
    :left-toolbar="leftToolbar"
    :disabled-menus="disabledMenus"
    @change="change"
    @upload-image="handleUploadImage"
    @save="save"
  />
</template>

<script>
import { uploaderImage } from '@/api/qiniu'

// https://code-farmer-i.github.io/vue-markdown-editor/zh/
import VMdEditor from '@kangc/v-md-editor/lib/codemirror-editor'
import '@kangc/v-md-editor/lib/style/codemirror-editor.css'
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js' // 主题
import './prism' // 语法高亮 有的语法包可能需要别的语法依赖，比如PHP：https://github.com/PrismJS/prism/issues/1395
VMdEditor.use(vuepressTheme)

// 快捷复制代码
import createCopyCodePlugin from '@kangc/v-md-editor/lib/plugins/copy-code/index'
VMdEditor.use(createCopyCodePlugin())

// 高亮代码行
import createHighlightLinesPlugin from '@kangc/v-md-editor/lib/plugins/highlight-lines/index'
VMdEditor.use(createHighlightLinesPlugin())

// 显示代码行号
import createLineNumbertPlugin from '@kangc/v-md-editor/lib/plugins/line-number/index'
VMdEditor.use(createLineNumbertPlugin())

// 任务列表 createTodoListPlugin 时可以传入的参数。例如 createTodoListPlugin({ color: '' })
// 默认值: #3eaf7c
import createTodoListPlugin from '@kangc/v-md-editor/lib/plugins/todo-list/index'
VMdEditor.use(createTodoListPlugin())

// emoji表情
import createEmojiPlugin from '@kangc/v-md-editor/lib/plugins/emoji/index'
VMdEditor.use(createEmojiPlugin())

// 提示信息 vuepress 主题已内置此插件
// import createTipPlugin from '@kangc/v-md-editor/lib/plugins/tip/index'
// VMdEditor.use(createTipPlugin())
export default {
  name: 'MyVMdEditor',
  components: {
    VMdEditor
  },
  props: {
    value: { // 初始值
      type: String,
      default: ''
    },
    placeholder: { // 占位值
      type: String,
      default: '开始编辑...'
    },
    height: { // 高度
      type: String,
      default: '400px'
    },
    disabledMenus: { // 禁用的菜单。
      type: Array,
      default: function() {
        return []
      }
    },
    mode: { // 模式。 edit(纯编辑模式) editable(编辑与预览模式) preview(纯预览模式)。
      type: String,
      default: 'editable'
    },
    includeLevel: { // 目录导航生成时包含的标题
      type: Array,
      default: function() {
        return [1, 2, 3, 4, 5, 6]
      }
    },
    leftToolbar: { // 左侧工具栏
      type: String,
      default: 'undo redo clear | h bold italic strikethrough quote emoji | ul ol table hr tip todo-list | link image code | save'
    },
    rightToolbar: { // 右侧工具栏
      type: String,
      default: 'preview toc sync-scroll fullscreen'
    }
  },
  data() {
    return {
      text: this.value,
      loading: false
    }
  },
  watch: {
    value: function() {
      this.text = this.value
    }
  },
  // computed: {
  //   text: {
  //     get() {
  //       return this.value
  //     },
  //     set(v) {
  //       this.value = v
  //     }
  //   }
  // },
  methods: {
    change(markdown, html) {
      this.$emit('contentChange', markdown, html)
    },
    handleUploadImage(event, insertImage, files) {
      this.loading = true
      const formData = new FormData()
      // 得到的文件是数组，拿到第一个就是文件
      formData.append('file', files[0])
      uploaderImage(formData).then(response => {
        this.$message({
          message: '图片上传成功',
          type: 'success'
        })
        // 写入图片
        insertImage({
          url: response.data.fileUrl,
          desc: response.data.fileName
        })
        this.loading = false
      }).catch(response => {
        this.$message.error(response.data)
        this.loading = false
      })
    },
    save(markdown, html) {
      this.$emit('save', markdown, html)
    }
  }
}
</script>

<style>

</style>
