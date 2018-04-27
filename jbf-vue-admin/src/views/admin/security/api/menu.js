/**
 * Created by lihui on 2018/3/31.
 */
import axios from 'axios'
import qs from 'qs'
import config from '@/config'

export function queryMenuTree (type) {
  return axios.get(config.system.server_path + '/security/menu/queryTree/' + type)
}

export function addMenu (menu) {
  return axios.post(config.system.server_path + '/security/menu/save', qs.stringify(menu))
}

export function updateMenu (menu) {
  return axios.put(config.system.server_path + '/security/menu/update', qs.stringify(menu))
}

export function deleteMenu (id) {
  return axios.delete(config.system.server_path + '/security/menu/delete/' + id)
}

export function queryMenuById (id) {
  return axios.get(config.system.server_path + '/security/menu/queryById/' + id)
}

export function queryRoles (id) {
  return axios.get(config.system.server_path + '/security/menu/queryRoles/' + id)
}

export function assignRoles (menu, type, roles) {
  return axios.post(config.system.server_path + '/security/menu/assignRoles', qs.stringify({'menu': menu, 'type': type, 'roles': roles}))
}
