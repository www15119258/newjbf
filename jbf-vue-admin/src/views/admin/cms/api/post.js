/**
 * Created by lihui on 2018/4/2.
 */
import axios from 'axios'
import qs from 'qs'
import config from '@/config'

export function queryPost (post) {
  return axios.get(config.system.server_path + '/cms/post/query', {params: post})
}

export function queryPostById (id) {
  return axios.get(config.system.server_path + '/cms/post/queryById/' + id)
}

export function addPost (post) {
  return axios.post(config.system.server_path + '/cms/post/save', qs.stringify(post))
}

export function updatePost (post) {
  return axios.put(config.system.server_path + '/cms/post/update', qs.stringify(post))
}

export function deletePost (id) {
  return axios.delete(config.system.server_path + '/cms/post/delete/' + id)
}

export function queryCategorys (id) {
  return axios.delete(config.system.server_path + '/cms/post/queryCategorys/' + id)
}
