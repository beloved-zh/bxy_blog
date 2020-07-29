<template>
  <div class="blog">
    <el-row type="flex" :gutter="10" justify="start">
      <el-col :span="3">
        <el-input
          v-model="keyword"
          placeholder="请输入博客名"
          clearable
          type="text"
        />
      </el-col>
      <el-col :span="3">
        <el-select v-model="sortId" clearable placeholder="请选择分类名">
          <el-option
            v-for="item in sortList"
            :key="item.id"
            :label="item.sortTitle"
            :value="item.id"
          />
        </el-select>
      </el-col>
      <el-col :span="3">
        <el-select v-model="tagId" clearable placeholder="请选择标签名">
          <el-option
            v-for="item in tagList"
            :key="item.id"
            :label="item.tagTitle"
            :value="item.id"
          />
        </el-select>
      </el-col>
      <el-col :span="3">
        <el-select v-model="level" clearable placeholder="推荐等级">
          <el-option label="正常" :value="0" />
          <el-option label="一级推荐" :value="1" />
          <el-option label="二级推荐" :value="2" />
          <el-option label="三级推荐" :value="3" />
        </el-select>
      </el-col>
      <el-col :span="3">
        <el-select v-model="status" clearable placeholder="是否发布">
          <el-option label="发布" :value="true" />
          <el-option label="下架" :value="false" />
        </el-select>
      </el-col>
      <el-col :span="3">
        <el-select v-model="original" clearable placeholder="是否原创">
          <el-option label="原创" :value="true" />
          <el-option label="转载" :value="false" />
        </el-select>
      </el-col>
      <el-col :span="2">
        <el-button type="primary" style="margin-bottom:30px;" icon="el-icon-search" @click="getBlogList()">查找</el-button>
      </el-col>
      <el-col :span="2">
        <el-button type="primary" style="margin-bottom:30px;" @click="addBlog()">添加博客</el-button>
      </el-col>
    </el-row>
    <el-table
      :data="tableData"
      row-key="id"
      style="width: 100%"
    >
      <el-table-column
        align="center"
        label="序号"
        type="index"
        width="80"
      />
      <el-table-column
        align="center"
        prop="title"
        label="文章标题"
        width="150"
      />
      <el-table-column
        align="center"
        prop="image"
        label="标题图"
        width="150"
      >
        <template slot-scope="scope">
          <el-image :src="scope.row.image" :preview-src-list="[scope.row.image]" fit="fill" style="width: 130px; height: 70px" />
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="user.username"
        label="作者"
        width="100"
      />
      <el-table-column
        align="center"
        prop="isOriginal"
        label="是否原创"
        width="100"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isOriginal" type="success">原创</el-tag>
          <el-tag v-else type="danger">转载</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="sort.sortTitle"
        label="分类"
        width="100"
      />
      <el-table-column
        align="center"
        label="标签"
        width="150"
      >
        <template slot-scope="scope">
          <el-tag v-for="(tag,index) in scope.row.tags" :key="index" type="info">{{ tag.tagTitle }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="blogLevel"
        label="推荐等级"
        width="150"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.blogLevel === 1">一级推荐</el-tag>
          <el-tag v-else-if="scope.row.blogLevel === 2" type="success">二级推荐</el-tag>
          <el-tag v-else-if="scope.row.blogLevel === 3" type="warning">三级推荐</el-tag>
          <el-tag v-else type="info">正常</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="clickCount"
        label="点击数"
        width="100"
      />
      <el-table-column
        align="center"
        prop="startComment"
        label="评论状态"
        width="100"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.startComment" type="success">开启</el-tag>
          <el-tag v-else type="danger">关闭</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="blogStatus"
        label="文章状态"
        width="100"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.blogStatus" type="success">发布</el-tag>
          <el-tag v-else type="danger">下架</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="createTime"
        sortable
        :formatter="dateFormat"
        show-overflow-tooltip
        label="创建时间"
        width="150"
      />
      <el-table-column label="操作" align="center" fixed="right" width="220">
        <template slot-scope="scope">
          <el-button type="success" size="small" @click="preview(scope.row)">预览</el-button>
          <el-button type="primary" size="small" @click="updateBlog(scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="deleteBlog(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      background
      layout="total, prev, pager, next, jumper"
      :page-size="pageSize"
      :page-count="pageCount"
      :current-page="currentPage"
      :total="total"
      style="margin-top:10px"
      @current-change="handleCurrentChange"
    />
    <!-- 添加或修改 -->
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      :before-close="handleClose"
      destroy-on-close
      fullscreen
    >
      <el-form :ref="form" hide-required-asterisk :model="form" :rules="rules">
        <el-form-item label="标题图" label-width="120px" prop="image">
          <el-upload
            v-loading="loading"
            element-loading-text="图片上传中"
            class="avatar-uploader"
            action=""
            :show-file-list="false"
            :http-request="uploadImage"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="form.image" :src="form.image" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
        </el-form-item>
        <el-form-item label="标题" label-width="120px" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="简介" label-width="120px" prop="summary">
          <el-input v-model="form.summary" />
        </el-form-item>
        <el-row>
          <el-col :span="4">
            <el-form-item label="分类" label-width="120px" prop="sortId">
              <el-select
                v-model="form.sortId"
                size="small"
                placeholder="请选择"
                style="width:150px"
              >
                <el-option
                  v-for="item in sortList"
                  :key="item.id"
                  :label="item.sortTitle"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="标签" label-width="120px" prop="tags">
              <el-select
                v-model="form.tags"
                size="small"
                multiple
                filterable
                allow-create
                default-first-option
                placeholder="请选择"
                style="width:150px"
              >
                <el-option
                  v-for="item in tagList"
                  :key="item.id"
                  :label="item.tagTitle"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="推荐等级" label-width="120px" prop="level">
              <el-select v-model="form.level" clearable placeholder="推荐等级">
                <el-option label="正常" :value="0" />
                <el-option label="一级推荐" :value="1" />
                <el-option label="二级推荐" :value="2" />
                <el-option label="三级推荐" :value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="是否发布" label-width="120px" prop="status">
              <el-select v-model="form.status" clearable placeholder="是否发布">
                <el-option label="发布" :value="true" />
                <el-option label="下架" :value="false" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="是否原创" label-width="120px" prop="original">
              <el-select v-model="form.original" clearable placeholder="是否原创">
                <el-option label="原创" :value="true" />
                <el-option label="转载" :value="false" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="是否开启评论" label-width="120px" prop="startComment">
              <el-select v-model="form.startComment" clearable placeholder="请选择">
                <el-option label="开启" :value="true" />
                <el-option label="关闭" :value="false" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 转载文章显示 -->
        <el-form-item v-if="!form.original" label="转载地址" label-width="120px" prop="articlesPart">
          <el-input v-model="form.articlesPart" />
        </el-form-item>
        <el-form-item label="内容" label-width="120px" prop="contentMd">
          <VMdEditor :value="form.contentMd" @contentChange="contentChange" @save="save" />
        </el-form-item>
        <el-form-item style="text-align: right">
          <el-button @click="handleClose()">取 消</el-button>
          <el-button type="primary" @click="submitForm(form)">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 预览 -->
    <el-dialog
      :title="form.title"
      :visible.sync="dialogPreview"
      :before-close="handleClose"
      destroy-on-close
      fullscreen
    >
      <VMdPreview :value="form.contentMd" />
    </el-dialog>
  </div>
</template>

<script>
import { findAllSort } from '@/api/sort'
import { findAllTag, addTag } from '@/api/tag'
import { uploadImage } from '@/api/qiniu'
import { getBlog, addBlog, deleteBlog, updateBlog } from '@/api/blog'
import VMdEditor from '@/components/MyEditor/VMdEditor'
import VMdPreview from '@/components/MyEditor/VMdPreview'
import { formatDate } from '@/utils/webUtils'
export default {
  components: {
    VMdEditor,
    VMdPreview
  },
  data() {
    return {
      keyword: '',
      sortId: '',
      tagId: '',
      level: '',
      status: '',
      original: '',
      sortList: [],
      tagList: [],
      currentPage: 1, // 当前页
      pageSize: 5, // 每页大小
      total: 0, // 总条目数
      pageCount: 0, // 总页数
      tableData: [],
      title: '',
      dialogVisible: false,
      loading: false,
      form: {
        title: '',
        summary: '',
        image: '',
        contentMd: '',
        contentHtml: '',
        sortId: '',
        tags: '',
        level: '',
        status: true,
        original: true,
        startComment: true,
        articlesPart: '',
        userId: this.$store.state.user.id
      },
      rules: {
        image: [
          { required: true, message: '请选择', trigger: 'blur' },
          { required: true, message: '请选择', trigger: 'change' }
        ],
        title: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        summary: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        articlesPart: [
          { required: true, message: '请输入', trigger: 'blur' },
          { pattern: /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/, message: '请输入正确的地址' }
        ],
        sortId: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        tags: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        level: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        status: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        original: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        startComment: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        contentMd: [
          { required: true, message: '请输入', trigger: 'blur' }
        ]
      },
      dialogPreview: false
    }
  },
  computed: {
    tagIdS() {
      return this.tagList.map(x => { return x.id })
    },
    sortMax() {
      const tagSortS = this.tagList.map(x => { return x.sort })
      return Math.max.apply(null, tagSortS)
    }
  },
  watch: {
    'form.tags'() {
      for (const item of this.form.tags) {
        const index = this.tagIdS.indexOf(item)
        if (index < 0) {
          this.addTag(item)
        }
      }
    }
  },
  created() {
    this.findAllSort()
    this.findAllTag()
    this.getBlogList()
  },
  methods: {
    addTag(item) {
      var params = new URLSearchParams()
      params.append('tagTitle', item)
      params.append('sort', this.sortMax + 1)
      addTag(params).then(response => {
        this.findAllTag()
        const { id } = response.data
        this.$message({
          message: '标签添加成功',
          type: 'success'
        })
        this.form.tags.splice(this.form.tags.indexOf(item), 1, id)
      })
    },
    preview(row) {
      this.form = {
        id: row.id,
        title: row.title,
        summary: row.summary,
        image: row.image,
        contentMd: row.contentMd,
        contentHtml: row.contentHtml,
        sortId: row.sortId,
        tags: [],
        level: row.blogLevel,
        status: row.blogStatus,
        original: row.isOriginal,
        startComment: row.startComment,
        articlesPart: row.articlesPart,
        userId: row.userId
      }
      this.dialogPreview = true
    },
    deleteBlog(row) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        var params = new URLSearchParams()
        params.append('id', row.id)
        deleteBlog(params).then(response => {
          this.$message({
            type: 'success',
            message: response.data
          })
          this.getBlogList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    updateBlog(row) {
      this.title = '修改' + row.title
      this.form = {
        id: row.id,
        title: row.title,
        summary: row.summary,
        image: row.image,
        contentMd: row.contentMd,
        contentHtml: row.contentHtml,
        sortId: row.sortId,
        tags: [],
        level: row.blogLevel,
        status: row.blogStatus,
        original: row.isOriginal,
        startComment: row.startComment,
        articlesPart: row.articlesPart,
        userId: row.userId
      }
      for (const tag of row.tags) {
        this.form.tags.push(tag.id)
      }
      this.dialogVisible = true
    },
    // 处理分页
    handleCurrentChange(val) {
      this.currentPage = val
      this.getBlogList()
    },
    // 格式化日期
    dateFormat: function(row, column) {
      var date = new Date(row[column.property])
      if (date === undefined) {
        return ''
      }
      return formatDate(date, 'YYYY-MM-DD hh:mm:ss')
    },
    getBlogList() {
      const params = {}
      params.keyword = this.keyword
      params.sortId = this.sortId
      params.tagId = this.tagId
      params.level = this.level
      params.status = this.status
      params.original = this.original
      params.currentPage = this.currentPage
      params.pageSize = this.pageSize
      getBlog(params).then(response => {
        const { data } = response
        this.currentPage = data.current
        this.pageSize = data.size
        this.total = data.total
        this.pageCount = data.pages
        this.tableData = data.records
      })
    },
    submitForm(form) {
      this.$refs[form].validate((valid) => {
        // 校验规则
        if (valid) {
          var params = new URLSearchParams(this.form)
          if (this.title === '添加博客') {
            addBlog(params).then(response => {
              const { data } = response
              this.initForm()
              this.dialogVisible = false
              this.$message({
                message: data,
                type: 'success'
              })
              this.getBlogList()
            })
          } else {
            updateBlog(params).then(response => {
              const { data } = response
              this.initForm()
              this.dialogVisible = false
              this.$message({
                message: data,
                type: 'success'
              })
              this.getBlogList()
            })
          }
        } else {
          return false
        }
      })
    },
    initForm() {
      this.form = {
        title: '',
        summary: '',
        image: '',
        contentMd: '',
        contentHtml: '',
        sortId: '',
        tags: '',
        level: '',
        status: true,
        original: true,
        startComment: true,
        articlesPart: '',
        userId: this.$store.state.user.id
      }
    },
    save(markdown, html) {
      alert(html)
    },
    contentChange(markdown, html) {
      this.form.contentMd = markdown
      this.form.contentHtml = html
    },
    // 自定义文件上传
    uploadImage(param) {
      this.loading = true
      const formData = new FormData()
      formData.append('file', param.file)
      uploadImage(formData).then(response => {
        this.form.image = response.data.fileUrl
        this.$message({
          message: '图片上传成功',
          type: 'success'
        })
        this.loading = false
      }).catch(response => {
        this.$message.error(response.data)
        this.loading = false
      })
    },
    // 文件上传前钩子
    beforeAvatarUpload(file) {
      const types = ['image/jpeg', 'image/gif', 'image/bmp', 'image/png']
      const isImage = types.includes(file.type)
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isImage) {
        this.$message.error('上传图片只能是 JPG、GIF、BMP、PNG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!')
      }
      return isImage && isLt2M
      // return false
    },
    addBlog() {
      this.initForm()
      this.title = '添加博客'
      this.dialogVisible = true
    },
    // 关闭模态框
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.initForm()
          this.dialogVisible = false
          this.dialogPreview = false
          done()
        })
        .catch(_ => {})
    },
    findAllSort() {
      findAllSort().then(response => {
        this.sortList = response.data
      })
    },
    findAllTag() {
      findAllTag().then(response => {
        this.tagList = response.data
      })
    }
  }
}
</script>

<style>
  .blog{
    margin: 10px 15px 0;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: rgb(140, 147, 157);
    width: 195px;
    height: 105px;
    line-height: 105px;
    text-align: center;
  }
  .avatar {
    width: 195px;
    height: 105px;
    display: block;
  }
</style>
