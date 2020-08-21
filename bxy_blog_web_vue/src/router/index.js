import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '@/views/Home.vue'
import Articles from '@/views/Articles.vue'
import PlaceOnFile from '@/views/PlaceOnFile.vue'
import Sort from '@/views/Sort.vue'
import Tag from '@/views/Tag.vue'
import AboutMe from '@/views/AboutMe.vue'
import store from '@/store'

Vue.use(VueRouter)

import Layout from '@/layout'

  const routes = [
    {
      path: '/',
      component: Layout,
      // redirect: '/',
      children: [
        {
          path: '/',
          component: Home,
          name: 'Home',
          meta: { title: '首页'}
        }
      ]
    },
    {
      path: '/article/:id',
      component: Layout,
      // redirect: '/',
      children: [
        {
          path: '/',
          component: Articles,
          name: 'article',
          meta: { title: '文章'}
        }
      ]
    },
    {
      path: '/placeOnFile',
      component: Layout,
      // redirect: '/',
      children: [
        {
          path: '/',
          component: PlaceOnFile,
          name: 'PlaceOnFile',
          meta: { title: '归档'}
        }
      ]
    },
    {
      path: '/sort',
      component: Layout,
      // redirect: '/',
      children: [
        {
          path: '/',
          component: Sort,
          name: 'Sort',
          meta: { title: '分类'}
        }
      ]
    },
    {
      path: '/tag',
      component: Layout,
      // redirect: '/',
      children: [
        {
          path: '/',
          component: Tag,
          name: 'Tag',
          meta: { title: '标签'}
        }
      ]
    },
    {
      path: '/aboutMe',
      component: Layout,
      // redirect: '/',
      children: [
        {
          path: '/',
          component: AboutMe,
          name: 'AboutMe',
          meta: { title: '关于我'}
        }
      ]
    }
  ]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
router.beforeEach((to, from, next) => {
  let title = 'Gblog'
  if (to.meta.params){
      title = `${to.meta.title}:${to.params[to.meta.params] || ''} - ${title}`
  }else {
      title = `${to.meta.title} - ${title}`
  }
  document.title = title
  if (to.path !== from.path) {
      store.dispatch('setLoading', true);
  }
  next();
})
router.afterEach(() => {
  // 最多延迟 关闭 loading
  setTimeout(() => {
      store.dispatch('setLoading', false);
  }, 1500)
})
export default router
