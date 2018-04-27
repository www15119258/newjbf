/**
 * Created by lihui on 2018/3/31.
 */
import axios from 'axios'
import qs from 'qs'
import config from '@/config'

export function queryRoleTree (type) {
  return axios.get(config.system.server_path + '/security/role/queryTree/' + type)
}

export function addRole (role) {
  return axios.post(config.system.server_path + '/security/role/save', qs.stringify(role))
}

export function addUser (role, user) {
  return axios.post(config.system.server_path + '/security/role/addUser', qs.stringify({'role': role, 'user': user}))
}

export function updateRole (role) {
  return axios.put(config.system.server_path + '/security/role/update', qs.stringify(role))
}

export function assignMenus (role, type, menus) {
  return axios.post(config.system.server_path + '/security/role/assignMenus', qs.stringify({'role': role, 'type': type, 'menus': menus}))
}

export function assignModules (role, modules) {
  return axios.post(config.system.server_path + '/security/role/assignModules', qs.stringify({'role': role, 'modules': modules}))
}

export function deleteRole (id) {
  return axios.delete(config.system.server_path + '/security/role/delete/' + id)
}

export function removeUser (role, user) {
  return axios.delete(config.system.server_path + '/security/role/removeUser', {params: {'role': role, 'user': user}})
}

export function queryRoleById (id) {
  return axios.get(config.system.server_path + '/security/role/queryById/' + id)
}

export function queryMenus (id) {
  return axios.get(config.system.server_path + '/security/role/queryMenus/' + id)
}

export function queryModules (id) {
  return axios.get(config.system.server_path + '/security/role/queryModules/' + id)
}

export function queryUsers (role) {
  return axios.get(config.system.server_path + '/security/role/queryUsers', {params: role})
}

export function queryUnSelectUsers (form) {
  return axios.get(config.system.server_path + '/security/role/queryUnSelectUsers', {params: form})
}
