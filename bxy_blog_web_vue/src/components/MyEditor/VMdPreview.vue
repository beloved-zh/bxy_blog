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
    },
    lineIndex: { // 初始值
      type: String,
      default: '0'
    }
  },
  data() {
    return {
      text: this.value,
      menus: []
    }
  },
  watch: {
    value: function() {
      this.text = this.value
    },
    lineIndex: function() {
      if(this.lineIndex === '0'){
        return
      }
      this.handleAnchorClick()
    }
  },
  mounted(){
    // 获取目录
    this.createMenus()
    // 将目录传给父组件
    this.getMenus()
  },
  methods: {
    handleAnchorClick() {
      const lineIndex  = this.lineIndex;

      const { editor } = this.$refs;

      const heading = editor.$el.querySelector(
        `.v-md-editor-preview [data-v-md-line="${lineIndex}"]`
      );

      if (heading) {
        editor.scrollToTarget({
          target: heading,
          scrollContainer: window,
          top: 60,
        });
      }
    },
    getMenus(){
      this.$emit('getMenus', this.menus)
    },
    // 生成目录
    createMenus(){
      const anchors = this.$refs.editor.$el.querySelectorAll(
        '.v-md-editor-preview h1,h2,h3,h4,h5,h6'
      );
      const titles = Array.from(anchors).filter((title) => !!title.innerText.trim());

      if (!titles.length) {
        this.titles = [];
        return;
      }

      const hTags = Array.from(new Set(titles.map((title) => title.tagName))).sort();
      this.menus = titles.map((el) => ({
        title: el.innerText,
        lineIndex: el.getAttribute('data-v-md-line'),
        indent: hTags.indexOf(el.tagName),
      }));
    }
  }
}
</script>

<style>
  .v-md-editor-preview{
    padding: 0px;
    font-family: miranafont, "Hiragino Sans GB", STXihei, "Microsoft YaHei", SimSun, sans-serif;
  }
</style>
