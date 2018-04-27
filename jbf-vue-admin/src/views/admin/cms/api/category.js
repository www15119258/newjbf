/**
 * Created by lihui on 2018/3/31.
 */
import axios from 'axios'
import qs from 'qs'
import config from '@/config'

export function queryCategoryTree () {
  return axios.get(config.system.server_path + '/cms/category/queryTree')
}

export function queryCategoryList () {
  return axios.get(config.system.server_path + '/cms/category/queryList')
}

export function addCategory (category) {
  return axios.post(config.system.server_path + '/cms/category/save', qs.stringify(category))
}

export function updateCategory (category) {
  return axios.put(config.system.server_path + '/cms/category/update', qs.stringify(category))
}

export function deleteCategory (id) {
  return axios.delete(config.system.server_path + '/cms/category/delete/' + id)
}

export function queryCategoryById (id) {
  return axios.get(config.system.server_path + '/cms/category/queryById/' + id)
}
