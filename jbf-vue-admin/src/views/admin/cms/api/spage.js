/**
 * Created by lihui on 2018/4/2.
 */
import axios from 'axios'
import qs from 'qs'
import config from '@/config'

export function querySpage (spage) {
  return axios.get(config.system.server_path + '/cms/spage/query', {params: spage})
}

export function querySpageById (id) {
  return axios.get(config.system.server_path + '/cms/spage/queryById/' + id)
}

export function addSpage (spage) {
  return axios.post(config.system.server_path + '/cms/spage/save', qs.stringify(spage))
}

export function updateSpage (spage) {
  return axios.put(config.system.server_path + '/cms/spage/update', qs.stringify(spage))
}

export function deleteSpage (id) {
  return axios.delete(config.system.server_path + '/cms/spage/delete/' + id)
}
