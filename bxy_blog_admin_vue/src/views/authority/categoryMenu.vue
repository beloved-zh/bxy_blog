<template>
  <div class="authority-menu">
    <div class="head">
      <el-row type="flex" :gutter="10" justify="start">
        <el-col :span="5" :xs="12">
          <el-input
            v-model="keyword"
            placeholder="请输入路由名称"
            clearable
            type="text"
          />
        </el-col>
        <el-col :span="3" :xs="6" class="switch">
          <el-select v-model="menuType" clearable placeholder="请选择类型">
            <el-option label="后台组件" :value="false" />
            <el-option label="前台组件" :value="true" />
          </el-select>
        </el-col>
        <el-col :span="2" :xs="6">
          <el-button type="primary" style="margin-bottom:30px;" icon="el-icon-search" @click="getMenusList()">查找</el-button>
        </el-col>
        <el-col :span="2" :xs="6">
          <el-button type="primary" style="margin-bottom:30px;" @click="openDialog('添加菜单')">添加菜单</el-button>
        </el-col>
      </el-row>
    </div>
    <el-table
      :data="tableData"
      row-key="id"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column
        align="center"
        label="序号"
        type="index"
      />
      <!-- <el-table-column label="序号" width="60" align="center">
        <template slot-scope="scope">
          <span>{{ scope.$index + 1 }}</span>
        </template>
      </el-table-column> -->
      <el-table-column
        align="center"
        prop="menuTitle"
        label="菜单名称"
        width="150"
        show-overflow-tooltip
      />
      <el-table-column
        align="center"
        prop="menuIcon"
        label="图标"
        width="60"
      >
        <template slot-scope="scope">
          <i :class="scope.row.menuIcon" />
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="menuUrl"
        label="路由"
        width="100"
        show-overflow-tooltip
      />
      <el-table-column
        align="center"
        prop="component"
        label="路由组件"
        show-overflow-tooltip
        width="150"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.component === 'Layout'">{{ scope.row.component }}</span>
          <span v-else>@views/{{ scope.row.component }}.vue</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="redirect"
        label="转发地址"
        width="100"
        show-overflow-tooltip
      />
      <el-table-column
        align="center"
        prop="isShow"
        label="是否在侧边栏"
        width="120"
      >
        <template slot-scope="scope">
          <el-switch :value="scope.row.isShow ? false : true" disabled />
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="角色"
        width="100"
        show-overflow-tooltip
      >
        <template slot-scope="scope">
          <span v-for="(role,index) in scope.row.roles" :key="index">
            {{ role.roleName }}
          </span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="createTime"
        sortable
        :formatter="dateFormat"
        show-overflow-tooltip
        width="150"
        label="创建时间"
      />
      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <router-link :to="scope.row.menuUrl">
            <el-button type="success" size="small">跳转</el-button>
          </router-link>
          <el-button type="primary" size="small" @click="updateMenu(scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="deleteMenu(scope.row)">删除</el-button>
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

    <!-- 添加或修改模态框 -->
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      :before-close="handleClose"
    >
      <el-form :ref="form" :model="form" label-width="150px" :rules="rules">
        <el-form-item label="菜单名称" prop="menuTitle">
          <el-input v-model="form.menuTitle" clearable />
        </el-form-item>
        <el-form-item label="图标" prop="menuIcon">
          <el-input v-model="form.menuIcon" clearable :suffix-icon="form.menuIcon" />
        </el-form-item>
        <el-form-item label="地址" prop="menuUrl">
          <el-input v-model="form.menuUrl" clearable />
        </el-form-item>
        <el-form-item label="路由组件" prop="component">
          <el-input v-model="form.component" clearable placeholder="一级菜单Layout 子菜单不需要@views和.vue" />
        </el-form-item>
        <el-form-item label="父菜单" prop="menuId">
          <el-select v-model="form.menuId" placeholder="请选择父菜单" filterable clearable>
            <el-option
              v-for="(item,index) in Menu"
              :key="index"
              :label="item.menuTitle"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="角色" prop="roles">
          <el-select v-model="form.roles" placeholder="请选择角色" multiple clearable>
            <el-tooltip
              v-for="(item,index) in roles"
              :key="index"
              effect="dark"
              :content="item.summary"
              placement="right"
            >
              <el-option
                :label="item.roleName"
                :value="item.id"
              />
            </el-tooltip>
          </el-select>
        </el-form-item>
        <el-form-item label="转发地址" prop="redirect">
          <el-input v-model="form.redirect" clearable />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <!-- <el-input v-model="form.sort" clearable /> -->
          <el-slider v-model="form.sort" :min="0" :max="sort" show-stops show-tooltip />
        </el-form-item>
        <el-form-item label="是否显示在侧边栏" prop="isShow">
          <el-switch v-model="form.isShow" />
        </el-form-item>
        <el-form-item label="是否固定在tagview" prop="affix">
          <el-switch v-model="form.affix" />
        </el-form-item>
        <el-form-item label="菜单分类" prop="menuType">
          <el-tooltip
            effect="dark"
            :content="form.menuType ? '后台' : '前台'"
            placement="right"
          >
            <el-switch v-model="form.menuType" />
          </el-tooltip>
        </el-form-item>
        <el-form-item>
          <el-button @click="handleClose()">取 消</el-button>
          <el-button type="primary" @click="submitForm(form)">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { getMenus, addMenu, deleteMenu, updateMenu, findAllMenu } from '@/api/categoryMenu'
import { findAll } from '@/api/role'
import { formatDate } from '@/utils/webUtils'
export default {
  data() {
    return {
      currentPage: 1, // 当前页
      pageSize: 5, // 每页大小
      total: 0, // 总条目数
      pageCount: 0, // 总页数
      keyword: '', // 搜索值
      menuType: false, // 前后台
      tableData: [], // table数据
      title: '', // 模态框标题
      dialogVisible: false, // 模态框是否可见
      roles: [], // 角色
      Menu: [],
      form: {
        menuTitle: '',
        menuIcon: '',
        menuUrl: '',
        component: '',
        menuId: '',
        redirect: '',
        sort: 0,
        isShow: true,
        affix: false,
        menuType: true,
        roles: []
      },
      rules: { // 表单校验规则
        menuTitle: [
          { required: true, message: '菜单名称不能为空', trigger: 'blur' }
        ],
        menuIcon: [
          { required: true, message: '图标不能为空', trigger: 'blur' }
        ],
        menuUrl: [
          { required: true, message: '地址不能为空', trigger: 'blur' },
          { pattern: /^\/.*/, message: `地址必须以\/开头` }
        ],
        component: [
          { required: true, message: '组件不能为空', trigger: 'blur' }
        ],
        roles: [
          { required: true, message: '角色不能为空', trigger: 'change' }
        ],
        redirect: [
          { pattern: /^\/.*/, message: `地址必须以\/开头` }
        ]
      }
    }
  },
  computed: {
    // 计算属性 根据菜单节点计算排序选择的最大长度
    // eslint-disable-next-line vue/return-in-computed-property
    sort: function() {
      // eslint-disable-next-line no-constant-condition
      if (this.form.menuId === '' || this.form.menuId === null || this.form.menuId === 'null' || this.form.menuId === 'undefined' || this.form.menuId === undefined) {
        return this.Menu.length
      } else {
        for (const row of this.Menu) {
          if (this.form.menuId === row.id) {
            return row.children.length
          }
        }
      }
    }
  },
  created() {
    this.getMenusList()
    this.findAllRoles()
  },
  methods: {
    findAllMenu() {
      findAllMenu().then(response => {
        this.Menu = response.data
      })
    },
    // 初始化form数据
    initForm() {
      this.form = {
        menuTitle: '',
        menuIcon: '',
        menuUrl: '',
        component: '',
        menuId: '',
        redirect: '',
        sort: 0,
        isShow: true,
        affix: false,
        menuType: true,
        roles: []
      }
    },
    // 获取角色
    findAllRoles() {
      findAll().then(response => {
        const { data } = response
        this.roles = data
      })
    },
    // 打开修改模态框 初始化数据
    updateMenu(row) {
      this.title = '修改' + row.menuTitle
      this.form = {
        menuTitle: row.menuTitle,
        menuIcon: row.menuIcon,
        menuUrl: row.menuUrl,
        component: row.component,
        menuId: row.menuId === undefined ? '' : row.menuId,
        redirect: row.redirect === undefined ? '' : row.redirect,
        sort: row.sort,
        isShow: !row.isShow,
        affix: row.affix,
        menuType: !row.menuType,
        roles: []
      }
      for (const role of row.roles) {
        this.form.roles.push(role.id)
      }
      this.findAllMenu()
      this.dialogVisible = true
      this.form['id'] = row.id
    },
    // 删除
    deleteMenu(row) {
      if (row.children !== undefined) {
        if (row.children.length > 0) {
          this.$message.error('父组件不可以直接删除')
          return false
        }
      }
      this.$confirm('此操作将永久删除该文件, 是否继续?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        var params = new URLSearchParams()
        params.append('id', row.id)
        deleteMenu(params).then(response => {
          this.$message({
            type: 'success',
            message: response.data
          })
          this.getMenusList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 表单提交
    submitForm(form) {
      this.$refs[form].validate((valid) => {
        // 校验规则
        if (valid) {
          if (this.form.roles.length <= 0) {
            this.$message({
              showClose: true,
              message: '角色是必选项哦~',
              type: 'error'
            })
            return false
          }
          if (this.form.menuUrl === this.form.redirect) {
            this.$message({
              showClose: true,
              message: '不能转发自己哦~',
              type: 'error'
            })
            return false
          }
          this.form.component = this.form.component === '' ? 'Layout' : this.form.component
          var params = new URLSearchParams(this.form)
          if (this.title === '添加菜单') {
            addMenu(params).then(response => {
              const { data } = response
              this.initForm()
              this.dialogVisible = false
              this.$message({
                message: data,
                type: 'success'
              })
              this.getMenusList()
            })
          } else {
            updateMenu(params).then(response => {
              this.$message({
                type: 'success',
                message: response.data
              })
              this.initForm()
              this.dialogVisible = false
              delete this.form['id']
              this.getMenusList()
            })
          }
        } else {
          return false
        }
      })
    },
    // 关闭模态框
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.initForm()
          this.dialogVisible = false
          done()
        })
        .catch(_ => {})
    },
    // 打开添加模态框
    openDialog(title) {
      this.title = title
      this.findAllMenu()
      this.dialogVisible = true
      // findAll().then(response => {
      //   const { data } = response
      //   console.log(data)
      //   this.roles = data
      // })
    },
    // 处理分页
    handleCurrentChange(val) {
      this.currentPage = val
      this.getMenusList()
    },
    // 获取菜单数据
    getMenusList() {
      if (this.menuType === '' || null) {
        this.$message({
          message: '类型必须选择，默认后台类型',
          type: 'warning'
        })
        this.menuType = false
      }
      const params = {}
      params.keyword = this.keyword
      params.menuType = this.menuType
      params.currentPage = this.currentPage
      params.pageSize = this.pageSize
      getMenus(params).then(response => {
        const { data } = response
        console.log(data)
        this.currentPage = data.current
        this.pageSize = data.size
        this.total = data.total
        this.pageCount = data.pages
        this.tableData = data.records
      })
    },
    // 格式化日期
    dateFormat: function(row, column) {
      var date = new Date(row[column.property])
      if (date === undefined) {
        return ''
      }
      return formatDate(date, 'YYYY-MM-DD hh:mm:ss')
    }
  }
}
</script>

<style>
  .authority-menu{
      margin: 10px 15px 0;
  }
</style>
