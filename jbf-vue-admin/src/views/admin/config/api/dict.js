/**
 * Created by lihui on 2018/4/2.
 */
import axios from 'axios'
import qs from 'qs'
import config from '@/config'

export function queryDict (dict) {
  return axios.get(config.system.server_path + '/config/sysDict/query', {params: dict})
}

export function queryDictTypes () {
  return axios.get(config.system.server_path + '/config/sysDict/queryTypes')
}

export function queryListByType (type) {
  return axios.get(config.system.server_path + '/config/sysDict/queryListByType/' + type)
}

export function addDict (dict) {
  return axios.post(config.system.server_path + '/config/sysDict/save', qs.stringify(dict))
}

export function updateDict (dict) {
  return axios.put(config.system.server_path + '/config/sysDict/update', qs.stringify(dict))
}

export function deleteDict (id) {
  return axios.delete(config.system.server_path + '/config/sysDict/delete/' + id)
}
