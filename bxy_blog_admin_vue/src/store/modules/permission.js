import { asyncRoutes, constantRoutes, errorRoutes } from '@/router'
import { getToken } from '@/utils/auth'
import { getMenus } from '@/api/user'
import Layout from '@/layout'

export function generaMenu(routes, data) {
  data.forEach(item => {
    const menu = {
      path: item.menuUrl,
      component: item.component === 'Layout' ? Layout : loadView(item.component),
      redirect: item.redirect,
      hidden: item.isShow,
      children: [],
      name: 'menu_' + item.id,
      meta: { title: item.menuTitle, icon: item.menuIcon, affix: item.affix }
    }
    if (item.children) {
      generaMenu(menu.children, item.children)
    } else {
      delete menu.children
    }
    if (!item.redirect || item.redirect === null || item.redirect === 'null') {
      delete menu.redirect
    }
    routes.push(menu)
  })
}

export const loadView = (view) => { // 路由懒加载
  // 使用webpack打包import导入必须指定组件的路径，不能使用变量，没有解决
  return resolve => require([`@/views/${view}`], resolve)
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes.concat(errorRoutes)
    state.routes = constantRoutes.concat(routes).concat(errorRoutes)
  }
}

const actions = {
  generateRoutes({ commit }, roles) {
    return new Promise(resolve => {
      const loadMenuData = []
      getMenus(getToken()).then(response => {
        if (response.code !== 200) {
          // eslint-disable-next-line no-undef
          Message({
            message: '菜单数据加载异常',
            type: 0,
            duration: 2 * 1000
          })
        } else {
          const data = response.data
          Object.assign(loadMenuData, data)
          generaMenu(asyncRoutes, loadMenuData)
          const accessedRoutes = asyncRoutes
          commit('SET_ROUTES', accessedRoutes)
          resolve(accessedRoutes)
        }
      }).catch(error => {
        console.log(error)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
