<template>
  <div class="system">
    <div class="head">
      <el-row type="flex" justify="start" :gutter="5">
        <el-col :span="5">
          <el-input
            v-model="keyword"
            placeholder="请输入文件名"
            clearable
            type="text"
          />
        </el-col>
        <el-col :span="5">
          <el-select v-model="bucket" placeholder="请选择空间">
            <el-option
              v-for="(item,idnex) in buckets"
              :key="idnex"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" icon="el-icon-search" @click="getList()">查找</el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" @click="dialogVisible = true">上传图片</el-button>
        </el-col>
        <el-col :span="3">
          <el-button v-if="isButton" type="danger" style="margin-bottom:30px;" icon="el-icon-delete" @click="deleteList()">删除所选数据</el-button>
        </el-col>
      </el-row>
    </div>
    <el-table
      v-loading="loading"
      element-loading-text="拼命加载中"
      :data="tableData"
      row-key="key"
      style="width: 100%"
      @selection-change="monitorCheckbox"
    >
      <el-table-column
        align="center"
        type="selection"
      />
      <el-table-column
        align="center"
        label="序号"
        sortable
      >
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="文件"
      >
        <template slot-scope="scope">
          <el-image
            v-if="scope.row.mimeType.indexOf('image') >= 0"
            :src="baseUrl + '/' + scope.row.key"
            :preview-src-list="[baseUrl + '/' + scope.row.key]"
            lazy
          >
            <div slot="placeholder" class="image-slot">
              <i class="el-icon-loading" />
            </div>
          </el-image>
          <el-image
            v-else
            src="https://qiniu.staticfile.org/static/no-prev-eefefb160de84ae92123de3ece7be82f.png"
            :preview-src-list="['https://qiniu.staticfile.org/static/no-prev-eefefb160de84ae92123de3ece7be82f.png']"
            lazy
          >
            <div slot="placeholder" class="image-slot">
              <i class="el-icon-loading" />
            </div>
          </el-image>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="文件名"
        width="100"
        show-overflow-tooltip
      >
        <template slot-scope="scope">
          <span
            v-clipboard:copy="scope.row.key"
            v-clipboard:success="clipboardSuccess"
          >
            {{ scope.row.key }}
          </span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="fsize"
        label="大小（字节）"
        width="150"
        show-overflow-tooltip
        sortable
      />
      <el-table-column
        align="center"
        prop="mimeType"
        label="文件类型"
        width="100"
        show-overflow-tooltip
      />
      <el-table-column
        align="center"
        prop="type"
        label="存储类型"
        width="100"
        show-overflow-tooltip
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type === 0">普通存储</el-tag>
          <el-tag v-else type="success">低频存储</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="status"
        label="状态"
        width="100"
        show-overflow-tooltip
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" type="warning">启用</el-tag>
          <el-tag v-else type="danger">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="putTime"
        sortable
        :formatter="dateFormat"
        show-overflow-tooltip
        label="上传时间"
      />
      <el-table-column label="操作" align="center" fixed="right" width="300">
        <template slot-scope="scope">
          <el-dropdown split-button type="primary">
            复制
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item
                v-clipboard:copy=" '![](' + baseUrl+ '/' + scope.row.key + ')'"
                v-clipboard:success="clipboardSuccess"
              >
                Markdown
              </el-dropdown-item>
              <el-dropdown-item
                v-clipboard:copy=" '<img src=\'' + baseUrl+ '/' + scope.row.key + '\'/>'"
                v-clipboard:success="clipboardSuccess"
              >
                HTML
              </el-dropdown-item>
              <el-dropdown-item
                v-clipboard:copy=" baseUrl+ '/' + scope.row.key"
                v-clipboard:success="clipboardSuccess"
              >
                URL
              </el-dropdown-item>
              <el-dropdown-item
                v-clipboard:copy=" '[IMG]'+ baseUrl+ '/' + scope.row.key + '[/IMG]'"
                v-clipboard:success="clipboardSuccess"
              >
                UBB
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-button type="danger" size="small" @click="deleteFile(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      v-loading="loadingUpload"
      element-loading-text="文件上传中"
      title="上传文件"
      :visible.sync="dialogVisible"
      @paste.native="obtainClipbrd"
    >
      <!-- style="text-align: center" -->
      <el-row type="flex" justify="center">
        <el-col :span="12">
          <el-upload
            class="upload-demo"
            drag
            action=""
            multiple
            :http-request="upload"
          >
            <i class="el-icon-upload" />
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          </el-upload>
        </el-col>
        <el-col :span="12" style="text-align: center">
          <el-button type="primary" round @click="obtainClipbrd">剪切板图片上传</el-button>
          <el-button-group v-if="fileUrl !== ''" style="margin-top: 100px">
            <el-button
              v-clipboard:copy=" '![](' + fileUrl + ')'"
              v-clipboard:success="clipboardSuccess"
              type="primary"
            >
              Markdown
            </el-button>
            <el-button
              v-clipboard:copy="'<img src=\'' + fileUrl + '\'/>'"
              v-clipboard:success="clipboardSuccess"
              type="primary"
            >
              HTML
            </el-button>
            <el-button
              v-clipboard:copy="fileUrl"
              v-clipboard:success="clipboardSuccess"
              type="primary"
            >
              URL
            </el-button>
            <el-button
              v-clipboard:copy="'[IMG]'+ fileUrl + '[/IMG]'"
              v-clipboard:success="clipboardSuccess"
              type="primary"
            >
              UBB
            </el-button>
          </el-button-group>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import { getBuckets, getList, getBaseURL, deleteFile, uploade, deleteList } from '@/api/qiniu'
import { formatDate } from '@/utils/webUtils'
// 复制指令
import clipboard from '@/directive/clipboard/index.js'
export default {
  directives: {
    clipboard
  },
  data() {
    return {
      keyword: '',
      buckets: [],
      bucket: '',
      baseUrl: '',
      tableData: [],
      loading: false,
      loadingUpload: false,
      dialogVisible: false,
      isButton: false,
      urls: [],
      keys: [],
      fileUrl: ''
    }
  },
  created() {
    this.getBuckets()
  },
  methods: {
    deleteList() {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        var params = new URLSearchParams()
        params.append('bucket', this.bucket)
        params.append('keys', this.keys)
        params.append('urls', this.urls)
        deleteList(params).then(response => {
          this.$message({
            type: 'success',
            message: response.message + '\n今日剩余刷新url限额' + response.data.urlSurplusDay
          })
          this.getList()
          this.keys = []
          this.urls = []
          this.keyword = ''
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 监控多选 如果有选择显示批量删除按钮
    monitorCheckbox(val) {
      this.urls = []
      this.keys = []
      if (val.length > 0) {
        this.isButton = true
      } else {
        this.isButton = false
      }
      for (const item of val) {
        this.keys.push(item.key)
        this.urls.push(this.baseUrl + '/' + item.key)
      }
    },
    upload(param) {
      this.loadingUpload = true
      const formData = new FormData()
      formData.append('bucket', this.bucket)
      formData.append('file', param.file)
      uploade(formData).then(response => {
        this.fileUrl = this.baseUrl + '/' + response.data.fileKey
        this.$message({
          message: '文件上传成功',
          type: 'success'
        })
        this.getList()
        this.loadingUpload = false
      }).catch(response => {
        this.$message.error(response.data)
        this.loadingUpload = false
      })
    },
    obtainClipbrd(event) {
      const items = event.clipboardData.items
      console.log(items)
      let file = null

      if (!items || items.length === 0) {
        this.$message.error('当前浏览器不支持')
        return
      }
      // 搜索剪切板items
      for (let i = 0; i < items.length; i++) {
        if (items[i].type.indexOf('image') !== -1) {
          file = items[i].getAsFile()
          break
        }
      }
      console.log(file)
      if (!file) {
        this.$message.error('粘贴内容非图片')
        return
      }
      // // 此时file就是我们的剪切板中的图片对象
      // // 如果需要预览，可以执行下面代码
      // const reader = new FileReader()
      // reader.onload = event => {
      //   preview.innerHTML = `<img src="${event.target.result}">`
      // };
      // reader.readAsDataURL(file)
      // this.file = file
    },
    deleteFile(row) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        var params = new URLSearchParams()
        params.append('bucket', this.bucket)
        params.append('fileName', row.key)
        params.append('url', this.baseUrl + '/' + row.key)
        deleteFile(params).then(response => {
          this.$message({
            type: 'success',
            message: response.message + '\n今日剩余刷新url限额' + response.data.urlSurplusDay
          })
          this.getList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    clipboardSuccess() {
      this.$message({
        showClose: true,
        message: '复制成功',
        type: 'success'
      })
    },
    // 格式化日期
    dateFormat: function(row, column) {
      var putTime = row[column.property]
      putTime = (putTime * 100) / 1000000
      var date = new Date(putTime)
      if (date === undefined) {
        return ''
      }
      return formatDate(date, 'YYYY-MM-DD hh:mm:ss')
    },
    getList() {
      this.loading = true
      const params = {}
      params.keyword = this.keyword
      params.bucket = this.bucket
      getList(params).then(response => {
        this.tableData = response.data
        this.getBaseURL()
      })
    },
    getBuckets() {
      getBuckets().then(response => {
        this.buckets = response.data
        this.bucket = this.buckets[1]
        this.getList()
      })
    },
    getBaseURL() {
      const params = {}
      params.bucket = this.bucket
      getBaseURL(params).then(response => {
        this.baseUrl = response.data
      })
      this.loading = false
    }
  }
}
</script>

<style>
  .system{
      margin: 10px 15px 0;
  }
</style>
